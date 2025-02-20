package attendance.domain;

import attendance.dto.WarnedStudentResponse;
import attendance.dto.WarnedStudentResponses;
import attendance.utility.DateGenerator;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class AttendanceManager {

    private final Map<String, Attendances> attendances;
    private final Holiday holiday;
    private final DateGenerator dateGenerator;

    public AttendanceManager(Holiday holiday, DateGenerator dateGenerator) {
        this.attendances = new HashMap<>();
        this.holiday = holiday;
        this.dateGenerator = dateGenerator;
    }

    public void addCrew(String name) {
        Attendances newAttendances = new Attendances();

        int dayAllCount = dateGenerator.now().lengthOfMonth();

        for (int i = 1; i <= dayAllCount; i++) {
            LocalDate date = dateGenerator.now().withDayOfMonth(i);
            if (holiday.isHoliday(date)) {
                continue;
            }

            newAttendances.add(new Attendance(date));
        }

        attendances.put(name, newAttendances);
    }

    public WarnedStudentResponses searchWarnedCrews(LocalDate date) {
        Set<String> nicknames = attendances.keySet();
        List<WarnedStudentResponse> responses = nicknames.stream()
                .map(nickname -> new WarnedStudentResponse(nickname,
                        attendances.get(nickname).createCountUntilYesterday(date)))
                .filter(response -> !response.attendanceGroupByStatus().warning()
                        .equals(AttendanceWarningType.NONE.getName()))
                .toList();

        return new WarnedStudentResponses(responses.stream().sorted().toList());
    }

    public boolean isNotContainNickname(String nickname) {
        return !attendances.containsKey(nickname);
    }

    public Attendances findCrewAttendance(String nickname) {
        if (isNotContainNickname(nickname)) {
            throw new IllegalArgumentException("[ERROR] 등록되지 않은 닉네임입니다.");
        }
        return attendances.get(nickname);
    }
}
