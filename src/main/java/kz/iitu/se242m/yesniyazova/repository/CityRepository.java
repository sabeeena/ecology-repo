package kz.iitu.se242m.yesniyazova.repository;

import kz.iitu.se242m.yesniyazova.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {
}
