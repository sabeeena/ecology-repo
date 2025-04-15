package kz.iitu.se242m.yesniyazova.repository;

import kz.iitu.se242m.yesniyazova.entity.AirSample;
import kz.iitu.se242m.yesniyazova.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AirSampleRepository extends JpaRepository<AirSample, Long> {
    Optional<AirSample> findTopByCityOrderByTsDesc(City city);
}
