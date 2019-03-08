package patchdemo.web;

import com.github.fge.jsonpatch.JsonPatchException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import patchdemo.exception.EntityNotFoundException;
import patchdemo.exception.InvalidEntityIdException;
import patchdemo.web.representations.ErrorResponse;

import java.io.IOException;

@ControllerAdvice("patchdemo")
@Slf4j
public class ExceptionHandlerControllerAdvice {

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handle(EntityNotFoundException ex){
        log.error(ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handle(InvalidEntityIdException ex){
        log.error(ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage()));
    }


    @ExceptionHandler({JsonPatchException.class, IOException.class})
    public ResponseEntity<ErrorResponse> handle(Exception ex){
        log.error(ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ErrorResponse(HttpStatus.BAD_REQUEST.value(),
                    String.format("Eror patching JSON value: %s", ex.getMessage()) ));
    }
}
