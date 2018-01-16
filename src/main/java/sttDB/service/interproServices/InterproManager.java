package sttDB.service.interproServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import sttDB.exception.InterproParsingException;
import sttDB.service.FileManager;

import java.io.IOException;
import java.util.List;

@Service
public class InterproManager {

    private FileManager fileManager;

    private InterproParser interproParser;

    private InterproStorer storer;

    public void treatInterpro(MultipartHttpServletRequest request) {
        try {
            fileManager.setUsedFile(request);
            List<LineItems> parsedItmes = interproParser.parse(fileManager.getFile());
            storer.storeItems(parsedItmes);
        } catch (IOException e) {
            throw new InterproParsingException(e);
        }
    }

    @Autowired
    public void setFileManager(FileManager fileManager) {
        this.fileManager = fileManager;
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
