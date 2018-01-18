package sttDB.service.experimentManager;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import sttDB.domain.Experiment;
import sttDB.repository.ExperimentRepository;
import sttDB.service.fastaServices.FastaParser;
import sttDB.service.storageService.StorageService;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentCaptor.forClass;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;


public class ExperimentManagerImplTest {

    public static final String EXPERIMENT_FASTA = "experiment.fasta";
    public static final String FASTA_CONTENT = "fasta text";
    ExperimentManagerImpl sut;
    private ExperimentRepository repository;
    private StorageService storage;
    private FastaParser fastaParser;
    private MockMultipartFile fastaFileMock;

    @Before
    public void setUp() throws IOException {
        repository = mock(ExperimentRepository.class);
        storage = mock(StorageService.class);
        fastaParser = mock(FastaParser.class);
        sut = new ExperimentManagerImpl(repository, storage, fastaParser);
        fastaFileMock = new MockMultipartFile(EXPERIMENT_FASTA, new ByteArrayInputStream(FASTA_CONTENT.getBytes()));
    }

    @Test
    public void createExperimentDocument() {
        ArgumentCaptor<Experiment> argument = forClass(Experiment.class);

        sut.processNewExperiment(fastaFileMock);

        verify(repository).save(argument.capture());
        assertThat(EXPERIMENT_FASTA, is(argument.getValue().getName()));
    }

    @Test
    public void experimentFileIsStored() {
        ArgumentCaptor<MultipartFile> file = forClass(MultipartFile.class);
        ArgumentCaptor<String> fileName = forClass(String.class);

        sut.processNewExperiment(fastaFileMock);

        verify(storage).storeFileInExperiment(file.capture(), fileName.capture());
        assertThat(file.getValue(), is(fastaFileMock));
        assertThat(fileName.getValue(), is(fastaFileMock.getName()));
    }

}