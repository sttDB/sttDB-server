package sttDB.service.storageService;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;

public interface StorageService {

    void init();

    Path storeFileInExperiment(MultipartFile file, String experimentName) throws StorageException;

    Path loadFileFromExperiment(String filename, String experimentName) throws StorageFileNotFoundException;

}