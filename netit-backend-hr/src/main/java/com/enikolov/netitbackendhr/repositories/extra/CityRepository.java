package com.enikolov.netitbackendhr.repositories.extra;

import com.enikolov.netitbackendhr.models.extra.City;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CityRepository extends JpaRepository<City, Integer> {

    public List<City> findAllByOrderByIdAsc();
}
