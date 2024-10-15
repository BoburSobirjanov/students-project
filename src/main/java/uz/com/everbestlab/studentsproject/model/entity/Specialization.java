package uz.com.everbestlab.studentsproject.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;
import uz.com.everbestlab.studentsproject.model.BaseEntity;

@Entity(name = "specialization")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Specialization extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @ManyToOne
    private University university;
}
