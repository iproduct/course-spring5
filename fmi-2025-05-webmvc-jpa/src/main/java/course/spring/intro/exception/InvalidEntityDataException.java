package course.spring.intro.exception;

import java.util.List;

public class InvalidEntityDataException extends RuntimeException {
    private List<String> constraintViolations = List.of();
    public InvalidEntityDataException() {
    }

    public InvalidEntityDataException(String message) {
        super(message);
    }

    public InvalidEntityDataException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidEntityDataException(Throwable cause) {
        super(cause);
    }

    public InvalidEntityDataException(List<String> constraintViolations) {
        this.constraintViolations = constraintViolations;
    }

    public InvalidEntityDataException(String message, List<String> constraintViolations) {
        super(message);
        this.constraintViolations = constraintViolations;
    }

    public InvalidEntityDataException(String message, Throwable cause, List<String> constraintViolations) {
        super(message, cause);
        this.constraintViolations = constraintViolations;
    }

    public InvalidEntityDataException(Throwable cause, List<String> constraintViolations) {
        super(cause);
        this.constraintViolations = constraintViolations;
    }

    public List<String> getConstraintViolations() {
        return constraintViolations;
    }
}
