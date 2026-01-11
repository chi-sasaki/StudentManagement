package raisetech.StudentManagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import raisetech.StudentManagement.controller.converter.StudentConverter;
import raisetech.StudentManagement.date.Student;
import raisetech.StudentManagement.date.StudentCourse;
import raisetech.StudentManagement.domain.StudentDetail;
import raisetech.StudentManagement.exceptionhandler.UserNotFoundException;
import raisetech.StudentManagement.repository.StudentRepository;

import java.time.LocalDate;
import java.util.List;

/**
 * 受講生の検索や登録、更新などを行うREST APIとして受け付けるControllerです。
 */
@Service
public class StudentService {
    private StudentRepository repository;
    private StudentConverter converter;

    @Autowired
    public StudentService(StudentRepository repository, StudentConverter converter) {
        this.repository = repository;
        this.converter = converter;
    }

    public List<StudentDetail> searchStudentList() {
        List<Student> studentList = repository.search();
        List<StudentCourse> coursesList = repository.searchCourseList();
        return converter.convertStudentDetails(studentList, coursesList);
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

        if (student == null) {
            throw new UserNotFoundException("学生が見つかりません");
        }

        List<StudentCourse> studentCourse = repository.searchStudentCourse(student.getStudentId());
        return new StudentDetail(student , studentCourse);
    }

    /**
     * 学生情報および受講コース情報を新規登録する。
     * 学生情報を先にデータベースへ登録し、自動生成された学生IDを受講コース情報に設定した上で、コース情報を登録する。
     * 本処理はトランザクション管理されており、途中でエラーが発生した場合はすべての登録処理がロールバックされる。
     *
     * @param studentDetail 登録情報を付与した受講生詳細
     */

    @Transactional
    public StudentDetail registerStudent(StudentDetail studentDetail) {
        Student student = studentDetail.getStudent();
        repository.registerStudent(student);
        for(StudentCourse studentCourse : studentDetail.getStudentCourseList()){
            initStudentCourse(studentCourse, student);
            repository.registerCourse(studentCourse);
        }
        return studentDetail;
    }

    /**
     * 受講生コース情報を登録する際の初期情報を設定する。
     * @param studentCourse 受講生コース情報
     * @param student 受講生
     */
    private void initStudentCourse(StudentCourse studentCourse, Student student) {
        LocalDate now = LocalDate.now();

        studentCourse.setStudentId(student.getStudentId());
        studentCourse.setCourseStartDate(now);
        studentCourse.setCourseCompletionDate(now.plusYears(1));
    }

    /**
     * 受講生詳細の更新を行います。受講生と受講生コース情報をそれぞれ更新します。
     * 更新処理の前に、指定された学生IDが既に存在するかを確認します。
     *
     * @param studentDetail 受講生詳細情報
     */
    @Transactional
    public void updateStudent(StudentDetail studentDetail) {

        String studentId = studentDetail.getStudent().getStudentId();
        if (!repository.existsStudentId(studentId)) {
            throw new UserNotFoundException("更新対象の学生が存在しません");
        }

        repository.updateStudent(studentDetail.getStudent());
        for(StudentCourse studentCourse : studentDetail.getStudentCourseList()){
            repository.updateCourse(studentCourse);
        }
    }
}
