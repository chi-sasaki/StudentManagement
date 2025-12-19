package raisetech.StudentManagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import raisetech.StudentManagement.controller.converter.StudentConverter;
import raisetech.StudentManagement.date.Course;
import raisetech.StudentManagement.date.Student;
import raisetech.StudentManagement.service.StudentService;

import java.util.List;

@Controller
public class StudentController {

    private StudentService service;
    private StudentConverter converter;

    @Autowired
    public StudentController(StudentService service, StudentConverter converter) {
        this.service = service;
        this.converter = converter;
    }

    @GetMapping("/studentList")
    public String getStudentList(Model model) {
        List<Student> students = service.searchStudentList();
        List<Course> courses = service.searchCourseList();

        model.addAttribute("studentList", converter.convertStudentDetails(students, courses));
        return "studentList";
    }

    @GetMapping("/courseList")
    public List<Course> getCourseList() {
        return service.searchCourseList();
    }
}
