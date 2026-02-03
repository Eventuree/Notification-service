package eventure.notificationservice.service;

import eventure.notificationservice.dto.PasswordResetEventDto;
import eventure.notificationservice.dto.RatingReminderDto;
import eventure.notificationservice.dto.StatusChangeNotificationDto;

public interface NotificationService {
    void sendPasswordResetNotification(PasswordResetEventDto dto);

    void sendStatusChangeNotification(StatusChangeNotificationDto dto);

    void sendRatingReminderNotification(RatingReminderDto dto);

}
