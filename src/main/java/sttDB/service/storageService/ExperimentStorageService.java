package sttDB.service.storageService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
    public Path storeFileInExperiment(MultipartFile file, String experimentName) throws StorageException {
        try {
            Path folder = getFolder(experimentName);
            Path fileToCopy = folder.resolve(file.getName());
            Files.copy(file.getInputStream(), fileToCopy);
            return fileToCopy;
        } catch (IOException e) {
            throw new StorageException("Failed to store file " + file.getOriginalFilename(), e);
        }

    }

    private Path getFolder(String experimentName) throws IOException {
        if (Files.exists(rootLocation.resolve(experimentName)))
            return rootLocation.resolve(experimentName);
        else
            return Files.createDirectory(rootLocation.resolve(experimentName));
    }

    @Override
    public Path loadFileFromExperiment(String filename, String experimentName) {
        Path path = rootLocation.resolve(experimentName).resolve(filename);
        if (Files.exists(path))
            return path;
        else
            throw new StorageException(String.format("File with name {0} in experiment {1} not found",
                    filename, experimentName));
    }

    public Path getRootLocation() {
        return rootLocation;
    }
}
