package course.spring.blog.exception;

public class InsufficientPrivilegiesException extends RuntimeException {
    public InsufficientPrivilegiesException() {
    }

    public InsufficientPrivilegiesException(String message) {
        super(message);
    }

    public InsufficientPrivilegiesException(String message, Throwable cause) {
        super(message, cause);
    }

    public InsufficientPrivilegiesException(Throwable cause) {
        super(cause);
    }
}
