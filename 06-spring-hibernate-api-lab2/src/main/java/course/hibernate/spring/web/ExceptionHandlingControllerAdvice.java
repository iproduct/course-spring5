package course.hibernate.spring.web;

import course.hibernate.spring.dao.ErrorResponse;
import course.hibernate.spring.exception.EntityNotFoundException;
import course.hibernate.spring.exception.InvalidClientDataException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlingControllerAdvice {
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleEntityNotFoundException(EntityNotFoundException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleInvalidClientDataException(InvalidClientDataException ex){
        return ResponseEntity.badRequest().body(
                new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), ex.getViolations()));
    }
}

