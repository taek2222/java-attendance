package attendance.dto;

public record AttendanceGroupByStatus(
        int expulsion,
        int late,
        int attendance,
        String warning
) {

}
