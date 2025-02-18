package attendance.domain;

import java.time.LocalTime;

public enum CampusTime {
    START_TIME(LocalTime.of(8, 0)),
    END_TIME(LocalTime.of(23, 0));

    private final LocalTime time;

    CampusTime(LocalTime time) {
        this.time = time;
    }

    public static void validateOperationTime(LocalTime target) {
        boolean isNotOperationTime = target.isBefore(START_TIME.time) || target.isAfter(END_TIME.time);
        if (isNotOperationTime) {
            throw new IllegalArgumentException("[ERROR] 캠퍼스 운영시간이 아닙니다.");
        }
    }
}
