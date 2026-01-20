package eventure.notificationservice.service;

import java.util.Map;

public interface EmailService {
    void sendPasswordResetMail(String userEmail, String rawToken);

    void sendTemplateMail(String to, String subject, String templateName, Map<String, Object> variables);
}
