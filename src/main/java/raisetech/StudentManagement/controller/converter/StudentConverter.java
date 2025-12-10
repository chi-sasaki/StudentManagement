package raisetech.StudentManagement.controller.converter;

import org.springframework.stereotype.Component;
import raisetech.StudentManagement.date.Course;
import raisetech.StudentManagement.date.Student;
import raisetech.StudentManagement.domain.StudentDetail;

import java.util.ArrayList;
import java.util.List;

@Component
public class StudentConverter {

    public List<StudentDetail> convertStudentDetails(List<Student> Student, List<Course> courses) {
        List<StudentDetail> studentDetails = new ArrayList<>();
        for (Student student : Student) {
            StudentDetail studentDetail = new StudentDetail();
            studentDetail.setStudent(student);

            List<Course> counvertCourses = new ArrayList<>();
            for (Course Course : courses) {
                if (student.getStudentId().equals(Course.getStudentId())) {
                    counvertCourses.add(Course);
                }
            }
            studentDetail.setCourse(counvertCourses);
            studentDetails.add(studentDetail);
        }
        return studentDetails;
    }
}
