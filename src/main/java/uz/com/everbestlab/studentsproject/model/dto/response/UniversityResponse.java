package uz.com.everbestlab.studentsproject.model.dto.response;


import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UniversityResponse {

    private UUID id;

    private String name;

    private String address;
}
