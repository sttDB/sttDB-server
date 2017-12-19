package sttDB.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import sttDB.exception.InterproParsingException;
import sttDB.service.FastaFileManager;
import sttDB.service.InterproParser;

import java.io.File;
import java.io.IOException;

@Controller
public class InterproUploadController {

    private FastaFileManager fileManager;

    private InterproParser interproParser;

    @PostMapping("/upload/interpro")
    public void processRequest(MultipartHttpServletRequest request) {
        try {
            fileManager.setUsedFile(request);
            interproParser.setFileToParse(new File(fileManager.getFile()));
            interproParser.parse();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("File error: " + e.getMessage());
        } catch (InterproParsingException e) {
            e.printStackTrace();
            System.err.println("Error parsing interpro: " + e.getMessage());
        }
    }

    @Autowired
    public void setFileManager(FastaFileManager fileManager) {
        this.fileManager = fileManager;
    }

    @Autowired
    public void setInterproParser(InterproParser interproParser) {
        this.interproParser = interproParser;
    }
}
