package attendance.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AttendanceWarningTypeTest {

    @Test
    @DisplayName("출결 상황에 따라 알맞은 경고를 반환한다.")
    void 출결_상황에_따라_알맞은_경고를_반환한다() {
        // given
        int absence = 3;
        int late = 2;

        // when
        AttendanceWarningType result = AttendanceWarningType.find(absence, late);

        // then
        assertThat(result)
                .isEqualTo(AttendanceWarningType.COUNSELING);
    }
}
