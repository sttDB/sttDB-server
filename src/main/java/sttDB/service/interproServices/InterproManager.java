package sttDB.service.interproServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.util.List;

@Service
public class InterproManager {

    private InterproParser interproParser;

    private InterproStorer storer;

    private Path fileToParse;

    public void treatInterpro(Path path) {
        fileToParse = path;
        List<LineItems> parsedItems = interproParser.parse(path);
        storer.storeItems(parsedItems);
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
