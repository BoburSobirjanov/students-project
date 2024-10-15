package uz.com.everbestlab.studentsproject.service.student;

import org.springframework.stereotype.Service;
import uz.com.everbestlab.studentsproject.model.dto.request.user.StudentDto;
import uz.com.everbestlab.studentsproject.model.dto.response.StandardResponse;
import uz.com.everbestlab.studentsproject.model.dto.response.StudentResponse;

@Service
public interface StudentService {

    StandardResponse<StudentResponse> save(StudentDto studentDto);
}
