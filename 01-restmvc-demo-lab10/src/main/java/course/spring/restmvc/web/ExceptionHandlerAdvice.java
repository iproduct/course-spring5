package course.spring.restmvc.web;

import course.spring.restmvc.exception.InvalidEntityDataException;
import course.spring.restmvc.exception.NonexistingEntityException;
import course.spring.restmvc.model.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler({NonexistingEntityException.class, InvalidEntityDataException.class})
    public ResponseEntity<ErrorResponse> handleInvalidEntityData(Exception ex) {
        if(ex instanceof InvalidEntityDataException) {
            return ResponseEntity.badRequest().body(new ErrorResponse(400, ex.getMessage()));
        } else {
            return ResponseEntity.status(404).body(new ErrorResponse(404, ex.getMessage()));
        }
    }
}
