package course.spring.restjpa.web;

import course.spring.restjpa.exception.EntityNotFoundException;
import course.spring.restjpa.exception.InvalidEntityDataException;
import course.spring.restjpa.exception.UnauthorisedException;
import course.spring.restjpa.model.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.ast.Not;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.nio.file.AccessDeniedException;

@ControllerAdvice
@Slf4j
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
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleMethodArgNotValidEntityData(MethodArgumentNotValidException ex) {
        return ResponseEntity.badRequest().body(
                new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage())
        );
    }

    @ExceptionHandler({AuthenticationException.class, UnauthorisedException.class})
    public ResponseEntity<ErrorResponse> handleAuthenticationException(Exception ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                new ErrorResponse(HttpStatus.UNAUTHORIZED.value(), ex.getMessage())
        );
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                new ErrorResponse(HttpStatus.FORBIDDEN.value(), ex.getMessage())
        );
    }
}
