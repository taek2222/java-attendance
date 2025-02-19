package attendance.utility;

import java.time.LocalDate;

public class CurrentDateGeneratorImpl implements DateGenerator {

    @Override
    public LocalDate now() {
        return LocalDate.now();
    }
}
