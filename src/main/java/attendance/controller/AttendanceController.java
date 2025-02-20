package attendance.controller;

import attendance.config.AppConfig;
import attendance.dto.AttendanceResponse;
import attendance.dto.AttendanceSearchResult;
import attendance.dto.AttendanceUpdateResult;
import attendance.dto.WarnedStudentResponses;
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
        attendanceService.initializeAttendance();

        LocalDate nowDate = dateGenerator.now();
        outputView.printMenu(nowDate);
        int command = Integer.parseInt(inputView.readMenuCommand()); // todo : 추후 검증

        if (command == 1) {
            AttendanceResponse response = attendanceService.processAttendance(nowDate);
            outputView.printAttendanceRecord(response);
        }
        if (command == 2) {
            AttendanceUpdateResult response = attendanceService.processUpdateAttendance(nowDate);
            outputView.printAttendUpdateResult(response);
        }
        if (command == 3) {
            AttendanceSearchResult response = attendanceService.processAttendanceSearch(nowDate);
            outputView.printAttendUpdateResult(response);
        }
        if (command == 4) {
            WarnedStudentResponses response = attendanceService.processWarnedStudent(nowDate);
//            outputView.printWarnedStudents(response);
        }
    }
}
