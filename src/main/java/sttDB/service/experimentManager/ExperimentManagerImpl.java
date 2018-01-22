package sttDB.service.experimentManager;

import com.sun.org.apache.xpath.internal.operations.Mult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import sttDB.domain.Experiment;
import sttDB.exception.ExperimentNotFoundException;
import sttDB.exception.WrongFileFormatException;
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
        checkFastaFormat(fastaFile);
        Experiment experiment = new Experiment(getNameNoExtension(fastaFile));
        experimentRepository.save(experiment);

        Path path = storageService.storeFileInExperiment(fastaFile, experiment.getName());

        fastaParser.treatFasta(path, experiment);
    }

    private void checkFastaFormat(MultipartFile fastaFile) {
        String fileExtension = fastaFile.getOriginalFilename().split("\\.")[1];
        if (!fileExtension.equals("fasta"))
            throw new WrongFileFormatException("File with 'fasta' extension expected, but was found " + fileExtension + " extension");
    }

    private String getNameNoExtension(MultipartFile file) {
        return file.getOriginalFilename().split("\\.")[0];
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
