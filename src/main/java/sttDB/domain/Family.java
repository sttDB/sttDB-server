package sttDB.domain;

import org.springframework.data.annotation.Id;

public class Family implements DomainInformation {

    @Id
    private String interproId;

    private String description;

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
}
