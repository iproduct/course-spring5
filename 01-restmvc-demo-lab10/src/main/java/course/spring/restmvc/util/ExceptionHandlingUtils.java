package course.spring.restmvc.util;

import course.spring.restmvc.exception.ValidationErrorsException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

public class ExceptionHandlingUtils {

    public static void hanleConstraintViolationException(RuntimeException e) throws RuntimeException {
        ValidationErrorsException ex = extractConstraintViolationException(e);
        if(ex  != null) {
            throw ex;
        } else {
            throw e;
        }
    }
    public static ValidationErrorsException extractConstraintViolationException(RuntimeException e) throws RuntimeException {
        if(e instanceof  ValidationErrorsException) {
            return (ValidationErrorsException) e;
        }
        Throwable ex = e;
        while(ex.getCause() != null && !(ex instanceof ConstraintViolationException) ) {
            ex = ex.getCause();
        }
        if(ex instanceof ConstraintViolationException) {
            ConstraintViolationException cvex = (ConstraintViolationException) ex;
            return new ValidationErrorsException(cvex.getConstraintViolations());
        } else {
            return null;
        }
    }
}
