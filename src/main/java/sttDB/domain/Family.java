package sttDB.domain;

import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.List;

public class Family {

    @Id
    private String interproId;

    private String description;

    private List<Sequence> sequences = new ArrayList<>();

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

    public List<Sequence> getSequences() {
        return sequences;
    }

    public void setSequences(List<Sequence> sequences) {
        this.sequences = sequences;
    }

    public boolean addSequence(Sequence sequence){
        return this.sequences.add(sequence);
    }
}
