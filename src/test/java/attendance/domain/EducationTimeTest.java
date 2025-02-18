package attendance.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;

import java.time.LocalDateTime;

class EducationTimeTest {

    @ParameterizedTest
    @DisplayName("요일에 알맞은 지각 시간을 반환한다.")
    void 요일에_알맞은_지각_시간을_반환한다() {
        // given
        LocalDateTime attendanceTime = LocalDateTime.of(2024, 12, 2, 13, 6, 0);

        // when
        int result = EducationTime.calculateOverTime(attendanceTime);

        // then
        Assertions.assertThat(result)
                .isEqualTo(6);
    }

}
