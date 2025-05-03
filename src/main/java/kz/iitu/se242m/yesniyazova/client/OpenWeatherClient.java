package kz.iitu.se242m.yesniyazova.client;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import kz.iitu.se242m.yesniyazova.entity.City;
import kz.iitu.se242m.yesniyazova.entity.dto.WeatherRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Component
public class OpenWeatherClient {

    private final WebClient webClient;
    private final XmlMapper xmlMapper;

    public OpenWeatherClient() {
        this.xmlMapper = (XmlMapper) new XmlMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        this.webClient = WebClient.builder()
                .baseUrl("https://api.openweathermap.org")
                .build();
    }

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

    public WeatherRecord fetchWeather(City city) {
        String xmlResponse = webClient.get()
                .uri("/data/2.5/weather?lat={lat}&lon={lon}&appid={k}&mode=xml",
                        city.getLat(), city.getLon(), key)
                .accept(MediaType.APPLICATION_XML)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        try {
            return xmlMapper.readValue(xmlResponse, WeatherRecord.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse XML response", e);
        }
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
