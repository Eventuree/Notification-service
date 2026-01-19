package eventure.notificationservice.service;

import eventure.notificationservice.dto.PasswordResetEventDto;

public interface EmailService {
    void sendPasswordResetEmail(PasswordResetEventDto event);

}
