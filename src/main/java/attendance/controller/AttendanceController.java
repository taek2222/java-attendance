package attendance.controller;

import attendance.config.AppConfig;
import attendance.domain.Attendance;
import attendance.domain.AttendanceInit;
import attendance.domain.AttendanceManager;
import attendance.domain.Attendances;
import attendance.dto.response.AttendanceGroupByStatus;
import attendance.dto.response.AttendanceRecord;
import attendance.dto.response.AttendanceRecordUntilToday;
import attendance.dto.response.AttendanceSearchResult;
import attendance.dto.response.AttendanceUpdateResult;
import attendance.dto.response.WarnedStudents;
import attendance.utility.DateGenerator;
import attendance.utility.DateTimeParser;
import attendance.view.InputView;
import attendance.view.OutputView;

import java.time.LocalDate;
import java.time.LocalTime;

import static attendance.controller.AttendanceMenu.CHECK;
import static attendance.controller.AttendanceMenu.QUIT;
import static attendance.controller.AttendanceMenu.SEARCH;
import static attendance.controller.AttendanceMenu.UPDATE;
import static attendance.controller.AttendanceMenu.WARNED_CREW;
import static attendance.controller.AttendanceMenu.find;
import static attendance.utility.DateTimeParser.parseDateByDay;

public class AttendanceController {

    private final InputView inputView;
    private final OutputView outputView;
    private final DateGenerator dateGenerator;
    private final AttendanceManager attendanceManager;

    public AttendanceController(AppConfig appConfig) {
        this.inputView = appConfig.getInputView();
        this.outputView = appConfig.getOutputView();
        this.dateGenerator = appConfig.getDateGenerator();
        this.attendanceManager = appConfig.getAttendanceManager();
    }

    public void run() {
        AttendanceInit attendanceInit = new AttendanceInit(attendanceManager);
        attendanceInit.initAttendances();

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

    private void processAttendanceCheck(AttendanceMenu menu, LocalDate today) {
        if (menu == CHECK) {
            AttendanceRecord response = processAttendance(today);
            outputView.printAttendanceRecord(response);
        }
    }

    private void processAttendanceWarnedCrew(AttendanceMenu menu, LocalDate today) {
        if (menu == WARNED_CREW) {
            WarnedStudents response = processWarnedStudent(today);
            outputView.printWarnedStudents(response);
        }
    }

    private void processAttendanceSearch(AttendanceMenu menu, LocalDate today) {
        if (menu == SEARCH) {
            AttendanceSearchResult response = processAttendanceSearch(today);
            outputView.printAttendUpdateResult(response);
        }
    }

    private void processAttendanceUpdate(AttendanceMenu menu, LocalDate today) {
        if (menu == UPDATE) {
            AttendanceUpdateResult response = processUpdateAttendance(today);
            outputView.printAttendUpdateResult(response);
        }
    }

    private AttendanceMenu selectMenu(LocalDate today) {
        outputView.printMenu(today);
        return find(inputView.readMenuCommand());
    }

    public AttendanceRecord processAttendance(LocalDate today) {
        Attendances attendances = findAttendance(false);

        Attendance findAttendance = attendances.find(today);
        validateAlreadyAttendance(findAttendance);

        LocalTime parseTime = parseTime(false);

        findAttendance.updateTime(parseTime);
        return findAttendance.createResponse();
    }

    public AttendanceUpdateResult processUpdateAttendance(LocalDate today) {
        Attendances attendances = findAttendance(true);

        LocalDate date = parseDate(today);
        LocalTime time = parseTime(true);

        Attendance findAttendance = attendances.find(date);
        AttendanceRecord before = findAttendance.createResponse();

        findAttendance.updateTime(time);
        return new AttendanceUpdateResult(before, findAttendance.createResponse());
    }

    private LocalDate parseDate(LocalDate today) {
        int day = inputView.readDateForUpdate();
        return parseDateByDay(today, day);
    }

    public AttendanceSearchResult processAttendanceSearch(LocalDate today) {
        String nickname = inputView.readNickname(false);
        Attendances attendance = attendanceManager.findCrewAttendance(nickname);

        AttendanceRecordUntilToday recordUntilToday = attendance.createRecordUntilTodayResponse(today);
        AttendanceGroupByStatus groupByStatus = attendance.createCountUntilYesterday(today);
        return new AttendanceSearchResult(nickname, recordUntilToday, groupByStatus);
    }

    public WarnedStudents processWarnedStudent(LocalDate today) {
        return attendanceManager.searchWarnedCrews(today);
    }

    private Attendances findAttendance(boolean isForUpdated) {
        String nickname = inputView.readNickname(isForUpdated);
        return attendanceManager.findCrewAttendance(nickname);
    }

    private LocalTime parseTime(boolean isForUpdated) {
        String time = inputView.readAttendanceTime(isForUpdated);
        return DateTimeParser.parseTime(time);
    }

    private void validateAlreadyAttendance(Attendance attendance) {
        if (attendance.isAlreadyCheck()) {
            throw new IllegalArgumentException("[ERROR] 이미 출석을 완료하셨습니다. 수정 기능을 이용해주세요.");
        }
    }
}
