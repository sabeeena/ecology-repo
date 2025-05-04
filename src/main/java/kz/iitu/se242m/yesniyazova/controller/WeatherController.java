package kz.iitu.se242m.yesniyazova.controller;

import kz.iitu.se242m.yesniyazova.entity.WeatherHistoryResponse;
import kz.iitu.se242m.yesniyazova.entity.dto.WeatherFilterDto;
import kz.iitu.se242m.yesniyazova.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/weather")
public class WeatherController {

    @Autowired
    private WeatherService weatherService;

    @PostMapping("dashboard")
    public WeatherHistoryResponse findAllByFilter(@RequestBody WeatherFilterDto filter) {
        return weatherService.history(filter);
    }
}
