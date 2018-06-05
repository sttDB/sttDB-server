package sttDB.service.uploadService;

import sttDB.domain.Experiment;

import java.nio.file.Path;

public interface FileUploader {
    void treatFile(Path file, Experiment experiment);
}
