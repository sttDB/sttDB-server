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
import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
public class ExperimentStorageService implements StorageService {

    private final Path rootLocation;
    private Logger logger = Logger.getLogger(ExperimentStorageService.class);

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
            Path fileToCopy = folder.resolve(file.getOriginalFilename());
            if (Files.exists(fileToCopy))
                Files.delete(fileToCopy);
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
    public Path loadFileFromExperiment(String filename, String experimentName) throws StorageFileNotFoundException {
        Path path = rootLocation.resolve(experimentName).resolve(filename);
        if (Files.exists(path))
            return path;
        else
            throw new StorageFileNotFoundException(String.format("File with name {0} in experiment {1} not found",
                    filename, experimentName));
    }

    @Override
    public List<String> getExperimentFileNames(String experimentName) throws StorageException {
        try {
            Path path = rootLocation.resolve(experimentName);
            List<String> fileNames = Files.list(path)
                    .map(p -> p.toFile().getName())
                    .collect(toList());
            return fileNames;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Path getRootLocation() {
        return rootLocation;
    }
}
