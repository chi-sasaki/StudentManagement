package raisetech.StudentManagement.date;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class StudentCourse {
    private String courseId;
    private String studentId;

    @NotEmpty(message = "受講コースの入力は必須です")
    private String courseName;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate courseStartDate;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate courseCompletionDate;
}