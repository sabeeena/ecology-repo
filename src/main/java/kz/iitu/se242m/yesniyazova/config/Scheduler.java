package kz.iitu.se242m.yesniyazova.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Scheduler {

    // private final NotificationService notificationService;

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

}
