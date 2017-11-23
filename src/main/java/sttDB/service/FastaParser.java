package sttDB.service;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.Scanner;

@Controller
public class FastaParser {

    @RequestMapping(value = "/uploadFasta", method = RequestMethod.POST)
    public void treatFasta(MultipartHttpServletRequest request) throws IOException {
        saveRecievedFile(request);
        parseFile();
    }

    private void saveRecievedFile(MultipartHttpServletRequest request) throws IOException {
        Iterator<String> iterator = request.getFileNames();
        MultipartFile multiFile = request.getFile(iterator.next());
        multiFile.getContentType();
        if(!multiFile.getContentType().equals("fasta")){
            return;
        }
        File f = new File("./recievedFiles/fasta.fasta");
        multiFile.transferTo(f);
    }

    private void parseFile() throws FileNotFoundException {
        Scanner fastaScanner = new Scanner(new File("./recievedFiles/fasta.fasta"));

    }

}
