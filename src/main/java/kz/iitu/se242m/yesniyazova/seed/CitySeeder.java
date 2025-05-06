package kz.iitu.se242m.yesniyazova.seed;

import kz.iitu.se242m.yesniyazova.entity.City;
import kz.iitu.se242m.yesniyazova.repository.CityRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * For test purposes only - not intended for production
 */

@Component
class CitySeeder implements CommandLineRunner {

    private final CityRepository cityRepository;

    CitySeeder(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    public void run(String... args) {
        if (cityRepository.count() > 0) return;

        cityRepository.saveAll(List.of(
                new City("Almaty",      43.2389,  76.8897),
                new City("Astana",      51.1605,  71.4704),
                new City("Shymkent",    42.3417,  69.5901),
                new City("Karaganda",   49.8031,  73.0525),
                new City("Aktobe",      50.2830,  57.1660),
                new City("Taraz",       42.8980,  71.4270),
                new City("Pavlodar",    52.2871,  76.9674),
                new City("Oskemen",     49.9781,  82.6018),
                new City("Semey",       50.4111,  80.2275),
                new City("Kostanay",    53.2194,  63.6358),
                new City("Kyzylorda",   44.8488,  65.4823),
                new City("Atyrau",      47.1167,  51.8833),
                new City("Aktau",       43.6532,  51.1972),
                new City("Petropavl",   54.8714,  69.1456),
                new City("Oral",        51.2300,  51.3700)
        ));
    }
}
