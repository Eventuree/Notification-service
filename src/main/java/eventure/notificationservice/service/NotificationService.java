package eventure.notificationservice.service;

import eventure.notificationservice.dto.PasswordResetEventDto;
import eventure.notificationservice.dto.StatusChangeNotificationDto;
import eventure.notificationservice.dto.UserRegistrationDto;

public interface NotificationService {
    void sendPasswordResetNotification(PasswordResetEventDto dto);

    void sendStatusChangeNotification(StatusChangeNotificationDto dto);

    void sendRegistrationNotification(UserRegistrationDto user);
}
