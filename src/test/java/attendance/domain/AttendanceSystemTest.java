package attendance.domain;

import attendance.utility.CurrentDateGeneratorImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;

class AttendanceSystemTest {

    @DisplayName("크루의 출석 데이터를 저장한다.")
    @Test
    void 크루의_출석_데이터를_저장한다() {
        // given
        AttendanceManager attendanceManager = new AttendanceManager(
                new Holiday(),
                new CurrentDateGeneratorImpl()
        );

        AttendanceInit attendanceInit = new AttendanceInit(attendanceManager);
        attendanceInit.initAttendances();

        AttendanceSystem attendanceSystem = new AttendanceSystem(attendanceManager);

        LocalTime givenTime = LocalTime.of(10, 8);
        String nickname = "이든";

        // when
        Attendance result = attendanceSystem.processAttendanceCheck(givenTime, nickname);

        // then
        assertThat(result.getDateTime().toLocalTime())
                .isEqualTo(givenTime);

        assertThat(result.getStatus())
                .isEqualTo(AttendanceStatusType.LATE);
    }
}
