package sttDB.service.experimentManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import sttDB.domain.Experiment;
import sttDB.repository.ExperimentRepository;
import sttDB.service.fastaServices.FastaParser;
import sttDB.service.storageService.StorageService;

@Service
public class ExperimentManagerImpl implements ExperimentManager {

    private ExperimentRepository experimentRepository;
    private StorageService storageService;
    private FastaParser fastaParser;

    @Autowired
    public ExperimentManagerImpl(ExperimentRepository experimentRepository,
                                 StorageService storageService,
                                 FastaParser fastaParser) {
        this.experimentRepository = experimentRepository;
        this.storageService = storageService;
        this.fastaParser = fastaParser;
    }

    @Override
    public void processNewExperiment(MultipartFile fastaFile) {
        // TODO: Check if experiment exists and create it -> ExperimentRepository
        Experiment experiment = new Experiment(fastaFile.getName());
        experimentRepository.save(experiment);
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
