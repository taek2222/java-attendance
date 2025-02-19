package attendance.dto;

import java.util.Map;

public record AttendanceGroupByStatus(
        Map<String, Integer> countByStatus,
        String warning
) {
}
