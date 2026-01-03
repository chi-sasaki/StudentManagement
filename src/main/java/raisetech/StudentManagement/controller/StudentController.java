package raisetech.StudentManagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import raisetech.StudentManagement.controller.converter.StudentConverter;
import raisetech.StudentManagement.date.Course;
import raisetech.StudentManagement.date.Student;
import raisetech.StudentManagement.domain.StudentDetail;
import raisetech.StudentManagement.service.StudentService;

import java.util.Arrays;
import java.util.List;

@RestController
public class StudentController {

    private StudentService service;
    private StudentConverter converter;

    @Autowired
    public StudentController(StudentService service, StudentConverter converter) {
        this.service = service;
        this.converter = converter;
    }

    /**
     * 学生一覧およびコース一覧を取得し、それらの情報を統合して画面（またはAPIレスポンス）表示用の
     * StudentDetail一覧に変換して返却する
     *
     * @return 学生情報と受講コース情報を統合したStudentDetailの一覧
     */
    @GetMapping("/studentList")
    public List<StudentDetail> getStudentList() {
        List<Student> students = service.searchStudentList();
        List<Course> courses = service.searchCourseList();
        return converter.convertStudentDetails(students, courses);
    }

    @GetMapping("/courseList")
    public String getCourseList(Model model) {
        List<Course> courses = service.searchCourseList();

        model.addAttribute("courseList", courses);
        return "courseList";
    }

    /**
     *新規学生登録画面を表示する。
     * 学生登録フォームに表示するための初期データとしてStudentDetailオブジェクトを生成し、
     * 受講コース情報を1件分初期化してModelに格納する。
     *
     * @param model 画面（View）へデータを受け渡す
     * @return 新規学生登録画面（registerStudent.html）のビュー名
     */
    @GetMapping("/newStudent")
    public String newStudent(Model model) {
        StudentDetail studentDetail = new StudentDetail();
        studentDetail.setCourse(Arrays.asList(new Course()));
        // 空リストだと画面に何も表示されないため、あらかじめ1件入れておくために初期化
        // 1行以上表示させるために、asListでリスト化させている
        model.addAttribute("studentDetail", studentDetail);
        return "registerStudent";
    }

    /**
     * 学生登録処理をする。@ModelAttributeで、フォーム入力内容をStudentDetailに自動的に詰める。
     * 入力エラーがあれば、登録画面に戻す。
     *
     * @param studentDetail フォームから送信された学生情報を保持する
     * @param result        入力チェックの結果を保持する
     * @return 処理後に表示する画面。正常に完了した時は、学生一覧画面へリダイレクト
     */
    @PostMapping("/registerStudent")
    public String registerStudent(@ModelAttribute StudentDetail studentDetail, BindingResult result) {
        if (result.hasErrors()) {
            return "registerStudent";
        }
        service.registerStudent(studentDetail);
        return "redirect:/studentList";
    }
    
    /**
     * 学生情報更新画面から送信されたリクエストを受け取り、学生基本情報および受講コース情報を更新する
     *
     * @param studentDetail 更新対象となる学生情報および受講コース情報
     * @return 更新処理の結果メッセージを含むResponseEntity
     */
    @PostMapping("/updateStudent")
    public ResponseEntity<String> updateStudent(@RequestBody StudentDetail studentDetail) {
        service.updateStudent(studentDetail);
        return ResponseEntity.ok("更新処理が成功しました");
    }
}