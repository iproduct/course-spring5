package org.iproduct.spring.webmvcdemo.exceptions;

public class NonexistingEntityException extends RuntimeException{

    public NonexistingEntityException() {
        super();
    }

    public NonexistingEntityException(String message, Throwable cause) {
        super(message, cause);
    }

    public NonexistingEntityException(Throwable cause) {
        super(cause);
    }

    public NonexistingEntityException(String message) {
        super(message);
    }
}
