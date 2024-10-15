package uz.com.everbestlab.studentsproject.model.dto.request.user;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class StudentDto {

    private String fullName;

    private String description;

    private String address;

    private String studyStartDate;

    private String studyEndDate;

    private String specialization;

    private String gender;

    private String dateOfBirth;

    private String university;

}
