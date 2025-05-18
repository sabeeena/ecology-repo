package kz.iitu.se242m.yesniyazova.service.impl;

import kz.iitu.se242m.yesniyazova.entity.SamplePoint;
import kz.iitu.se242m.yesniyazova.entity.WeatherHistoryResponse;
import kz.iitu.se242m.yesniyazova.entity.dto.AirFilterDto;
import kz.iitu.se242m.yesniyazova.entity.dto.WeatherFilterDto;
import kz.iitu.se242m.yesniyazova.service.AirQualityService;
import kz.iitu.se242m.yesniyazova.service.ReportBuilderFactory;
import kz.iitu.se242m.yesniyazova.service.ReportDocService;
import kz.iitu.se242m.yesniyazova.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportDocServiceImpl implements ReportDocService {
    private final AirQualityService airService;
    private final WeatherService weatherService;
    private final ReportBuilderFactory docFactory;

    public byte[] buildWeatherDocument(WeatherFilterDto f, String type) {
        WeatherHistoryResponse data = weatherService.history(f);
        return docFactory.make(type).weatherReport(data, f);
    }

    public byte[] buildAirDocument(AirFilterDto f, String type) {
        List<SamplePoint> data = airService.findByFilter(f);
        return docFactory.make(type).airReport(data, f);
    }
}
