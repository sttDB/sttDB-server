package sttDB.service.keggService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sttDB.domain.Experiment;
import sttDB.domain.Kegg;
import sttDB.repository.KeggRepository;
import sttDB.repository.SequenceRepository;

import java.util.Arrays;

@Service
public class KeggSaver {

    @Autowired
    private SequenceRepository sequenceRepository;

    @Autowired
    private KeggRepository keggRepository;

    private String keggId;

    private String firstPath;

    private String secondPath;

    private String thirdPath;

    void setFirstPath(String firstPath) {
        this.firstPath = firstPath;
    }

    void setSecondPath(String secondPath) {
        this.secondPath = secondPath;
    }

    void setThirdPath(String thirdPath) {
        this.thirdPath = thirdPath;
    }

    void setKeggId(String keggId) {
        this.keggId = keggId;
    }

    void saveSequences(String[] sequences, Experiment experiment) {
        if (keggIsNotDefined()) {
            throw new UnsupportedOperationException("Kegg data is not defined");
        }
        Kegg kegg = saveKegg();
        Arrays.asList(sequences)
                .forEach(sequence -> sequenceRepository.sequenceKeggTermUpload(sequence, experiment, kegg));
    }

    private boolean keggIsNotDefined() {
        return keggId == null || firstPath == null || secondPath == null || thirdPath == null;
    }

    private Kegg saveKegg() {
        Kegg storedKegg = keggRepository.findByKeggId(keggId);
        if (storedKegg == null) {
            storedKegg = createKegg();
        }else{
            addPathsToExistentKegg(storedKegg);
        }
        keggRepository.save(storedKegg);
        return storedKegg;
    }

    private Kegg createKegg() {
        Kegg storedKegg;
        storedKegg = new Kegg();
        storedKegg.setKeggId(keggId);
        storedKegg.setPath1(Arrays.asList(firstPath));
        storedKegg.setPath2(Arrays.asList(secondPath));
        storedKegg.setPath3(Arrays.asList(thirdPath));
        return storedKegg;
    }

    private void addPathsToExistentKegg(Kegg storedKegg) {
        if (!storedKegg.getPath1().contains(firstPath)){
            storedKegg.addPath1(firstPath);
        }
        if(!storedKegg.getPath2().contains(secondPath)){
            storedKegg.addPath2(secondPath);
        }
        if (!storedKegg.getPath3().contains(thirdPath)){
            storedKegg.addPath3(thirdPath);
        }
    }
}
