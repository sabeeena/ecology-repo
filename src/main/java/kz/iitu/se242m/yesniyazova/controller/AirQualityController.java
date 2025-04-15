package kz.iitu.se242m.yesniyazova.controller;

import kz.iitu.se242m.yesniyazova.entity.AirSample;
import kz.iitu.se242m.yesniyazova.service.AirQualityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/air")
public class AirQualityController {

    @Autowired
    private AirQualityService airQualityService;
    public AirQualityController(AirQualityService airQualityService) {
        this.airQualityService = airQualityService;
    }

    @GetMapping("{cityId}/latest")
    public List<AirSample> latest(@PathVariable Long cityId) {
        return airQualityService.latestByCity(cityId);
    }
}
