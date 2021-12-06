package com.enikolov.netitbackendhr.services.data;

import com.enikolov.netitbackendhr.models.extra.City;
import com.enikolov.netitbackendhr.repositories.extra.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CityDataService {
    @Autowired
    private CityRepository cityRepository;

    public List<City> getAllCities(){
        return this.cityRepository.findAll();
    }

}
