package kz.iitu.se242m.yesniyazova.entity;

import java.util.List;

public record WeatherHistoryResponse(
        List<SamplePoint> temp,
        List<SamplePoint> hum,
        List<SamplePoint> press,
        List<SamplePoint> wind
) {}
