package sttDB.service.keggService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sttDB.domain.Experiment;
import sttDB.service.uploadService.FileUploader;

import java.nio.file.Path;

@Service
public class KeggUploader implements FileUploader {

    @Autowired
    private KeggParser keggParser;

    @Override
    public void treatFile(Path file, Experiment experiment) {

    }
}
