package sttDB.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import sttDB.service.storageService.StorageService;

import java.io.IOException;
import java.util.Iterator;

@Component
public class FileManager {

    private StorageService storageService;
    private MultipartFile usedFile;

    @Autowired
    public FileManager(StorageService storageService) {
        this.storageService = storageService;
    }

    public String getFile() {
        return storageService.getLastUsedPath().toString();
    }

    public String getUsedFile() {
        return usedFile.getOriginalFilename();
    }

    public void setUsedFile(MultipartHttpServletRequest request) throws IOException {
        saveReceivedFile(getFile(request));
    }

    private MultipartFile getFile(MultipartHttpServletRequest request) {
        Iterator<String> fileNames = request.getFileNames();
        usedFile = request.getFile(fileNames.next());
        return usedFile;
    }

    private void saveReceivedFile(MultipartFile multiFile) throws IOException {
        storageService.store(multiFile);
    }
}
