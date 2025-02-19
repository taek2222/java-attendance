package attendance.dto;

import java.time.LocalDateTime;

public record AttendanceResponse(
        LocalDateTime dateTime,
        String status
) {

}
