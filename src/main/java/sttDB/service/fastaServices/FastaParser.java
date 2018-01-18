package sttDB.service.fastaServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import sttDB.domain.Experiment;
import sttDB.domain.Sequence;
import sttDB.exception.FastaParsingException;
import sttDB.repository.SequenceRepository;
import sttDB.service.TEMP_FileManager;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Scanner;

@Component
public class FastaParser {

    @Autowired
    private SequenceRepository sequenceRepository;

    @Autowired
    private TEMP_FileManager TEMPFileManager;

    @Deprecated
    public void treatFasta(MultipartHttpServletRequest request) {
        try {
            TEMPFileManager.setUsedFile(request);
            deleteOldSequences();
            parseFile();
        } catch (IOException e) {
            throw new FastaParsingException(e);
        }
    }

    public void treatFasta(Path fastaFile) {

    }

    private void deleteOldSequences() {
        String experiment = TEMPFileManager.getUsedFile();
        List<Sequence> oldSequences = sequenceRepository.findByExperiment(experiment);
        sequenceRepository.delete(oldSequences);
    }

    private void parseFile() {
        Scanner fastaScanner = null;
        try {
            fastaScanner = new Scanner(new FileReader(TEMPFileManager.getFile()));
            Sequence sequence = new Sequence();
            String transcript = "";
            while (fastaScanner.hasNextLine()) {
                String line = fastaScanner.nextLine();
                transcript = treatLine(sequence, transcript, line);
            }
            closeLastSequence(sequence, transcript);
        } catch (FileNotFoundException e) {
            throw new FastaParsingException("File not found: " + TEMPFileManager.getFile(), e);
        } finally {
            if (fastaScanner != null)
                fastaScanner.close();
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
        }
    }

    //This method intention is only to have the same object but in a different hash in every save that is done.
    //When spring tries to save the same object(the hash) with different attributes, it only saves the first time.
    private Sequence convertSequence(Sequence sequence) {
        Sequence savedSequence = new Sequence();
        savedSequence.setLength(sequence.getTranscript().length());//We calculate the length, the fasta may not have it.
        savedSequence.setTrinityId(sequence.getTrinityId());
        savedSequence.setTranscript(sequence.getTranscript());
        savedSequence.setParentExperiment(new Experiment(TEMPFileManager.getUsedFile()));
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
