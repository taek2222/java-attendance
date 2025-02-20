package attendance.domain;

import java.util.Arrays;

public enum AttendanceStatusType {
    EXPULSION("결석", 30),
    LATE("지각", 5),
    ATTENDANCE("출석", 0);

    private final String name;
    private final int threshold;

    AttendanceStatusType(String name, int threshold) {
        this.name = name;
        this.threshold = threshold;
    }

    public static AttendanceStatusType find(int overTime) { // todo : findBy~~ 네이밍 의논, static 선언, 필터 인자 status는 어떤지
        return Arrays.stream(AttendanceStatusType.values())
                .filter(type -> type.threshold < overTime)
                .findFirst()
                .orElse(ATTENDANCE);
    }

    public String getName() {
        return name;
    }
}
