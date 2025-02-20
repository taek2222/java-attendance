package attendance.domain;

import attendance.dto.AttendanceGroupByStatus;
import attendance.dto.AttendanceResponse;
import attendance.dto.AttendanceRecordUntilToday;

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

    public AttendanceGroupByStatus createCountUntilYesterday(LocalDate date) {
        int expulsion = calculateStatusUntilYesterday(AttendanceStatusType.EXPULSION, date);
        int late = calculateStatusUntilYesterday(AttendanceStatusType.LATE, date);
        int attendance = calculateStatusUntilYesterday(AttendanceStatusType.ATTENDANCE, date);

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

    public AttendanceRecordUntilToday createRecordUntilTodayResponse(LocalDate today) {
        List<AttendanceResponse> responses = attendances.stream()
                .map(Attendance::createResponse)
                .filter(response -> response.dateTime().toLocalDate().isBefore(today))
                .toList();
        return new AttendanceRecordUntilToday(responses);
    }

    private int calculateStatusUntilYesterday(AttendanceStatusType status, LocalDate date) {
        return (int) attendances.stream()
                .filter(attendance -> attendance.isBefore(date))
                .filter(attendance -> attendance.isEqualsStatus(status))
                .count();
    }
}
