package com.enikolov.netitbackendhr.models.DTO;

import com.enikolov.netitbackendhr.enums.UserRole;
import com.enikolov.netitbackendhr.models.users.User;

public class UserDTO {

    // private int id
    private String username;
    private String password;
    private String confirmPassword;
    private String mail;
    private String userRole;

    public UserDTO(){

    }

    public User createUserEntity(){
        User entity = new User();
        entity.setMail(     this.mail);
        entity.setUsername( this.username);
        entity.setPassword( this.password);
        entity.setUserRole( this.userRole);

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

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }
}
