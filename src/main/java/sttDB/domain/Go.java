package sttDB.domain;

import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.List;

public class Go implements DomainInformation {

    private String goType;

    private List<String> slimId = new ArrayList<>();

    private List<String> goName = new ArrayList<>();

    private String inputAccession;

    @Id
    private String goId;

    private String description;

    public Go() {
    }

    public Go(String goType, String slimId, String goName, String inputAccession, String goId, String description) {
        this.goType = goType;
        this.slimId.add(slimId);
        this.goName.add(goName);
        this.inputAccession = inputAccession;
        this.goId = goId;
        this.description = description;
    }

    public String getGoType() {
        return goType;
    }

    public void setGoType(String goType) {
        this.goType = goType;
    }

    public List<String> getSlimId() {
        return slimId;
    }

    public void setSlimId(List<String> slimId) {
        this.slimId = slimId;
    }

    public void addSlimId(String newSlimId){
        slimId.add(newSlimId);
    }

    public List<String> getGoName() {
        return goName;
    }

    public void setGoName(List<String> goName) {
        this.goName = goName;
    }

    public void addGoName(String newGoName){
        goName.add(newGoName);
    }

    public String getInputAccession() {
        return inputAccession;
    }

    public void setInputAccession(String inputAccession) {
        this.inputAccession = inputAccession;
    }

    public String getGoId() {
        return goId;
    }

    public void setGoId(String goId) {
        this.goId = goId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
