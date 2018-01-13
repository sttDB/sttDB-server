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
import java.util.stream.Stream;

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
        Scanner fastaScanner = new Scanner(new FileReader(fastaFileManager.getFile()));
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

    //This method intention is only to have the same object but in a different hash in every save that is done.
    //When spring tries to save the same object(the hash) with different attributes, it only saves the first time.
    private Sequence convertSequence(Sequence sequence) {
        Sequence savedSequence = new Sequence();
        savedSequence.setLength(sequence.getTranscript().length());//We calculate the length, the fasta may not have it.
        savedSequence.setTrinityId(sequence.getTrinityId());
        savedSequence.setTranscript(sequence.getTranscript());
        savedSequence.setExperiment(fastaFileManager.getUsedFile());
        savedSequence.setDynamicFastaInfo(sequence.getDynamicFastaInfo());
        return savedSequence;
    }

    private void insertNewSequence(Sequence sequence, String line) {
        String[] lineParts = line.split(" ");
        sequence.setTrinityId(lineParts[0].split(">")[1]);
        sequence.setDynamicFastaInfo(getRestOfTheDynamicLine(lineParts));
    }

    private String getRestOfTheDynamicLine(String[] lineParts) {
        StringBuilder dynamicLine = new StringBuilder();
        for(int i = 1; i < lineParts.length; i++){
            dynamicLine.append(lineParts[i]).append(" ");
        }
        return dynamicLine.toString();
    }

}
