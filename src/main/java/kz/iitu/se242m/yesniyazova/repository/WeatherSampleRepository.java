package kz.iitu.se242m.yesniyazova.repository;

import kz.iitu.se242m.yesniyazova.entity.WeatherSample;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeatherSampleRepository extends JpaRepository<WeatherSample, Long>, WeatherSampleRepositoryCustom {
}

