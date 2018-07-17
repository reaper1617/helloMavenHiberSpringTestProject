package com.gerasimchuk.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {


    private AuthenticationManager authenticationManager;
    private UserDetailsService userDetailsService;
    private BCryptPasswordEncoder encoder;

    @Autowired
    public LoginServiceImpl(UserDetailsService userDetailsService, AuthenticationManager authenticationManager, BCryptPasswordEncoder encoder) {
        this.userDetailsService = userDetailsService;
        this.authenticationManager = authenticationManager;
        this.encoder = encoder;
    }

    @Override
    public boolean login(String fullUserName, String password){
        UserDetails userDetails = userDetailsService.loadUserByUsername(fullUserName);
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userDetails, encoder.encode(password), userDetails.getAuthorities());


        Authentication auth = authenticationManager.authenticate(authenticationToken);
        if (authenticationToken.isAuthenticated()){
            SecurityContextHolder.getContext().setAuthentication(auth);
            return true;
        }
        else return false;

    }

    @Override
    public boolean logout(String fullUserName){
        if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            SecurityContextHolder.getContext().getAuthentication().setAuthenticated(false);
            return true;
        }
        return false;
    }
//    public DaoAuthenticationProvider authProvider() {
//        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
//        authProvider.setUserDetailsService(userDetailsService);
//        authProvider.setPasswordEncoder(encoder);
//        return authProvider;
//    }

}
