package eventure.notificationservice.service.impl;

import eventure.notificationservice.dto.PasswordResetEventDto;
import eventure.notificationservice.dto.StatusChangeNotificationDto;
import eventure.notificationservice.service.EmailService;
import eventure.notificationservice.service.NotificationService;
import eventure.notificationservice.service.component.EmailContentBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final EmailService emailService;
    private final EmailContentBuilder contentBuilder;

    @Value("${app.password-reset.frontend-url}")
    private String resetBaseUrl;
    @Value("${app.password-reset.mail.subject}")
    private String resetPasswordSubject;
    @Value("${app.password-reset.template-name}")
    private String resetPasswordTemplateName;

    @Value("${app.status-change.mail.subject}")
    private String statusChangeSubject;
    @Value("${app.status-change.template-name}")
    private String statusChangeTemplateName;

    @Override
    public void sendPasswordResetNotification(PasswordResetEventDto dto) {
        String resetLink = resetBaseUrl + "?token=" + dto.getToken();
        Map<String, Object> vars = Map.of("resetLink", resetLink);

        processAndSend(dto.getEmail(), resetPasswordSubject, resetPasswordTemplateName, vars);
    }

    @Override
    public void sendStatusChangeNotification(StatusChangeNotificationDto dto) {
        Map<String, Object> vars = Map.of(
                "userName", dto.getUserName(),
                "eventTitle", dto.getEventTitle(),
                "newStatus", dto.getNewStatus()
        );

        processAndSend(dto.getUserEmail(), statusChangeSubject, statusChangeTemplateName, vars);
    }

    private void processAndSend(String to, String subject, String templateName, Map<String, Object> vars) {
        String htmlBody = contentBuilder.buildHtmlContent(templateName, vars);
        String textBody = contentBuilder.buildTextContent(templateName, vars);

        emailService.sendEmail(to, subject, htmlBody, textBody);
    }
}