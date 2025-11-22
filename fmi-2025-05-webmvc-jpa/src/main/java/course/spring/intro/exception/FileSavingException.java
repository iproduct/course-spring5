package course.spring.intro.exception;

public class FileSavingException extends RuntimeException {
    public FileSavingException(String message) {
        super(message);
    }
}
