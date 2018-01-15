package sttDB.service.interproServices;

import sttDB.exception.InterproParsingException;

import java.io.File;
import java.util.List;

public interface InterproParser {

    List<LineItems> parse() throws InterproParsingException;

    void setFileToParse(String path) throws InterproParsingException;

}
