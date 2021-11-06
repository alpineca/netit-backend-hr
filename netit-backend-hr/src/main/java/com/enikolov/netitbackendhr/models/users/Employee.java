package com.enikolov.netitbackendhr.models.users;

import com.enikolov.netitbackendhr.models.extra.infoContent.Category;
import com.enikolov.netitbackendhr.models.extra.Contacts;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "td_employees")
public class Employee {

    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String firstName;
    private String lastName;
    @OneToOne
    private User user;
    private int age;

    @OneToOne
    private Contacts contacts;
    @ManyToMany
    @JoinTable(
            name                = "tc_employee_interests",
            joinColumns         = @JoinColumn(name = "employee_id"),
            inverseJoinColumns  = @JoinColumn(name = "category_id")
    )
    private List<Category> interestedCategories;

}
