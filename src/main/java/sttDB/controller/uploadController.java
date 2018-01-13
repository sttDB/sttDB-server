package sttDB.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import sttDB.service.FastaParser;

import java.io.IOException;

@RequestMapping(value = "/upload")
@Controller
public class uploadController {

    @Autowired
    FastaParser fastaParser;

    @PostMapping("/fasta")
    @ResponseBody
    public void recieveFasta(MultipartHttpServletRequest request) throws IOException{
        fastaParser.treatFasta(request);
    }
}
