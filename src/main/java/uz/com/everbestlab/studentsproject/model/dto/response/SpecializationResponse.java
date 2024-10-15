package uz.com.everbestlab.studentsproject.model.dto.response;
import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class SpecializationResponse {

    private UUID id;

    private String name;

    private UniversityResponse university;
}
