package raisetech.StudentManagement.domain;

import lombok.Getter;
import lombok.Setter;
import raisetech.StudentManagement.date.Course;
import raisetech.StudentManagement.date.Student;

import java.util.List;

@Getter
@Setter
public class StudentDetail {
    private Student student;
    private List<Course> course;
}
