package course.spring.webmvc.web;

import course.spring.webmvc.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.ModelAndView;

import java.nio.file.FileSystemException;

@ControllerAdvice
@Slf4j
public class ArticleControllerAdvice {
    @ExceptionHandler({ MaxUploadSizeExceededException.class, FileSystemException.class })
    @Order(1)
    public ModelAndView handle(Exception ex) {
        log.error("Article Controller Error:",ex);
        ModelAndView modelAndView = new ModelAndView("errors");
        modelAndView.getModel().put("message", ex.getMessage());
        return modelAndView;
    }

    @ModelAttribute("loggedUserNames")
    public String getLoggedUserNames(Authentication authentication) {
        if (authentication != null) {
            User loggedId = (User) authentication.getPrincipal();
            return loggedId.getFullName();
        } else {
            return null;
        }
    }

}