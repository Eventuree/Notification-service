package eventure.notificationservice.service;

import java.util.Map;

public interface EmailService {
    void sendPasswordResetMail(String userEmail, String rawToken);

    void sendRegistrationMail(String userEmail, String firstName, String lastNam);

    void sendTemplateMail(String to, String subject, String templateName, Map<String, Object> variables);
}
