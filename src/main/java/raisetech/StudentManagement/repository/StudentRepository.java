package raisetech.StudentManagement.repository;

import org.apache.ibatis.annotations.*;
import raisetech.StudentManagement.date.Course;
import raisetech.StudentManagement.date.Student;

import java.util.List;

/**
 * 受講生情報を扱うリポジトリ。
 * 受講生情報の検索、コース情報の検索、受講生情報の登録、コース情報の登録が行えるクラスです。
 */

@Mapper
public interface StudentRepository {

    @Select("SELECT * FROM students")
    List<Student> search();

    @Select("SELECT * FROM students_courses")
    List<Course> course();

    @Select("SELECT * FROM students WHERE student_id = #{studentId}")
    Student searchStudent(String studentId);

    @Select("SELECT * FROM students_courses WHERE student_id = #{studentId}")
    List<Course> searchStudentCourse(String studentId);

    /**
     * 学生情報をデータベースに登録する
     * 引数で受け取ったStudentオブジェクトのプロパティを、studentsテーブルの各カラムにマッピングしてINSERTを実行する
     * SQLでNOTNULLに指定していた項目を、登録画面からユーザーが登録できるように変更
     *
     * @param student 登録する学生情報を保持する
     */
    @Insert("INSERT INTO students (full_name, furigana, nickname, email_address, city, age, gender, remark, is_deleted) "
            + "VALUES (#{fullName},#{furigana},#{nickname},#{emailAddress},#{city},#{age},#{gender},#{remark},false)")
    @Options(useGeneratedKeys = true, keyProperty = "studentId")
    void registerStudent(Student student);

    @Insert("INSERT INTO students_courses(student_id, course_name, course_start_date, course_completion_date) "
            + "VALUES (#{studentId}, #{courseName}, #{courseStartDate}, #{courseCompletionDate})")
    @Options(useGeneratedKeys = true, keyProperty = "courseId")
    void registerCourse(Course course);

    /**
     *学生IDをキーに、学生基本情報を更新する。
     *
     * @param student 更新対象となる学生情報
     */
    @Update("UPDATE students SET full_name = #{fullName}, furigana = #{furigana}, nickname = #{nickname}, email_address = #{emailAddress}," +
            "city = #{city}, age = #{age}, gender = #{gender}, remark = #{remark}, is_deleted = #{isDeleted} WHERE student_id = #{studentId}")
    void updateStudent(Student student);

    @Update("UPDATE students_courses SET course_name = #{courseName} WHERE course_id = #{courseId}")
    void updateCourse(Course course);

}
