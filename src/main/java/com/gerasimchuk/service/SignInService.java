package com.gerasimchuk.service;

import com.gerasimchuk.dto.UserDTO;
import com.gerasimchuk.entities.User;


public interface SignInService {

    UserDTO validate(UserDTO u);
    User signIn(UserDTO u);


}
