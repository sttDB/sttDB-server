package sttDB.service.storageService;

import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.List;

public interface StorageService {

    void init();

    Path storeFileInExperiment(MultipartFile file, String experimentName) throws StorageException;

    List<String> getExperimentFileNames(String experimentName) throws StorageException;

    Path loadFileFromExperiment(String filename, String experimentName) throws StorageFileNotFoundException;

    Path getRootLocation();
}