package sttDB.service.downloadFileService;

import cucumber.api.java.eo.Se;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import sttDB.domain.Sequence;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileCreatorTest {

    Sequence sequence;
    FileCreator<Sequence> fastaFileCreator;
    FileWriter<Sequence> fastaWriter;

    @Before
    public void setUp() {
        sequence = new Sequence();
        sequence.setTrinityId("test");
        sequence.setDynamicFastaInfo("bla");
        sequence.setTranscript("ABC");
        fastaFileCreator = new FileCreator<>("fasta");
        fastaWriter = new FastaFileWriter();
    }

    @Test
    public void createSimpleFile() throws IOException {
        List<Sequence> sequenceList = new ArrayList<>();
        sequenceList.add(sequence);
        fastaFileCreator.createFile(sequenceList, fastaWriter);
        BufferedReader fileReader = new BufferedReader(new FileReader(new File("searchedQuery.fasta")));
        String actualLine = fileReader.readLine();
        String expectedLine = ">test bla";
        assertEquals(expectedLine, actualLine);
        actualLine = fileReader.readLine();
        expectedLine = "ABC";
        assertEquals(expectedLine, actualLine);
        fileReader.close();
    }

    @Test
    public void createEmptyFile() throws IOException {
        List<Sequence> sequenceList = new ArrayList<>();
        fastaFileCreator.createFile(sequenceList, fastaWriter);
        BufferedReader fileReader = new BufferedReader(new FileReader(new File("searchedQuery.fasta")));
        assertEquals(null, fileReader.readLine());
    }
}
