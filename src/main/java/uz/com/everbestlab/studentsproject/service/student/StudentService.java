package uz.com.everbestlab.studentsproject.service.student;

import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.com.everbestlab.studentsproject.model.dto.request.user.StudentDto;
import uz.com.everbestlab.studentsproject.model.dto.response.StandardResponse;
import uz.com.everbestlab.studentsproject.model.dto.response.StudentResponse;
import uz.com.everbestlab.studentsproject.model.entity.Student;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.UUID;

@Service
public interface StudentService {

    StandardResponse<StudentResponse> save(StudentDto studentDto);

    StandardResponse<String> delete(UUID id);

    StandardResponse<StudentResponse> getById(UUID id);


    StandardResponse<StudentResponse> update(StudentDto studentDto, UUID id);

    Page<StudentResponse> findAllStudents(Pageable pageable);

    List<Student> getAllStudentsToExcel();

    String saveStudentPicture(UUID id, MultipartFile file) throws IOException;

    Resource getUserPicture(UUID id) throws MalformedURLException;
}
