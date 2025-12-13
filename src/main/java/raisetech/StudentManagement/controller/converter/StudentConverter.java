package raisetech.StudentManagement.controller.converter;

import org.springframework.stereotype.Component;
import raisetech.StudentManagement.date.Course;
import raisetech.StudentManagement.date.Student;
import raisetech.StudentManagement.domain.StudentDetail;

import java.util.ArrayList;
import java.util.List;

@Component
public class StudentConverter {

    public List<StudentDetail> convertStudentDetails(List<Student> students, List<Course> courses) {
        List<StudentDetail> studentDetails = new ArrayList<>();
        for (Student student : students) {
            StudentDetail studentDetail = new StudentDetail();
            studentDetail.setStudent(student);

            List<Course> convertCourses = new ArrayList<>();
            for (Course course : courses) {
                if (student.getStudentId().equals(course.getStudentId())) {
                    convertCourses.add(course);
                }
            }
            studentDetail.setCourse(convertCourses);
            studentDetails.add(studentDetail);
        }
        return studentDetails;
    }
}
