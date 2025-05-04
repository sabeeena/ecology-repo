package kz.iitu.se242m.yesniyazova.service.impl;

import kz.iitu.se242m.yesniyazova.client.OpenWeatherClient;
import kz.iitu.se242m.yesniyazova.entity.City;
import kz.iitu.se242m.yesniyazova.entity.WeatherHistoryResponse;
import kz.iitu.se242m.yesniyazova.entity.WeatherSample;
import kz.iitu.se242m.yesniyazova.entity.dto.WeatherFilterDto;
import kz.iitu.se242m.yesniyazova.entity.dto.WeatherRecord;
import kz.iitu.se242m.yesniyazova.repository.CityRepository;
import kz.iitu.se242m.yesniyazova.repository.WeatherSampleRepository;
import kz.iitu.se242m.yesniyazova.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

@Service
public class WeatherServiceImpl implements WeatherService {

    @Autowired
    private OpenWeatherClient openWeatherClient;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private WeatherSampleRepository weatherSampleRepository;

    @Override
    public void pullCurrent() {
        cityRepository.findAll().forEach(this::saveNow);
    }

    @Override
    public long countSamples() {
        return weatherSampleRepository.count();
    }

    @Override
    public WeatherHistoryResponse history(WeatherFilterDto filterDto) {
        return new WeatherHistoryResponse(
                weatherSampleRepository.findWeatherHistory(filterDto.getCityId(), filterDto.getFrom(), filterDto.getTo(), "temperature"),
                weatherSampleRepository.findWeatherHistory(filterDto.getCityId(), filterDto.getFrom(), filterDto.getTo(), "humidity"),
                weatherSampleRepository.findWeatherHistory(filterDto.getCityId(), filterDto.getFrom(), filterDto.getTo(), "pressure"),
                weatherSampleRepository.findWeatherHistory(filterDto.getCityId(), filterDto.getFrom(), filterDto.getTo(), "wind_speed")
        );
    }

    private void saveNow(City city) {
        var weatherRecord = openWeatherClient.fetchWeather(city);
        WeatherSample sample = mapEntry(city, weatherRecord);
        weatherSampleRepository.save(sample);
    }

    private static final DateTimeFormatter WEATHER_TIME =
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
                    .withZone(ZoneOffset.UTC);

    private WeatherSample mapEntry(City city, WeatherRecord record) {
        WeatherSample s = new WeatherSample();
        s.setCity(city);
        Instant ts = LocalDateTime.parse(record.lastUpdate.value, WEATHER_TIME)
                .toInstant(ZoneOffset.UTC);
        s.setTs(ts);
        s.setTemperature(record.temperature.value);
        s.setHumidity(record.humidity.value);
        s.setPressure(record.pressure.value);
        s.setWindSpeed(record.wind.speed.value);
        s.setWindDirection(record.wind.direction.name);
        s.setCloudCoverage(record.clouds.name);
        s.setWeatherDescription(record.weather.value);
        return s;
    }
}

