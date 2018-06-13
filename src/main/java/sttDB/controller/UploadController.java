package sttDB.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import sttDB.service.experimentManager.ExperimentManager;
import sttDB.service.goService.GoUploader;
import sttDB.service.keggService.KeggUploader;

import java.util.Iterator;

@RequestMapping(value = "/upload")
@Controller
public class UploadController {

    private ExperimentManager manager;

    private GoUploader goUploader;

    private KeggUploader keggUploader;

    @Autowired
    public UploadController(ExperimentManager manager, GoUploader goUploader) {
        this.manager = manager;
        this.goUploader = goUploader;
    }

    @PostMapping("/fasta")
    @ResponseBody
    public void receiveFasta(MultipartHttpServletRequest request) {
        Iterator<String> fileNames = request.getFileNames();
        MultipartFile fastaFile = request.getFile(fileNames.next());
        manager.processNewExperiment(fastaFile);
    }

    @PostMapping("/interpro")
    @ResponseBody
    public void receiveInterpro(MultipartHttpServletRequest request) {
        Iterator<String> fileNames = request.getFileNames();
        MultipartFile familyFamily = request.getFile(fileNames.next());
        String experiment = request.getRequestHeaders().get("experiment").get(0);
        manager.addFamilyFileToExperiment(familyFamily, experiment);
    }

    @PostMapping("/go")
    @ResponseBody
    public void receiveGo(MultipartHttpServletRequest request) {
        Iterator<String> fileNames = request.getFileNames();
        MultipartFile goFile = request.getFile(fileNames.next());
        String experiment = request.getRequestHeaders().get("experiment").get(0);
        manager.addOtherDataToExperiment(goFile, experiment, goUploader);
    }

    @PostMapping("/kegg")
    @ResponseBody
    public void receiveKegg(MultipartHttpServletRequest request){
        Iterator<String> fileNames = request.getFileNames();
        MultipartFile keggFile = request.getFile(fileNames.next());
        String experiment = request.getRequestHeaders().get("experiment").get(0);
        manager.addOtherDataToExperiment(keggFile, experiment, keggUploader);
    }
}
