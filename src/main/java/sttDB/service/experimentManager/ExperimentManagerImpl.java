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
import sttDB.service.interproServices.InterproManager;
import sttDB.service.storageService.StorageException;
import sttDB.service.storageService.StorageService;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

@Service
public class ExperimentManagerImpl implements ExperimentManager {

    private ExperimentRepository experimentRepository;
    private StorageService storageService;
    private InterproManager interproManager;
    private NucleotideSaver nucleotideSaver;

    @Autowired
    public ExperimentManagerImpl(ExperimentRepository experimentRepository,
                                 StorageService storageService,
                                 InterproManager interproManager,
                                 NucleotideSaver nucleotideSaver) {
        this.experimentRepository = experimentRepository;
        this.storageService = storageService;
        this.interproManager = interproManager;
        this.nucleotideSaver = nucleotideSaver;
    }

    @Override
    public void processNewExperiment(MultipartFile fastaFile) throws IOException {
        checkFastaFormat(fastaFile);
        Experiment experiment = new Experiment(getNameNoExtension(fastaFile));
        experimentRepository.save(experiment);

        Path path = storageService.storeFileInExperiment(fastaFile, experiment.getName());
        FastaUploader fastaUploader = new FastaUploader(nucleotideSaver, new FastaParser(nucleotideSaver));
        fastaUploader.treatFasta(path, experiment);
        String rootFile = storageService.getRootLocation().toString();
        restartBlastDatabase();
    }

    private void restartBlastDatabase() throws IOException {
        Runtime.getRuntime().exec("find . -name \\*.*.* -type f -delete");
        Runtime.getRuntime().exec("docker stop sttdb-blast");
        Runtime.getRuntime().exec("docker run --rm -itp 4567:4567 -v ~/db:/db --name sttdb-blast wurmlab/sequenceserver:1.0.11");
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

    @Override
    public List<String> getFilesOfExperiment(String experiment) {
        try {
            return storageService.getExperimentFileNames(experiment);
        } catch (StorageException e) {
            throw new ExperimentNotFoundException(e);
        }
    }
}
