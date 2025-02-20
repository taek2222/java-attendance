package attendance.domain;

import attendance.dto.response.AttendanceRecord;
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

    public boolean isEqualDate(LocalDate date) {
        return dateTime.toLocalDate().isEqual(date);
    }

    public boolean isAlreadyCheck() {
        return !dateTime.toLocalTime().equals(LocalTime.MIN);
    }

    public void updateTime(LocalTime givenTime) {
        LocalDateTime newDateTime = LocalDateTime.of(dateTime.toLocalDate(), givenTime);
        dateTime = newDateTime;
        status = AttendanceStatusType.find(EducationTime.calculateOverTime(newDateTime));
    }

    public boolean isEqualsStatus(AttendanceStatusType status) {
        return this.status == status;
    }

    public boolean isBefore(LocalDate date) {
        return dateTime.toLocalDate().isBefore(date);
    }

    public AttendanceRecord createResponse() {
        return new AttendanceRecord(dateTime, status.getName());
    }
}
