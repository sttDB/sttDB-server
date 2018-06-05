package sttDB.service.experimentManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import sttDB.domain.Experiment;
import sttDB.exception.ExperimentNotFoundException;
import sttDB.exception.WrongFileFormatException;
import sttDB.repository.ExperimentRepository;
import sttDB.service.fastaServices.FastaParser;
import sttDB.service.fastaServices.FastaUploader;
import sttDB.service.fastaServices.NucleotideSaver;
import sttDB.service.interproServices.InterproUploader;
import sttDB.service.storageService.StorageException;
import sttDB.service.storageService.StorageService;
import sttDB.service.uploadService.FileUploader;

import java.nio.file.Path;
import java.util.List;

@Service
public class ExperimentManagerImpl implements ExperimentManager {

    private ExperimentRepository experimentRepository;
    private StorageService storageService;
    private InterproUploader interproUploader;
    private NucleotideSaver nucleotideSaver;

    @Autowired
    public ExperimentManagerImpl(ExperimentRepository experimentRepository,
                                 StorageService storageService,
                                 InterproUploader interproUploader,
                                 NucleotideSaver nucleotideSaver) {
        this.experimentRepository = experimentRepository;
        this.storageService = storageService;
        this.interproUploader = interproUploader;
        this.nucleotideSaver = nucleotideSaver;
    }

    @Override
    public void processNewExperiment(MultipartFile fastaFile) {
        checkFastaFormat(fastaFile);
        Experiment experiment = new Experiment(getNameNoExtension(fastaFile));
        experimentRepository.save(experiment);

        Path path = storageService.storeFileInExperiment(fastaFile, experiment.getName());
        FastaUploader fastaUploader = new FastaUploader(nucleotideSaver, new FastaParser(nucleotideSaver));
        fastaUploader.treatFile(path, experiment);
        restartBlastDatabase();
    }

    private void restartBlastDatabase() {
        String rootFile = storageService.getRootLocation().toString();
        try {
            ProcessBuilder pb = new ProcessBuilder("./reset_blast.sh", rootFile);
            Process p = pb.start();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to reset Blast Database");
        }
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

        interproUploader.treatFile(path, experiment);
    }

    @Override
    public void addOtherDataToExperiment(MultipartFile file, String experimentName, FileUploader fileUploader) {
        Experiment experiment = experimentRepository.findOne(experimentName);
        if (experiment == null)
            throw new ExperimentNotFoundException("Experiment " + experimentName + " not found");

        Path path = storageService.storeFileInExperiment(file, experimentName);

        fileUploader.treatFile(path, experiment);
    }

    @Override
    public List<String> getFilesOfExperiment(String experiment) {
        try {
            return storageService.getExperimentFileNames(experiment);
        } catch (StorageException e) {
            throw new ExperimentNotFoundException(e);
        }
    }
}
