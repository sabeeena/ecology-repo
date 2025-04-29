package kz.iitu.se242m.yesniyazova.service.impl;

import kz.iitu.se242m.yesniyazova.entity.City;
import kz.iitu.se242m.yesniyazova.repository.CityRepository;
import kz.iitu.se242m.yesniyazova.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityServiceImpl implements CityService {

    @Autowired
    private CityRepository cityRepository;

    public List<City> listAllCities() {
        return cityRepository.findAll();
    }
}
