package uz.com.everbestlab.studentsproject.service.university;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.com.everbestlab.studentsproject.exception.DataNotFoundException;
import uz.com.everbestlab.studentsproject.model.dto.request.UniversityDto;
import uz.com.everbestlab.studentsproject.model.dto.response.StandardResponse;
import uz.com.everbestlab.studentsproject.model.dto.response.Status;
import uz.com.everbestlab.studentsproject.model.dto.response.UniversityResponse;
import uz.com.everbestlab.studentsproject.model.entity.University;
import uz.com.everbestlab.studentsproject.repository.UniversityRepository;

import java.time.LocalDateTime;
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
        University university = universityRepository.getUniversityById(id);
        if (university==null){
            throw new DataNotFoundException("University not found!");
        }
        university.setDeleted(true);
        university.setDeletedTime(LocalDateTime.now());
        universityRepository.save(university);

        return StandardResponse.<String>builder()
                .status(Status.SUCCESS)
                .message("University deleted!")
                .data("DELETED")
                .build();
    }





    @Override
    public StandardResponse<UniversityResponse> getById(UUID id) {
        University university = universityRepository.getUniversityById(id);
        if (university==null){
            throw new DataNotFoundException("University not found!");
        }
        UniversityResponse universityResponse = modelMapper.map(university, UniversityResponse.class);

        return StandardResponse.<UniversityResponse>builder()
                .status(Status.SUCCESS)
                .message("University deleted!")
                .data(universityResponse)
                .build();
    }





    @Override
    public StandardResponse<UniversityResponse> update(UniversityDto universityDto, UUID id) {
        University university = universityRepository.getUniversityById(id);
        if (university==null){
            throw new DataNotFoundException("University not found!");
        }
        university.setAddress(universityDto.getAddress());
        university.setName(universityDto.getName());
        University save = universityRepository.save(university);
        UniversityResponse universityResponse = modelMapper.map(save, UniversityResponse.class);

        return StandardResponse.<UniversityResponse>builder()
                .status(Status.SUCCESS)
                .message("University updated!")
                .data(universityResponse)
                .build();
    }




    @Override
    public Page<UniversityResponse> findAllUniversity(Pageable pageable) {
        Page<University> universities = universityRepository.findAllUniversity(pageable);

        return universities.map(university -> new UniversityResponse(university.getId(),
                university.getName(), university.getAddress()));
    }
}
