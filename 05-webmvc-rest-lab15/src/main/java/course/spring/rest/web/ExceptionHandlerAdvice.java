package course.spring.rest.web;

import course.spring.exception.EntityNotFoundException;
import course.spring.exception.InvalidClientDataException;
import course.spring.rest.model.ErorResposnse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class ExceptionHandlerAdvice {
    @ExceptionHandler
    public ResponseEntity<ErorResposnse> handleEntityNotFound(EntityNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErorResposnse(HttpStatus.NOT_FOUND.value(), ex.getMessage(), ex.toString()));
    }

    @ExceptionHandler
    public ResponseEntity<ErorResposnse> handleIvalidClientData(InvalidClientDataException ex) {
        return ResponseEntity.badRequest()
                .body(new ErorResposnse(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), ex.toString()));
    }


}
