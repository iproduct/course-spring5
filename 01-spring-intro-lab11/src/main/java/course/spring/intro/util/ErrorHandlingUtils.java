package course.spring.intro.util;

import org.springframework.validation.Errors;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ErrorHandlingUtils {
    public static List<String> getErrors(Errors errors) {
        List<String> globalErr = errors.getGlobalErrors().stream()
                .map(err -> err.getDefaultMessage())
                .collect(Collectors.toList());
        List<String> filedErr = errors.getFieldErrors().stream()
                .map(err -> String.format("%s='%s': %s", err.getField(), err.getRejectedValue(), err.getDefaultMessage()))
                .collect(Collectors.toList());
        List<String> allErr = new ArrayList<>();
        allErr.addAll(globalErr);
        allErr.addAll(filedErr);
        return allErr;
    }
}
