package com.enikolov.netitbackendhr.services.data;

import com.enikolov.netitbackendhr.models.users.User;
import com.enikolov.netitbackendhr.services.auth.UserLogin;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class UserData {

    public User getLoggedUser(){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserLogin userLoginModel = (UserLogin) auth.getPrincipal();
        

        return userLoginModel.getUser();
    }
    
}
