package kz.iitu.se242m.yesniyazova.entity.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName = "current")
public class WeatherRecord {

    @JacksonXmlProperty(localName = "city")
    public City city;

    @JacksonXmlProperty(localName = "temperature")
    public Temperature temperature;

    @JacksonXmlProperty(localName = "humidity")
    public Humidity humidity;

    @JacksonXmlProperty(localName = "pressure")
    public Pressure pressure;

    @JacksonXmlProperty(localName = "wind")
    public Wind wind;

    @JacksonXmlProperty(localName = "clouds")
    public Clouds clouds;

    @JacksonXmlProperty(localName = "weather")
    public Weather weather;

    @JacksonXmlProperty(localName = "lastupdate")
    public LastUpdate lastUpdate;

    public static class City {
        @JacksonXmlProperty(isAttribute = true, localName = "id")
        public long id;

        @JacksonXmlProperty(isAttribute = true) public String name;
        @JacksonXmlProperty(localName = "coord") public Coord coord;
    }
    public static class Coord {
        @JacksonXmlProperty(isAttribute = true) public double lon;
        @JacksonXmlProperty(isAttribute = true) public double lat;
    }
    public static class Temperature {
        @JacksonXmlProperty(isAttribute = true) public double value;
        @JacksonXmlProperty(isAttribute = true) public String unit;
    }
    public static class Humidity {
        @JacksonXmlProperty(isAttribute = true) public int value;
        @JacksonXmlProperty(isAttribute = true) public String unit;
    }
    public static class Pressure {
        @JacksonXmlProperty(isAttribute = true) public int value;
        @JacksonXmlProperty(isAttribute = true) public String unit;
    }
    public static class Wind {
        @JacksonXmlProperty(localName = "speed")     public Speed speed;
        @JacksonXmlProperty(localName = "direction") public Direction direction;

        public static class Speed {
            @JacksonXmlProperty(isAttribute = true) public double value;
            @JacksonXmlProperty(isAttribute = true) public String unit;
        }
        public static class Direction {
            @JacksonXmlProperty(isAttribute = true) public int value;
            @JacksonXmlProperty(isAttribute = true) public String name;
        }
    }
    public static class Clouds {
        @JacksonXmlProperty(isAttribute = true) public int value;
        @JacksonXmlProperty(isAttribute = true) public String name;
    }
    public static class Weather {
        @JacksonXmlProperty(isAttribute = true) public String value;
        @JacksonXmlProperty(isAttribute = true) public String icon;
    }
    public static class LastUpdate {
        @JacksonXmlProperty(isAttribute = true) public String value;
    }
}
