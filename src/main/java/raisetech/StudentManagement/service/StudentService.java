package raisetech.StudentManagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import raisetech.StudentManagement.date.Course;
import raisetech.StudentManagement.date.Student;
import raisetech.StudentManagement.repository.StudentRepository;

import java.util.List;
import java.util.UUID;

@Service
public class StudentService {
    private StudentRepository repository;

    @Autowired
    public StudentService(StudentRepository repository) {
        this.repository = repository;
    }

    public List<Student> searchStudentList() {
        return repository.search();
    }

    /**
     *新しい学生を登録する
     * 学生情報に一意なIDを自動生成し、データベースに保存する
     *
     * @param student 登録対象の学生情報を保持する
     */
    public void registerStudent(Student student) {
        // UUIDオブジェクト（IDの数列）をtoString()でString型に変換する
        student.setStudentId(UUID.randomUUID().toString());
        // データベースに学生情報を挿入
        repository.insertStudent(student);
    }

    public List<Course> searchCourseList() {
        return repository.course();
    }

    public void registerCourse(Course course) {
        // UUIDオブジェクト（IDの数列）をtoString()でString型に変換する
        course.setCourseId(UUID.randomUUID().toString());
        repository.insertCourse(course);
        }
    }
