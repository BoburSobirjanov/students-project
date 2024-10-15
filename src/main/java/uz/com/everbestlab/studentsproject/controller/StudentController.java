package uz.com.everbestlab.studentsproject.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.com.everbestlab.studentsproject.model.dto.request.user.StudentDto;
import uz.com.everbestlab.studentsproject.model.dto.request.user.StudentInfoResume;
import uz.com.everbestlab.studentsproject.model.dto.response.StandardResponse;
import uz.com.everbestlab.studentsproject.model.dto.response.StudentResponse;
import uz.com.everbestlab.studentsproject.service.FileService;
import uz.com.everbestlab.studentsproject.service.student.StudentService;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/students")
public class StudentController {

    private final FileService fileService;
    private final StudentService studentService;



    @PostMapping("/save")
    public StandardResponse<StudentResponse> save(
            @RequestBody StudentDto studentDto
            ){
        return studentService.save(studentDto);
    }



    @GetMapping("{id}/create-resume")
    public ResponseEntity<byte[]> getUserPdf(@PathVariable UUID id,
                                             @RequestBody StudentInfoResume studentInfoResume) {
        try {
            byte[] pdf = fileService.createResume(id,studentInfoResume);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", "student" + id + LocalDateTime.now()+".pdf");

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(pdf);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }
}
