package raisetech.StudentManagement.controller;

import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import raisetech.StudentManagement.domain.StudentDetail;
import raisetech.StudentManagement.service.StudentService;

import java.util.List;

@Validated
@RestController
public class StudentController {

    private StudentService service;

    @Autowired
    public StudentController(StudentService service) {
        this.service = service;
    }

    /**
     * 学生一覧およびコース一覧を取得し、それらの情報を統合して画面（またはAPIレスポンス）表示用の
     * StudentDetail一覧に変換して返却する
     *
     * @return 学生情報と受講コース情報を統合したStudentDetailの一覧
     */
    @GetMapping("/studentList")
    public List<StudentDetail> getStudentList() {
        return service.searchStudentList();
    }

    @GetMapping("/student/{id}")
    public StudentDetail getStudent(@PathVariable @Size(min = 1, max = 3) String id) {
        return service.searchStudent(id);
    }

    /**
     * 受講生詳細を登録する。クライアントから送信された学生情報（JSON）を受け取り、登録処理を行う。
     *
     * @param studentDetail リクエストボディに含まれる学生情報
     * @return 登録後の学生情報を含むレスポンス
     */
    @PostMapping("/registerStudent")
    public ResponseEntity<StudentDetail> registerStudent(@RequestBody StudentDetail studentDetail) {
        StudentDetail responseStudentDetail = service.registerStudent(studentDetail);
        return ResponseEntity.ok(responseStudentDetail);
    }

    /**
     * 受講生詳細の更新を行います。キャンセルフラグの更新もここで行います（論理削除）
     *
     * @param studentDetail 受講生詳細
     * @return 更新処理の結果メッセージを含むResponseEntity
     */
    @PutMapping("/updateStudent")
    public ResponseEntity<String> updateStudent(@RequestBody StudentDetail studentDetail) {
        service.updateStudent(studentDetail);
        return ResponseEntity.ok("更新処理が成功しました");
    }
}