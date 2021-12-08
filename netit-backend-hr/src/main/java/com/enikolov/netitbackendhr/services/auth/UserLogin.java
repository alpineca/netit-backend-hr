package com.enikolov.netitbackendhr.services.auth;

import java.util.Collection;

import com.enikolov.netitbackendhr.models.users.User;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserLogin implements UserDetails {

    private User currentUser;

    public UserLogin(User user){
        this.currentUser = user;
    }

    public int getUserId(){
        return this.currentUser.getId();
    }

    public User getUser(){
        return this.currentUser;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getPassword() {
        return this.currentUser.getPassword();
    }

    @Override
    public String getUsername() {
        return this.currentUser.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isEnabled() {
        // TODO Auto-generated method stub
        return true;
    }

    
}
