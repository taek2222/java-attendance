package attendance.dto.response;

public record WarnedStudent(
        String name,
        AttendanceGroupByStatus groupByStatus
) implements Comparable<WarnedStudent> {

    @Override
    public int compareTo(WarnedStudent other) {
        if (groupByStatus.warning().equals(other.groupByStatus().warning())) {
            if (calculateScore(groupByStatus) == calculateScore(other.groupByStatus)) {
                return name.compareTo(other.name);
            }
            return Integer.compare(calculateScore(other.groupByStatus),
                    calculateScore(groupByStatus));
        }
        return other.groupByStatus().warning().compareTo(groupByStatus.warning());
    }

    private int calculateScore(AttendanceGroupByStatus attendanceGroupByStatus) {
        return attendanceGroupByStatus.expulsion() * 3 + attendanceGroupByStatus.late();
    }
}
