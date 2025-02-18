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

    public static AttendanceStatusType find(int overTime) {
        return Arrays.stream(AttendanceStatusType.values())
                .filter(type -> type.threshold < overTime)
                .findFirst()
                .orElse(ATTENDANCE);
    }
}
