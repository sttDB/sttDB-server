package sttDB.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import sttDB.service.interproServices.InterproManager;

@Controller
public class InterproUploadController {

    private InterproManager interproManager;

    @PostMapping("/upload/interpro")
    @ResponseBody
    public void processRequest(MultipartHttpServletRequest request) {
        interproManager.treatInterpro(request);
    }

    @Autowired
    public void setInterproManager(InterproManager interproManager) {
        this.interproManager = interproManager;
    }
}
