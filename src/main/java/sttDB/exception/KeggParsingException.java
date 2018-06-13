package sttDB.exception;

import java.io.FileNotFoundException;

public class KeggParsingException extends RuntimeException {
    public KeggParsingException(String message, FileNotFoundException exception) {
        super(message, exception);
    }
}
