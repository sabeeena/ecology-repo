package kz.iitu.se242m.yesniyazova.repository;

import kz.iitu.se242m.yesniyazova.entity.City;
import kz.iitu.se242m.yesniyazova.entity.FireSample;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FireSampleRepository extends JpaRepository<FireSample, Long> {
    Optional<FireSample> findTopByCityOrderByTsDesc(City city);
}
