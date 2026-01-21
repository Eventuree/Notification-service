package eventure.notificationservice.listener;

import eventure.notificationservice.dto.StatusChangeNotificationDto;
import eventure.notificationservice.exception.EmailSendException;
import eventure.notificationservice.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class EventStatusChangeListener {

    private final NotificationService notificationService;

    @RabbitListener(queues = "${rabbitmq.queue.status-change}")
    public void handleStatusChange(StatusChangeNotificationDto dto) {
        log.info("Received status change for: {}", dto.getUserEmail());
        try {
            notificationService.sendStatusChangeNotification(dto);
        } catch (Exception e) {
            log.error("Error processing email for {}. Message will be retried or sent to DLQ.", dto.getUserEmail(), e);
            throw new EmailSendException("Failed to send status change email", e);
        }
    }
}
