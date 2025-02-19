package attendance.dto;

public record AttendanceUpdateResult(
        AttendanceResponse before,
        AttendanceResponse after
) {

}
