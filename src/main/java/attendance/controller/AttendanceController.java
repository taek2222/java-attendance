package attendance.controller;

import static attendance.controller.AttendanceMenu.CHECK;
import static attendance.controller.AttendanceMenu.QUIT;
import static attendance.controller.AttendanceMenu.SEARCH;
import static attendance.controller.AttendanceMenu.UPDATE;
import static attendance.controller.AttendanceMenu.WARNED_CREW;
import static attendance.controller.AttendanceMenu.find;

import attendance.config.AppConfig;
import attendance.dto.AttendanceResponse;
import attendance.dto.AttendanceSearchResult;
import attendance.dto.AttendanceUpdateResult;
import attendance.dto.WarnedStudentResponses;
import attendance.service.AttendanceInitService;
import attendance.service.AttendanceService;
import attendance.utility.DateGenerator;
import attendance.view.InputView;
import attendance.view.OutputView;
import java.time.LocalDate;

public class AttendanceController {

    private final InputView inputView;
    private final OutputView outputView;
    private final DateGenerator dateGenerator;
    private final AttendanceInitService attendanceInitService;
    private final AttendanceService attendanceService;

    public AttendanceController(AppConfig appConfig) {
        this.inputView = appConfig.getInputView();
        this.outputView = appConfig.getOutputView();
        this.dateGenerator = appConfig.getDateGenerator();
        this.attendanceInitService = appConfig.getAttendanceInitService();
        this.attendanceService = appConfig.getAttendanceService();
    }

    public void run() {
        attendanceInitService.initAttendances();

        while (true) {
            LocalDate today = dateGenerator.now();
            AttendanceMenu menu = selectMenu(today);

            processAttendance(menu, today);
            if (menu == QUIT) {
                return;
            }
        }
    }

    private void processAttendance(AttendanceMenu menu, LocalDate today) {
        processAttendanceCheck(menu, today);
        processAttendanceUpdate(menu, today);
        processAttendanceSearch(menu, today);
        processAttendanceWarnedCrew(menu, today);
    }

    private void processAttendanceWarnedCrew(AttendanceMenu menu, LocalDate today) {
        while (true) {
            try {
                if (menu == WARNED_CREW) {
                    WarnedStudentResponses response = attendanceService.processWarnedStudent(today);
                    outputView.printWarnedStudents(response);
                }
                return;
            } catch (IllegalArgumentException e) {
                outputView.printErrorMessage(e.getMessage());
            }
        }
    }

    private void processAttendanceSearch(AttendanceMenu menu, LocalDate today) {
        while (true) {
            try {
                if (menu == SEARCH) {
                    AttendanceSearchResult response = attendanceService.processAttendanceSearch(today);
                    outputView.printAttendUpdateResult(response);
                }
                return;
            } catch (IllegalArgumentException e) {
                outputView.printErrorMessage(e.getMessage());
            }
        }
    }

    private void processAttendanceUpdate(AttendanceMenu menu, LocalDate today) {
        while (true) {
            try {
                if (menu == UPDATE) {
                    AttendanceUpdateResult response = attendanceService.processUpdateAttendance(today);
                    outputView.printAttendUpdateResult(response);
                }
                return;
            } catch (IllegalArgumentException e) {
                outputView.printErrorMessage(e.getMessage());
            }
        }
    }

    private void processAttendanceCheck(AttendanceMenu menu, LocalDate today) {
        while (true) {
            try {
                if (menu == CHECK) {
                    AttendanceResponse response = attendanceService.processAttendance(today);
                    outputView.printAttendanceRecord(response);
                }
                return;
            } catch (IllegalArgumentException e) {
                outputView.printErrorMessage(e.getMessage());
            }
        }
    }

    private AttendanceMenu selectMenu(LocalDate today) {
        while (true) {
            try {
                outputView.printMenu(today);
                return find(inputView.readMenuCommand());
            } catch (IllegalArgumentException e) {
                outputView.printErrorMessage(e.getMessage());
            }
        }
    }
}
