package sttDB.domain;

import org.springframework.data.annotation.Id;

public class Family {

    @Id
    private Long id;

    private String interproId;

    private String description;



    public Long getId() {
        return id;
    }

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
