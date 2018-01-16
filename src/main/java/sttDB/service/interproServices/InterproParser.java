package sttDB.service.interproServices;

import sttDB.exception.InterproParsingException;

import java.io.File;
import java.util.List;

public interface InterproParser {

    List<LineItems> parse(String path) throws InterproParsingException;

}
