package sttDB.service;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.Scanner;

@Controller
public class FastaParser {

    @RequestMapping(value = "/uploadFasta", method = RequestMethod.POST)
    public void treatFasta(MultipartHttpServletRequest request) throws IOException {
        saveReceivedFile(request);
        parseFile();
    }

    private void saveReceivedFile(MultipartHttpServletRequest request) throws IOException {
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
        Scanner fastaScanner = new Scanner(new FileReader("./recievedFiles/fasta.fasta"));
        String trinityId="";
        while(fastaScanner.hasNextLine()){
            String line = fastaScanner.nextLine();
            if(line.startsWith(">")){
                trinityId = insertNewSequence();
            }else{
                addSequenceInfo(trinityId);
            }
        }
    }

    private String insertNewSequence() {
        return null;
    }

    private void addSequenceInfo(String trinityId) {
        //need to see how to do it
    }

}
