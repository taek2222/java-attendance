package attendance.dto.response;

public record AttendanceSearchResult(
        String nickname,
        AttendanceRecordUntilToday records,
        AttendanceGroupByStatus groupByStatus
) {

}
