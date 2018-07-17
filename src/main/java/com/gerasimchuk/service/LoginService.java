package com.gerasimchuk.service;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public interface LoginService {
    boolean login(String fullUserName, String password);

    boolean logout(String fullUserName);

}
