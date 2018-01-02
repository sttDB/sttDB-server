package sttDB.exception;

public class InterproParsingException extends Exception {

    public InterproParsingException() {
    }

    public InterproParsingException(String message) {
        super(message);
    }

    public InterproParsingException(String message, Throwable cause) {
        super(message, cause);
    }

    public InterproParsingException(Throwable cause) {
        super(cause);
    }

    public InterproParsingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
