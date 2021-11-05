package com.enikolov.netitbackendhr.models.extra.infoContent;

import javax.persistence.*;

@Entity
@Table(name = "tm_cities")
public class City {

    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String cityName;
}
