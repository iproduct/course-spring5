package course.spring.restmvc.web;

import course.spring.restmvc.exception.InvalidEntityDataException;
import course.spring.restmvc.exception.NonexistingEntityException;
import course.spring.restmvc.exception.ValidationErrorsException;
import course.spring.restmvc.model.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolation;

@ControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler({NonexistingEntityException.class, InvalidEntityDataException.class})
    public ResponseEntity<ErrorResponse> handleInvalidEntityData(Exception ex) {
        if (ex instanceof InvalidEntityDataException) {
            return ResponseEntity.badRequest().body(new ErrorResponse(400, ex.getMessage()));
        } else {
            return ResponseEntity.status(404).body(new ErrorResponse(404, ex.getMessage()));
        }
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleValidationErrors(ValidationErrorsException ex) {
        ErrorResponse errorResponse = new ErrorResponse(400, "Post not valid");
        if (ex.getErrors() != null) {
            ex.getErrors().getAllErrors().forEach(err -> {
                if (err.contains(ConstraintViolation.class)) {
                    ConstraintViolation cv = err.unwrap(ConstraintViolation.class);
                    String vStr = String.format("%s[%s]: '%s' - %s", cv.getLeafBean().getClass().getSimpleName(), cv.getPropertyPath(), cv.getInvalidValue(), cv.getMessage());
                    errorResponse.getConstraintViolations().add(vStr);
                } else if (err.contains(Exception.class)) {
                    errorResponse.getExceptionMessages().add(err.unwrap(Exception.class).getMessage());
                }
            });
        } else {
            ex.getViolations().forEach(cv -> {
                String vStr = String.format("%s[%s]: '%s' - %s", cv.getLeafBean().getClass().getSimpleName(), cv.getPropertyPath(), cv.getInvalidValue(), cv.getMessage());
                errorResponse.getConstraintViolations().add(vStr);
            });
        }
        return ResponseEntity.badRequest().body(errorResponse);
    }
}
