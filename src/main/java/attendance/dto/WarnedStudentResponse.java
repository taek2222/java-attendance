package attendance.dto;

public record WarnedStudentResponse(
        String name,
        AttendanceGroupByStatus attendanceGroupByStatus
) {

}
