package uz.com.everbestlab.studentsproject.model.dto.request.user;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class StudentInfoResume {

    private String phoneNumber;

    private String major;

    private String email;

    private Double yearsOfExperience;

    private String workedPlaces;

    private String skills;

    private String achievements;

    private String jobType;

    private String languages;

    private String linkedIn;

    private String telegramUsername;


}
