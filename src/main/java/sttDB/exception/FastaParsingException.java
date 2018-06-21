package sttDB.exception;

public class FastaParsingException extends RuntimeException {

    public FastaParsingException(String message, Throwable cause) {
        super(message, cause);
    }

    public FastaParsingException(Throwable cause) {
        super(cause);
    }

}
