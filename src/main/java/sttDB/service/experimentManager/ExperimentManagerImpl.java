package sttDB.service.experimentManager;

import org.springframework.web.multipart.MultipartFile;

public class ExperimentManagerImpl implements ExperimentManager {

    @Override
    public void processNewExperiment(MultipartFile fastaFile) {
        // TODO: Check if experiment exists and create it -> ExperimentRepository
        // TODO: Store fasta file -> StorageService.storeFileInExperiment(...)
        // TODO: Process fasta file -> FastaParser
    }

    @Override
    public void addFamilyFileToExperiment(MultipartFile familyFile, String experimentName) {

    }

    @Override
    public void addOtherDataToExperiment(MultipartFile file, String experimentName) {

    }
}
