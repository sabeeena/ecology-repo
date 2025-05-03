package kz.iitu.se242m.yesniyazova.client;

import kz.iitu.se242m.yesniyazova.entity.City;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Component
public class OpenWeatherClient {

    private final WebClient webClient = WebClient.create("https://api.openweathermap.org");

    @Value("${openweather.apikey}")
    String key;

    public AirRecord fetchNow(City city) {
        return webClient.get()
                .uri("/data/2.5/air_pollution?lat={lat}&lon={lon}&appid={k}",
                        city.getLat(), city.getLon(), key)
                .retrieve()
                .bodyToMono(AirRecord.class)
                .block();
    }

    public AirRecord fetchHistory(City city, long start, long end) {
        return webClient.get()
                .uri("/data/2.5/air_pollution/history?lat={lat}&lon={lon}&start={s}&end={e}&appid={k}",
                        city.getLat(), city.getLon(), start, end, key)
                .retrieve()
                .bodyToMono(AirRecord.class)
                .block();
    }

    public FireRecord fetchFireIndex(City city) {
        return webClient.get()
                .uri("/data/2.5/fwi?lat={lat}&lon={lon}&appid={k}",
                        city.getLat(), city.getLon(), key)
                .retrieve()
                .bodyToMono(FireRecord.class)
                .block();
    }

    public static class AirRecord {
        public java.util.List<Entry> list;
        public static class Entry{
            public Main main;
            public Map<String,Double> components;
            public long dt;
            public static class Main{ public int aqi; }
        }
    }

    public static class FireRecord {
        public Coord coord;
        public java.util.List<Entry> list;

        public static class Coord {
            public double lon;
            public double lat;
        }

        public static class Entry {
            public Main main;
            public DangerRating danger_rating;
            public long dt;

            public static class Main {
                public double fwi;
            }

            public static class DangerRating {
                public String description;
                public String value;
            }
        }
    }
}
