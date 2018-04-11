package sttDB.service.interproServices;

import sttDB.domain.Family;
import sttDB.exception.InterproParsingException;

import java.io.File;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface InterproParser {

    List<LineItems> parse(Path path) throws InterproParsingException;

    default Map<String, List<Family>> getGroupedItems(){
        throw new InterproParsingException("The parsed did not contain any grouped item");
    }

}
