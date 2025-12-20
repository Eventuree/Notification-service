package eventure.notificationservice.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_notification_settings")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserNotificationSettings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "email_enabled", nullable = false)
    private Boolean emailEnabled;

    @Column(name = "settings_json", columnDefinition = "JSONB")
    private String settingsJson;
}
