package course.ws.blogs.web;

import course.ws.blogs.dto.ErrorResponse;
import course.ws.blogs.exception.EntityNotFoundException;
import course.ws.blogs.exception.InvalidEntityDataException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@ControllerAdvice
public class ExceptionHandlerControllerAdvice {

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleEntityNotFoundException(EntityNotFoundException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage(), List.of()));
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleInvalidEntityDataException(InvalidEntityDataException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), ex.getViolations()));
    }
}
