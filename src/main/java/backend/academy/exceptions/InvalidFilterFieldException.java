package backend.academy.exceptions;

public class InvalidFilterFieldException extends RuntimeException {
    public InvalidFilterFieldException(String message) {
        super(message);
    }

    public InvalidFilterFieldException(String message, Throwable cause) {
        super(message, cause);
    }
}
