package sttDB.exception;

public class FastaParsingException extends RuntimeException {

    public FastaParsingException() {
    }

    public FastaParsingException(String message) {
        super(message);
    }

    public FastaParsingException(String message, Throwable cause) {
        super(message, cause);
    }

    public FastaParsingException(Throwable cause) {
        super(cause);
    }

    public FastaParsingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
