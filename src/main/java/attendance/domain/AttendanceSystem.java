package attendance.domain;

import java.time.LocalDateTime;

public class AttendanceSystem {

    private final AttendanceManager manager;

    public AttendanceSystem(final AttendanceManager manager) {
        this.manager = manager;
    }

    public Attendance processAttendanceCheck(final LocalDateTime dateTime, final String nickname) {
        Attendances attendances = manager.findCrewAttendance(nickname);
        return attendances.update(dateTime);
    }
}
