package sttDB.service.experimentManager;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.mock.web.MockMultipartFile;
import sttDB.domain.Experiment;
import sttDB.repository.ExperimentRepository;
import sttDB.service.fastaServices.FastaParser;
import sttDB.service.storageService.StorageService;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;


public class ExperimentManagerImplTest {

    ExperimentManagerImpl sut;

    @Test
    public void createExperimentDocument() throws IOException {
        ExperimentRepository repository = mock(ExperimentRepository.class);
        StorageService storage = mock(StorageService.class);
        FastaParser fastaParser = mock(FastaParser.class);
        sut = new ExperimentManagerImpl(repository, storage, fastaParser);
        ArgumentCaptor<Experiment> argument = ArgumentCaptor.forClass(Experiment.class);

        MockMultipartFile fastaFile = new MockMultipartFile("experiment.fasta", new ByteArrayInputStream("fasta text".getBytes()));
        sut.processNewExperiment(fastaFile);

        verify(repository).save(argument.capture());
        assertThat("experiment.fasta", is(argument.getValue().getName()));
    }

}