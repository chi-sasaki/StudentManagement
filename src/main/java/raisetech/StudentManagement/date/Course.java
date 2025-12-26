package raisetech.StudentManagement.date;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
public class Course {
    private String courseId;
    private String studentId;
    private String courseName;

    // LocalDateTimeだと、コース情報を登録する際にエラーと見なされてしまうため、LocalDateに修正
    //DateTimeFormatは、Springに明確にLocalDateを利用することを伝えるアノテーション

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate courseStartDate;
    private LocalDate courseCompletionDate;
}