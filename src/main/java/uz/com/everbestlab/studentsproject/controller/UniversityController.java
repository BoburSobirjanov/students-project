package uz.com.everbestlab.studentsproject.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import uz.com.everbestlab.studentsproject.model.dto.request.UniversityDto;
import uz.com.everbestlab.studentsproject.model.dto.response.StandardResponse;
import uz.com.everbestlab.studentsproject.model.dto.response.UniversityResponse;
import uz.com.everbestlab.studentsproject.service.university.UniversityService;

import java.util.UUID;

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






    @GetMapping("/get-by-id/{id}")
    public StandardResponse<UniversityResponse> getById(
            @PathVariable UUID id
    ){
        return universityService.getById(id);
    }






    @DeleteMapping("/{id}/delete")
    public StandardResponse<String> delete(
            @PathVariable UUID id
    ){
        return universityService.delete(id);
    }





    @PutMapping("/{id}/update")
    public StandardResponse<UniversityResponse> update(
            @RequestBody UniversityDto universityDto,
            @PathVariable UUID id
    ){
        return universityService.update(universityDto,id);
    }





    @GetMapping("/get-all")
    public Page<UniversityResponse> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        Pageable pageable = PageRequest.of(page, size);
        return universityService.findAllUniversity(pageable);
    }
}
