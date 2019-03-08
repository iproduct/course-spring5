package patchdemo.exception;

public class InvalidEntityIdException extends RuntimeException {
    public InvalidEntityIdException() {
    }

    public InvalidEntityIdException(String message) {
        super(message);
    }

    public InvalidEntityIdException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidEntityIdException(Throwable cause) {
        super(cause);
    }
}
