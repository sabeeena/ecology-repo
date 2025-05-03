package kz.iitu.se242m.yesniyazova.service.impl;

import kz.iitu.se242m.yesniyazova.client.OpenWeatherClient;
import kz.iitu.se242m.yesniyazova.entity.City;
import kz.iitu.se242m.yesniyazova.entity.FireSample;
import kz.iitu.se242m.yesniyazova.repository.CityRepository;
import kz.iitu.se242m.yesniyazova.repository.FireSampleRepository;
import kz.iitu.se242m.yesniyazova.service.FireWeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class FireWeatherServiceImpl implements FireWeatherService {

    @Autowired
    private OpenWeatherClient openWeatherClient;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private FireSampleRepository fireSampleRepository;

    public void pullCurrent() {
        cityRepository.findAll().forEach(this::saveNow);
    }

    public long countSamples() {
        return fireSampleRepository.count();
    }

    private void saveNow(City city) {
        var record = openWeatherClient.fetchFireIndex(city);
        FireSample s = mapEntry(city, record);
        fireSampleRepository.save(s);
    }

    private FireSample mapEntry(City city, OpenWeatherClient.FireRecord record) {
        if (record.list == null || record.list.isEmpty()) {
            throw new IllegalStateException("No fire weather data available for city: " + city.getName());
        }

        var entry = record.list.get(0);

        FireSample sample = new FireSample();
        sample.setCity(city);
        sample.setTs(Instant.ofEpochSecond(entry.dt));
        sample.setFwi(entry.main.fwi);
        sample.setDangerDescription(entry.danger_rating.description);

        try {
            sample.setDangerValue(Integer.parseInt(entry.danger_rating.value));
        } catch (NumberFormatException e) {
            sample.setDangerValue(-1);
        }

        return sample;
    }

}
