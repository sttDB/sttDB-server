package sttDB.service;

import org.springframework.stereotype.Service;
import sttDB.exception.InterproParsingException;

import java.io.File;

@Service
public class TrapidInterproParser implements InterproParser {

    private File fileToParse;

    @Override
    public void parse() throws InterproParsingException {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public File getFileToParse() {
        return fileToParse;
    }

    public void setFileToParse(File fileToParse) {
        this.fileToParse = fileToParse;
    }
}
