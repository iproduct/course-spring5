package course.spring.intro.web;

import course.spring.intro.exception.EntityNotFoundException;
import course.spring.intro.exception.InvalidEntityDataException;
import course.spring.intro.model.ErrorResposnse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.print.DocFlavor;

@ControllerAdvice(basePackageClasses = ExceptionHandlingControllerAdvice.class)
public class ExceptionHandlingControllerAdvice {

    @ExceptionHandler
    public ResponseEntity<ErrorResposnse> handleEntityNotFound(EntityNotFoundException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ErrorResposnse(HttpStatus.NOT_FOUND.value(), ex.getMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResposnse> handleInvalidEntityData(InvalidEntityDataException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ErrorResposnse(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), ex.getViolations()));
    }
}
