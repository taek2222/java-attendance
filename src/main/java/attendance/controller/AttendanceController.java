package attendance.controller;

import attendance.config.AppConfig;
import attendance.dto.AttendanceResponse;
import attendance.service.AttendanceService;
import attendance.utility.DateGenerator;
import attendance.view.InputView;
import attendance.view.OutputView;

import java.time.LocalDate;

public class AttendanceController {

    private final InputView inputView;
    private final OutputView outputView;
    private final DateGenerator dateGenerator;
    private final AttendanceService attendanceService;

    public AttendanceController(AppConfig appConfig) {
        this.inputView = appConfig.getInputView();
        this.outputView = appConfig.getOutputView();
        this.dateGenerator = appConfig.getDateGenerator();
        this.attendanceService = appConfig.getAttendanceService();
    }

    public void run() {
        LocalDate nowDate = dateGenerator.now();
        outputView.printMenu(nowDate);
        int command = Integer.parseInt(inputView.readMenuCommand()); // todo : 추후 검증

        if (command == 1) {
            AttendanceResponse response = attendanceService.processAttendance(nowDate);
            outputView.printAttendanceRecord(response);
        }
    }
}
