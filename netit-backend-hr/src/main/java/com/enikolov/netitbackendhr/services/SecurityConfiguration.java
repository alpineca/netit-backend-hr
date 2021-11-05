package com.enikolov.netitbackendhr.services;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // http/localhost:8181/products/models
        http.authorizeRequests().antMatchers("/").permitAll();

        http.csrf().disable();
    }
}