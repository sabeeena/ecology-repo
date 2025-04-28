package kz.iitu.se242m.yesniyazova.repository;

import kz.iitu.se242m.yesniyazova.entity.SamplePoint;

import java.time.Instant;
import java.util.List;

public interface AirSampleRepositoryCustom {
    List<SamplePoint> findPollutantHistory(Long cityId, Instant from, Instant to, String pollutant);
}