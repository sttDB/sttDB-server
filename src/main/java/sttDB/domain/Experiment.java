package sttDB.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Experiment {

    @Id
    private String name;

    private String description;

    public Experiment(String name) {
        this.name = name;
        this.description = "";
    }

    public Experiment(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
