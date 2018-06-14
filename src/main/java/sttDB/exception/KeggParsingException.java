package sttDB.exception;

public class KeggParsingException extends RuntimeException {
    public KeggParsingException(String message, Exception exception) {
        super(message, exception);
    }

    public KeggParsingException(String message) {
        super(message);
    }
}
