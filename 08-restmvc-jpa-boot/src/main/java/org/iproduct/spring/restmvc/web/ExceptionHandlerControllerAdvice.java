package org.iproduct.spring.restmvc.web;

import lombok.extern.slf4j.Slf4j;
import org.iproduct.spring.restmvc.exception.EntityNotFoundException;
import org.iproduct.spring.restmvc.exception.InvalidEntityIdException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolationException;

@ControllerAdvice("org.iproduct.spring.restmvc")
@Slf4j
public class ExceptionHandlerControllerAdvice {

    @ExceptionHandler
    public ResponseEntity<String> handle(EntityNotFoundException ex){
        log.error(ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler ({InvalidEntityIdException.class, ConstraintViolationException.class})
    public ResponseEntity<String> handle(Exception ex){
        log.error(ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<String> handle(AccessDeniedException ex){
        log.error(ex.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getMessage());
    }

}
