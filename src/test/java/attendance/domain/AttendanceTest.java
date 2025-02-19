package attendance.domain;

import static org.assertj.core.api.Assertions.assertThatCode;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AttendanceTest {

    @DisplayName("기본 날짜와 시간으로 출석을 생성할 수 있다.")
    @Test
    void 기본_날짜와_시간으로_출석을_생성할_수_있다() {
        assertThatCode(Attendance::new)
                .doesNotThrowAnyException();
    }
}