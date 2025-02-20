package attendance.view;

import java.util.Scanner;

public class InputView { // todo : 상수 분리, 최소한의 검증 후 반환

    private static final Scanner console = new Scanner(System.in);

    public String readMenuCommand() {
        return console.nextLine();
    }

    public String readNickname() {
        System.out.println("닉네임을 입력해 주세요.");
        return console.nextLine();
    }

    public String readAttendanceTime() {
        System.out.println("등교 시간을 입력해 주세요.");
        return console.nextLine();
    }

    public String readNickNameForUpdate() {
        System.out.println("출석을 수정하려는 크루의 닉네임을 입력해 주세요.");
        return console.nextLine();
    }

    public String readDateForUpdate() {
        System.out.println("수정하려는 날짜(일)를 입력해 주세요.");
        return console.nextLine();
    }

    public String readAttendanceTimeForUpdate() {
        System.out.println("언제로 변경하겠습니까?");
        return console.nextLine();
    }
}
