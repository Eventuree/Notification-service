    package eventure.notificationservice.model.entity;

    import jakarta.persistence.*;
    import lombok.*;
    import org.hibernate.annotations.CreationTimestamp;

    import java.time.Instant;
    import java.time.LocalDateTime;

    @Entity
    @Table(name = "notification_templates")
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public class NotificationTemplate {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(name = "template_key", length = 100, nullable = false)
        private String templateKey;

        @Column(name = "subject_template",length = 255)
        private String subjectTemplate;

        @Column(name = "body_template", columnDefinition = "TEXT")
        private String bodyTemplate;

        @Column(name = "created_at")
        @Builder.Default
        private LocalDateTime createdAt = LocalDateTime.now();
    }
