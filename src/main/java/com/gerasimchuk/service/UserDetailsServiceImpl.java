package com.gerasimchuk.service;

import com.gerasimchuk.dao.UserDAO;
import com.gerasimchuk.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {


    private UserDAO userDAO;

    @Autowired
    public UserDetailsServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public UserDetails loadUserByUsername(String fullUserName) throws UsernameNotFoundException {
        // TODO: validate string!!!!
        String[] strings = fullUserName.split(" ");
        String name = strings[0];
        String middleName = strings[1];
        String lastName = strings[2];

        User user = userDAO.getByFullName(name, middleName, lastName);

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(user.getRole().name()));

        return new org.springframework.security.core.userdetails.User(fullUserName,user.getPassword(),grantedAuthorities);
    }
}
