package course.spring.webmvc.exception;

public class NonexistingEntityException extends Exception {
    public NonexistingEntityException() {
    }

    public NonexistingEntityException(String message) {
        super(message);
    }

    public NonexistingEntityException(String message, Throwable cause) {
        super(message, cause);
    }

    public NonexistingEntityException(Throwable cause) {
        super(cause);
    }
}
