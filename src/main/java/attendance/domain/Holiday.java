package attendance.domain;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Holiday {

    private static final DayOfWeek SATURDAY = DayOfWeek.SATURDAY;
    private static final DayOfWeek SUNDAY = DayOfWeek.SUNDAY;

    private final List<LocalDate> publicHolidays = new ArrayList<>(); // todo : 공휴일 네이밍 의문, 객체 생성 과정 의문

    public void addHoliday(LocalDate holiday) {
        if (publicHolidays.contains(holiday)) {
            throw new IllegalArgumentException("[ERROR] 이미 추가된 휴일입니다.");
        }
        publicHolidays.add(holiday);
    }

    public boolean isHoliday(LocalDate date) {
        return date.getDayOfWeek() == SATURDAY || date.getDayOfWeek() == SUNDAY || publicHolidays.contains(date);
    }

    public void validateHoliday(LocalDate date) {
        if (date.getDayOfWeek() == SATURDAY || date.getDayOfWeek() == SUNDAY || publicHolidays.contains(date)) {
            String formatted = String.format("[ERROR] %d월 %d일 %s은 등교일이 아닙니다.",
                    date.getMonthValue(),
                    date.getDayOfMonth(),
                    date.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREA)
            );
            throw new IllegalArgumentException(formatted);
        }
    }
}
