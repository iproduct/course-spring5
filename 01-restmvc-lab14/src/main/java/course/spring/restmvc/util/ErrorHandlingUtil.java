package course.spring.restmvc.util;

import course.spring.restmvc.exception.InvalidEntityDataException;
import org.springframework.validation.Errors;

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
}
