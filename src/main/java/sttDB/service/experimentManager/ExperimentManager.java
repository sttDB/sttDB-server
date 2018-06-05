package sttDB.service.experimentManager;

import org.springframework.web.multipart.MultipartFile;
import sttDB.service.uploadService.FileUploader;

import java.io.IOException;
import java.util.List;

public interface ExperimentManager {

    void processNewExperiment(MultipartFile fastaFile);

    void addFamilyFileToExperiment(MultipartFile familyFile, String experimentName);

    void addOtherDataToExperiment(MultipartFile file, String experimentName, FileUploader fileUploader);

    List<String> getFilesOfExperiment(String experiment);

}
