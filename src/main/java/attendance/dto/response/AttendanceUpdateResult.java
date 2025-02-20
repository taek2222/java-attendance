package attendance.dto.response;

public record AttendanceUpdateResult(
        AttendanceRecord before,
        AttendanceRecord after
) {

}
