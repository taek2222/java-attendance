package attendance.domain;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

public class Holiday {

    private static final DayOfWeek SATURDAY = DayOfWeek.SATURDAY;
    private static final DayOfWeek SUNDAY = DayOfWeek.SUNDAY;

    private List<LocalDate> publicHolidays; // todo : 공휴일 네이밍 의문

    public void validateHoliday(LocalDate date) {
        if (date.getDayOfWeek() == SATURDAY || date.getDayOfWeek() == SUNDAY) {
            String formatted = String.format("[ERROR] %d월 %d일 %s은 등교일이 아닙니다.",
                    date.getMonthValue(),
                    date.getDayOfMonth(),
                    date.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREA)
            );
            throw new IllegalArgumentException(formatted);
        }
    }
}
