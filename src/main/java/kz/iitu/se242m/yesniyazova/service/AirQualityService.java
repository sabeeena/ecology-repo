package kz.iitu.se242m.yesniyazova.service;

import kz.iitu.se242m.yesniyazova.entity.AirSample;
import kz.iitu.se242m.yesniyazova.entity.SamplePoint;
import kz.iitu.se242m.yesniyazova.entity.dto.AirFilterDto;

import java.util.List;

public interface AirQualityService {
    void pullCurrent();
    void backfillHistory();
    List<AirSample> latestByCity(Long id);
    List<SamplePoint> findByFilter(AirFilterDto filter);
    long countSamples();
}
