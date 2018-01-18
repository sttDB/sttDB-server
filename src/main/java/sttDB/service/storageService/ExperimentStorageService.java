package sttDB.service.storageService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class ExperimentStorageService implements StorageService {

    private final Path rootLocation;
    private Logger logger = Logger.getLogger(BaselineFileSystemStorageService.class);

    @Autowired
    public ExperimentStorageService(StorageProperties properties) {
        this.rootLocation = Paths.get(properties.getLocation());
        init();
    }

    public void init() {
        try {
            Files.createDirectory(rootLocation);
        } catch (FileAlreadyExistsException ex) {
            logger.info("Root directory already created, proceeding.");
        } catch (IOException e) {
            throw new StorageException("Could not initialize storage", e);
        }
    }

    @Override
    public Path storeFileInExperiment(MultipartFile file, String experimentName) throws IOException {
        Path folder = getFolder(experimentName);
        Path fileToCopy = folder.resolve(file.getName());
        Files.copy(file.getInputStream(), fileToCopy);
        return fileToCopy;
    }

    private Path getFolder(String experimentName) throws IOException {
        if (Files.exists(rootLocation.resolve(experimentName)))
            return rootLocation.resolve(experimentName);
        else
            return Files.createDirectory(rootLocation.resolve(experimentName));
    }

    @Override
    public Path loadFileFromExperiment(String filename, String experimentName) {
        return null;
    }

    public Path getRootLocation() {
        return rootLocation;
    }
}
