package uz.com.everbestlab.studentsproject.service.university;

import org.springframework.stereotype.Service;
import uz.com.everbestlab.studentsproject.model.dto.request.UniversityDto;
import uz.com.everbestlab.studentsproject.model.dto.response.StandardResponse;
import uz.com.everbestlab.studentsproject.model.dto.response.UniversityResponse;

import java.util.UUID;

@Service
public interface UniversityService {

     StandardResponse<UniversityResponse> save(UniversityDto universityDto);
     StandardResponse<String> delete(UUID id);
     StandardResponse<UniversityResponse> getById(UUID id);
}
