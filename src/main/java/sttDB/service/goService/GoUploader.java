package sttDB.service.goService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sttDB.domain.Experiment;
import sttDB.service.uploadService.FileUploader;

import java.nio.file.Path;

@Service
public class GoUploader implements FileUploader {

    @Autowired
    private GoParser goParser;

    @Override
    public void treatFile(Path file, Experiment experiment) {
        goParser.parseFile(file, experiment);
    }
}
