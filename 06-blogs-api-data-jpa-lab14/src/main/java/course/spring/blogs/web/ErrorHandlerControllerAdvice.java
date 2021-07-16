package course.spring.blogs.web;

import course.spring.blogs.dto.ErrorResponse;
import course.spring.blogs.exception.InvalidEntityDataException;
import course.spring.blogs.exception.NonexistingEntityException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ControllerAdvice(basePackages = "course.spring.blogs.web")
public class ErrorHandlerControllerAdvice {
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleNonexisitngEntityException(
            NonexistingEntityException e) {
        return ResponseEntity.status(NOT_FOUND).body(
                new ErrorResponse(NOT_FOUND.value(), e.getMessage())
        );
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleInvalidEntityDataException(
            InvalidEntityDataException e) {
        return ResponseEntity.badRequest().body(
                new ErrorResponse(BAD_REQUEST.value(), e.getMessage())
        );
    }

    @ExceptionHandler()
    public ResponseEntity<ErrorResponse> handleValidationErrors(MethodArgumentNotValidException e) {
        return ResponseEntity.badRequest().body(new ErrorResponse(BAD_REQUEST.value(), e.getMessage(),
                e.getBindingResult().getAllErrors().stream().map(err -> {
                            if (err instanceof FieldError) {
                                FieldError ferr = (FieldError) err;
                                return String.format("'%s=%s': %s",
                                        ferr.getField(), ferr.getRejectedValue(), ferr.getDefaultMessage());
                            } else {
                                return err.getDefaultMessage();
                            }
                        }
                ).collect(Collectors.toList())));
    }
}
