package course.spring.restjpa.exception;

public class UnauthorisedException extends RuntimeException{
    public UnauthorisedException() {
    }

    public UnauthorisedException(String message) {
        super(message);
    }

    public UnauthorisedException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnauthorisedException(Throwable cause) {
        super(cause);
    }
}
