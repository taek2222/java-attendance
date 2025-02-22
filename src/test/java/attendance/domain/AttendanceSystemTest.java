package attendance.domain;

import attendance.utility.DateGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;

class AttendanceSystemTest {

    @DisplayName("크루의 출석 데이터를 저장한다.")
    @Test
    void 크루의_출석_데이터를_저장한다() {
        // given
        DateGenerator dateGenerator = new MockingDateGenerator();
        AttendanceManager attendanceManager = new AttendanceManager(
                new Holiday(),
                dateGenerator
        );

        String nickname = "이든";

        attendanceManager.addCrew(nickname);
        AttendanceSystem attendanceSystem = new AttendanceSystem(attendanceManager);

        LocalDate nowDate = dateGenerator.now();
        LocalDateTime givenDateTime = LocalDateTime.of(nowDate, LocalTime.of(10, 6));

        // when
        Attendance result = attendanceSystem.processAttendanceCheck(givenDateTime, nickname);

        // then
        assertThat(result.getDateTime())
                .isEqualTo(givenDateTime);

        assertThat(result.getStatus())
                .isEqualTo(AttendanceStatusType.LATE);
    }

    private static class MockingDateGenerator implements DateGenerator {

        @Override
        public LocalDate now() {
            return LocalDate.of(2024, 12, 5);
        }
    }
}
