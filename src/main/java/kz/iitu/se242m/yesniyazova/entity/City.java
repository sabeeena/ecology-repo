package kz.iitu.se242m.yesniyazova.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "city")
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String name;
    double lat, lon;
    public City() {}
    public City(String name, double lat, double lon) {
        this.name = name;
        this.lat = lat;
        this.lon = lon;
    }
}