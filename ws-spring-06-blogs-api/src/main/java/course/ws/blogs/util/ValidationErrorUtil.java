package course.ws.blogs.util;

import course.ws.blogs.exception.InvalidEntityDataException;
import org.springframework.validation.Errors;

import java.util.List;
import java.util.stream.Collectors;

public class ValidationErrorUtil {
    public static void handleValidationErrors(Errors errors) {
        if (errors.hasErrors()) {
            List<String> fieldViolations = errors.getFieldErrors().stream()
                    .map(err -> String.format("Invalid field value: %s='%s' : %s",
                            err.getField(), err.getRejectedValue(), err.getDefaultMessage()))
                    .collect(Collectors.toList());
            List<String> violations = errors.getGlobalErrors().stream()
                    .map(err -> err.getObjectName() + ": " + err.getDefaultMessage())
                    .collect(Collectors.toList());
            violations.addAll(fieldViolations);
            throw new InvalidEntityDataException("Invalid user data", violations);
        }
    }
}
