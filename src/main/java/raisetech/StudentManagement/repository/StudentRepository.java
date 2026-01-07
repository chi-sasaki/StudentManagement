package raisetech.StudentManagement.repository;

import org.apache.ibatis.annotations.*;
import raisetech.StudentManagement.date.StudentCourse;
import raisetech.StudentManagement.date.Student;

import java.util.List;

/**
 * 受講生テーブルと受講生コース情報テーブルと紐づくRepositoryです。
 */

@Mapper
public interface StudentRepository {

    List<Student> search();

    @Select("SELECT * FROM students_courses")
    List<StudentCourse> searchCourseList();

    Student searchStudent(String studentId);

    @Select("SELECT * FROM students_courses WHERE student_id = #{studentId}")
    List<StudentCourse> searchStudentCourse(String studentId);

    /**
     * 学生情報をデータベースに登録する
     * 引数で受け取ったStudentオブジェクトのプロパティを、studentsテーブルの各カラムにマッピングしてINSERTを実行する
     * SQLでNOTNULLに指定していた項目を、登録画面からユーザーが登録できるように変更
     * IDに関しては自動採番を行う
     *
     * @param student 登録する学生情報を保持する
     */
    @Insert("INSERT INTO students (full_name, furigana, nickname, email_address, city, age, gender, remark, is_deleted) "
            + "VALUES (#{fullName},#{furigana},#{nickname},#{emailAddress},#{city},#{age},#{gender},#{remark},false)")
    @Options(useGeneratedKeys = true, keyProperty = "studentId")
    void registerStudent(Student student);

    /**
     * 受講生コース情報を新規登録します。IDに関しては自動採番を行う
     *
     * @param studentCourse 受講生コース情報
     */
    @Insert("INSERT INTO students_courses(student_id, course_name, course_start_date, course_completion_date) "
            + "VALUES (#{studentId}, #{courseName}, #{courseStartDate}, #{courseCompletionDate})")
    @Options(useGeneratedKeys = true, keyProperty = "courseId")
    void registerCourse(StudentCourse studentCourse);

    /**
     *学生IDをキーに、学生基本情報を更新する。
     *
     * @param student 更新対象となる学生情報
     */
    @Update("UPDATE students SET full_name = #{fullName}, furigana = #{furigana}, nickname = #{nickname}, email_address = #{emailAddress}," +
            "city = #{city}, age = #{age}, gender = #{gender}, remark = #{remark}, is_deleted = #{isDeleted} WHERE student_id = #{studentId}")
    void updateStudent(Student student);

    /**
     * 受講生コース情報のコース名を更新します。
     *
     * @param studentCourse 受講生コース情報
     */
    @Update("UPDATE students_courses SET course_name = #{courseName} WHERE course_id = #{courseId}")
    void updateCourse(StudentCourse studentCourse);

}
