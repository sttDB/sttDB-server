package sttDB.service.keggService;

import org.springframework.stereotype.Service;

@Service
public class KeggSaver {

    private String firstPath;

    private String secondPath;

    private String thirdPath;

    public void setFirstPath(String firstPath) {
        this.firstPath = firstPath;
    }

    public void setSecondPath(String secondPath) {
        this.secondPath = secondPath;
    }

    public void setThirdPath(String thirdPath) {
        this.thirdPath = thirdPath;
    }
}
