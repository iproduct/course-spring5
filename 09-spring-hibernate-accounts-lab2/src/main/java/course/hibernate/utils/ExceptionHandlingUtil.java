package course.hibernate.utils;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

public class ExceptionHandlingUtil {
    public static final List<String> extractConstraintViolations(Throwable ex){
        while(!(ex instanceof ConstraintViolationException) && ex.getCause() != null) {
            ex = ex.getCause();
        }
        if(ex instanceof ConstraintViolationException){
            ConstraintViolationException cve = (ConstraintViolationException) ex;
            List<String> violations =  cve.getConstraintViolations().stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.toList());
            return violations;
        }
        return List.of();
    }
}












