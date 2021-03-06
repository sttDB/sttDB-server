package sttDB.service.interproServices;

import org.junit.Before;
import org.junit.Test;
import sttDB.exception.InterproParsingException;

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;

public class TrapidInterproParserTest {

    public static final String BASE_INTERPRO_DIR = "files/interpro-test-files/";
    private TrapidInterproParser sut;

    @Before
    public void setUp() throws Exception {
        sut = new TrapidInterproParser();
    }

    @Test(expected = NullPointerException.class)
    public void fileToParseNotSet_throwException() throws InterproParsingException {
        sut.parse(null);
    }

    @Test
    public void fileWithHeaderOnly_emptyList() throws InterproParsingException {
        List<LineItems> result = sut.parse(getResource("header-only.txt"));

        assertThat(result, is(empty()));
    }

    @Test(expected = InterproParsingException.class)
    public void wrongFile_exception() throws InterproParsingException {
        List<LineItems> result = sut.parse(getResource("wrong-file.fasta"));
    }

    @Test
    public void fileWithHeaderAndOneLine_parsedItem() throws InterproParsingException {
        List<LineItems> result = sut.parse(getResource("ok-file.txt"));
        LineItems parsed = result.get(0);

        assertThat(result.size(), is(1));
        assertThat(parsed.trinityID, is("comp153003_c0_seq1"));
        assertThat(parsed.interproId, is("PF00859"));
        assertThat(parsed.description, is("CTF/NF-I family transcription modulation region"));
    }

    @Test
    public void fileWithManyLines_parsedItems() throws InterproParsingException {
        List<LineItems> result = sut.parse(getResource("ok-many-transcripts.txt"));

        assertThat(result, contains(
                new LineItems("comp153003_c0_seq1",
                        "PF03165",
                        "MH1 domain"),
                new LineItems("comp153003_c0_seq1",
                        "PF00859",
                        "CTF/NF-I family transcription modulation region")
        ));
    }

    private Path getResource(String path) {
        try {
            URL url = getClass().getClassLoader().getResource(BASE_INTERPRO_DIR + path);
            return Paths.get(url.toURI());
        } catch (NullPointerException e) {
            throw new NullPointerException("Test resource <" + path + "> not found");
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

}