package kz.iitu.se242m.yesniyazova.service;

import kz.iitu.se242m.yesniyazova.entity.WeatherHistoryResponse;
import kz.iitu.se242m.yesniyazova.entity.dto.WeatherFilterDto;

public interface WeatherService {
    void pullCurrent();
    long countSamples();
    WeatherHistoryResponse history(WeatherFilterDto filterDto);
}
