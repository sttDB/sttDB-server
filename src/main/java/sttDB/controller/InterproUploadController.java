package sttDB.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@Controller
public class InterproUploadController {

    @PostMapping("/upload/interpro")
    public void processRequest(MultipartHttpServletRequest request) {

    }

}
