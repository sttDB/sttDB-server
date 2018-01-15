package sttDB.service.interproServices;

import org.junit.Test;
import sttDB.exception.InterproParsingException;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsEmptyCollection.empty;

public class TrapidInterproParserTest {

    @Test(expected = InterproParsingException.class)
    public void fileToParseNotSet_throwException() throws InterproParsingException {
        TrapidInterproParser sut = new TrapidInterproParser();
        sut.parse();
    }

    @Test
    public void fileWithHeaderOnly_emptyList() throws InterproParsingException {
        TrapidInterproParser sut = new TrapidInterproParser();
        sut.setFileToParse(getResource("files/interpro-test-files/header-only.txt"));

        List<LineItems> result = sut.parse();

        assertThat(result, is(empty()));
    }

    @Test(expected = InterproParsingException.class)
    public void wrongFile_exception() throws InterproParsingException {
        TrapidInterproParser sut = new TrapidInterproParser();
        sut.setFileToParse(getResource("files/interpro-test-files/wrong-file.fasta"));

        List<LineItems> result = sut.parse();
    }

    private String getResource(String path) {
        try {
            return getClass().getClassLoader().getResource(path).getFile();
        } catch (NullPointerException e) {
            throw new NullPointerException("Test resource <" + path + "> not found");
        }
    }

}