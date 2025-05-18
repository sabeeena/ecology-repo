package kz.iitu.se242m.yesniyazova.controller;

import kz.iitu.se242m.yesniyazova.entity.dto.AirFilterDto;
import kz.iitu.se242m.yesniyazova.entity.dto.WeatherFilterDto;
import kz.iitu.se242m.yesniyazova.repository.CityRepository;
import kz.iitu.se242m.yesniyazova.service.ReportDocService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/api/report/doc")
@RequiredArgsConstructor
public class ReportDocController {
    private final ReportDocService docService;
    private final CityRepository cityRepository;

    @PostMapping(
            value = "/weather/export",
            produces = { "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" } )
    public ResponseEntity<byte[]> exportWeather(
            @RequestBody WeatherFilterDto filter,
            @RequestParam(defaultValue = "xlsx") String type) {

        byte[] doc = docService.buildWeatherDocument(filter, type);
        String filename = "weather-report";
        if (filter.getCityId() != null) {
            filename += "-" + cityRepository.findById(filter.getCityId()).get().getName().toLowerCase();
        }
        filename += "-" + DateTimeFormatter.ofPattern("dd-mm-yyyy").format(LocalDateTime.now()) + ".";
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=" + filename + type)
                .body(doc);
    }

    @PostMapping(value="/air/export",
            produces = { "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" })
    public ResponseEntity<byte[]> exportAir(@RequestBody AirFilterDto filter,
                                            @RequestParam(defaultValue="xlsx") String type) {

        byte[] doc = docService.buildAirDocument(filter, type);
        String filename = "air-report";
        if (filter.getPollutant() != null) {
            filename += "-" + filter.getPollutant().toLowerCase();
        }
        if (filter.getCityId() != null) {
            filename += "-" + cityRepository.findById(filter.getCityId()).get().getName().toLowerCase();
        }
        filename += "-" + DateTimeFormatter.ofPattern("dd-mm-yyyy").format(LocalDateTime.now()) + ".";
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=" + filename + type)
                .body(doc);
    }
}
