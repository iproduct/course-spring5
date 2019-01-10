package org.iproduct.spring.webmvcsericejpa.exceptions;

public class ResourceException extends RuntimeException {
    int code;

    public ResourceException(int code) {
        this.code = code;
    }

    public ResourceException(int code, String message) {
        super(message);
        this.code = code;
    }

    public ResourceException(int code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public ResourceException(int code, Throwable cause) {
        super(cause);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
