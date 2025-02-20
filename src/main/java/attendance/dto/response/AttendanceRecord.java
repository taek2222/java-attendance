package attendance.dto.response;

import java.time.LocalDateTime;

public record AttendanceRecord(
        LocalDateTime dateTime,
        String status
) {

}
