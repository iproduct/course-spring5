package course.spring.restmvc.web;

import course.spring.restmvc.exception.InvalidEntityException;
import course.spring.restmvc.exception.NonexisitngEntityException;
import course.spring.restmvc.model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(basePackageClasses = {course.spring.restmvc.web.ArticlesController.class})
public class ExceptionHandlerControllerAdvice {
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleExceptions(NonexisitngEntityException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage()));

    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleExceptions(InvalidEntityException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage()));
    }
}
