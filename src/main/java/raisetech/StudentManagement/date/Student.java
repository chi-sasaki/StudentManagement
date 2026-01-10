package raisetech.StudentManagement.date;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

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
    @NotBlank
    private int age;
    private String gender;
    private String remark;
    private boolean isDeleted;
}
