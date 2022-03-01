package course.ws.blogs.util;

import course.ws.blogs.exception.InvalidEntityDataException;
import org.springframework.validation.Errors;

import java.util.List;
import java.util.stream.Collectors;

public class ErrorHandlingUtils {
    public static void checkErrors(Errors errors) {
        if (errors.hasErrors()) {
            List<String> fieldViolations = errors.getFieldErrors().stream()
                    .map(err ->
                            String.format("%s = '%s': %s", err.getField(), err.getRejectedValue(), err.getDefaultMessage()))
                    .collect(Collectors.toList());
            List<String> violations = errors.getGlobalErrors().stream()
                    .map(err -> err.toString()).collect(Collectors.toList());
            violations.addAll(fieldViolations);
            throw new InvalidEntityDataException("Invalid "+ errors.getObjectName() + " data", violations);
        }
    }

    public static <T extends Throwable> T extractExceptionCauseFromClass(Throwable ex,
                                                           Class<T> targetExceptionClass){
        while(ex != null && !targetExceptionClass.isInstance(ex)) {
            ex = ex.getCause();
        }
        return (T) ex;
    }
}
