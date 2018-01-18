package sttDB.service.storageService;

import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

public class ExperimentStorageService implements StorageService {

    @Override
    public void init() {

    }

    @Override
    public Path storeFileInExperiment(MultipartFile file, String experimentName) {
        return null;
    }

    @Override
    public Path loadFileFromExperiment(String filename, String experimentName) {
        return null;
    }
}
