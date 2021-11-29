package com.enikolov.netitbackendhr.models.general;

import com.enikolov.netitbackendhr.models.extra.infoContent.Benefits;
import com.enikolov.netitbackendhr.models.extra.infoContent.Category;
import com.enikolov.netitbackendhr.models.extra.Contacts;
import com.enikolov.netitbackendhr.models.users.User;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "td_employers")
public class Employer {

    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private int rating;
    private int employees;
    private String name;
    private String information;
    @ManyToOne
    private User user;

    @OneToOne
    private Contacts contacts;

    @ManyToMany
    private List<Benefits> benefits;

    @ManyToMany
    private List<Category> categories;

    @OneToMany(mappedBy = "employer")
    private List<Campaign> campaigns;
}
