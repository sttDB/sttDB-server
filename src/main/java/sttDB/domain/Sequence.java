package sttDB.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Sequence {

    @Id
    private String id;

    private String trinityId;

    private String specie;

    private String transcript;

    private int length;

    private String dynamicFastaInfo;

    @DBRef
    private List<Family> families = new ArrayList<>();

    @Indexed
    private String experiment;

    private Map<String, Object> dynamicData = new HashMap<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public String getExperiment() {
        return experiment;
    }

    public void setExperiment(String experiment) {
        this.experiment = experiment;
    }

    public String getDynamicFastaInfo() {
        return dynamicFastaInfo;
    }

    public void setDynamicFastaInfo(String dynamicFastaInfo) {
        this.dynamicFastaInfo = dynamicFastaInfo;
    }

    public Map<String, Object> getDynamicData() {
        return dynamicData;
    }

    public void setDynamicData(Map<String, Object> dynamicData) {
        this.dynamicData = dynamicData;
    }

    public Object setDynamicDataProperty(String key, Object value) {
        return dynamicData.put(key, value);
    }

    public Object getDynamicDataProperty(String key) {
        return dynamicData.get(key);
    }
}
