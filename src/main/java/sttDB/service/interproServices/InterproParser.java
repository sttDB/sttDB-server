package sttDB.service.interproServices;

import sttDB.exception.InterproParsingException;

import java.io.File;
import java.nio.file.Path;
import java.util.List;

public interface InterproParser {

    List<LineItems> parse(Path path) throws InterproParsingException;

}
