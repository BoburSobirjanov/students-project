package uz.com.everbestlab.studentsproject.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import uz.com.everbestlab.studentsproject.model.dto.request.SpecializationDto;
import uz.com.everbestlab.studentsproject.model.dto.response.SpecializationResponse;
import uz.com.everbestlab.studentsproject.model.dto.response.StandardResponse;
import uz.com.everbestlab.studentsproject.service.specialization.SpecializationService;

import java.util.UUID;

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





    @GetMapping("/get-by-id/{id}")
    public StandardResponse<SpecializationResponse> getById(
            @PathVariable UUID id
    ){
        return specializationService.getById(id);
    }






    @DeleteMapping("/{id}/delete")
    public StandardResponse<String> delete(
            @PathVariable UUID id
    ){
        return specializationService.delete(id);
    }





    @PutMapping("/{id}/update")
    public StandardResponse<SpecializationResponse> update(
            @RequestBody SpecializationDto specializationDto,
            @PathVariable UUID id
    ){
        return specializationService.update(specializationDto,id);
    }




    @GetMapping("/get-all")
    public Page<SpecializationResponse> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        Pageable pageable = PageRequest.of(page, size);
        return specializationService.findAllSpecialization(pageable);
    }
}
