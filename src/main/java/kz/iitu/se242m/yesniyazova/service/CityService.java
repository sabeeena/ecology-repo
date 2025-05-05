package kz.iitu.se242m.yesniyazova.service;

import kz.iitu.se242m.yesniyazova.entity.City;

import java.util.List;

public interface CityService {
    List<City> listAllCities();
    City findNearestCity(double lat, double lon);
}
