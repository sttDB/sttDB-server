package sttDB.service.keggService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sttDB.domain.Experiment;
import sttDB.domain.Kegg;
import sttDB.repository.SequenceRepository;

import java.util.Arrays;

@Service
public class KeggSaver {

    @Autowired
    private SequenceRepository sequenceRepository;

    private String keggId;

    private String firstPath;

    private String secondPath;

    private String thirdPath;

    public void setFirstPath(String firstPath) {
        this.firstPath = firstPath;
    }

    public void setSecondPath(String secondPath) {
        this.secondPath = secondPath;
    }

    public void setThirdPath(String thirdPath) {
        this.thirdPath = thirdPath;
    }

    public void setKeggId(String keggId) {
        this.keggId = keggId;
    }

    public void saveSequences(String[] sequences, Experiment experiment) {
        if(keggIsNotDefined()){
            throw new UnsupportedOperationException("Kegg data is not defined");
        }
        Kegg kegg = new Kegg(keggId, firstPath, secondPath, thirdPath);
        Arrays.asList(sequences)
                .forEach(sequence -> sequenceRepository.sequenceKeggTermUpload(sequence, experiment, kegg));
    }

    private boolean keggIsNotDefined() {
        return keggId == null || firstPath == null || secondPath == null || thirdPath == null;
    }
}
