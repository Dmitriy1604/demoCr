package service;


import entity.NotificationTask;
import org.springframework.stereotype.Service;
import repository.NotificationTaskRepository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
public class NotificationTaskService {
    private final NotificationTaskRepository notificationTaskRepository;
    public NotificationTaskService(NotificationTaskRepository notificationTaskRepository){
        this.notificationTaskRepository = notificationTaskRepository;

    }
    @Transactional
    public void create(Long chatId,
                       String massage,
                       LocalDateTime dateTime){
        NotificationTask notificationTask = new NotificationTask();
        notificationTask.setUserId(chatId);
        notificationTask.setMessage(massage);
        notificationTask.setNotificationDateTime(dateTime.truncatedTo(ChronoUnit.MINUTES));
        notificationTaskRepository.save(notificationTask);
    }
}
