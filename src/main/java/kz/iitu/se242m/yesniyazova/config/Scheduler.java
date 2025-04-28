package kz.iitu.se242m.yesniyazova.config;

import kz.iitu.se242m.yesniyazova.service.AirQualityService;
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

    private static boolean historyDone = false;

    @Scheduled(cron = "0 */5 * * * *") // Every 5 minutes
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
     * Temporary disabled
     */
    // @Scheduled(cron = "0 * * * * *")
    void hourlyAirQualityCheck() {
        log.info("[SCHEDULER] Updating air quality data...");
        airQualityService.pullCurrent();
        log.info("[SCHEDULER] Air quality update completed.");
    }

    @Scheduled(initialDelay = 10_000, fixedDelay = Long.MAX_VALUE)
    void backfillOnce() {
        if (historyDone) return;
        if (airQualityService.countSamples() > 0) return;
        airQualityService.backfillHistory();
        historyDone = true;
    }

}
