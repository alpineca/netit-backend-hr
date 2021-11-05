package com.enikolov.netitbackendhr.models.DTO;

import com.enikolov.netitbackendhr.enums.UserRole;
import com.enikolov.netitbackendhr.models.users.User;

public class UserDTO {

    private String username;
    private String password;
    private String matchingPassword;
    private String mail;
    private UserRole userRole;

    public UserDTO(){

    }

    public User createUserEntity(){
        User entity = new User();
        entity.setMail(this.mail);
        entity.setUsername(this.username);
        entity.setPassword(this.password);
        entity.setUserRole(this.userRole);

        return entity;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMatchingPassword() {
        return matchingPassword;
    }

    public void setMatchingPassword(String matchingPassword) {
        this.matchingPassword = matchingPassword;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }
}
