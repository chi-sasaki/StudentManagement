package raisetech.StudentManagement.date;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Course {
    private String courseId;
    private String studentId;
    private String courseName;
    private LocalDateTime courseStartDate;
    private LocalDateTime courseCompletionDate;
}