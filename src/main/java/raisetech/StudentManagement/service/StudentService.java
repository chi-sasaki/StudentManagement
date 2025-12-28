package raisetech.StudentManagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import raisetech.StudentManagement.date.Course;
import raisetech.StudentManagement.date.Student;
import raisetech.StudentManagement.domain.StudentDetail;
import raisetech.StudentManagement.repository.StudentRepository;

import java.time.LocalDate;
import java.util.List;

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

    public List<Course> searchCourseList() {
        return repository.course();
    }

    /**
     *指定された学生IDに紐づく学生情報および受講コース情報を取得する。
     * 学生基本情報は学生IDをキーに取得し、取得した学生IDを用いて受講コース情報を検索する。
     * 取得した情報はStudentDetailにまとめて返却する。
     *
     * @param studentId 検索対象となる学生ID
     * @return 学生情報と受講コース情報を保持したStudentDetail
     */
    public StudentDetail searchStudent(String studentId) {
        Student student = repository.searchStudent(studentId);
        List<Course> course = repository.searchStudentCourse(student.getStudentId());
        StudentDetail studentDetail = new StudentDetail();
        studentDetail.setStudent(student);
        studentDetail.setCourse(course);
        return studentDetail;
    }

    /**
     * 学生情報および受講コース情報を新規登録する。
     * 学生情報を先にデータベースへ登録し、自動生成された学生IDを受講コース情報に設定した上で、コース情報を登録する。
     * 本処理はトランザクション管理されており、途中でエラーが発生した場合はすべての登録処理がロールバックされる。
     *
     * @param studentDetail 登録対象の学生情報および受講コース情報を保持する
     */

    @Transactional
    public void registerStudent(StudentDetail studentDetail) {
        repository.registerStudent(studentDetail.getStudent());
        for(Course course : studentDetail.getCourse()){
            course.setStudentId(studentDetail.getStudent().getStudentId());
            course.setCourseStartDate(LocalDate.now());
            course.setCourseCompletionDate(LocalDate.now().plusYears(1));
        repository.registerCourse(course);
        }
    }

    @Transactional
    public void updateStudent(StudentDetail studentDetail) {
        repository.updateStudent(studentDetail.getStudent());
        for(Course course : studentDetail.getCourse()){
            repository.updateCourse(course);
        }
    }
}
    /*
    public void registerStudent(Student student) {
        // UUIDオブジェクト（IDの数列）をtoString()でString型に変換する
        student.setStudentId(UUID.randomUUID().toString());
        // データベースに学生情報を挿入
        repository.insertStudent(student);
    }

    public void registerCourse(Course course) {
        // UUIDオブジェクト（IDの数列）をtoString()でString型に変換する
        course.setCourseId(UUID.randomUUID().toString());
        repository.insertCourse(course);
        }
     */
