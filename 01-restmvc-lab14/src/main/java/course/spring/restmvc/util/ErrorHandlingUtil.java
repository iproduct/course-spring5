package course.spring.restmvc.util;

import course.spring.restmvc.exception.InvalidEntityDataException;
import org.springframework.validation.Errors;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ErrorHandlingUtil {
    public static void handleErrors(String message, Errors errors) throws InvalidEntityDataException {
        if(errors.hasErrors()) {
            List<String> globalViolations = errors.getGlobalErrors().stream()
                    .map(err -> err.getDefaultMessage())
                    .collect(Collectors.toList());
            List<String> fieldViolations = errors.getFieldErrors().stream()
                    .map(err ->
                            String.format("Field validation error for [%s]:'%s' - %s",
                                    err.getField(), err.getRejectedValue(), err.getDefaultMessage()))
                    .collect(Collectors.toList());
            List<String> allErrors= new ArrayList<>(globalViolations);
            allErrors.addAll(fieldViolations);
            throw new InvalidEntityDataException(message, allErrors);
        }
    }
    public static void handleErrors(Errors errors) throws InvalidEntityDataException {
        handleErrors(null, errors);
    }

    public static void hanleConstraintViolationException(RuntimeException e) throws RuntimeException {
        InvalidEntityDataException ex = extractConstraintViolationException(e);
        if(ex  != null) {
            throw ex;
        } else {
            throw e;
        }
    }
    public static InvalidEntityDataException extractConstraintViolationException(RuntimeException e) throws RuntimeException {
        if(e instanceof  InvalidEntityDataException) {
            return (InvalidEntityDataException) e;
        }
        Throwable ex = e;
        while(ex.getCause() != null && !(ex instanceof ConstraintViolationException) ) {
            ex = ex.getCause();
        }
        if(ex instanceof ConstraintViolationException) {
            ConstraintViolationException cvex = (ConstraintViolationException) ex;
            return new InvalidEntityDataException(
                    cvex.getConstraintViolations().stream().map(cv -> cv.toString())
                            .collect(Collectors.toList()));
        } else {
            return null;
        }
    }
}
