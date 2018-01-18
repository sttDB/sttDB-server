package sttDB.service.experimentManager;

import org.springframework.web.multipart.MultipartFile;

public interface ExperimentManager {

    void processNewExperiment(MultipartFile fastaFile);

    void addFamilyFileToExperiment(MultipartFile familyFile, String experimentName);

    void addOtherDataToExperiment(MultipartFile file, String experimentName);

}
