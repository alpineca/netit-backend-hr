package com.enikolov.netitbackendhr.models.users;

import com.enikolov.netitbackendhr.models.extra.infoContent.Category;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "td_employees")
public class Employee {

    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String fullName;
    private int age;
    // private City city;
    private String address;
    private String webAddress;

    @OneToOne
    private User user;

    // @OneToOne
    // private Contacts contacts;
    @ManyToMany
    @JoinTable(
            name                = "tc_employee_interests",
            joinColumns         = @JoinColumn(name = "employee_id"),
            inverseJoinColumns  = @JoinColumn(name = "category_id")
    )
    private List<Category> interestedCategories;

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the fullName
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * @param fullName the fullName to set
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     * @return the age
     */
    public int getAge() {
        return age;
    }

    /**
     * @param age the age to set
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return the webAddress
     */
    public String getWebAddress() {
        return webAddress;
    }

    /**
     * @param webAddress the webAddress to set
     */
    public void setWebAddress(String webAddress) {
        this.webAddress = webAddress;
    }

    /**
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * @return the interestedCategories
     */
    public List<Category> getInterestedCategories() {
        return interestedCategories;
    }

    /**
     * @param interestedCategories the interestedCategories to set
     */
    public void setInterestedCategories(List<Category> interestedCategories) {
        this.interestedCategories = interestedCategories;
    }

}
