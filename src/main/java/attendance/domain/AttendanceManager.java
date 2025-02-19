package attendance.domain;

import attendance.utility.DateGenerator;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

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

    public boolean isContainNickname(String nickname) {
        return attendances.containsKey(nickname);
    }

    public Attendances findCrewAttendance(String name) {
        return attendances.get(name);
    }
}
