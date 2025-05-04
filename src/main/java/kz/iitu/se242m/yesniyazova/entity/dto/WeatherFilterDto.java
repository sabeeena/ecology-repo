package kz.iitu.se242m.yesniyazova.entity.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Data
@Getter
@Setter
public class WeatherFilterDto {
    Long cityId;
    Instant from;
    Instant to;
}
