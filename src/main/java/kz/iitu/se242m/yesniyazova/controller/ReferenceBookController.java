package kz.iitu.se242m.yesniyazova.controller;

import kz.iitu.se242m.yesniyazova.entity.City;
import kz.iitu.se242m.yesniyazova.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/reference")
public class ReferenceBookController {

    @Autowired
    private CityService cityService;

    @GetMapping("/cities")
    public List<City> getAllCitiesRef() {
        return cityService.listAllCities();
    }
}
