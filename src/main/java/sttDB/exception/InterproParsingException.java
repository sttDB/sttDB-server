package sttDB.exception;

public class InterproParsingException extends RuntimeException {

    public InterproParsingException(String message) {
        super(message);
    }

    public InterproParsingException(String message, Throwable cause) {
        super(message, cause);
    }

    public InterproParsingException(Throwable cause) {
        super(cause);
    }

}
