package sttDB.service.interproServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sttDB.domain.Experiment;
import sttDB.service.uploadService.FileUploader;

import java.nio.file.Path;
import java.util.List;

@Service
public class InterproUploader implements FileUploader {

    private InterproParser interproParser;

    private InterproStorer storer;

    private Path fileToParse;

    public void treatFile(Path path, Experiment experiment) {
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
