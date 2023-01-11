package course.spring.blogs.exception;

public class UnautorizedException extends RuntimeException {
    public UnautorizedException() {
    }

    public UnautorizedException(String message) {
        super(message);
    }

    public UnautorizedException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnautorizedException(Throwable cause) {
        super(cause);
    }
}
