package sttDB.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import sttDB.domain.Sequence;
import sttDB.repository.SequenceRepository;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.Scanner;

@Controller
public class FastaParser {

    @Autowired
    private SequenceRepository sequenceRepository;

    @RequestMapping(value = "/uploadFasta", method = RequestMethod.POST)
    public void treatFasta(MultipartHttpServletRequest request) throws IOException {
        saveReceivedFile(request);
        parseFile();
    }

    private void saveReceivedFile(MultipartHttpServletRequest request) throws IOException {
        Iterator<String> iterator = request.getFileNames();
        MultipartFile multiFile = request.getFile(iterator.next());
        multiFile.getContentType();
        File f = new File("./receivedFiles/fasta.fasta");
        multiFile.transferTo(f);
    }

    private void parseFile() throws FileNotFoundException {
        Scanner fastaScanner = new Scanner(new FileReader("./receivedFiles/fasta.fasta"));
        Sequence sequence = new Sequence();
        String transcript = "";
        while (fastaScanner.hasNextLine()) {
            String line = fastaScanner.nextLine();
            transcript = treatLine(sequence, transcript, line);
        }
    }

    private String treatLine(Sequence sequence, String transcript, String line) {
        if (line.startsWith(">")) {
            closeLastSequence(sequence, transcript);
            insertNewSequence(sequence, line);
        } else {
            transcript+=line;
        }
        return transcript;
    }

    private void closeLastSequence(Sequence sequence, String transcript) {
        if(sequence.getTrinityId()!=null){
            sequence.setTranscript(transcript);
            sequenceRepository.save(sequence);
        }
    }

    private void insertNewSequence(Sequence sequence, String line) {
        //agafar trinity i longitud
    }

}
