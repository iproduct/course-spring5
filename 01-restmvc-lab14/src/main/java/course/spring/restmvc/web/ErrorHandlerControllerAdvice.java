package course.spring.restmvc.web;

import course.spring.restmvc.exception.EntityNotFoundException;
import course.spring.restmvc.exception.InvalidEntityDataException;
import course.spring.restmvc.model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(basePackageClasses = {PostResource.class})
public class ErrorHandlerControllerAdvice {
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleEntityNotFound(EntityNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage())
        );
    }
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleInvalidEntityData(InvalidEntityDataException ex) {
        return ResponseEntity.badRequest().body(
                new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), ex.getViolations())
        );
    }

}