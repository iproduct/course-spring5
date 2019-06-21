package course.spring.webmvc.exception;

public class IllegalEntityBodyException extends Exception{
    public IllegalEntityBodyException() {
    }

    public IllegalEntityBodyException(String message) {
        super(message);
    }

    public IllegalEntityBodyException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalEntityBodyException(Throwable cause) {
        super(cause);
    }
}
