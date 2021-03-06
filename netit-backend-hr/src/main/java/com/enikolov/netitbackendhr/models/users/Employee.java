package com.enikolov.netitbackendhr.models.users;

import com.enikolov.netitbackendhr.models.extra.Category;
import com.enikolov.netitbackendhr.models.extra.City;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "td_employees")
public class Employee {

    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int age;
    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;
    private String address;
    private String webAddress;
    private String telephone;
    private String socialMedia;

    @OneToOne
    private User user;

    @ManyToMany
    @JoinTable(
            name                = "tc_employee_interests",
            joinColumns         = @JoinColumn(name = "employee_id"),
            inverseJoinColumns  = @JoinColumn(name = "category_id")
    )
    private List<Category> interestedCategories;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getWebAddress() {
        return webAddress;
    }

    public void setWebAddress(String webAddress) {
        this.webAddress = webAddress;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getSocialMedia() {
        return socialMedia;
    }

    public void setSocialMedia(String socialMedia) {
        this.socialMedia = socialMedia;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Category> getInterestedCategories() {
        return interestedCategories;
    }

    public void setInterestedCategories(List<Category> interestedCategories) {
        this.interestedCategories = interestedCategories;
    }
}
