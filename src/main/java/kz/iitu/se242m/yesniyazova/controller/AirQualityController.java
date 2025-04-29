package kz.iitu.se242m.yesniyazova.controller;

import kz.iitu.se242m.yesniyazova.entity.AirSample;
import kz.iitu.se242m.yesniyazova.entity.SamplePoint;
import kz.iitu.se242m.yesniyazova.entity.dto.AirFilterDto;
import kz.iitu.se242m.yesniyazova.service.AirQualityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("dashboard")
    public List<SamplePoint> findAllByFilter(@RequestBody AirFilterDto filter) {
        return airQualityService.findByFilter(filter);
    }
}
