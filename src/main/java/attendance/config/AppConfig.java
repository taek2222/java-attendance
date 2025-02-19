package attendance.config;

import attendance.domain.Attendances;
import attendance.domain.Holiday;
import attendance.service.AttendanceService;
import attendance.utility.CurrentDateGeneratorImpl;
import attendance.utility.DateGenerator;
import attendance.view.InputView;
import attendance.view.OutputView;

public class AppConfig {

    private final InputView inputView;
    private final OutputView outputView;
    private final Holiday holiday;
    private final DateGenerator dateGenerator;
    private final Attendances attendances;
    private final AttendanceService attendanceService;

    public AppConfig() {
        this.inputView = new InputView();
        this.outputView = new OutputView();
        this.holiday = new Holiday();
        this.dateGenerator = new CurrentDateGeneratorImpl();
        this.attendances = new Attendances(holiday, dateGenerator);
        this.attendanceService = new AttendanceService(inputView, attendances);
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

    public AttendanceService getAttendanceService() {
        return attendanceService;
    }
}
