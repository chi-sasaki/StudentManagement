package raisetech.StudentManagement.repository;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
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

    /**
     * 学生情報をデータベースに登録する
     * 引数で受け取ったStudentオブジェクトのプロパティを、studentsテーブルの各カラムにマッピングしてINSERTを実行する
     * SQLでNOTNULLに指定していた項目を、登録画面からユーザーが登録できるように変更
     *
     * @param student 登録する学生情報を保持する
     */
    @Insert("INSERT INTO students (student_id, full_name, email_address) "
            + "VALUES (#{studentId},#{fullName},#{emailAddress})")
    void insertStudent(Student student);

    @Select("SELECT * FROM students_courses")
    List<Course> course();

    /**
     * コース情報をデータベースに登録する
     * 引数で受け取ったCourseオブジェクトのプロパティを、students_coursesテーブルの各カラムにマッピングしてINSERTを実行する
     * SQLでNOTNULLに指定していた項目を、登録画面からユーザーが登録できるように変更
     *
     * @param course 登録する受講コース情報を保持する
     */
    @Insert("INSERT INTO students_courses (course_id, student_id, course_name, course_start_date) "
            + "VALUES (#{courseId}, #{studentId}, #{courseName}, #{courseStartDate})")
    void insertCourse(Course course);
}
