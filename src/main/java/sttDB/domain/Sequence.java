package sttDB.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class Sequence {

    @Id
    private BigInteger id;

    private String trinityId;

    private String specie;

    private String transcript;

    private int length;

    private String dynamicFastaInfo;

    @DBRef
    private List<Family> families = new ArrayList<>();

    @DBRef
    private Experiment parentExperiment;

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

    public List<Family> getFamilies() {
        return families;
    }

    public void setFamilies(List<Family> families) {
        this.families = families;
    }

    public boolean addFamily(Family family) {
        return families.add(family);
    }

    public Experiment getParentExperiment() {
        return parentExperiment;
    }

    public void setParentExperiment(Experiment parentExperiment) {
        this.parentExperiment = parentExperiment;
    }

    public String getDynamicFastaInfo() {
        return dynamicFastaInfo;
    }

    public void setDynamicFastaInfo(String dynamicFastaInfo) {
        this.dynamicFastaInfo = dynamicFastaInfo;
    }
}
