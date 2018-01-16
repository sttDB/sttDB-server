package sttDB.service.fastaServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import sttDB.domain.Sequence;
import sttDB.exception.FastaParsingException;
import sttDB.repository.SequenceRepository;
import sttDB.service.FileManager;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

@Component
public class FastaParser {

    @Autowired
    private SequenceRepository sequenceRepository;

    @Autowired
    private FileManager fileManager;

    public void treatFasta(MultipartHttpServletRequest request) {
        try {
            fileManager.setUsedFile(request);
            deleteOldSequences();
            parseFile();
        } catch (IOException e) {
            throw new FastaParsingException(e);
        }
    }

    private void deleteOldSequences() {
        String experiment = fileManager.getUsedFile();
        List<Sequence> oldSequences = sequenceRepository.findByExperiment(experiment);
        sequenceRepository.delete(oldSequences);
    }

    private void parseFile() {
        try (Scanner fastaScanner = new Scanner(new FileReader(fileManager.getFile()));) {
            Sequence sequence = new Sequence();
            String transcript = "";
            while (fastaScanner.hasNextLine()) {
                String line = fastaScanner.nextLine();
                transcript = treatLine(sequence, transcript, line);
            }
            closeLastSequence(sequence, transcript);
        } catch (FileNotFoundException e) {
            throw new FastaParsingException("File not found: " + fileManager.getFile(), e);
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
        if (sequence.getTrinityId() != null) {
            sequence.setTranscript(transcript);
            sequenceRepository.save(convertSequence(sequence));
        } else {
            throw new FastaParsingException("Wrong file format");
        }
    }

    //This method intention is only to have the same object but in a different hash in every save that is done.
    //When spring tries to save the same object(the hash) with different attributes, it only saves the first time.
    private Sequence convertSequence(Sequence sequence) {
        Sequence savedSequence = new Sequence();
        savedSequence.setLength(sequence.getTranscript().length());//We calculate the length, the fasta may not have it.
        savedSequence.setTrinityId(sequence.getTrinityId());
        savedSequence.setTranscript(sequence.getTranscript());
        savedSequence.setExperiment(fileManager.getUsedFile());
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
