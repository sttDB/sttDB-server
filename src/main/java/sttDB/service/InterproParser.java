package sttDB.service;

import sttDB.exception.InterproParsingException;

import java.io.File;

public interface InterproParser {

    void parse() throws InterproParsingException;

    File getFileToParse();

    void setFileToParse(File path);

}
