package course.spring.blogs.utils;

import course.spring.blogs.exception.InvalidEntityDataException;
import org.springframework.validation.Errors;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ErrorHandlingUtils {
    public static void handleValidationErrors(Errors errors) {
        if(errors.hasErrors()) {
            List<String> errorMessages = new ArrayList<>();
            List<String> fieldErrorMessages = errors.getFieldErrors().stream()
                    .map(err -> String.format("%s for: '%s' = '%s'", err.getDefaultMessage(), err.getField(), err.getRejectedValue()))
                    .collect(Collectors.toList());
            errorMessages.addAll(fieldErrorMessages);
            List<String> globalErrorMessages = errors.getGlobalErrors().stream()
                    .map(err -> String.format("%s", err.getDefaultMessage()))
                    .collect(Collectors.toList());
            errorMessages.addAll(globalErrorMessages);
            throw new InvalidEntityDataException("Invalid blog post data", errorMessages);
        }
    }
}
