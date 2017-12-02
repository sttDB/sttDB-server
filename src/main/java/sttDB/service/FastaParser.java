package sttDB.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import sttDB.domain.Sequence;
import sttDB.repository.SequenceRepository;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

@Controller
public class FastaParser {

    @Autowired
    private SequenceRepository sequenceRepository;

    @Autowired
    private FastaFileManager fastaFileManager;

    @PostMapping("/uploadFasta")
    @ResponseBody
    public void treatFasta(MultipartHttpServletRequest request) throws IOException {
        fastaFileManager.setUsedFile(request);
        parseFile();
    }

    private void parseFile() throws FileNotFoundException {
        Scanner fastaScanner = new Scanner(new FileReader(fastaFileManager.getFasta()));
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
            transcript="";
        } else {
            transcript+=line;
        }
        return transcript;
    }

    private void closeLastSequence(Sequence sequence, String transcript) {
        if(sequence.getTrinityId()!=null){
            sequence.setTranscript(transcript);
            sequenceRepository.save(convertSequence(sequence));
        }
    }

    private Sequence convertSequence(Sequence sequence) {
        Sequence savedSequence = new Sequence();
        savedSequence.setLength(sequence.getLength());
        savedSequence.setTrinityId(sequence.getTrinityId());
        savedSequence.setTranscript(sequence.getTranscript());
        return savedSequence;
    }

    private void insertNewSequence(Sequence sequence, String line) {
        String[] lineParts = line.split(" ");
        sequence.setTrinityId(lineParts[0].split(">")[1]);
        String sequenceLength = lineParts[1].split("len=")[1];
        sequence.setLength(Integer.parseInt(sequenceLength));
    }

}
