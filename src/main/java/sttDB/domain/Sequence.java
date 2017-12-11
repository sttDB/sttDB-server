package sttDB.domain;

import org.springframework.data.annotation.Id;

import java.math.BigInteger;

public class Sequence {

    @Id
    private BigInteger id;

    private String trinityId;

    private String specie;

    private String transcript;

    private int length;

    private String familyId;

    private String experiment;

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getTrinityId() {
        return trinityId;
    }

    public void setTrinityId(String trinityId) {
        this.trinityId = trinityId;
    }

    public String getSpecie() {
        return specie;
    }

    public void setSpecie(String specie) {
        this.specie = specie;
    }

    public String getTranscript() {
        return transcript;
    }

    public void setTranscript(String transcript) {
        this.transcript = transcript;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getFamilyId() {
        return familyId;
    }

    public void setFamilyId(String familyId) {
        this.familyId = familyId;
    }

    public String getExperiment() {
        return experiment;
    }

    public void setExperiment(String experiment) {
        this.experiment = experiment;
    }
}
