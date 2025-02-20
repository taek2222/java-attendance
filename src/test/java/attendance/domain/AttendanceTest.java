package attendance.domain;

import static attendance.domain.AttendanceStatusType.ATTENDANCE;
import static attendance.domain.AttendanceStatusType.EXPULSION;
import static attendance.domain.AttendanceStatusType.LATE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import attendance.dto.response.AttendanceRecord;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

class AttendanceTest {

    @DisplayName("기본 날짜와 시간으로 출석을 생성할 수 있다.")
    @Test
    void 기본_날짜와_시간으로_출석을_생성할_수_있다() {
        assertThatCode(() -> new Attendance(LocalDate.MIN))
                .doesNotThrowAnyException();
    }

    @DisplayName("동일 날짜에 여부를 확인한다")
    @ParameterizedTest
    @CsvSource({
            "2024-12-05, 2024-12-05, true",
            "2024-12-05, 2024-12-06, false"
    })
    void 동일_날짜에_여부를_확인한다(LocalDate origin, LocalDate compare, boolean expected) {
        // given
        Attendance attendance = new Attendance(origin);

        // then
        assertThat(attendance.isEqualDate(compare))
                .isEqualTo(expected);
    }

    @DisplayName("출석 시간을 수정할 수 있다.")
    @ParameterizedTest
    @MethodSource
    void 출석_시간을_수정할_수_있다(LocalDate givenDate, LocalTime newTime, AttendanceStatusType status) {
        // given
        Attendance attendance = new Attendance(givenDate);

        // when
        attendance.updateTime(newTime);
        AttendanceRecord response = attendance.createResponse();

        // then
        assertThat(response.dateTime().toLocalTime())
                .isEqualTo(newTime);
        assertThat(response.status())
                .isEqualTo(status.getName());
    }


    static Stream<Arguments> 출석_시간을_수정할_수_있다() {
        return Stream.of(
                Arguments.of(LocalDate.of(2024, 12, 2), LocalTime.of(13, 0), ATTENDANCE),
                Arguments.of(LocalDate.of(2024, 12, 2), LocalTime.of(13, 6), LATE),
                Arguments.of(LocalDate.of(2024, 12, 2), LocalTime.of(13, 31), EXPULSION),
                Arguments.of(LocalDate.of(2024, 12, 3), LocalTime.of(10, 0), ATTENDANCE),
                Arguments.of(LocalDate.of(2024, 12, 3), LocalTime.of(10, 6), LATE),
                Arguments.of(LocalDate.of(2024, 12, 3), LocalTime.of(10, 31), EXPULSION)
        );
    }
}
