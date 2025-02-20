package attendance.dto;

public record WarnedStudentResponse(
        String name,
        AttendanceGroupByStatus attendanceGroupByStatus
) implements Comparable<WarnedStudentResponse> {

    @Override
    public int compareTo(WarnedStudentResponse other) {
        if (attendanceGroupByStatus.warning().equals(other.attendanceGroupByStatus().warning())) {
            if (calculateScore(attendanceGroupByStatus) == calculateScore(other.attendanceGroupByStatus)) {
                return name.compareTo(other.name);
            }
            return Integer.compare(calculateScore(other.attendanceGroupByStatus),
                    calculateScore(attendanceGroupByStatus));
        }
        return other.attendanceGroupByStatus().warning().compareTo(attendanceGroupByStatus.warning());
    }

    private int calculateScore(AttendanceGroupByStatus attendanceGroupByStatus) {
        return attendanceGroupByStatus.expulsion() * 3 + attendanceGroupByStatus.late();
    }
}
