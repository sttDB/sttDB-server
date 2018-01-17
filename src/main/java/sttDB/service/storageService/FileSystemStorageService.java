package sttDB.service.storageService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

@Service
public class FileSystemStorageService implements StorageService {

    private final Path rootLocation;
    private Path lastUsed;
    private Logger logger = Logger.getLogger(FileSystemStorageService.class);

    @Autowired
    public FileSystemStorageService(StorageProperties properties) {
        this.rootLocation = Paths.get(properties.getLocation());
        init();
    }

    @Override
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
    public void store(MultipartFile file) {
        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file " + file.getOriginalFilename());
            }
            new File(this.rootLocation.resolve(file.getOriginalFilename()).toString()).delete();
            lastUsed = this.rootLocation.resolve(file.getOriginalFilename());
            Files.copy(file.getInputStream(), lastUsed);
        } catch (IOException e) {
            throw new StorageException("Failed to store file " + file.getOriginalFilename(), e);
        }
    }

    @Override
    public Path load(String filename) {
        return rootLocation.resolve(filename);
    }

    public Path getLastUsedPath() {
        return lastUsed;
    }
}