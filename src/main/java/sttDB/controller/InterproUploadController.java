package sttDB.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import sttDB.exception.InterproParsingException;
import sttDB.service.FileManager;
import sttDB.service.interproServices.InterproParser;
import sttDB.service.interproServices.InterproStorer;
import sttDB.service.interproServices.LineItems;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
public class InterproUploadController {

    private FileManager fileManager;

    private InterproParser interproParser;

    private InterproStorer storer;

    @PostMapping("/upload/interpro")
    @ResponseBody
    public void processRequest(MultipartHttpServletRequest request) {
        try {
            fileManager.setUsedFile(request);
            interproParser.setFileToParse(new File(fileManager.getFile()));
            List<LineItems> parsedItmes = interproParser.parse();
            storer.storeItems(parsedItmes);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("File error: " + e.getMessage());
        } catch (InterproParsingException e) {
            e.printStackTrace();
            System.err.println("Error parsing interpro: " + e.getMessage());
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
