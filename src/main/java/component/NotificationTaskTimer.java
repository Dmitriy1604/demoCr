package component;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import repository.NotificationTaskRepository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;

@Component
public class NotificationTaskTimer {
    private final NotificationTaskRepository notificationTaskRepository;
    private final SendHelper sendHelper;

    public NotificationTaskTimer(NotificationTaskRepository notificationTaskRepository,
                                 SendHelper sendHelper) {
        this.notificationTaskRepository = notificationTaskRepository;
        this.sendHelper = sendHelper;
    }
    @Scheduled(fixedRate = 1, timeUnit = TimeUnit.MINUTES)
    @Transactional
    public void task() {
notificationTaskRepository.findAllByNotificationDateTime(
        LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES)
    ).forEach(notificationTask -> {
    sendHelper.sendMessage(notificationTask.getUserId(), notificationTask.getMassage());
    notificationTaskRepository.delete(notificationTask);

});

    }
}
