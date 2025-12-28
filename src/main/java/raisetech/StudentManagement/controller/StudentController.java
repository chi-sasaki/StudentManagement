package raisetech.StudentManagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import raisetech.StudentManagement.controller.converter.StudentConverter;
import raisetech.StudentManagement.date.Course;
import raisetech.StudentManagement.date.Student;
import raisetech.StudentManagement.domain.StudentDetail;
import raisetech.StudentManagement.service.StudentService;

import java.util.Arrays;
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

    /**
     * 学生一覧、コース一覧をserviceから取得する。
     * さらに、その情報を統合し、画面表示用のデータに変換。studentListという名前で画面に渡す。
     *
     * @param model 画面に渡すモデル
     * @return 受講生情報の一覧
     */
    @GetMapping("/studentList")
    public String getStudentList(Model model) {
        List<Student> students = service.searchStudentList();
        List<Course> courses = service.searchCourseList();

        model.addAttribute("studentList", converter.convertStudentDetails(students, courses));
        return "studentList";
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
     * 学生一覧画面から選択された学生の情報を取得し、学生情報更新画面を表示する。
     * URL に含まれる学生IDをPathVariableとして受け取り、該当する学生情報および受講コース情報を検索する。
     * 取得した情報はModelに格納し、更新画面へ引き渡す。
     *
     * @param studentId URLパスから取得した学生ID
     * @param model 画面へデータを渡す
     * @return 学生情報更新画面（updateStudent）
     */
    @GetMapping("/student/{id}")
    public String getStudent(@PathVariable("id") String studentId, Model model) {
        StudentDetail studentDetail = service.searchStudent(studentId);
        model.addAttribute("studentDetail", studentDetail);
        return "updateStudent";
    }

    /**
     *学生情報更新画面で入力された内容を受け取り、学生情報および受講コース情報を更新する。
     * フォームの入力値はModelAttributeにより、StudentDetailへ自動的にバインドされる
     *
     * @param studentDetail 更新対象の学生情報および受講コース情報
     * @param result 入力チェック結果を保持する
     * @return 学生一覧画面へのリダイレクト、または更新画面
     */
    @PostMapping("/updateStudent")
    public String updateStudent(@ModelAttribute StudentDetail studentDetail, BindingResult result) {
        if (result.hasErrors()) {
            return "updateStudent";
        }
        service.updateStudent(studentDetail);
        return "redirect:/studentList";
    }
}