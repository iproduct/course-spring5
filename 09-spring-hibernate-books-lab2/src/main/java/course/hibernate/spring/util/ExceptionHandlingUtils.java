package course.hibernate.spring.util;

import org.springframework.dao.DataIntegrityViolationException;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

public class ExceptionHandlingUtils {

    public static List<String> extractViolations(Throwable ex) {
        if (ex instanceof DataIntegrityViolationException){
            while (!(ex instanceof SQLIntegrityConstraintViolationException) && ex.getCause() != null) {
                ex = ex.getCause();
            }
            if (ex instanceof SQLIntegrityConstraintViolationException) {
                return List.of(ex.getMessage());
            }
        }
        return List.of();
    }
}
