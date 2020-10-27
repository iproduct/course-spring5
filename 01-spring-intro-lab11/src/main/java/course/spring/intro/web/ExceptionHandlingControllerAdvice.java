package course.spring.intro.web;

import course.spring.intro.exception.EntityNotFoundException;
import course.spring.intro.model.ErrorResposnse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.print.DocFlavor;

@ControllerAdvice(basePackageClasses = ExceptionHandlingControllerAdvice.class)
public class ExceptionHandlingControllerAdvice {

    @ExceptionHandler
    public ResponseEntity<ErrorResposnse> handleEntityNotFound(EntityNotFoundException ex){
        return ResponseEntity.status(404).body(
                new ErrorResposnse(404, ex.getMessage()));
    }
}
