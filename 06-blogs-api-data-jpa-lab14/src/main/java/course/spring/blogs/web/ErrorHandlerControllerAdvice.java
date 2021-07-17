package course.spring.blogs.web;

import course.spring.blogs.dto.ErrorResponse;
import course.spring.blogs.exception.InvalidEntityDataException;
import course.spring.blogs.exception.NonexistingEntityException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.nio.file.AccessDeniedException;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.*;

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
                                return String.format("%s='%s': %s",
                                        ferr.getField(), ferr.getRejectedValue(), ferr.getDefaultMessage());
                            } else {
                                return err.getDefaultMessage();
                            }
                        }
                ).collect(Collectors.toList())));
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleDbConstraintViolations(DataIntegrityViolationException ex) {
        Throwable cause = ex;
        while (cause.getCause() != null) {
            cause = cause.getCause();
        }
        return ResponseEntity.badRequest().body(
                new ErrorResponse(BAD_REQUEST.value(), cause.getMessage())
        );
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleAuthenticationException(AuthenticationException e) {
        return ResponseEntity.status(UNAUTHORIZED.value()).body(
                new ErrorResponse(UNAUTHORIZED.value(), e.getMessage())
        );
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException e) {
        return ResponseEntity.status(FORBIDDEN.value()).body(
                new ErrorResponse(FORBIDDEN.value(), e.getMessage())
        );
    }
}
