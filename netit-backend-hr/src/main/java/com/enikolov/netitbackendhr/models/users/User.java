package com.enikolov.netitbackendhr.models.users;

import com.enikolov.netitbackendhr.enums.UserRole;
import com.enikolov.netitbackendhr.models.general.Employer;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="td_users")
public class User {

    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String username;

    private String password;

    private String mail;

    private UserRole userRole;

    @OneToMany(mappedBy = "user")
    private List<Employer> employers;

    public User(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
