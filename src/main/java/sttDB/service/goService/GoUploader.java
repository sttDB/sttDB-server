package sttDB.service.goService;

import org.springframework.beans.factory.annotation.Autowired;
import sttDB.domain.Experiment;
import sttDB.service.uploadService.FileUploader;

import java.nio.file.Path;

public class GoUploader implements FileUploader {

    private GoParser goParser;

    @Override
    public void treatFile(Path file, Experiment experiment) {
        goParser.parseFile(file, experiment);
    }

    @Autowired
    public void setGoParser(GoParser goParser) {
        this.goParser = goParser;
    }
}
