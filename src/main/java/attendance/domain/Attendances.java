package attendance.domain;

import attendance.dto.AttendanceGroupByStatus;
import attendance.dto.AttendanceResponse;
import attendance.dto.AttendanceResponses;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

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
        LinkedHashMap<String, Integer> countByStatus = Arrays.stream(AttendanceStatusType.values())
                .collect(Collectors.toMap(
                        AttendanceStatusType::getName,
                        this::calculateStatus,
                        (existing, replacement) -> existing,
                        LinkedHashMap::new
                ));

        AttendanceWarningType warning = AttendanceWarningType.find(
                countByStatus.get(AttendanceStatusType.EXPULSION.getName()),
                countByStatus.get(AttendanceStatusType.LATE.getName())
        );

        return new AttendanceGroupByStatus(
                countByStatus,
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
