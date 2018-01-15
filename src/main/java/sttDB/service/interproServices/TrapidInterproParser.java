package sttDB.service.interproServices;

import sttDB.exception.InterproParsingException;

import java.io.File;
import java.util.List;

public class TrapidInterproParser implements InterproParser {

    @Override
    public List<LineItems> parse() throws InterproParsingException {
        return null;
    }

    @Override
    public File getFileToParse() {
        return null;
    }

    @Override
    public void setFileToParse(String path) {

    }
}
