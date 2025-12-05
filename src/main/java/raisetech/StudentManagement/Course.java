package raisetech.StudentManagement;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Course {
    private String courseId;
    private String studentId;
    private String courseName;
    private String courseStartDate;
    private String courseCompletionDate;
}