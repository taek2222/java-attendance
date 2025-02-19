package attendance.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

class HolidayTest {

    @DisplayName("등교일이 아닌 경우 예외가 발생한다")
    @Test
    void 등교일이_아닌_경우_예외가_발생한다() {
        // given
        LocalDate date = LocalDate.of(2024, 12, 1);
        Holiday holiday = new Holiday();

        // when & then
        Assertions.assertThatIllegalArgumentException()
                .isThrownBy(() -> holiday.validateHoliday(date))
                .withMessage("[ERROR] 12월 1일 일요일은 등교일이 아닙니다.");
    }

}
