package kz.iitu.se242m.yesniyazova.config;

import kz.iitu.se242m.yesniyazova.service.AirQualityService;
import kz.iitu.se242m.yesniyazova.service.FireWeatherService;
import kz.iitu.se242m.yesniyazova.service.WeatherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Scheduler {

    // private final NotificationService notificationService;

    @Autowired
    private AirQualityService airQualityService;

    @Autowired
    private FireWeatherService fireWeatherService;

    @Autowired
    private WeatherService weatherService;

    private static boolean historyDone = false;

    @Scheduled(fixedRate = 5 * 60 * 1000) // Every 5 minutes
    public void sendPendingUserNotifications() {
        log.info("[SCHEDULER] Checking for pending notifications...");

        /*
        List<User> users = notificationService.getUsersWithPendingAlerts();
        for (User user : users) {
            notificationService.sendReminder(user);
            log.info("Sent reminder to user: {}", user.getEmail());
        }
        */

        log.info("[SCHEDULER] Notification check completed.");
    }

    /**
     * Temporary disabled (Request limit)
     */
    // @Scheduled(cron = "0 0 * * * *") // Every hour
    void hourlyAirQualityCheck() {
        log.info("[SCHEDULER] Updating air quality data...");
        airQualityService.pullCurrent();
        log.info("[SCHEDULER] Air quality update completed.");
    }

    /**
     * Temporary disabled - Requires subscription
     */
    // @Scheduled(cron = "0 0 3 * * *") // Daily at 3AM
    public void pullDailyFireIndex() {
        log.info("[SCHEDULER] Updating fire index data...");
        fireWeatherService.pullCurrent();
        log.info("[SCHEDULER] Fire index data update completed.");
    }

    /**
     * Temporary disabled (Request limit)
     */
    // @Scheduled(cron = "0 0 * * * *") // Every hour
    public void pullHourlyWeather() {
        log.info("[SCHEDULER] Updating weather data...");
        airQualityService.pullCurrent();
        log.info("[SCHEDULER] Weather update completed.");
    }

    @Scheduled(initialDelay = 10_000, fixedDelay = Long.MAX_VALUE)
    void backfillOnce() {
        log.info("[SCHEDULER] Backfilling DB...");
        if (historyDone) return;

        log.info("[SCHEDULER] Loading air quality data...");
        if (airQualityService.countSamples() <= 0) {
            airQualityService.backfillHistory();
        }

//        log.info("[SCHEDULER] Loading fire index data...");
//        if (fireWeatherService.countSamples() <= 0) {
//            fireWeatherService.pullCurrent();
//        }

        log.info("[SCHEDULER] Loading weather data...");
        if (weatherService.countSamples() <= 0) {
            weatherService.pullCurrent();
        }

        historyDone = true;
        log.info("[SCHEDULER] Backfilling DB completed.");
    }

}
