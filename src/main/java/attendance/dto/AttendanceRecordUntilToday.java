package attendance.dto;

import java.util.List;

public record AttendanceRecordUntilToday(
        List<AttendanceResponse> responses
) {

}
