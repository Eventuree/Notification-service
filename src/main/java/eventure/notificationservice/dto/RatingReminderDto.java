package eventure.notificationservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RatingReminderDto {
    private String email;
    private String userName;
    private String eventTitle;
    private Long eventId;
}
