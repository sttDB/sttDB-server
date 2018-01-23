package sttDB.service.storageService;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.springframework.mock.web.MockMultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.core.Is.is;

public class ExperimentStorageServiceTest {

    public static final String TEST_CONTENT = "test-content";
    public static final String FASTA_FILE = "fasta.fasta";
    public static final String EXPERIMENT = "fasta.fasta";
    public static final String FAMILIES_TXT = "families.txt";
    public static final String INTERPRO = "interpro";
    ExperimentStorageService sut;

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();
    public File rootFolder;
    private MockMultipartFile file;
    private MockMultipartFile notFasta;

    @Before
    public void setUp() throws IOException {
        rootFolder = folder.newFolder("root");
        StorageProperties properties = new StorageProperties(rootFolder.toString());
        sut = new ExperimentStorageService(properties);
        file = new MockMultipartFile("file", FASTA_FILE, "multipart/form-data",
                new ByteArrayInputStream(TEST_CONTENT.getBytes()));
        notFasta = new MockMultipartFile("file", FAMILIES_TXT, "multipart/form-data",
                new ByteArrayInputStream(INTERPRO.getBytes()));
    }

    @Test
    public void rootLocationIsInitiated() {
        assertThat(sut.getRootLocation(), is(rootFolder.toPath()));
    }

    @Test
    public void experimentFolderIsCreated() {
        Path path = sut.storeFileInExperiment(file, EXPERIMENT);

        assertThat(Files.exists(rootFolder.toPath().resolve(EXPERIMENT)), is(true));
    }

    @Test
    public void experimentFileIsStored() throws IOException {
        Path path = sut.storeFileInExperiment(file, EXPERIMENT);

        assertThat(path, is(rootFolder.toPath().resolve(EXPERIMENT).resolve(FASTA_FILE)));
        assertThat(TEST_CONTENT, is(Files.readAllLines(path).get(0)));
    }

    @Test
    public void storeNotFastaFileInExistingExperiment() {
        Path path = sut.storeFileInExperiment(notFasta, EXPERIMENT);

        assertThat(path, is(rootFolder.toPath().resolve(EXPERIMENT).resolve(FAMILIES_TXT)));
    }

    @Test
    public void storeFastaFirstAndNotFastaSecond() {
        Path fastaPath = sut.storeFileInExperiment(file, EXPERIMENT);
        Path notFastaPath = sut.storeFileInExperiment(notFasta, EXPERIMENT);

        assertThat(fastaPath, is(is(rootFolder.toPath().resolve(EXPERIMENT).resolve(FASTA_FILE))));
        assertThat(notFastaPath, is(rootFolder.toPath().resolve(EXPERIMENT).resolve(FAMILIES_TXT)));
    }

    @Test
    public void storeFilesNotOrdered() {
        Path notFastaPath = sut.storeFileInExperiment(notFasta, EXPERIMENT);
        Path fastaPath = sut.storeFileInExperiment(file, EXPERIMENT);

        assertThat(fastaPath, is(is(rootFolder.toPath().resolve(EXPERIMENT).resolve(FASTA_FILE))));
        assertThat(notFastaPath, is(rootFolder.toPath().resolve(EXPERIMENT).resolve(FAMILIES_TXT)));
    }

    @Test
    public void experimentExists_loadFile() throws IOException {
        Path path = sut.storeFileInExperiment(file, EXPERIMENT);

        Path retrieved = sut.loadFileFromExperiment(file.getOriginalFilename(), EXPERIMENT);

        assertThat(retrieved, is(path));
        assertThat(TEST_CONTENT, is(Files.readAllLines(path).get(0)));
    }

    @Test(expected = StorageFileNotFoundException.class)
    public void getFileThatDoesntExist() {
        Path retrieved = sut.loadFileFromExperiment(file.getOriginalFilename(), EXPERIMENT);
    }

    @Test
    public void overwriteFile() throws IOException {
        sut.storeFileInExperiment(file, EXPERIMENT);
        sut.storeFileInExperiment(file, EXPERIMENT);

        Path path = sut.loadFileFromExperiment(file.getOriginalFilename(), EXPERIMENT);
        assertThat(TEST_CONTENT, is(Files.readAllLines(path).get(0)));
    }

    @Test
    public void getFileNamesOfExperiment() {
        MockMultipartFile familiyFile = notFasta;
        sut.storeFileInExperiment(file, EXPERIMENT);
        sut.storeFileInExperiment(familiyFile, EXPERIMENT);

        List<String> fileNames = sut.getExperimentFileNames(EXPERIMENT);

        assertThat(fileNames, containsInAnyOrder(FASTA_FILE, FAMILIES_TXT));
    }

    @Test(expected = StorageException.class)
    public void getFilesOfExeperimentThatDoesntExist() {
        List<String> fileNames = sut.getExperimentFileNames("missing-experiment");
    }

}