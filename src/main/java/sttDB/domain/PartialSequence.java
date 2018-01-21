package sttDB.domain;

import org.springframework.data.annotation.Id;

import java.math.BigInteger;

public class PartialSequence {

    private String trinityId;

    private String experiment;

    public PartialSequence(String trinityId, String experiment) {
        this.trinityId = trinityId;
        this.experiment = experiment;
    }

    public String getTrinityId() {
        return this.trinityId;
    }

    public void setTrinityId(String trinityId) {
        this.trinityId = trinityId;
    }

    public String getExperiment() {
        return experiment;
    }

    public void setExperiment(String experiment) {
        this.experiment = experiment;
    }

    public boolean representsSequence(Sequence sequence) {
        return sequence.getTrinityId().equals(this.trinityId)
                && sequence.getExperiment().equals(this.experiment);
    }
}
