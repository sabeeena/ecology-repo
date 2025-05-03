package kz.iitu.se242m.yesniyazova.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;

@Entity
@Data
@Table(name = "weather_sample")
public class WeatherSample {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private City city;

    private Instant ts;
    private double temperature;
    private int humidity;
    private int pressure;
    private double windSpeed;
    private String windDirection;
    private String cloudCoverage;
    private String weatherDescription;

}
