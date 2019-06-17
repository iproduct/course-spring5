package course.spring.webfluxdemo.web;

import course.spring.webfluxdemo.exception.IllegalEntityBodyException;
import course.spring.webfluxdemo.exception.NonexistingEntityException;
import course.spring.webfluxdemo.model.HttpErrorResponse;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ControllerAdvice
@Order(1)
public class ErrorHandlerAdvisor {
    @ExceptionHandler
    public ResponseEntity<HttpErrorResponse> handleError(NonexistingEntityException e){
        return ResponseEntity.status(NOT_FOUND).body(
                new HttpErrorResponse(NOT_FOUND, e.getMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<HttpErrorResponse> handleError(IllegalEntityBodyException e){
        return ResponseEntity.badRequest().body(
                new HttpErrorResponse(BAD_REQUEST, e.getMessage()));
    }

}
