package sttDB.service.fastaServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sttDB.domain.Experiment;
import sttDB.domain.Sequence;
import sttDB.repository.FamilyRepository;
import sttDB.repository.SequenceRepository;

@Service
public class NucleotideSaver implements FastaInfoSaver {

    private SequenceRepository sequenceRepository;

    @Autowired
    public NucleotideSaver(SequenceRepository sequenceRepository) {
        this.sequenceRepository = sequenceRepository;
    }


    @Override
    public void deleteOldSequences(Experiment experiment) {
        sequenceRepository.deleteByExperiment(experiment.getName());
    }

    @Override
    public void saveInfo(String[] sequenceLine, Experiment experiment) {
        if(!sequenceLine[0].isEmpty()){
            Sequence sequence = convertFirstLine(sequenceLine[0]);
            sequence.setTranscript(sequenceLine[1]);
            sequence.setExperiment(experiment.getName());
            sequence.setLength(sequenceLine[1].length());
            sequenceRepository.save(sequence);
        }
    }

    private Sequence convertFirstLine(String line) {
        String[] lineParts = line.split(" ");
        Sequence sequence = new Sequence();
        sequence.setTrinityId(lineParts[0].split(">")[1]);
        sequence.setDynamicFastaInfo(getRestOfTheDynamicLine(lineParts));
        return sequence;
    }

    private String getRestOfTheDynamicLine(String[] lineParts) {
        StringBuilder dynamicLine = new StringBuilder();
        for (int i = 1; i < lineParts.length; i++) {
            dynamicLine.append(lineParts[i]).append(" ");
        }
        return dynamicLine.toString().trim();
    }
}
