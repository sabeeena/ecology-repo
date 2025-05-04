package kz.iitu.se242m.yesniyazova.repository.impl;

import jakarta.persistence.EntityManager;
import kz.iitu.se242m.yesniyazova.entity.SamplePoint;
import kz.iitu.se242m.yesniyazova.repository.WeatherSampleRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@Repository
public class WeatherSampleRepositoryImpl implements WeatherSampleRepositoryCustom {

    @Autowired
    private EntityManager entityManager;

    private static final Map<String,String> COL = Map.of(
            "temperature","temperature",
            "humidity",   "humidity",
            "pressure",   "pressure",
            "wind_speed", "wind_speed"
    );

    @Override
    public List<SamplePoint> findWeatherHistory(Long cityId, Instant from, Instant to, String metric) {
        String col = COL.get(metric);
        if (col == null) throw new IllegalArgumentException("Bad metric: " + metric);

        StringBuilder sql = new StringBuilder(
                "SELECT ts, " + col + " FROM weather_sample WHERE 1=1 ");

        if (cityId != null) sql.append("AND city_id = :id ");
        if (from   != null) sql.append("AND ts >= :f ");
        if (to     != null) sql.append("AND ts <= :t ");

        sql.append("ORDER BY ts ASC");

        var q = entityManager.createNativeQuery(sql.toString());
        if (cityId != null) q.setParameter("id", cityId);
        if (from   != null) q.setParameter("f",  from);
        if (to     != null) q.setParameter("t",  to);

        @SuppressWarnings("unchecked")
        List<Object[]> rows = q.getResultList();

        return rows.stream()
                .map(r -> new SamplePoint((Instant) r[0], toDouble(r[1])))
                .toList();
    }

    private static Double toDouble(Object o) {
        if (o == null) return null;
        return (o instanceof Number n) ? n.doubleValue()
                : Double.valueOf(o.toString());
    }
}
