package uz.com.everbestlab.studentsproject.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.com.everbestlab.studentsproject.model.dto.request.SpecializationDto;
import uz.com.everbestlab.studentsproject.model.dto.response.SpecializationResponse;
import uz.com.everbestlab.studentsproject.model.dto.response.StandardResponse;
import uz.com.everbestlab.studentsproject.service.specialization.SpecializationService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/specialization")
public class SpecializationController {


    private final SpecializationService specializationService;

    @PostMapping("/save")
    public StandardResponse<SpecializationResponse> save(
            @RequestBody SpecializationDto specializationDto
            ){
        return specializationService.save(specializationDto);
    }
}
