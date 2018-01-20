package sttDB.domain;

import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Family {

    @Id
    private String interproId;

    private String description;

    private List<PartialSequence> sequences = new ArrayList<>();

    public String getInterproId() {
        return interproId;
    }

    public void setInterproId(String interproId) {
        this.interproId = interproId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<PartialSequence> getSequences() {
        return sequences;
    }

    public void setSequences(List<PartialSequence> sequences) {
        this.sequences = sequences;
    }

    public boolean addSequence(PartialSequence sequence) {
        return this.sequences.add(sequence);
    }

    public void deleteSequence(String experiment) {
        sequences = sequences.stream()
                .filter(sequence -> !sequence.getExperiment().equals(experiment))
                .collect(Collectors.toList());
    }
}
