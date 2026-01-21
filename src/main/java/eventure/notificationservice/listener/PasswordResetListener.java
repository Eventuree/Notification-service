package eventure.notificationservice.listener;


import eventure.notificationservice.dto.PasswordResetEventDto;
import eventure.notificationservice.dto.UserRegistrationDto;
import eventure.notificationservice.exception.EmailSendException;
import eventure.notificationservice.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PasswordResetListener {

    private final NotificationService notificationService;

    @RabbitListener(queues = "${rabbitmq.queue.password-reset}")
    public void handlePasswordReset(PasswordResetEventDto event) {
        log.info("Received password reset request for: {}", event.getEmail());
        try {
            notificationService.sendPasswordResetNotification(event);
        } catch (Exception e) {
            log.error("Error processing email for {}. Message will be retried or sent to DLQ.", event.getEmail(), e);
            throw new EmailSendException("Failed to send password reset email", e);
        }
    }

    @RabbitListener(queues = "${rabbitmq.queue.user-registration}")
    public void handleRegistration(UserRegistrationDto message) {
        log.info("Received registration message: userId = {}, userEmail = {}", message.getUserId(), message.getEmail());
        try {
            emailService.sendRegistrationMail(message.getEmail(),
                    message.getFirstName(),
                    message.getLastName());
        }
        catch (Exception e) {
            log.error("Error processing email for {}. Message will be retried or sent to DLQ.", message.getEmail());
            throw new EmailSendException("Failed to send registration email", e);
        }
    }
}