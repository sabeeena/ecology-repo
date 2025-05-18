package kz.iitu.se242m.yesniyazova.service;

import kz.iitu.se242m.yesniyazova.entity.dto.AirFilterDto;
import kz.iitu.se242m.yesniyazova.entity.dto.WeatherFilterDto;

public interface ReportDocService {
    byte[] buildWeatherDocument(WeatherFilterDto f, String type);
    byte[] buildAirDocument(AirFilterDto f, String type);
}
