package kz.iitu.se242m.yesniyazova.repository;

import kz.iitu.se242m.yesniyazova.entity.SamplePoint;

import java.time.Instant;
import java.util.List;

public interface WeatherSampleRepositoryCustom {
    List<SamplePoint> findWeatherHistory(Long cityId, Instant from, Instant to, String metric);
}
