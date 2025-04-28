package kz.iitu.se242m.yesniyazova.repository.impl;

import jakarta.persistence.EntityManager;
import kz.iitu.se242m.yesniyazova.entity.SamplePoint;
import kz.iitu.se242m.yesniyazova.repository.AirSampleRepositoryCustom;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Set;

@Repository
@RequiredArgsConstructor
public class AirSampleRepositoryImpl implements AirSampleRepositoryCustom {

    private final EntityManager entityManager;

    public List<SamplePoint> findPollutantHistory(Long cityId, Instant from, Instant to, String pollutant) {
        Set<String> allowed = Set.of("co", "nh3", "no", "no2", "o3", "pm10", "pm2_5", "so2", "aqi");

        if (!allowed.contains(pollutant)) {
            throw new IllegalArgumentException("Invalid pollutant selected: " + pollutant);
        }

        StringBuilder sql = new StringBuilder("SELECT ts, " + pollutant + " FROM air_sample WHERE 1=1 ");

        if (cityId != null) sql.append("AND city_id = :cityId ");
        if (from != null)   sql.append("AND ts >= :from ");
        if (to != null)     sql.append("AND ts <= :to ");

        sql.append("ORDER BY ts ASC");

        var query = entityManager.createNativeQuery(sql.toString());

        if (cityId != null) query.setParameter("cityId", cityId);
        if (from != null)   query.setParameter("from", from);
        if (to != null)     query.setParameter("to", to);

        List<Object[]> rows = query.getResultList();

        return rows.stream()
                .map(r -> new SamplePoint((Instant) r[0], toDouble(r[1])))
                .toList();
    }

    private Double toDouble(Object o) {
        if (o == null) return null;
        if (o instanceof Number n) {
            return n.doubleValue();
        }
        throw new IllegalArgumentException("Expected a numeric value: " + o);
    }

}

