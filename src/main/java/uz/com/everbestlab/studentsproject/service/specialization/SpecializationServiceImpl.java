package uz.com.everbestlab.studentsproject.service.specialization;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import uz.com.everbestlab.studentsproject.model.dto.request.SpecializationDto;
import uz.com.everbestlab.studentsproject.model.dto.response.SpecializationResponse;
import uz.com.everbestlab.studentsproject.model.dto.response.StandardResponse;
import uz.com.everbestlab.studentsproject.model.dto.response.Status;
import uz.com.everbestlab.studentsproject.model.entity.Specialization;
import uz.com.everbestlab.studentsproject.repository.SpecializationRepository;
import uz.com.everbestlab.studentsproject.repository.UniversityRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SpecializationServiceImpl implements SpecializationService{

    private final ModelMapper modelMapper;
    private final SpecializationRepository specializationRepository;
    private final UniversityRepository universityRepository;


    @Override
    public StandardResponse<SpecializationResponse> save(SpecializationDto specializationDto) {
        Specialization specialization = modelMapper.map(specializationDto, Specialization.class);
        specialization.setName(specializationDto.getName());
        specialization.setUniversity(universityRepository.getUniversityById(UUID.fromString(specializationDto.getUniversityId())));
        Specialization save = specializationRepository.save(specialization);
        SpecializationResponse specializationResponse = modelMapper.map(save, SpecializationResponse.class);

        return StandardResponse.<SpecializationResponse>builder()
                .status(Status.SUCCESS)
                .message("Speciality added!")
                .data(specializationResponse)
                .build();
    }
}
