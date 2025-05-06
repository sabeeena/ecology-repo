package kz.iitu.se242m.yesniyazova.seed;

import jakarta.annotation.PostConstruct;
import kz.iitu.se242m.yesniyazova.entity.AirSample;
import kz.iitu.se242m.yesniyazova.entity.City;
import kz.iitu.se242m.yesniyazova.repository.AirSampleRepository;
import kz.iitu.se242m.yesniyazova.repository.CityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Random;

/**
 * For test purposes only - not intended for production
 */

@Component
@RequiredArgsConstructor
public class AirSampleSeeder {

    private final CityRepository cityRepository;
    private final AirSampleRepository airSampleRepository;
    private final Random rnd = new Random();

    private int randomAqi() {
        int[] weights = {35, 30, 20, 10, 5};
        int roll = rnd.nextInt(100);
        int acc = 0;
        for (int i = 0; i < weights.length; i++) {
            acc += weights[i];
            if (roll < acc) return i + 1;
        }
        return 5;
    }

    @PostConstruct
    @Transactional
    public void seedAirSamples() {

        if (airSampleRepository.count() > 0) {
            System.out.println("Air-quality samples already exist – skipping seeder.");
            return;
        }

        List<City> cities = cityRepository.findAll();

        LocalDateTime start = LocalDateTime.of(2025, 5, 1, 0, 0);
        LocalDateTime end   = LocalDateTime.of(2025, 5, 6, 0, 0);

        for (City city : cities) {
            LocalDateTime ts = start;
            while (ts.isBefore(end)) {

                int aqi = randomAqi();

                AirSample s = new AirSample();
                s.setCity(city);
                s.setTs(ts.toInstant(ZoneOffset.UTC));
                s.setAqi(aqi);

                s.setCo   ( 4400 + rnd.nextDouble()* (aqi*2200));
                s.setNh3  (   1  + rnd.nextDouble()* 80);
                s.setNo   (   0.1+ rnd.nextDouble()* 50);
                s.setNo2  (  40  + rnd.nextDouble()* (aqi*30));
                s.setO3   (  60  + rnd.nextDouble()* (aqi*25));
                s.setPm10 (  20  + rnd.nextDouble()* (aqi*40));
                s.setPm2_5(  10  + rnd.nextDouble()* (aqi*20));
                s.setSo2  (  20  + rnd.nextDouble()* (aqi*65));

                airSampleRepository.save(s);
                ts = ts.plusHours(1);
            }
        }

        System.out.println("Air-quality samples seeded for " + cities.size() + " cities.");
    }
}