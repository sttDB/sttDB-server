package sttDB.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Experiment {

    @Id
    private String name;

    public Experiment(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
