package kz.iitu.se242m.yesniyazova.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;

@Entity
@Data
@Table(name = "air_sample")
public class AirSample {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private City city;

    private Instant ts;
    private int aqi;

    private Double co;
    private Double no;
    private Double no2;
    private Double o3;
    private Double so2;
    private Double pm2_5;
    private Double pm10;
    private Double nh3;

    public void setComponent(String key, Double value) {
        switch (key) {
            case "co" -> this.co = value;
            case "no" -> this.no = value;
            case "no2" -> this.no2 = value;
            case "o3" -> this.o3 = value;
            case "so2" -> this.so2 = value;
            case "pm2_5" -> this.pm2_5 = value;
            case "pm10" -> this.pm10 = value;
            case "nh3" -> this.nh3 = value;
        }
    }
}