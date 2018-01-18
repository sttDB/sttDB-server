package sttDB.service.storageService;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ExperimentStorageServiceTest {

    ExperimentStorageService sut;

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Before
    public void setUp() {
        StorageProperties properties = new StorageProperties(folder.getRoot().toString());
        sut = new ExperimentStorageService(properties);
    }

    @Test
    public void rootLocationIsInitiated() {
        assertThat(sut.getRootLocation().toString(), is(folder.getRoot().toString()));
    }

    @Test
    public void experimentFolderIsCreated() throws IOException {
        MultipartFile file = mock(MultipartFile.class);
        when(file.getName()).thenReturn("fasta.fasta");

        Path path = sut.storeFileInExperiment(file, "experiment-A");

        assertThat(Files.exists(folder.getRoot().toPath().resolve("experiment-A")), is(true));
    }


}