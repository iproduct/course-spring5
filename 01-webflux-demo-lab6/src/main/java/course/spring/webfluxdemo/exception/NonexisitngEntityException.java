package course.spring.webfluxdemo.exception;

public class NonexisitngEntityException extends Exception {
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
