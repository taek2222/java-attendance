package attendance.domain;

import static attendance.domain.AttendanceStatusType.ATTENDANCE;
import static attendance.domain.AttendanceStatusType.EXPULSION;
import static attendance.domain.AttendanceStatusType.LATE;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class AttendanceStatusTypeTest {
    @ParameterizedTest
    @MethodSource()
    @DisplayName("임계값에 해당하는 출결 상태를 반환한다.")
    void 임계값에_해당하는_출결_상태를_반환한다(int threshold, AttendanceStatusType type) {
        // when
        AttendanceStatusType result = AttendanceStatusType.find(threshold);

        // then
        assertThat(result)
                .isEqualTo(type);
    }

    static Stream<Arguments> 임계값에_해당하는_출결_상태를_반환한다() {
        return Stream.of(
                Arguments.of(0, ATTENDANCE),
                Arguments.of(6, LATE),
                Arguments.of(31, EXPULSION)
        );
    }
}
