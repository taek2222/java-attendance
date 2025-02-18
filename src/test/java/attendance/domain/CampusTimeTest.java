package attendance.domain;

import java.time.LocalTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CampusTimeTest {

    @Test
    @DisplayName("캠퍼스 운영시간이 아니라면 예외를 발생한다.")
    void 캠퍼스_운영시간이_아니라면_예외를_발생한다() {
        Assertions.assertThatIllegalArgumentException()
                .isThrownBy(() -> CampusTime.validateOperationTime(LocalTime.of(7, 59)))
                .withMessage("[ERROR] 캠퍼스 운영시간이 아닙니다.");
    }

}