package eventure.notificationservice.listener;

import eventure.notificationservice.dto.RatingReminderDto;
import eventure.notificationservice.exception.EmailSendException;
import eventure.notificationservice.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class RatingReminderListener {

    private final NotificationService notificationService;

    @RabbitListener(queues = "${rabbitmq.queue.rating-reminder}")
    public void handleRatingReminder(RatingReminderDto event) {
        try {
            notificationService.sendRatingReminderNotification(event);
        } catch (Exception e) {
            log.error("Error processing rating reminder for {}. Message will be retried or sent to DLQ.", event.getEmail(), e);
            throw new EmailSendException("Failed to send rating reminder email", e);
        }
    }
}
