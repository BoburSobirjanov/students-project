package uz.com.everbestlab.studentsproject.service.student;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.com.everbestlab.studentsproject.exception.DataNotFoundException;
import uz.com.everbestlab.studentsproject.model.dto.request.user.StudentDto;
import uz.com.everbestlab.studentsproject.model.dto.response.*;
import uz.com.everbestlab.studentsproject.model.entity.Gender;
import uz.com.everbestlab.studentsproject.model.entity.Student;
import uz.com.everbestlab.studentsproject.repository.SpecializationRepository;
import uz.com.everbestlab.studentsproject.repository.StudentRepository;
import uz.com.everbestlab.studentsproject.repository.UniversityRepository;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
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








    @Override
    public StandardResponse<String> delete(UUID id) {
        Student student = studentRepository.getStudentById(id);
        if (student==null){
            throw new DataNotFoundException("Student not found!");
        }
        student.setDeleted(true);
        student.setDeletedTime(LocalDateTime.now());
        studentRepository.save(student);

        return StandardResponse.<String>builder()
                .status(Status.SUCCESS)
                .message("Student deleted!")
                .data("DELETED")
                .build();
    }






    @Override
    public StandardResponse<StudentResponse> getById(UUID id) {
        Student student = studentRepository.getStudentById(id);
        if (student==null){
            throw new DataNotFoundException("Student not found!");
        }
        StudentResponse studentResponse = modelMapper.map(student, StudentResponse.class);
        return StandardResponse.<StudentResponse>builder()
                .status(Status.SUCCESS)
                .message("Student deleted!")
                .data(studentResponse)
                .build();
    }






    @Override
    public StandardResponse<StudentResponse> update(StudentDto studentDto, UUID id) {
        Student student = studentRepository.getStudentById(id);
        if (student==null){
            throw new DataNotFoundException("Student not found!");
        }
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
        StudentResponse studentRes = modelMapper.map(save, StudentResponse.class);

        return StandardResponse.<StudentResponse>builder()
                .status(Status.SUCCESS)
                .message("Student updated!")
                .data(studentRes)
                .build();
    }




    @Override
    public Page<StudentResponse> findAllStudents(Pageable pageable) {
        Page<Student> students = studentRepository.findAllStudents(pageable);
        return students.map(student -> new StudentResponse(student.getId(), student.getFullName(),
                student.getDescription(),student.getAddress(),student.getStudyStartDate(),
                student.getStudyEndDate(),modelMapper.map(student.getSpecialization(), SpecializationResponse.class),
                student.getGender(),student.getDateOfBirth(), modelMapper.map(student.getUniversity(), UniversityResponse.class)));
    }




    @Override
    public List<Student> getAllStudentsToExcel(){
        List<Student> students = studentRepository.getAllStudentsToExcel();
        if (students==null){
            throw new DataNotFoundException("Students not found!");
        }
        return students;
    }



    @Override
    public String saveStudentPicture(UUID id, MultipartFile file) throws IOException {
        Student student = studentRepository.getStudentById(id);
        if (student==null){
            throw new DataNotFoundException("Student not found!");
        }
        String fileName = student.getId().toString() + "_" + file.getOriginalFilename();
        String uploadDir = "D://forImages";
        Path filePath = Paths.get(uploadDir, fileName);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        student.setPicturePath(filePath.toString());
        studentRepository.save(student);

        return fileName;
    }




    @Override
    public Resource getUserPicture(UUID id) throws MalformedURLException {
        Student user = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        Path picturePath = Paths.get(user.getPicturePath());
        Resource resource = new UrlResource(picturePath.toUri());
        if (!resource.exists() || !resource.isReadable()) {
            throw new RuntimeException("File not found or not readable");
        }
        return resource;
    }
}
