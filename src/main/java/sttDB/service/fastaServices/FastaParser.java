package sttDB.service.fastaServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import sttDB.domain.Experiment;
import sttDB.domain.Family;
import sttDB.domain.Sequence;
import sttDB.exception.FastaParsingException;
import sttDB.repository.ExperimentRepository;
import sttDB.repository.FamilyRepository;
import sttDB.repository.SequenceRepository;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

@Component
public class FastaParser {

    private SequenceRepository sequenceRepository;
    private FamilyRepository familyRepository;
    private Path filePath;
    private Experiment experiment;

    @Autowired
    public FastaParser(SequenceRepository sequenceRepository, FamilyRepository familyRepository) {
        this.sequenceRepository = sequenceRepository;
        this.familyRepository = familyRepository;
    }

    public void treatFasta(Path fastaFile, Experiment experiment) {
        try {
            filePath = fastaFile;
            this.experiment = experiment;
            deleteOldSequences();
            parseFile();
        } catch (Exception e) {
            throw new FastaParsingException(e);
        }
    }

    private void deleteOldSequences() {
        sequenceRepository.deleteByExperiment(experiment.getName());
    }

    private void parseFile() {
        Scanner fastaScanner = null;
        try {
            fastaScanner = new Scanner(new FileReader(filePath.toFile()));
            Sequence sequence = new Sequence();
            String transcript = "";
            while (fastaScanner.hasNextLine()) {
                String line = fastaScanner.nextLine();
                transcript = treatLine(sequence, transcript, line);
            }
            closeLastSequence(sequence, transcript);
        } catch (FileNotFoundException e) {
            throw new FastaParsingException("File not found: " + filePath, e);
        } finally {
            if (fastaScanner != null)
                fastaScanner.close();
        }
    }

    private String treatLine(Sequence sequence, String transcript, String line) {
        if (line.startsWith(">")) {
            closeLastSequence(sequence, transcript);
            insertNewSequence(sequence, line);
            transcript = "";
        } else {
            transcript += line;
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
        savedSequence.setExperiment(experiment.getName());
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
        for (int i = 1; i < lineParts.length; i++) {
            dynamicLine.append(lineParts[i]).append(" ");
        }
        return dynamicLine.toString().trim();
    }

}
