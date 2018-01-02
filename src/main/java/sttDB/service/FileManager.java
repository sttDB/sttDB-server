package sttDB.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;

public interface FileManager {

    Path saveFile(MultipartFile file) throws IOException;

    Path getFile(Path path) throws FileNotFoundException;

    boolean pathExists(Path path) throws FileNotFoundException;

}
