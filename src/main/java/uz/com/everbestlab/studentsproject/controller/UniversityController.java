package uz.com.everbestlab.studentsproject.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.com.everbestlab.studentsproject.model.dto.request.UniversityDto;
import uz.com.everbestlab.studentsproject.model.dto.response.StandardResponse;
import uz.com.everbestlab.studentsproject.model.dto.response.UniversityResponse;
import uz.com.everbestlab.studentsproject.service.university.UniversityService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/university")
public class UniversityController {

    private final UniversityService universityService;

    @PostMapping("/save")
    public StandardResponse<UniversityResponse> save(
            @RequestBody UniversityDto universityDto
            ){
        return universityService.save(universityDto);
    }
}
