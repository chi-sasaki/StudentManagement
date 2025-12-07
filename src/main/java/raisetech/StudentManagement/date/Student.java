package raisetech.StudentManagement.date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Student {
    private String studentId;
    private String fullName;
    private String furigana;
    private String nickname;
    private String emailAddress;
    private String city;
    private int age;
    private String gender;
}
