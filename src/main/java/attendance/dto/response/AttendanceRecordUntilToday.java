package attendance.dto.response;

import java.util.List;

public record AttendanceRecordUntilToday(
        List<AttendanceRecord> records
) {

}
