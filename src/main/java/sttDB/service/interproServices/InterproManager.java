package sttDB.service.interproServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import sttDB.exception.InterproParsingException;
import sttDB.service.TEMP_FileManager;

import java.io.IOException;
import java.util.List;

@Service
public class InterproManager {

    private TEMP_FileManager TEMPFileManager;

    private InterproParser interproParser;

    private InterproStorer storer;

    public void treatInterpro(MultipartHttpServletRequest request) {
        try {
            TEMPFileManager.setUsedFile(request);
            List<LineItems> parsedItmes = interproParser.parse(TEMPFileManager.getFile());
            storer.storeItems(parsedItmes);
        } catch (IOException e) {
            throw new InterproParsingException(e);
        }
    }

    @Autowired
    public void setTEMPFileManager(TEMP_FileManager TEMPFileManager) {
        this.TEMPFileManager = TEMPFileManager;
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
