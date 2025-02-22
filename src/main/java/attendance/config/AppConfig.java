package attendance.config;

import attendance.domain.AttendanceManager;
import attendance.domain.AttendanceSystem;
import attendance.domain.Holiday;
import attendance.utility.CurrentDateGeneratorImpl;
import attendance.utility.DateGenerator;
import attendance.view.InputView;
import attendance.view.OutputView;

public class AppConfig {

    private final InputView inputView;
    private final OutputView outputView;
    private final Holiday holiday;
    private final DateGenerator dateGenerator;
    private final AttendanceManager attendanceManager;
    private final AttendanceSystem attendanceSystem;

    public AppConfig() {
        this.inputView = new InputView();
        this.outputView = new OutputView();
        this.holiday = new Holiday();
        this.dateGenerator = new CurrentDateGeneratorImpl();
        this.attendanceManager = new AttendanceManager(holiday, dateGenerator);
        this.attendanceSystem = new AttendanceSystem(attendanceManager);
    }

    public InputView getInputView() {
        return inputView;
    }

    public OutputView getOutputView() {
        return outputView;
    }

    public DateGenerator getDateGenerator() {
        return dateGenerator;
    }

    public Holiday getHoliday() {
        return holiday;
    }

    public AttendanceManager getAttendanceManager() {
        return attendanceManager;
    }

    public AttendanceSystem getAttendanceSystem() {
        return attendanceSystem;
    }
}
