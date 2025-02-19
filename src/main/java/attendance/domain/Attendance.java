package attendance.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Attendance {

    private final LocalDateTime dateTime;
    private final AttendanceStatusType status;

    public Attendance(LocalDate date) {
        this.dateTime = LocalDateTime.of(date, LocalTime.MIN);
        this.status = AttendanceStatusType.EXPULSION;
    }
}
