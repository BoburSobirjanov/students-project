package uz.com.everbestlab.studentsproject.service.specialization;

import org.springframework.stereotype.Service;
import uz.com.everbestlab.studentsproject.model.dto.request.SpecializationDto;
import uz.com.everbestlab.studentsproject.model.dto.response.SpecializationResponse;
import uz.com.everbestlab.studentsproject.model.dto.response.StandardResponse;
import uz.com.everbestlab.studentsproject.model.entity.Specialization;

@Service
public interface SpecializationService {

    StandardResponse<SpecializationResponse> save(SpecializationDto specializationDto);
}
