package uz.com.everbestlab.studentsproject.service.student;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import uz.com.everbestlab.studentsproject.model.dto.request.user.StudentDto;
import uz.com.everbestlab.studentsproject.model.dto.response.StandardResponse;
import uz.com.everbestlab.studentsproject.model.dto.response.Status;
import uz.com.everbestlab.studentsproject.model.dto.response.StudentResponse;
import uz.com.everbestlab.studentsproject.model.entity.Gender;
import uz.com.everbestlab.studentsproject.model.entity.Student;
import uz.com.everbestlab.studentsproject.repository.SpecializationRepository;
import uz.com.everbestlab.studentsproject.repository.StudentRepository;
import uz.com.everbestlab.studentsproject.repository.UniversityRepository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final SpecializationRepository specializationRepository;
    private final UniversityRepository universityRepository;
    private final ModelMapper modelMapper;

    @Override
    public StandardResponse<StudentResponse> save(StudentDto studentDto) {
        Student student = modelMapper.map(studentDto, Student.class);
        student.setFullName(studentDto.getFullName());
        student.setGender(Gender.valueOf(studentDto.getGender()));
        student.setAddress(studentDto.getAddress());
        student.setDescription(studentDto.getDescription());
        student.setSpecialization(specializationRepository.getSpecializationById(UUID.fromString(studentDto.getSpecialization())));
        student.setUniversity(universityRepository.getUniversityById(UUID.fromString(studentDto.getUniversity())));
        student.setStudyEndDate(LocalDate.parse(studentDto.getStudyEndDate(), DateTimeFormatter.ofPattern("yyyy.MM.dd")));
        student.setStudyStartDate(LocalDate.parse(studentDto.getStudyStartDate(), DateTimeFormatter.ofPattern("yyyy.MM.dd")));
        student.setDateOfBirth(LocalDate.parse(studentDto.getDateOfBirth(), DateTimeFormatter.ofPattern("yyyy.MM.dd")));
        Student save = studentRepository.save(student);
        StudentResponse studentResponse = modelMapper.map(save, StudentResponse.class);

        return StandardResponse.<StudentResponse>builder()
                .status(Status.SUCCESS)
                .message("Student added!")
                .data(studentResponse)
                .build();
    }
}
