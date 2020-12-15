package course.springdata.mapping.exception;

public class NonexistingEntityException extends RuntimeException {
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
