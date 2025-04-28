package kz.iitu.se242m.yesniyazova.entity.dto;

import lombok.Data;

import java.time.Instant;

@Data
public class AirFilterDto {
    Long cityId;
    Instant from;
    Instant to;
    String pollutant;
}
