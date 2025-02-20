package attendance.dto;

public record AttendanceSearchResult(
        String nickname,
        AttendanceRecordUntilToday recordUntilToday,
        AttendanceGroupByStatus groupByStatus
) {

}
