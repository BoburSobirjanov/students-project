package uz.com.everbestlab.studentsproject.model.dto.response;

import lombok.*;
import uz.com.everbestlab.studentsproject.model.entity.Gender;

import java.time.LocalDate;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class StudentResponse {

    private UUID id;

    private String fullName;

    private String description;

    private String address;

    private LocalDate studyStartDate;

    private LocalDate studyEndDate;

    private SpecializationResponse specialization;

    private Gender gender;

    private LocalDate dateOfBirth;

    private UniversityResponse university;
}
