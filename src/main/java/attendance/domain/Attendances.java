package attendance.domain;

import attendance.utility.DateGenerator;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Attendances {

    private final Map<String, List<Attendance>> attendances;
    private final Holiday holiday;
    private final DateGenerator dateGenerator;

    public Attendances(Holiday holiday, DateGenerator dateGenerator) {
        this.attendances = new HashMap<>();
        this.holiday = holiday;
        this.dateGenerator = dateGenerator;
    }

    public void addCrew(String name) {
        List<Attendance> attendanceList = new ArrayList<>();

        int dayAllCount = dateGenerator.now().lengthOfMonth();

        for (int i = 1; i <= dayAllCount; i++) {
            LocalDate date = dateGenerator.now().withDayOfMonth(i);
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
