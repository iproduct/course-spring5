package course.hibernate.spring.exception;

import java.util.List;

public class InvalidClientDataException extends RuntimeException{
    private List<String> violations = List.of();
    public InvalidClientDataException() {
    }

    public InvalidClientDataException(String message) {
        super(message);
    }

    public InvalidClientDataException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidClientDataException(Throwable cause) {
        super(cause);
    }

    public InvalidClientDataException(List<String> violations) {
        this.violations = violations;
    }

    public InvalidClientDataException(String message, List<String> violations) {
        super(message);
        this.violations = violations;
    }

    public InvalidClientDataException(String message, Throwable cause, List<String> violations) {
        super(message, cause);
        this.violations = violations;
    }

    public InvalidClientDataException(Throwable cause, List<String> violations) {
        super(cause);
        this.violations = violations;
    }
}
