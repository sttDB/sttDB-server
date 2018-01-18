package sttDB.service.storageService;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class ExperimentStorageServiceTest {

    ExperimentStorageService sut;

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Test
    public void rootLocationIsInitiated() {
        StorageProperties properties = new StorageProperties(folder.getRoot().toString());
        sut = new ExperimentStorageService(properties);

        assertThat(sut.getRootLocation().toString(), is(folder.getRoot().toString()));
    }


}