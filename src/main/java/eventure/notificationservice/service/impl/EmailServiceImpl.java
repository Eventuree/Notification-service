package eventure.notificationservice.service.impl;

import jakarta.mail.internet.MimeMessage;
import eventure.notificationservice.exception.EmailSendException;
import eventure.notificationservice.service.EmailService;
import eventure.notificationservice.service.component.EmailContentBuilder;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;
    private final EmailContentBuilder emailContentBuilder;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Value("${app.password-reset.frontend-url}")
    private String resetBaseUrl;

    @Value("${app.password-reset.mail.subject}")
    private String resetPasswordSubject;

    @Value("${app.password-reset.template-name}")
    private String resetPasswordTemplateName;


    @Override
    public void sendPasswordResetMail(String userEmail, String rawToken) {
        String resetLink = resetBaseUrl + "?token=" + rawToken;

        Map<String, Object> vars = Map.of(
                "resetLink", resetLink
        );

        sendTemplateMail(
                userEmail,
                resetPasswordSubject,
                resetPasswordTemplateName,
                vars
        );
    }

    @Override
    public void sendTemplateMail(String to,
                                 String subject,
                                 String templateName,
                                 Map<String, Object> variables) {
        try {
            String htmlBody = emailContentBuilder.buildHtmlContent(templateName, variables);
            String textBody = emailContentBuilder.buildTextContent(templateName, variables);

            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            helper.setFrom(fromEmail);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(textBody, htmlBody);

            mailSender.send(mimeMessage);
            log.info("Email '{}' sent to {}", subject, to);

        } catch (Exception e) {
            log.error("Failed to send email '{}' to {}", subject, to, e);
            throw new EmailSendException("Could not send email", e);
        }
    }
}