package sttDB.exception;

public class GoParsingException extends RuntimeException {
    public GoParsingException(String message, Throwable cause) {
        super(message, cause);
    }

    public GoParsingException(String message) {
        super(message);
    }
}
