package raisetech.StudentManagement.date;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Student {

    @Pattern(regexp = "^\\d+$")
    private String studentId;
    @NotBlank
    private String fullName;
    @NotBlank
    private String furigana;
    private String nickname;
    @NotBlank
    @Email
    private String emailAddress;
    @NotBlank
    private String city;
    @NotNull(message = "年齢の入力は必須です")
    private Integer age;
    private String gender;
    private String remark;
    private boolean isDeleted;
}
