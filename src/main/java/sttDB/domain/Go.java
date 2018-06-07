package sttDB.domain;

import org.springframework.data.annotation.Id;

public class Go implements DomainInformation {

    private String goType;

    private String slimId;

    private String goName;

    private String inputAccession;

    @Id
    private String goId;

    private String description;

    public Go() {
    }

    public Go(String goType, String slimId, String goName, String inputAccession, String goId, String description) {
        this.goType = goType;
        this.slimId = slimId;
        this.goName = goName;
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

    public String getSlimId() {
        return slimId;
    }

    public void setSlimId(String slimId) {
        this.slimId = slimId;
    }

    public String getGoName() {
        return goName;
    }

    public void setGoName(String goName) {
        this.goName = goName;
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
