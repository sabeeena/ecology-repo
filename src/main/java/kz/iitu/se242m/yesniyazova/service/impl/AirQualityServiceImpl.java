package kz.iitu.se242m.yesniyazova.service.impl;

import kz.iitu.se242m.yesniyazova.client.OpenWeatherClient;
import kz.iitu.se242m.yesniyazova.entity.AirSample;
import kz.iitu.se242m.yesniyazova.entity.City;
import kz.iitu.se242m.yesniyazova.repository.AirSampleRepository;
import kz.iitu.se242m.yesniyazova.repository.CityRepository;
import kz.iitu.se242m.yesniyazova.service.AirQualityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.time.Instant;
import java.util.List;

@Service
public class AirQualityServiceImpl implements AirQualityService {

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private AirSampleRepository airSampleRepository;

    @Autowired
    private OpenWeatherClient openWeatherClient;

    public void pullCurrent() {
        cityRepository.findAll().forEach(this::saveNow);
    }

    private void saveNow(City city) {
        var record = openWeatherClient.fetchNow(city).list.get(0);
        AirSample s = mapEntry(city, record);
        airSampleRepository.save(s);
    }

    public void backfillHistory() {
        long start = Instant.parse("2025-03-15T00:00:00Z").getEpochSecond();
        long end   = Instant.now().minus(1, ChronoUnit.HOURS).getEpochSecond();
        cityRepository.findAll().forEach(c -> {
            var record = openWeatherClient.fetchHistory(c, start, end);
            record.list.forEach(e -> airSampleRepository.save(mapEntry(c, e)));
        });
    }

    public List<AirSample> latestByCity(Long id) {
        return airSampleRepository.findTopByCityOrderByTsDesc(cityRepository.getReferenceById(id)).stream().toList();
    }

    private AirSample mapEntry(City city, OpenWeatherClient.Record.Entry entry) {
        AirSample sample = new AirSample();
        sample.setCity(city);
        sample.setTs(Instant.ofEpochSecond(entry.dt));
        sample.setAqi(entry.main.aqi);
        entry.components.forEach(sample::setComponent);
        return sample;
    }
}

