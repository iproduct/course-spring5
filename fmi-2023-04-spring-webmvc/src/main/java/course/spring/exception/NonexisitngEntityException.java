package course.spring.exception;

public class NonexisitngEntityException extends RuntimeException {
    public NonexisitngEntityException() {
    }

    public NonexisitngEntityException(String message) {
        super(message);
    }

    public NonexisitngEntityException(String message, Throwable cause) {
        super(message, cause);
    }

    public NonexisitngEntityException(Throwable cause) {
        super(cause);
    }
}
