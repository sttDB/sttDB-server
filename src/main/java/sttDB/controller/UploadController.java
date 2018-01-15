package sttDB.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import sttDB.service.fastaServices.FastaParser;
import sttDB.service.interproServices.InterproManager;

import java.io.IOException;

@RequestMapping(value = "/upload")
@Controller
public class UploadController {

    private FastaParser fastaParser;

    private InterproManager interproManager;

    @PostMapping("/fasta")
    @ResponseBody
    public void recieveFasta(MultipartHttpServletRequest request) throws IOException {
        fastaParser.treatFasta(request);
    }

    @PostMapping("/interpro")
    @ResponseBody
    public void processRequest(MultipartHttpServletRequest request) {
        interproManager.treatInterpro(request);
    }

    @Autowired
    public void setFastaParser(FastaParser fastaParser) {
        this.fastaParser = fastaParser;
    }

    @Autowired
    public void setInterproManager(InterproManager interproManager) {
        this.interproManager = interproManager;
    }
}
