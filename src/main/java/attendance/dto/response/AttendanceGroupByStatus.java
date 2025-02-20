package attendance.dto.response;

public record AttendanceGroupByStatus(
        int expulsion,
        int late,
        int attendance,
        String warning
) {

}
