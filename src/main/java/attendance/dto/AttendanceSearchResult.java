package attendance.dto;

import java.util.List;

public record AttendanceSearchResult(
        String nickname,
        List<AttendanceResponse> responses
) {

}
