package attendance.domain;

import static org.assertj.core.api.Assertions.assertThat;

import attendance.dto.AttendanceResponse;
import java.time.LocalDate;
import java.util.stream.IntStream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AttendancesTest {

    @DisplayName("출석을 추가할 수 있다.")
    @Test
    void 출석을_추가할_수_있다() {
        Attendances attendances = new Attendances();
        LocalDate givenDate = LocalDate.of(2024, 12, 5);
        Attendance attendance = new Attendance(givenDate);

        attendances.add(attendance);
        AttendanceResponse actualResponse = attendances.find(givenDate).createResponse();

        assertThat(actualResponse.dateTime().toLocalDate()).isEqualTo(givenDate);
    }

    @DisplayName("해당 날짜의 출석을 찾을 수 있다.")
    @Test
    void 해당_날짜의_출석을_찾을_수_있다() {
        // given
        Attendances attendances = new Attendances();
        IntStream.range(1, 8)
                .mapToObj(day -> LocalDate.of(2024, 12, day))
                .forEach(date -> attendances.add(new Attendance(date)));
        LocalDate givenDate = LocalDate.of(2024, 12, 5);

        // when
        attendances.find(givenDate);
        AttendanceResponse actualResponse = attendances.find(givenDate).createResponse();

        // then
        assertThat(actualResponse.dateTime().toLocalDate()).isEqualTo(givenDate);
    }

}