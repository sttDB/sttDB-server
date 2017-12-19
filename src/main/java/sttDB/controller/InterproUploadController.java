package sttDB.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import sttDB.service.FileManager;

@Controller
public class InterproUploadController {

    private FileManager fileManager;

    @Autowired
    public InterproUploadController(FileManager fileManager) {
        this.fileManager = fileManager;
    }

    @PostMapping("/upload/interpro")
    public void processRequest(MultipartHttpServletRequest request) {

    }

}
