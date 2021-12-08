package com.enikolov.netitbackendhr.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {
    
    @Autowired
    private AutenticationProvider autenticationProvider;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(this.autenticationProvider.provide());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        

        http.authorizeRequests()
            .antMatchers("/style/**").permitAll()
            .antMatchers("/images/**").permitAll()
            .antMatchers("/").permitAll()
            .antMatchers("/register").anonymous()
            .antMatchers("/finish-registration").anonymous()
            .anyRequest().authenticated()
            .and()
                .formLogin().loginPage("/login").usernameParameter("username")
                .permitAll().defaultSuccessUrl("/user-dispatch")
            .and()
                .logout().permitAll()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/")
                .and()
                .exceptionHandling().accessDeniedPage("/");
    }

    

}
