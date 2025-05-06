package kz.iitu.se242m.yesniyazova.seed;

import jakarta.annotation.PostConstruct;
import kz.iitu.se242m.yesniyazova.entity.City;
import kz.iitu.se242m.yesniyazova.entity.WeatherSample;
import kz.iitu.se242m.yesniyazova.repository.CityRepository;
import kz.iitu.se242m.yesniyazova.repository.WeatherSampleRepository;
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
public class WeatherSampleSeeder {

    private final CityRepository cityRepository;
    private final WeatherSampleRepository weatherSampleRepository;

    private final Random random = new Random();

    @PostConstruct
    @Transactional
    public void seedWeatherData() {
        if (weatherSampleRepository.count() > 0) {
            System.out.println("Weather data already exists, skipping seeding.");
            return;
        }

        List<City> cities = cityRepository.findAll();

        LocalDateTime start = LocalDateTime.of(2025, 5, 1, 0, 0);
        LocalDateTime end = LocalDateTime.of(2025, 5, 6, 0, 0);

        for (City city : cities) {
            LocalDateTime current = start;
            while (current.isBefore(end)) {
                WeatherSample sample = new WeatherSample();
                sample.setCity(city);
                sample.setTs(current.toInstant(ZoneOffset.UTC));
                sample.setTemperature(270 + random.nextDouble() * 20);
                sample.setHumidity((int) (50 + random.nextDouble() * 50));
                sample.setPressure((int) (1000 + random.nextDouble() * 30));
                sample.setCloudCoverage("clear sky");
                sample.setWindSpeed(random.nextDouble() * 10);
                sample.setWeatherDescription("clear sky");
                sample.setWindDirection("South");

                weatherSampleRepository.save(sample);

                current = current.plusHours(1);
            }
        }

        System.out.println("Weather data seeded successfully!");
    }
}
