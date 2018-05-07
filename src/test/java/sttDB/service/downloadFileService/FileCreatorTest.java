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

    private Sequence sequence;
    private FileCreator<Sequence> fastaFileCreator;
    private FileWriterDouble fileWriter;

    @Before
    public void setUp() {
        sequence = new Sequence();
        sequence.setTrinityId("test");
        sequence.setDynamicFastaInfo("bla");
        sequence.setTranscript("ABC");
        fastaFileCreator = new FileCreator<>("fasta");
        fileWriter = new FileWriterDouble();
    }

    @Test
    public void createSimpleFile() throws IOException {
        List<Sequence> sequenceList = new ArrayList<>();
        sequenceList.add(sequence);
        fastaFileCreator.createFile(sequenceList.stream(), fileWriter);
        Sequence actualSequence = (Sequence) fileWriter.passedData;
        assertEquals(sequence.getTrinityId(), actualSequence.getTrinityId());
        assertEquals(sequence.getTranscript(), actualSequence.getTranscript());
        assertEquals(sequence.getDynamicFastaInfo(), actualSequence.getDynamicFastaInfo());
    }

    @Test
    public void createEmptyFile() throws IOException {
        List<Sequence> sequenceList = new ArrayList<>();
        fastaFileCreator.createFile(sequenceList.stream(), fileWriter);
        BufferedReader fileReader = new BufferedReader(new FileReader(new File("searchedQuery.fasta")));
        assertEquals(null, fileReader.readLine());
    }
}


class FileWriterDouble implements FileWriter {
    Object passedData;
    PrintWriter passedWriter;
    @Override
    public void insertDataLine(PrintWriter writer, Object data) throws InvalidObjectException {
        passedData = data;
        passedWriter = writer;
    }
}