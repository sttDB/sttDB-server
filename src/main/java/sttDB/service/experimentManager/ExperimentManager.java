package sttDB.service.experimentManager;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ExperimentManager {

    void processNewExperiment(MultipartFile fastaFile) throws IOException;

    void addFamilyFileToExperiment(MultipartFile familyFile, String experimentName);

    void addOtherDataToExperiment(MultipartFile file, String experimentName);

    List<String> getFilesOfExperiment(String experiment);

}
