package bookstore.web;

import java.nio.file.FileSystemException;

import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.ModelAndView;

import bookstore.exception.EntityExistsException;
import bookstore.exception.EntityNotFoundException;
import bookstore.exception.InvalidEntityIdException;
import bookstore.model.Book;
import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class BooksControllerAdvice {
    @ExceptionHandler({EntityExistsException.class, EntityNotFoundException.class, 
    	InvalidEntityIdException.class, 
    	MaxUploadSizeExceededException.class, FileSystemException.class})
    @Order(1)
    public ModelAndView handle(Exception ex) {
        log.error("Article Controller Error:",ex);
        ModelAndView modelAndView = new ModelAndView("my-errors");
        modelAndView.getModel().put("message", ex.getMessage());
        return modelAndView;
    }

}
