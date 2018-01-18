package sttDB.exception;

public class ExperimentNotFoundException extends RuntimeException {
    public ExperimentNotFoundException() {
    }

    public ExperimentNotFoundException(String message) {
        super(message);
    }

    public ExperimentNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExperimentNotFoundException(Throwable cause) {
        super(cause);
    }

    public ExperimentNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
