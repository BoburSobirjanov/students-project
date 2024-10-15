package uz.com.everbestlab.studentsproject.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import uz.com.everbestlab.studentsproject.model.BaseEntity;

import java.time.LocalDate;

@Entity(name = "students")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Student extends BaseEntity {

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String address;

    @JsonFormat(pattern = "yyyy.MM.dd")
    private LocalDate studyStartDate;

    @JsonFormat(pattern = "yyyy.MM.dd")
    private LocalDate studyEndDate;

    @ManyToOne
    private Specialization specialization;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @JsonFormat(pattern = "yyyy.MM.dd")
    private LocalDate dateOfBirth;

    @ManyToOne
    private University university;
}
