package course.ws.blogs.exception;

import java.util.List;

public class ForbiddenException extends RuntimeException {
    private List<String> violations = List.of();
    public ForbiddenException() {
    }

    public ForbiddenException(String message) {
        super(message);
    }

    public ForbiddenException(String message, Throwable cause) {
        super(message, cause);
    }

    public ForbiddenException(Throwable cause) {
        super(cause);
    }

    public ForbiddenException(List<String> violations) {
        this.violations = violations;
    }

    public ForbiddenException(String message, List<String> violations) {
        super(message);
        this.violations = violations;
    }

    public ForbiddenException(String message, Throwable cause, List<String> violations) {
        super(message, cause);
        this.violations = violations;
    }

    public ForbiddenException(Throwable cause, List<String> violations) {
        super(cause);
        this.violations = violations;
    }

    public List<String> getViolations() {
        return violations;
    }
}
