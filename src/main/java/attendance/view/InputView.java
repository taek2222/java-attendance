package attendance.view;

import static attendance.view.InputValidator.validateIsNumeric;

import java.util.Scanner;

public class InputView {

    private static final String NEW_LINE = System.lineSeparator();
    private static final Scanner console = new Scanner(System.in);

    public String readMenuCommand() {
        return console.nextLine();
    }

    public String readNickname(boolean isForUpdated) {
        if (!isForUpdated) {
            System.out.println(NEW_LINE + "닉네임을 입력해 주세요.");
        }

        if (isForUpdated) {
            System.out.println(NEW_LINE + "출석을 수정하려는 크루의 닉네임을 입력해 주세요.");
        }
        return console.nextLine();
    }

    public String readAttendanceTime(boolean isForUpdated) {
        if (!isForUpdated) {
            System.out.println("등교 시간을 입력해 주세요");
        }

        if (isForUpdated) {
            System.out.println("언제로 변경하겠습니까?");
        }
        return console.nextLine();
    }

    public int readDateForUpdate() {
        System.out.println("수정하려는 날짜(일)를 입력해 주세요.");
        String input = console.nextLine();

        validateIsNumeric(input);
        return Integer.parseInt(input);
    }
}
