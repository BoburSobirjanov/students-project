package uz.com.everbestlab.studentsproject.service.specialization;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.com.everbestlab.studentsproject.model.dto.request.SpecializationDto;
import uz.com.everbestlab.studentsproject.model.dto.response.SpecializationResponse;
import uz.com.everbestlab.studentsproject.model.dto.response.StandardResponse;

import java.util.UUID;

@Service
public interface SpecializationService {

    StandardResponse<SpecializationResponse> save(SpecializationDto specializationDto);


    StandardResponse<String> delete(UUID id);

    StandardResponse<SpecializationResponse> getById(UUID id);

    StandardResponse<SpecializationResponse> update(SpecializationDto specializationDto, UUID id);

    Page<SpecializationResponse> findAllSpecialization(Pageable pageable);
}
