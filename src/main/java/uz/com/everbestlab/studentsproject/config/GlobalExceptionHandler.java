package uz.com.everbestlab.studentsproject.config;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import uz.com.everbestlab.studentsproject.exception.DataNotFoundException;
import uz.com.everbestlab.studentsproject.model.dto.response.StandardResponse;
import uz.com.everbestlab.studentsproject.model.dto.response.Status;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {DataNotFoundException.class})
    public ResponseEntity<StandardResponse<String>> dataNotFoundExceptionHandler(
            DataNotFoundException e){
        return ResponseEntity.status(404).body(StandardResponse.<String>builder().status(Status.ERROR).message(e.getMessage()).build());

    }
}
