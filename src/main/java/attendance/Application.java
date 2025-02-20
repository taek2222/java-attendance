package attendance;

import attendance.config.AppConfig;
import attendance.controller.AttendanceController;

class Application {
    public static void main(String[] args) {
        AppConfig config = new AppConfig();
        AttendanceController controller = new AttendanceController(config);

        controller.run();
    }

    // todo : dto 안에 response 패키지를 추가해 뒤에 response 네이밍 제거 고려
    // todo : 위 진행후, Attendance -> AttendanceRecord 이런식의 변경 고려(view 레이어 통합)
}
