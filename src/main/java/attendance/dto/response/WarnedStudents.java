package attendance.dto.response;

import java.util.List;

public record WarnedStudents(
        List<WarnedStudent> students
) {

}
