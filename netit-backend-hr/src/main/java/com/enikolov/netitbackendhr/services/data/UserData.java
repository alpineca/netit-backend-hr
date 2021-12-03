package com.enikolov.netitbackendhr.services.data;

import com.enikolov.netitbackendhr.models.users.Employer;
import com.enikolov.netitbackendhr.models.users.User;
import com.enikolov.netitbackendhr.repositories.users.EmployerRepository;
import com.enikolov.netitbackendhr.services.auth.UserLogin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserData {

    @Autowired
    private EmployerRepository employerRepository;

    public User getLoggedUser(){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserLogin userLoginModel = (UserLogin) auth.getPrincipal();
        

        return userLoginModel.getUser();
    }

    public Employer getLoggedEmployer(){
        int userId =  this.getLoggedUser().getId();

        Optional<Employer> employerModel = this.employerRepository.findEmployerByUserId(userId);

        if(employerModel.isPresent()){
            return employerModel.get();
        }

        return null;
    }
    
}
