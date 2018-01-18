package sttDB.service.experimentManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import sttDB.domain.Experiment;
import sttDB.repository.ExperimentRepository;
import sttDB.service.fastaServices.FastaParser;
import sttDB.service.storageService.StorageService;

import java.nio.file.Path;

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
        Experiment experiment = new Experiment(fastaFile.getOriginalFilename());
        experimentRepository.save(experiment);

        Path path = storageService.storeFileInExperiment(fastaFile, fastaFile.getName());

        fastaParser.treatFasta(path);
    }

    @Override
    public void addFamilyFileToExperiment(MultipartFile familyFile, String experimentName) {

    }

    @Override
    public void addOtherDataToExperiment(MultipartFile file, String experimentName) {

    }
}
