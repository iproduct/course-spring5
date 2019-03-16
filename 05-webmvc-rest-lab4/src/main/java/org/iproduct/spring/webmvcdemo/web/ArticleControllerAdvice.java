package org.iproduct.spring.webmvcdemo.web;

import lombok.extern.slf4j.Slf4j;
import org.iproduct.spring.webmvcdemo.exceptions.InvalidEntityIdException;
import org.iproduct.spring.webmvcdemo.exceptions.NonexistingEntityException;
import org.iproduct.spring.webmvcdemo.model.ErrorResponse;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import java.nio.file.FileSystemException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ControllerAdvice
@Slf4j
public class ArticleControllerAdvice {
//    @ExceptionHandler({ MaxUploadSizeExceededException.class, FileSystemException.class })
//    @Order(100)
//    public ModelAndView handle(Exception ex) {
//        log.error("Article Controller Error:",ex);
//        ModelAndView modelAndView = new ModelAndView("errors");
//        modelAndView.getModel().put("message", ex.getMessage());
//        return modelAndView;
//    }

    @ExceptionHandler(NonexistingEntityException.class)
    @Order(1)
    public ResponseEntity<ErrorResponse> handleNonexistingEntityException(Exception ex) {
        log.error("Nonexisting Entity:", ex);
        return ResponseEntity.status(NOT_FOUND)
                .body(new ErrorResponse(NOT_FOUND.value(), ex.getMessage()));
    }

    @ExceptionHandler(InvalidEntityIdException.class)
    @Order(1)
    public ResponseEntity<ErrorResponse> handleInvalidEntityIdException(Exception ex) {
        log.error("Invalid Entity Id:",ex);
        return ResponseEntity.status(BAD_REQUEST)
                .body(new ErrorResponse(BAD_REQUEST.value(), ex.getMessage()));
    }

}
