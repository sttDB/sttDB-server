package sttDB.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import sttDB.service.experimentManager.ExperimentManager;
import sttDB.service.interproServices.InterproManager;

import java.util.Iterator;

@RequestMapping(value = "/upload")
@Controller
public class UploadController {

    private ExperimentManager manager;

    private InterproManager interproManager;

    @Autowired
    public UploadController(ExperimentManager manager) {
        this.manager = manager;
    }

    @PostMapping("/fasta")
    @ResponseBody
    public void recieveFasta(MultipartHttpServletRequest request) {
        Iterator<String> fileNames = request.getFileNames();
        MultipartFile fastaFile = request.getFile(fileNames.next());
        manager.processNewExperiment(fastaFile);
    }

    @PostMapping("/interpro")
    @ResponseBody
    public void processRequest(MultipartHttpServletRequest request) {
        interproManager.treatInterpro(request);
    }

    @Autowired
    public void setInterproManager(InterproManager interproManager) {
        this.interproManager = interproManager;
    }
}
