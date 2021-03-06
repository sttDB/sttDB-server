package sttDB.service.experimentManager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;
import sttDB.domain.Experiment;
import sttDB.exception.ExperimentNotFoundException;
import sttDB.repository.ExperimentRepository;
import sttDB.service.fastaServices.FastaParser;
import sttDB.service.fastaServices.NucleotideSaver;
import sttDB.service.interproServices.InterproUploader;
import sttDB.service.storageService.StorageService;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentCaptor.forClass;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ExperimentManagerImplTest {

    public static final String EXPERIMENT_FASTA = "experiment.fasta";
    public static final String FASTA_CONTENT = "fasta text";
    public static final String SOME_FAMILIES = "some families";
    public static final String INTERPRO = "interpro";

    private ExperimentManagerImpl sut;
    private ExperimentRepository repository;
    private StorageService storage;
    private FastaParser fastaParser;
    private InterproUploader interprinterproManagerarser;
    private NucleotideSaver nucleotideSaver;
    private MockMultipartFile fastaFileMock;
    private MockMultipartFile familyFileMock;

    @Autowired
    private NucleotideSaver nucleotideSaverWired;

    @Autowired
    private StorageService storageWired;

    @Before
    public void setUp() throws IOException {
        repository = mock(ExperimentRepository.class);
        storage = mock(StorageService.class);
        fastaParser = mock(FastaParser.class);
        nucleotideSaver = mock(NucleotideSaver.class);
        interprinterproManagerarser = mock(InterproUploader.class);
        sut = new ExperimentManagerImpl(repository, storage, interprinterproManagerarser, nucleotideSaver);
        fastaFileMock = new MockMultipartFile("file", EXPERIMENT_FASTA, "multipart/form-data",
                new ByteArrayInputStream(FASTA_CONTENT.getBytes()));
        familyFileMock = new MockMultipartFile("file", INTERPRO, "multipart/form-data",
                new ByteArrayInputStream(SOME_FAMILIES.getBytes()));
    }

    @Test
    public void createExperimentDocument() {
        ArgumentCaptor<Experiment> argument = forClass(Experiment.class);


        sut = new ExperimentManagerImpl(repository, storageWired,interprinterproManagerarser, nucleotideSaverWired);
        sut.processNewExperiment(fastaFileMock);

        verify(repository).save(argument.capture());
        assertThat("experiment", is(argument.getValue().getName()));
    }

    @Test
    public void fastaUploaderIsCalledWithFastaFilePath() {
        ArgumentCaptor<String[]> stringArgument = forClass(String[].class);
        ArgumentCaptor<Experiment> experimentArgument = forClass(Experiment.class);
        Path path = Paths.get("/myPath");
        Experiment mockExperiment = new Experiment(EXPERIMENT_FASTA);
        when(storage.storeFileInExperiment(fastaFileMock, "experiment"))
                .thenReturn(path);

        sut = new ExperimentManagerImpl(repository, storageWired,interprinterproManagerarser, nucleotideSaverWired);
        sut.processNewExperiment(fastaFileMock);


    }

    @Test
    public void experimentFileIsStored() {
        ArgumentCaptor<MultipartFile> file = forClass(MultipartFile.class);
        ArgumentCaptor<String> fileName = forClass(String.class);

        sut = new ExperimentManagerImpl(repository, storageWired,interprinterproManagerarser, nucleotideSaverWired);
        sut.processNewExperiment(fastaFileMock);

        assertTrue(Paths.get(System.getenv("FILES_DIR")+"experiment").isAbsolute());
    }

    private String getFileWithoutExtension() {
        String fileName = fastaFileMock.getOriginalFilename().split("\\.")[0];
        return fileName;
    }

    @Test(expected = ExperimentNotFoundException.class)
    public void uploadFamilyFile_ExperimentNotExists() {
        when(repository.findOne("notFoundExperiment")).thenReturn(null);

        sut.addFamilyFileToExperiment(familyFileMock, "notFoundExperiment");
    }

    @Test
    public void storeFamilyFileInExperiment() {
        ArgumentCaptor<String> experimentName = forClass(String.class);
        ArgumentCaptor<MultipartFile> file = forClass(MultipartFile.class);
        when(repository.findOne(EXPERIMENT_FASTA)).thenReturn(new Experiment(EXPERIMENT_FASTA));

        sut.addFamilyFileToExperiment(familyFileMock, EXPERIMENT_FASTA);

        verify(storage).storeFileInExperiment(file.capture(), experimentName.capture());
        assertThat(file.getValue(), is(familyFileMock));
        assertThat(experimentName.getValue(), is(EXPERIMENT_FASTA));
    }

}