package attendance.domain;

import attendance.utility.DateGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class AttendanceSystemTest {

    private DateGenerator dateGenerator;
    private AttendanceManager attendanceManager;
    private AttendanceSystem attendanceSystem;

    @BeforeEach
    void setup() {
        dateGenerator = new MockingDateGenerator();
        attendanceManager = new AttendanceManager(new Holiday(), dateGenerator);
        attendanceSystem = new AttendanceSystem(attendanceManager);
    }

    @ParameterizedTest(name = "출석 시간: {0} | 출석 상황 결과 : {1}")
    @MethodSource
    void 크루_출석_데이터로_출석_체크한다(LocalTime time, AttendanceStatusType expected) {
        // given
        String nickname = "이든";
        LocalDate nowDate = dateGenerator.now();

        attendanceManager.addCrew(nickname);
        LocalDateTime dateTime = LocalDateTime.of(nowDate, time);

        // when
        Attendance result = attendanceSystem.processAttendanceCheck(dateTime, nickname);

        // then
        assertThat(result.getDateTime()).isEqualTo(dateTime);
        assertThat(result.getStatus()).isEqualTo(expected);
    }

    @Test
    void 등록되지_않은_닉네임으로_출석_체크시_에러가_발생한다() {
        // given
        String nickname = "이든";

        // when & then
        assertThatIllegalArgumentException()
                .isThrownBy(() -> attendanceSystem.validateNicknameExists(nickname))
                .withMessage("[ERROR] 등록되지 않은 닉네임입니다.");
    }

    static Stream<Arguments> 크루_출석_데이터로_출석_체크한다() {
        return Stream.of(
                Arguments.of(LocalTime.of(10, 5), AttendanceStatusType.ATTENDANCE),
                Arguments.of(LocalTime.of(10, 30), AttendanceStatusType.LATE),
                Arguments.of(LocalTime.MAX, AttendanceStatusType.EXPULSION)
        );
    }

    private static class MockingDateGenerator implements DateGenerator {

        @Override
        public LocalDate now() {
            return LocalDate.of(2024, 12, 5);
        }
    }
}
