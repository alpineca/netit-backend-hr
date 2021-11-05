package com.enikolov.netitbackendhr.models.extra;

import com.enikolov.netitbackendhr.models.extra.infoContent.City;

import javax.persistence.*;

@Entity
@Table(name = "td_contacts")
public class Contacts {

    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String address;
    @ManyToOne
    private City city;
    private String phone;
    private String website;
    private String businessEmail;
}
