package kz.iitu.se242m.yesniyazova.service;

import kz.iitu.se242m.yesniyazova.entity.AirSample;

import java.util.List;

public interface AirQualityService {
    void pullCurrent();
    void backfillHistory();
    List<AirSample> latestByCity(Long id);
}
