package sttDB.domain;

import org.springframework.data.annotation.Id;

import java.math.BigInteger;

public class PartialSequence {
    @Id
    private BigInteger id;

    private String trinityId;

    private String experiment;

    public PartialSequence(String trinityId, String experiment) {
        this.trinityId = trinityId;
        this.experiment = experiment;
    }

    public BigInteger getId() {
        return id;
    }

    public String getTrinityId() {
        return trinityId;
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
}
