package kz.iitu.se242m.yesniyazova.service.impl;

import kz.iitu.se242m.yesniyazova.client.OpenWeatherClient;
import kz.iitu.se242m.yesniyazova.entity.AirSample;
import kz.iitu.se242m.yesniyazova.entity.City;
import kz.iitu.se242m.yesniyazova.entity.SamplePoint;
import kz.iitu.se242m.yesniyazova.entity.dto.AirFilterDto;
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
        long start = Instant.now().minus(7, ChronoUnit.DAYS).getEpochSecond();
        long end = Instant.now().getEpochSecond();
        cityRepository.findAll().forEach(c -> {
            var record = openWeatherClient.fetchHistory(c, start, end);
            record.list.forEach(e -> airSampleRepository.save(mapEntry(c, e)));
        });
    }

    public List<AirSample> latestByCity(Long id) {
        return airSampleRepository.findTopByCityOrderByTsDesc(cityRepository.getReferenceById(id)).stream().toList();
    }

    public List<SamplePoint> findByFilter(AirFilterDto filter) {
        return airSampleRepository.findPollutantHistory(filter.getCityId(), filter.getFrom(), filter.getTo(), filter.getPollutant());
    }

    public long countSamples() {
        return airSampleRepository.count();
    }

    private AirSample mapEntry(City city, OpenWeatherClient.AirRecord.Entry entry) {
        AirSample sample = new AirSample();
        sample.setCity(city);
        sample.setTs(Instant.ofEpochSecond(entry.dt));
        sample.setAqi(entry.main.aqi);
        entry.components.forEach(sample::setComponent);
        return sample;
    }
}

