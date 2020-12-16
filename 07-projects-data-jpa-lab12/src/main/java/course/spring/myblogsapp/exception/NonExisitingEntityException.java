package course.spring.myblogsapp.exception;

public class NonExisitingEntityException extends RuntimeException{
    public NonExisitingEntityException() {
    }

    public NonExisitingEntityException(String message) {
        super(message);
    }

    public NonExisitingEntityException(String message, Throwable cause) {
        super(message, cause);
    }

    public NonExisitingEntityException(Throwable cause) {
        super(cause);
    }
}
