package uz.com.everbestlab.studentsproject.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;
import uz.com.everbestlab.studentsproject.model.BaseEntity;

@Entity(name = "university")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class University extends BaseEntity {

    @Column(nullable = false,unique = true)
    private String name;

    @Column(nullable = false)
    private String address;
}
