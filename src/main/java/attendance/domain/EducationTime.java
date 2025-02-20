package attendance.domain;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;

public enum EducationTime {
    MONDAY(LocalTime.of(13, 0, 0), LocalTime.of(18, 0, 0)),
    NOT_MONDAY(LocalTime.of(10, 0, 0), LocalTime.of(18, 0, 0)); // todo : 추후 네이밍 고민

    private static final DayOfWeek MONDAY_DAY_OF_WEEK = DayOfWeek.MONDAY;

    private final LocalTime startTime;
    private final LocalTime endTime; // todo : 삭제 고려

    EducationTime(LocalTime startTime, LocalTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public static int calculateOverTime(LocalDateTime attendanceTime) { // todo : 리팩토링 필요
        DayOfWeek dayOfWeek = attendanceTime.getDayOfWeek();
        if (dayOfWeek == MONDAY_DAY_OF_WEEK) {
            int i = attendanceTime.toLocalTime().toSecondOfDay() - MONDAY.startTime.toSecondOfDay();
            int overTime = i / 60;

            return Math.max(overTime, 0);
        }

        int i = attendanceTime.toLocalTime().toSecondOfDay() - NOT_MONDAY.startTime.toSecondOfDay();
        int overTime = i / 60;

        return Math.max(overTime, 0);
    }
}
