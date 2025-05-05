package kz.iitu.se242m.yesniyazova.controller;

import kz.iitu.se242m.yesniyazova.entity.City;
import kz.iitu.se242m.yesniyazova.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/find-city")
    public City getNearestCity(@RequestParam double lat, @RequestParam double lon) {
        return cityService.findNearestCity(lat, lon);
    }
}
