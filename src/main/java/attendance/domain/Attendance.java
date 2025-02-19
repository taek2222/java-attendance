package attendance.domain;

import attendance.dto.AttendanceResponse;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Attendance {

    private LocalDateTime dateTime;
    private AttendanceStatusType status;

    public Attendance(LocalDate date) {
        this.dateTime = LocalDateTime.of(date, LocalTime.MIN);
        this.status = AttendanceStatusType.EXPULSION;
    }

    public AttendanceResponse createResponse() {
        return new AttendanceResponse(dateTime, status.getName());
    }

    public void updateTime(LocalTime givenTime) {
        LocalDateTime newDateTime = LocalDateTime.of(dateTime.toLocalDate(), givenTime);
        dateTime = newDateTime;
        status = AttendanceStatusType.find(EducationTime.calculateOverTime(newDateTime));
    }
}
