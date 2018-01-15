package sttDB.service.interproServices;

import org.junit.Before;
import org.junit.Test;
import sttDB.exception.InterproParsingException;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsEmptyCollection.empty;

public class TrapidInterproParserTest {

    public static final String BASE_INTERPRO_DIR = "files/interpro-test-files/";
    private TrapidInterproParser sut;

    @Before
    public void setUp() throws Exception {
        sut = new TrapidInterproParser();
    }

    @Test(expected = InterproParsingException.class)
    public void fileToParseNotSet_throwException() throws InterproParsingException {
        sut.parse();
    }

    @Test
    public void fileWithHeaderOnly_emptyList() throws InterproParsingException {
        sut.setFileToParse(getResource("header-only.txt"));

        List<LineItems> result = sut.parse();

        assertThat(result, is(empty()));
    }

    @Test(expected = InterproParsingException.class)
    public void wrongFile_exception() throws InterproParsingException {
        sut.setFileToParse(getResource("wrong-file.fasta"));

        List<LineItems> result = sut.parse();
    }

    private String getResource(String path) {
        try {
            return getClass().getClassLoader()
                    .getResource(BASE_INTERPRO_DIR + path).getFile();
        } catch (NullPointerException e) {
            throw new NullPointerException("Test resource <" + path + "> not found");
        }
    }

}