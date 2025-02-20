package attendance.service;

import attendance.domain.Attendance;
import attendance.domain.AttendanceManager;
import attendance.domain.Attendances;
import attendance.dto.AttendanceGroupByStatus;
import attendance.dto.AttendanceResponse;
import attendance.dto.AttendanceResponses;
import attendance.dto.AttendanceSearchResult;
import attendance.dto.AttendanceUpdateResult;
import attendance.dto.WarnedStudentResponses;
import attendance.view.InputView;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class AttendanceService { // todo : 최대한 메서드 분리 작업

    private final InputView inputView;
    private final AttendanceManager attendanceManager;

    public AttendanceService(InputView inputView, AttendanceManager attendanceManager) {
        this.inputView = inputView;
        this.attendanceManager = attendanceManager;
    }

    public AttendanceResponse processAttendance(LocalDate today) {
        String nickname = inputView.readNickname();
        Attendances attendances = this.attendanceManager.findCrewAttendance(nickname);

        Attendance findAttendance = attendances.find(today);
        validateAlreadyAttendance(findAttendance);

        String time = inputView.readAttendanceTime(); // todo : 파싱, 검증 필요
        LocalTime parseTime = LocalTime.parse(time);

        findAttendance.updateTime(parseTime);
        return findAttendance.createResponse();
    }

    public AttendanceUpdateResult processUpdateAttendance(LocalDate today) {
        String nickname = inputView.readNickNameForUpdate();
        Attendances attendances = this.attendanceManager.findCrewAttendance(nickname);

        int day = inputView.readDateForUpdate();
        LocalDate newDate = today.withDayOfMonth(day); // todo : 파싱, 검증 필요

        String time = inputView.readAttendanceTimeForUpdate();
        LocalTime parseTime = LocalTime.parse(time); // todo : 파싱, 검증 필요

        Attendance findAttendance = attendances.find(newDate);
        AttendanceResponse before = findAttendance.createResponse();

        findAttendance.updateTime(parseTime);

        return new AttendanceUpdateResult(before, findAttendance.createResponse());
    }

    public AttendanceSearchResult processAttendanceSearch(LocalDate today) {
        String nickname = inputView.readNickNameForUpdate();
        Attendances attendance = attendanceManager.findCrewAttendance(nickname);
        AttendanceResponses response = attendance.createResponse();
        List<AttendanceResponse> filteredResponses = response.responses().stream()
                .filter(attendanceResponse -> attendanceResponse.dateTime().toLocalDate().isBefore(today))
                .toList();

        AttendanceGroupByStatus groupByStatus = attendance.createCountUntilYesterday(today);

        return new AttendanceSearchResult(nickname, filteredResponses, groupByStatus);
    }

    public WarnedStudentResponses processWarnedStudent(LocalDate today) {
        return attendanceManager.searchWarnedCrews(today);
    }

    private void validateAlreadyAttendance(Attendance attendance) {
        if (attendance.isAlreadyCheck()) {
            throw new IllegalArgumentException("[ERROR] 이미 출석을 완료하셨습니다. 수정 기능을 이용해주세요.");
        }
    }
}
