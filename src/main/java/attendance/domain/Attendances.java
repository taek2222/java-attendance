package attendance.domain;

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

    public int size() {
        return attendances.size();
    }

    public AttendanceResponses createResponse() {
        List<AttendanceResponse> responses = attendances.stream()
                .map(Attendance::createResponse)
                .toList();
        return new AttendanceResponses(responses);
    }
}
