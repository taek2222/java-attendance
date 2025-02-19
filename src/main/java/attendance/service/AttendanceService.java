package attendance.service;

import attendance.domain.Attendance;
import attendance.domain.AttendanceManager;
import attendance.domain.Attendances;
import attendance.dto.AttendanceResponse;
import attendance.dto.AttendanceUpdateResult;
import attendance.utility.FileUtil;
import attendance.view.InputView;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class AttendanceService {

    private final InputView inputView;
    private final AttendanceManager attendanceManager;

    public AttendanceService(InputView inputView, AttendanceManager attendanceManager) {
        this.inputView = inputView;
        this.attendanceManager = attendanceManager;
    }

    public AttendanceResponse processAttendance(LocalDate nowDate) {
        String nickname = inputView.readNickname();
        Attendances attendances = this.attendanceManager.findCrewAttendance(nickname);

        String time = inputView.readAttendanceTime(); // todo : 파싱, 검증 필요
        LocalTime parseTime = LocalTime.parse(time);

        Attendance findAttendance = attendances.find(nowDate);
        findAttendance.updateTime(parseTime);
        return findAttendance.createResponse();
    }

    public AttendanceUpdateResult processUpdateAttendance(LocalDate nowDate) {
        String nickname = inputView.readNickNameForUpdate();
        Attendances attendances = this.attendanceManager.findCrewAttendance(nickname);

        int day = Integer.parseInt(inputView.readDateForUpdate());
        LocalDate newDate = nowDate.withDayOfMonth(day);

        String time = inputView.readAttendanceTimeForUpdate();
        LocalTime parseTime = LocalTime.parse(time);

        Attendance findAttendance = attendances.find(newDate);
        AttendanceResponse before = findAttendance.createResponse();

        findAttendance.updateTime(parseTime);

        return new AttendanceUpdateResult(before, findAttendance.createResponse());
    }

    public void initializeAttendance() {
        List<String> lines = FileUtil.readFile("attendances.csv");
        for (String line : lines) {
            List<String> nicknameAndDateTime = List.of(line.split(","));

            if (!attendanceManager.isContainNickname(nicknameAndDateTime.getFirst())) {
                attendanceManager.addCrew(nicknameAndDateTime.getFirst());
            }

            String dateTime = nicknameAndDateTime.get(1);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

            LocalDateTime parsedDateTime = LocalDateTime.parse(dateTime, formatter);

            Attendances attendances = attendanceManager.findCrewAttendance(nicknameAndDateTime.getFirst());
            Attendance attendance = attendances.find(parsedDateTime.toLocalDate());
            attendance.updateTime(parsedDateTime.toLocalTime());
        }
    }
}
