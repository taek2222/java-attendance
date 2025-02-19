package attendance.view;

import attendance.dto.AttendanceGroupByStatus;
import attendance.dto.AttendanceResponse;
import attendance.dto.AttendanceSearchResult;
import attendance.dto.AttendanceUpdateResult;
import attendance.dto.WarnedStudentResponse;
import attendance.dto.WarnedStudentResponses;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class OutputView {

    private static final String OUTPUT_MENU = """
            오늘은 %d월 %d일 %s입니다. 기능을 선택해 주세요.
            1. 출석 확인
            2. 출석 수정
            3. 크루별 출석 기록 확인
            4. 제적 위험자 확인
            Q. 종료
            """;

    public void printMenu(LocalDate today) {
        System.out.printf(OUTPUT_MENU,
                today.getMonthValue(),
                today.getDayOfMonth(),
                today.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREA)
        );
    }

    public void printAttendanceRecord(AttendanceResponse response) {

        LocalDateTime dateTime = response.dateTime();

        String timeContent = dateTime.toLocalTime().toString();
        if (dateTime.toLocalTime().equals(LocalTime.MIN)) {
            timeContent = "--:--";
        }

        System.out.printf("%d월 %d일 %s %s (%s)\n",
                dateTime.getMonthValue(),
                dateTime.getDayOfMonth(),
                dateTime.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREA),
                timeContent,
                response.status()
        );
    }

    public void printAttendUpdateResult(AttendanceUpdateResult result) {
        printAttendanceRecord(result.before());
        System.out.printf(" -> %s (%s) 수정 완료",
                result.after().dateTime().toLocalTime(),
                result.after().status()
        );
    }

    public void printAttendUpdateResult(AttendanceSearchResult result) {
        System.out.printf("이번 달 %s의 출석 기록입니다.\n\n", result.nickname());
        List<AttendanceResponse> response = result.responses();

        response.forEach(this::printAttendanceRecord);
        System.out.println();

        AttendanceGroupByStatus groupByStatus = result.groupByStatus();
        Map<String, Integer> countByStatus = groupByStatus.countByStatus();
        countByStatus.entrySet().forEach(this::printStatusCount);
        System.out.println();

        System.out.printf("%s 대상자입니다.", groupByStatus.warning()); // todo : 출력 순서랑 이후 날짜 중재
    }

    private void printStatusCount(Map.Entry<String, Integer> statusCount) {
        System.out.println(statusCount.getKey() + ": " + statusCount.getValue() + "회");
    }

    public void printWarnedStudents(WarnedStudentResponses response) {
        System.out.println("제적 위험자 조회 결과");
        List<WarnedStudentResponse> warnedStudents = response.responses();
        warnedStudents.forEach(student -> {
            System.out.printf("- %s: 결석 %d회, 지각 %d회 (%s)\n",
                    student.name(),
                    student.attendanceGroupByStatus().countByStatus().get("결석"),
                    student.attendanceGroupByStatus().countByStatus().get("지각"),
                    student.attendanceGroupByStatus().warning()
            );
        });

    }
}
