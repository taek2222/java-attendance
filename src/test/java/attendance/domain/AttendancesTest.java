package attendance.domain;

import attendance.dto.response.AttendanceRecord;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class AttendancesTest {

    @DisplayName("출석을 추가할 수 있다.")
    @Test
    void 출석을_추가할_수_있다() {
        Attendances attendances = new Attendances();
        LocalDateTime givenDateTime = LocalDateTime.of(2024, 12, 5, 10, 0);
        Attendance attendance = new Attendance(givenDateTime);

        attendances.add(attendance);
        AttendanceRecord actualResponse = attendances.find(givenDateTime.toLocalDate()).createResponse();

        assertThat(actualResponse.dateTime().toLocalDate()).isEqualTo(givenDateTime);
    }

    @DisplayName("해당 날짜의 출석을 찾을 수 있다.")
    @Test
    void 해당_날짜의_출석을_찾을_수_있다() {
        // given
        Attendances attendances = new Attendances();
        IntStream.range(1, 8)
                .mapToObj(day -> LocalDateTime.of(2024, 12, day, 10, 0))
                .forEach(dateTime -> attendances.add(new Attendance(dateTime)));
        LocalDate givenDate = LocalDate.of(2024, 12, 5);

        // when
        attendances.find(givenDate);
        AttendanceRecord actualResponse = attendances.find(givenDate).createResponse();

        // then
        assertThat(actualResponse.dateTime().toLocalDate()).isEqualTo(givenDate);
    }

    @Test
    void 이미_출석한_크루의_출석_확인할_경우_에러가_발생한다() {
        // given
        Attendances attendances = new Attendances();

        LocalDateTime dateTime = LocalDateTime.of(2024, 12, 5, 10, 0, 0);
        Attendance attendance = new Attendance(dateTime);

        attendances.add(attendance);

        // when & then
        assertThatIllegalArgumentException()
                .isThrownBy(() -> attendances.checkAndUpdateAttendance(dateTime))
                .withMessage("[ERROR] 이미 출석을 완료하셨습니다. 수정 기능을 이용해주세요.");
    }
}
