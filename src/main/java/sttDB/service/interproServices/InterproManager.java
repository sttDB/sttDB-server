package sttDB.service.interproServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sttDB.domain.Experiment;

import java.nio.file.Path;
import java.util.List;

@Service
public class InterproManager {

    private InterproParser interproParser;

    private InterproStorer storer;

    private Path fileToParse;

    public void treatInterpro(Path path, Experiment experiment) {
        fileToParse = path;
        List<LineItems> parsedItems = interproParser.parse(path);
        storer.storeItems(parsedItems, experiment, interproParser.getGroupedItems());
    }

    @Autowired
    public void setInterproParser(InterproParser interproParser) {
        this.interproParser = interproParser;
    }

    @Autowired
    public void setStorer(InterproStorer storer) {
        this.storer = storer;
    }

}
