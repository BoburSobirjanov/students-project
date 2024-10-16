package uz.com.everbestlab.studentsproject.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.com.everbestlab.studentsproject.model.dto.request.user.StudentDto;
import uz.com.everbestlab.studentsproject.model.dto.request.user.StudentInfoResume;
import uz.com.everbestlab.studentsproject.model.dto.response.StandardResponse;
import uz.com.everbestlab.studentsproject.model.dto.response.StudentResponse;
import uz.com.everbestlab.studentsproject.model.entity.Student;
import uz.com.everbestlab.studentsproject.service.FileService;
import uz.com.everbestlab.studentsproject.service.student.StudentService;

import java.io.IOException;
import java.net.MalformedURLException;
import java.time.LocalDateTime;
import java.util.List;
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







    @GetMapping("/get-by-id/{id}")
    public StandardResponse<StudentResponse> getById(
            @PathVariable UUID id
    ){
        return studentService.getById(id);
    }









    @DeleteMapping("/{id}/delete")
    public StandardResponse<String> delete(
            @PathVariable UUID id
    ){
        return studentService.delete(id);
    }








    @PutMapping("/{id}/update")
    public StandardResponse<StudentResponse> update(
            @RequestBody StudentDto studentDto,
            @PathVariable UUID id
    ){
        return studentService.update(studentDto,id);
    }








    @GetMapping("/get-all")
    public Page<StudentResponse> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
            ){
        Pageable pageable = PageRequest.of(page, size);
       return studentService.findAllStudents(pageable);
    }







    @PostMapping("/{id}/upload-picture")
    public ResponseEntity<String> uploadUserPicture(
            @PathVariable UUID id,
            @RequestParam("picture") MultipartFile file) {
        try {
            String fileName = studentService.saveStudentPicture(id, file);
            return ResponseEntity.ok("Picture uploaded successfully: " + fileName);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload picture.");
        }
    }








    @GetMapping("/{id}/get-picture")
    public ResponseEntity<Resource> getUserPicture(
            @PathVariable UUID id) throws MalformedURLException {
        Resource resource = studentService.getUserPicture(id);
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(resource);
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






    @GetMapping("/get-all-excel")
    public ResponseEntity<byte[]> getAllUsersExcel() {
        List<Student> student = studentService.getAllStudentsToExcel();
        try {
            byte[] excelFile = fileService.generateUsersExcel(student);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", "students.xlsx");
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(excelFile);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }
}
