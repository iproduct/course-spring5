package course.ws.blogs.exception;

import java.util.List;

public class UnauthorizedException extends RuntimeException {
    private List<String> violations = List.of();
    public UnauthorizedException() {
    }

    public UnauthorizedException(String message) {
        super(message);
    }

    public UnauthorizedException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnauthorizedException(Throwable cause) {
        super(cause);
    }

    public UnauthorizedException(List<String> violations) {
        this.violations = violations;
    }

    public UnauthorizedException(String message, List<String> violations) {
        super(message);
        this.violations = violations;
    }

    public UnauthorizedException(String message, Throwable cause, List<String> violations) {
        super(message, cause);
        this.violations = violations;
    }

    public UnauthorizedException(Throwable cause, List<String> violations) {
        super(cause);
        this.violations = violations;
    }

    public List<String> getViolations() {
        return violations;
    }
}
