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

    @Indexed
    private String experiment;

    // Here we add dynamic information, we know it exists, but it is not always here.
    private Map<String, List<DynamicInformation>> dynamicData = new HashMap<>();

    // This structure should have information we do not know about.
    private Map<String, Object> anonymousData = new HashMap<>();

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

    public Map<String, List<DynamicInformation>> getDynamicData() {
        return dynamicData;
    }

    public void setDynamicData(Map<String, List<DynamicInformation>> dynamicData) {
        this.dynamicData = dynamicData;
    }

    public List<DynamicInformation> setDynamicDataProperty(String key, List<DynamicInformation> value) {
        return dynamicData.put(key, value);
    }

    public List<DynamicInformation> getDynamicDataProperty(String key) {
        return dynamicData.get(key);
    }

    public List<DynamicInformation> addIntoDynamicInformation(String key, DynamicInformation value) {
        List<DynamicInformation> information = dynamicData.get(key);
        if(information == null)
            information = new ArrayList<>();
        information.add(value);
        return dynamicData.put(key, information);
    }

    public Map<String, Object> getAnonymousData() {
        return anonymousData;
    }

    public void setAnonymousData(Map<String, Object> anonymousData) {
        this.anonymousData = anonymousData;
    }

    public Object getAnonymousDataProperty(String key) {
        return anonymousData.get(key);
    }
}
