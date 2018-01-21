package sttDB.exception;

import org.springframework.hateoas.VndErrors;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionControllerAdvice {

    @ResponseBody
    @ExceptionHandler(InterproParsingException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public VndErrors interproParsingExceptionHandler(InterproParsingException ex) {
        return new VndErrors("error: ", ex.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(FastaParsingException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public VndErrors fastaParsingExceptionHandler(FastaParsingException ex) {
        return new VndErrors("error: ", ex.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(WrongFileFormatException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public VndErrors wrongFileFormatExceptionHandler(WrongFileFormatException ex) {
        return new VndErrors("Error: ", ex.getMessage());
    }

}

