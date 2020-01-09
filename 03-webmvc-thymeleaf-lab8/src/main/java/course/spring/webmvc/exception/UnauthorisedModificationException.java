package course.spring.webmvc.exception;

import lombok.NonNull;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UnauthorisedModificationException extends RuntimeException {
    public UnauthorisedModificationException(String msg) {
        super(msg);
    }

    public UnauthorisedModificationException() {
        super();
    }

    public UnauthorisedModificationException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnauthorisedModificationException(Throwable cause) {
        super(cause);
    }
}
