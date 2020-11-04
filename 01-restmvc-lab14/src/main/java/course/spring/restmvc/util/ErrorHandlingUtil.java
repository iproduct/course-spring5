package course.spring.restmvc.util;

import course.spring.restmvc.exception.InvalidEntityDataException;
import org.springframework.validation.Errors;

import java.util.List;
import java.util.stream.Collectors;

public class ErrorHandlingUtil {
    public static void handleErrors(String message, Errors errors) throws InvalidEntityDataException {
        if(errors.hasErrors()) {
            List<String> violations = errors.getAllErrors().stream()
                    .map(err -> err.toString())
                    .collect(Collectors.toList());
            throw new InvalidEntityDataException(message, violations);
        }
    }
    public static void handleErrors(Errors errors) throws InvalidEntityDataException {
        handleErrors(null, errors);
    }
}
