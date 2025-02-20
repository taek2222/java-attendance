package attendance.domain;

import attendance.dto.AttendanceGroupByStatus;
import attendance.dto.AttendanceResponse;
import attendance.dto.AttendanceResponses;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Attendances {

    private final List<Attendance> attendances = new ArrayList<>();

    public void add(Attendance attendance) {
        attendances.add(attendance);
    }

    public Attendance find(LocalDate date) {
        return attendances.stream()
                .filter(attendance -> attendance.isEqualDate(date))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 서버 오류가 발생했습니다."));
    }

    public AttendanceGroupByStatus createGroupByStatus() {
        int expulsion = calculateStatus(AttendanceStatusType.EXPULSION);
        int late = calculateStatus(AttendanceStatusType.LATE);
        int attendance = calculateStatus(AttendanceStatusType.ATTENDANCE);

        AttendanceWarningType warning = AttendanceWarningType.find(
                expulsion,
                late
        );

        return new AttendanceGroupByStatus(
                expulsion,
                late,
                attendance,
                warning.getName()
        );
    }

    public int size() {
        return attendances.size();
    }

    public AttendanceResponses createResponse() {
        List<AttendanceResponse> responses = attendances.stream()
                .map(Attendance::createResponse)
                .toList();
        return new AttendanceResponses(responses);
    }

    private int calculateStatus(AttendanceStatusType status) {
        return (int) attendances.stream()
                .filter(attendance -> attendance.isEqualsStatus(status))
                .count();
    }
}
