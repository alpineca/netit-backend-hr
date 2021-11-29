package com.enikolov.netitbackendhr.config;

import com.enikolov.netitbackendhr.services.auth.UserLoginService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AutenticationProvider {

    @Autowired
    private UserLoginService userLoginService;

    public DaoAuthenticationProvider provide(){
        
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(this.userLoginService);
        provider.setPasswordEncoder(new BCryptPasswordEncoder());
        
        return provider;
    }
    
}
