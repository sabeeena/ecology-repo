package kz.iitu.se242m.yesniyazova.service;

import kz.iitu.se242m.yesniyazova.entity.SamplePoint;
import kz.iitu.se242m.yesniyazova.entity.WeatherHistoryResponse;
import kz.iitu.se242m.yesniyazova.entity.dto.AirFilterDto;
import kz.iitu.se242m.yesniyazova.entity.dto.WeatherFilterDto;

import java.util.List;

public interface ReportBuilder {

    byte[] weatherReport(WeatherHistoryResponse data, WeatherFilterDto filter);

    byte[] airReport(List<SamplePoint> points, AirFilterDto f);
}
