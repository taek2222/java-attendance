package attendance.service;

import static attendance.utility.DateTimeParser.parseDateByDay;

import attendance.domain.Attendance;
import attendance.domain.AttendanceManager;
import attendance.domain.Attendances;
import attendance.dto.response.AttendanceGroupByStatus;
import attendance.dto.response.AttendanceRecordUntilToday;
import attendance.dto.response.AttendanceRecord;
import attendance.dto.response.AttendanceSearchResult;
import attendance.dto.response.AttendanceUpdateResult;
import attendance.dto.response.WarnedStudents;
import attendance.utility.DateTimeParser;
import attendance.view.InputView;
import java.time.LocalDate;
import java.time.LocalTime;

public class AttendanceService { // todo : 최대한 메서드 분리 작업

    private final InputView inputView;
    private final AttendanceManager attendanceManager;

    public AttendanceService(InputView inputView, AttendanceManager attendanceManager) {
        this.inputView = inputView;
        this.attendanceManager = attendanceManager;
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
