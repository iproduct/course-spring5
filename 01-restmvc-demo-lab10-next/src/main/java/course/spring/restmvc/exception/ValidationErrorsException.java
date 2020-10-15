package course.spring.restmvc.exception;

import course.spring.restmvc.model.Post;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.validation.Errors;

import javax.validation.ConstraintViolation;
import java.util.Set;

@Getter
@Setter
@ToString
public class ValidationErrorsException extends RuntimeException {
    private Errors errors;
    private Set<ConstraintViolation<?>> violations;
    public ValidationErrorsException() {
    }
    public ValidationErrorsException(Errors errors) {
        this.errors = errors;
    }
    public ValidationErrorsException(Set<ConstraintViolation<?>> violations) {
        this.violations = violations;
    }
    public ValidationErrorsException(String message) {
        super(message);
    }

    public ValidationErrorsException(String message, Throwable cause) {
        super(message, cause);
    }

    public ValidationErrorsException(Throwable cause) {
        super(cause);
    }
}
