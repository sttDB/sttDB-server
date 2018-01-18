package sttDB.service.experimentManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import sttDB.domain.Experiment;
import sttDB.exception.ExperimentNotFoundException;
import sttDB.repository.ExperimentRepository;
import sttDB.service.fastaServices.FastaParser;
import sttDB.service.interproServices.InterproManager;
import sttDB.service.interproServices.InterproParser;
import sttDB.service.storageService.StorageService;

import java.nio.file.Path;

@Service
public class ExperimentManagerImpl implements ExperimentManager {

    private ExperimentRepository experimentRepository;
    private StorageService storageService;
    private FastaParser fastaParser;
    private InterproManager interproManager;

    @Autowired
    public ExperimentManagerImpl(ExperimentRepository experimentRepository,
                                 StorageService storageService,
                                 FastaParser fastaParser,
                                 InterproManager interproManager) {
        this.experimentRepository = experimentRepository;
        this.storageService = storageService;
        this.fastaParser = fastaParser;
        this.interproManager = interproManager;
    }

    @Override
    public void processNewExperiment(MultipartFile fastaFile) {
        Experiment experiment = new Experiment(fastaFile.getOriginalFilename());
        experimentRepository.save(experiment);

        Path path = storageService.storeFileInExperiment(fastaFile, fastaFile.getOriginalFilename());

        fastaParser.treatFasta(path, experiment);
    }

    @Override
    public void addFamilyFileToExperiment(MultipartFile familyFile, String experimentName) {
        Experiment experiment = experimentRepository.findOne(experimentName);
        if (experiment == null)
            throw new ExperimentNotFoundException("Experiment " + experimentName + " not found");

        Path path = storageService.storeFileInExperiment(familyFile, experimentName);

        interproManager.treatInterpro(path, experiment);
    }

    @Override
    public void addOtherDataToExperiment(MultipartFile file, String experimentName) {
        throw new UnsupportedOperationException("Not implemented");
    }
}
