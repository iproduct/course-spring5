package course.spring.webfluxdemo.web;

import course.spring.webfluxdemo.exception.InvalidEntityDataException;
import course.spring.webfluxdemo.exception.NonexisitngEntityException;
import course.spring.webfluxdemo.model.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ControllerAdvice
public class ErrorHandlerAdvice {
    @ExceptionHandler //(NonexisitngEntityException.class)
    public ResponseEntity<ErrorResponse> handleNonexistingEntity(
            NonexisitngEntityException ex) {
        return ResponseEntity.status(NOT_FOUND).body(
                new ErrorResponse(NOT_FOUND, ex.getMessage())
        );
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleInvalidEntity(
            InvalidEntityDataException ex) {
        return ResponseEntity.status(BAD_REQUEST).body(
                new ErrorResponse(BAD_REQUEST, ex.getMessage())
        );
    }

}
