package uz.com.everbestlab.studentsproject.service.specialization;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.com.everbestlab.studentsproject.exception.DataNotFoundException;
import uz.com.everbestlab.studentsproject.model.dto.request.SpecializationDto;
import uz.com.everbestlab.studentsproject.model.dto.response.SpecializationResponse;
import uz.com.everbestlab.studentsproject.model.dto.response.StandardResponse;
import uz.com.everbestlab.studentsproject.model.dto.response.Status;
import uz.com.everbestlab.studentsproject.model.dto.response.UniversityResponse;
import uz.com.everbestlab.studentsproject.model.entity.Specialization;
import uz.com.everbestlab.studentsproject.repository.SpecializationRepository;
import uz.com.everbestlab.studentsproject.repository.UniversityRepository;

import java.time.LocalDateTime;
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







    @Override
    public StandardResponse<String> delete(UUID id) {
        Specialization specialization = specializationRepository.getSpecializationById(id);
        if (specialization==null){
            throw new DataNotFoundException("Specialization not found!");
        }
        specialization.setDeleted(true);
        specialization.setDeletedTime(LocalDateTime.now());
        specializationRepository.save(specialization);
        return StandardResponse.<String>builder()
                .status(Status.SUCCESS)
                .message("Specialization deleted!")
                .data("DELETED")
                .build();
    }







    @Override
    public StandardResponse<SpecializationResponse> getById(UUID id) {
        Specialization specialization = specializationRepository.getSpecializationById(id);
        if (specialization==null){
            throw new DataNotFoundException("Specialization not found!");
        }
        SpecializationResponse specializationResponse = modelMapper.map(specialization, SpecializationResponse.class);

        return StandardResponse.<SpecializationResponse>builder()
                .status(Status.SUCCESS)
                .message("Speciality added!")
                .data(specializationResponse)
                .build();
    }








    @Override
    public StandardResponse<SpecializationResponse> update(SpecializationDto specializationDto, UUID id) {
        Specialization specialization = specializationRepository.getSpecializationById(id);
        if (specialization==null){
            throw new DataNotFoundException("Specialization not found!");
        }
        specialization.setName(specializationDto.getName());
        specialization.setUniversity(universityRepository.getUniversityById(UUID.fromString(specializationDto.getUniversityId())));
        Specialization save = specializationRepository.save(specialization);
        SpecializationResponse specializationResponse = modelMapper.map(save, SpecializationResponse.class);

        return StandardResponse.<SpecializationResponse>builder()
                .status(Status.SUCCESS)
                .message("Speciality updated!")
                .data(specializationResponse)
                .build();
    }






    @Override
    public Page<SpecializationResponse> findAllSpecialization(Pageable pageable) {
        Page<Specialization> specializations = specializationRepository.findAllSpecialization(pageable);
        return specializations.map(specialization -> new SpecializationResponse(specialization.getId(),
                specialization.getName(),modelMapper.map(specialization.getUniversity(), UniversityResponse.class)));
    }
}
