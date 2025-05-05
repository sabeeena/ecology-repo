package kz.iitu.se242m.yesniyazova.repository;

import kz.iitu.se242m.yesniyazova.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {
    @Query(value = """
        SELECT *, (
            6371 * acos(
                cos(radians(:lat)) * cos(radians(c.lat)) *
                cos(radians(c.lon) - radians(:lon)) +
                sin(radians(:lat)) * sin(radians(c.lat))
            )
        ) AS distance
        FROM city c
        ORDER BY distance ASC
        LIMIT 1
        """, nativeQuery = true)
    City findNearestCity(@Param("lat") double lat, @Param("lon") double lon);
}
