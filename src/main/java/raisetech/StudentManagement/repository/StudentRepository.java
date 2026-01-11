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

    List<StudentCourse> searchCourseList();

    Student searchStudent(String studentId);

    List<StudentCourse> searchStudentCourse(String studentId);

    /**
     * 学生情報をデータベースに登録する
     * 引数で受け取ったStudentオブジェクトのプロパティを、studentsテーブルの各カラムにマッピングしてINSERTを実行する
     * SQLでNOTNULLに指定していた項目を、登録画面からユーザーが登録できるように変更
     * IDに関しては自動採番を行う
     *
     * @param student 登録対象となる学生情報
     */
        void registerStudent(Student student);

    /**
     * 受講生コース情報を新規登録します。IDに関しては自動採番を行う
     *
     * @param studentCourse 受講生コース情報
     */
    void registerCourse(StudentCourse studentCourse);

    /**
     *学生IDをキーに、学生基本情報を更新する。
     *
     * @param student 更新対象となる学生情報
     */
    void updateStudent(Student student);

    /**
     * 受講生コース情報のコース名を更新します。
     *
     * @param studentCourse 受講生コース情報
     */
    void updateCourse(StudentCourse studentCourse);

    /**
     * 指定された学生IDが既に学生テーブルに存在するかどうかを判定する
     *
     * @param studentId 判定対象の学生ID
     * @return 存在する場合はtrue、存在しない場合はfalse
     */
    boolean existsStudentId(String studentId);
}
