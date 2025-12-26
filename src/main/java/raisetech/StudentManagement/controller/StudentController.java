package raisetech.StudentManagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import raisetech.StudentManagement.controller.converter.StudentConverter;
import raisetech.StudentManagement.date.Course;
import raisetech.StudentManagement.date.Student;
import raisetech.StudentManagement.domain.StudentDetail;
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

    @GetMapping("/newStudent")
    public String newStudent(Model model) {
        model.addAttribute("studentDetail", new StudentDetail());
        return "registerStudent";
    }

    /**
     * 学生IDを指定して新しいコース登録画面を表示する
     * 指定された学生IDをもとに空のCourseオブジェクトを作成し、画面に渡すモデルに追加する
     * RequestParamは、HTTPリクエストのクエリパラメータを取得するためのSpringアノテーション
     * newCourse?studentId=001の形式でアクセスする
     *
     * @param studentId 学生ID（コースを登録する対象の学生）
     * @param model 画面に渡すモデル
     * @return 受講生コースの登録画面
     */
    @GetMapping("/newCourse")
    public String newCourse(@RequestParam String studentId, Model model) {
        Course course = new Course();
        course.setStudentId(studentId);
        model.addAttribute("course", course);
        return "registerCourse";
    }

    /**
     * 学生登録処理をする。@ModelAttributeで、フォーム入力内容をStudentDetailに自動的に詰める。
     * 入力エラーがあれば、登録画面に戻す。
     *
     * @param studentDetail フォームから送信された学生情報を保持する
     * @param result 入力チェックの結果を保持する
     * @return 処理後に表示する画面。正常に完了した時は、学生一覧画面へリダイレクト
     */
    @PostMapping("/registerStudent")
    public String registerStudent(@ModelAttribute StudentDetail studentDetail, BindingResult result) {
        if (result.hasErrors()) {
            return "registerStudent";
        }
        service.registerStudent(studentDetail.getStudent());
        return "redirect:/studentList";
    }

    /**
     *コース登録処理を行う。@ModelAttributeにより、フォームから送信された入力内容をCourseに詰める。
     * 入力エラーがある場合は、コース登録画面に戻る。
     * コースの情報だけを登録する時は、StudentDetailは不要。
     *
     * @param course フォームから送信された受講コース情報を保持する
     * @param result 入力チェック（バリデーション）の結果を保持する
     * @return 処理後に表示する画面。正常に完了した時は、コース一覧画面へリダイレクト
     */
    @PostMapping("/registerCourse")
    public String registerCourse(@ModelAttribute Course course, BindingResult result) {
        if (result.hasErrors()) {
            return "registerCourse";
        }
        service.registerCourse(course);
        return "redirect:/courseList";
    }
}