package uz.com.everbestlab.studentsproject.service.university;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import uz.com.everbestlab.studentsproject.model.dto.request.UniversityDto;
import uz.com.everbestlab.studentsproject.model.dto.response.StandardResponse;
import uz.com.everbestlab.studentsproject.model.dto.response.Status;
import uz.com.everbestlab.studentsproject.model.dto.response.UniversityResponse;
import uz.com.everbestlab.studentsproject.model.entity.University;
import uz.com.everbestlab.studentsproject.repository.UniversityRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UniversityServiceImpl implements UniversityService {

    private final UniversityRepository universityRepository;
    private final ModelMapper modelMapper;
    @Override
    public StandardResponse<UniversityResponse> save(UniversityDto universityDto) {
        University university = modelMapper.map(universityDto, University.class);
        university.setAddress(universityDto.getAddress());
        university.setName(universityDto.getName());
        University save = universityRepository.save(university);
        UniversityResponse universityResponse = modelMapper.map(save, UniversityResponse.class);

        return StandardResponse.<UniversityResponse>builder()
                .status(Status.SUCCESS)
                .message("University created!")
                .data(universityResponse)
                .build();
    }

    @Override
    public StandardResponse<String> delete(UUID id) {
        return null;
    }

    @Override
    public StandardResponse<UniversityResponse> getById(UUID id) {
        return null;
    }
}
