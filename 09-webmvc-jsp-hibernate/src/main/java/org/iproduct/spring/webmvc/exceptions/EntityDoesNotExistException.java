package org.iproduct.spring.webmvc.exceptions;

public class EntityDoesNotExistException extends RuntimeException {
    public EntityDoesNotExistException() {
    }

    public EntityDoesNotExistException(String message) {
        super(message);
    }

    public EntityDoesNotExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public EntityDoesNotExistException(Throwable cause) {
        super(cause);
    }
}
