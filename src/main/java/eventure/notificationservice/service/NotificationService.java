package eventure.notificationservice.service;

import eventure.notificationservice.dto.PasswordResetEventDto;

public interface NotificationService {
    void sendPasswordResetNotification(PasswordResetEventDto dto);
}
