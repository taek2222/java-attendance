package attendance.utility;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public final class DateTimeParser {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private DateTimeParser() {
    }

    public static LocalDateTime parseDateTime(String input) {
        try {
            return LocalDateTime.parse(input, FORMATTER);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("[ERROR] 잘못된 날짜와 시간 형식입니다.");
        }
    }

    public static LocalDate parseDateByDay(LocalDate date, int day) {
        try {
            return date.withDayOfMonth(day);
        } catch (DateTimeException e) {
            throw new IllegalArgumentException("[ERROR] 잘못된 날짜 형식입니다.");
        }
    }

    public static LocalTime parseTime(String input) {
        try {
            return LocalTime.parse(input);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("[ERROR] 잘못된 시간 형식입니다.");
        }
    }

}
