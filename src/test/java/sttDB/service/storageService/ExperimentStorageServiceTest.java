package sttDB.service.storageService;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.springframework.mock.web.MockMultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class ExperimentStorageServiceTest {

    public static final String TEST_CONTENT = "test-content";
    public static final String FASTA_FILE = "fasta.fasta";
    public static final String EXPERIMENT = "experiment-A";
    ExperimentStorageService sut;

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();
    private MockMultipartFile file;

    @Before
    public void setUp() throws IOException {
        StorageProperties properties = new StorageProperties(folder.getRoot().toString());
        sut = new ExperimentStorageService(properties);
        file = new MockMultipartFile(FASTA_FILE, new ByteArrayInputStream(TEST_CONTENT.getBytes()));
    }

    @Test
    public void rootLocationIsInitiated() {
        assertThat(sut.getRootLocation().toString(), is(folder.getRoot().toString()));
    }

    @Test
    public void experimentFolderIsCreated() throws IOException {
        Path path = sut.storeFileInExperiment(file, EXPERIMENT);

        assertThat(Files.exists(folder.getRoot().toPath().resolve(EXPERIMENT)), is(true));
    }

    @Test
    public void experimentFileIsStored() throws IOException {
        Path path = sut.storeFileInExperiment(file, EXPERIMENT);

        assertThat(Files.exists(folder.getRoot().toPath().resolve(EXPERIMENT).resolve(FASTA_FILE)), is(true));
        assertThat(Files.readAllLines(path).get(0).equals(TEST_CONTENT), is(true));
    }


}