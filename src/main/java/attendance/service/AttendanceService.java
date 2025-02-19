package attendance.service;

import attendance.domain.Attendance;
import attendance.domain.AttendanceManager;
import attendance.dto.AttendanceResponse;
import attendance.view.InputView;
import java.time.LocalDate;
import java.time.LocalTime;
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
        List<Attendance> attendances = this.attendanceManager.findCrewAttendance(nickname);

        String time = inputView.readAttendanceTime(); // todo : 파싱, 검증 필요
        LocalTime parseTime = LocalTime.parse(time);

        Attendance findAttendance = attendances.stream()
                .filter(attendance -> attendance.isEqualDate(nowDate))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 서버 오류가 발생했습니다."));

        findAttendance.updateTime(parseTime);
        return findAttendance.createResponse();
    }
}
