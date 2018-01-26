package sttDB.service.downloadFileService;

import org.junit.Before;
import org.junit.Test;
import sttDB.domain.Sequence;

import java.io.*;

import static org.junit.Assert.assertEquals;

public class FastaFileWriterTest {

    private PrintWriter printWriter;
    private Sequence sequence;
    private FileWriter<Sequence> fileWriter;

    @Before
    public void setUp() throws IOException {
        printWriter = new PrintWriter("searchedQuery.fasta", "UTF-8");
        sequence = new Sequence();
        fileWriter = new FastaFileWriter();
    }

    @Test(expected = NullPointerException.class)
    public void nullSequence() throws InvalidObjectException {
        fileWriter.insertDataLine(printWriter, null);
        printWriter.close();
    }

    @Test(expected = InvalidObjectException.class)
    public void invalidSequence() throws InvalidObjectException {
        fileWriter.insertDataLine(printWriter, sequence);
        printWriter.close();
    }

    @Test
    public void validSequence() throws IOException {
        sequence.setTrinityId("test");
        sequence.setDynamicFastaInfo("bla");
        sequence.setTranscript("ABC");
        fileWriter.insertDataLine(printWriter, sequence);
        printWriter.close();
        BufferedReader fileReader = new BufferedReader(new FileReader(new File("searchedQuery.fasta")));
        String actualLine = fileReader.readLine();
        String expectedLine = ">test bla";
        assertEquals(expectedLine, actualLine);
        actualLine = fileReader.readLine();
        expectedLine = "ABC";
        assertEquals(expectedLine, actualLine);
        fileReader.close();
    }

}
