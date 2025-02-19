package attendance.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Attendances {

    private final Map<String, List<Attendance>> attendances;
    private final Holiday holiday;

    public Attendances(Holiday holiday) {
        this.attendances = new HashMap<>();
        this.holiday = holiday;
    }

    public void addCrew(String name) {
        List<Attendance> attendanceList = new ArrayList<>();

        int dayAllCount = LocalDate.now().lengthOfMonth();

        for (int i = 1; i <= dayAllCount; i++) {
            LocalDate date = LocalDate.now().withDayOfMonth(i);
            if (holiday.isHoliday(date)) {
                continue;
            }

            Attendance attendance = new Attendance(date);
            attendanceList.add(attendance);
        }

        attendances.put(name, attendanceList);
    }

    public List<Attendance> findCrewAttendance(String name) {
        return attendances.get(name);
    }
}
