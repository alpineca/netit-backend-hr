package com.enikolov.netitbackendhr.models.extra;

import javax.persistence.*;

@Entity
@Table(name = "tm_cities")
public class City {

    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String cityName;

    public City() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}
