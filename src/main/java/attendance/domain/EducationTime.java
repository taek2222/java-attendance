package attendance.domain;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;

public enum EducationTime {
    MONDAY(LocalTime.of(13, 0, 0), LocalTime.of(18, 0, 0)),
    NOT_MONDAY(LocalTime.of(10, 0, 0), LocalTime.of(18, 0, 0)); // todo : 추후 네이밍 고민

    private static final DayOfWeek MONDAY_DAY_OF_WEEK = DayOfWeek.MONDAY;

    private final LocalTime startTime;
    private final LocalTime endTime;

    EducationTime(LocalTime startTime, LocalTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public static int calculateOverTime(LocalDateTime attendanceTime) { // todo : 리팩토링 필요
        DayOfWeek dayOfWeek = attendanceTime.getDayOfWeek();
        if (dayOfWeek == MONDAY_DAY_OF_WEEK) {
            int i = Math.abs(MONDAY.startTime.toSecondOfDay() - attendanceTime.toLocalTime().toSecondOfDay());
            return Math.abs(i / 60);
        }

        int i = NOT_MONDAY.startTime.toSecondOfDay() - attendanceTime.toLocalTime().toSecondOfDay();
        return Math.abs(i / 60);
    }
}
