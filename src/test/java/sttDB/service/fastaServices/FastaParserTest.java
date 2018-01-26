package sttDB.service.fastaServices;

import org.junit.Before;
import org.junit.Test;
import sttDB.domain.Experiment;
import sttDB.domain.Sequence;
import sttDB.repository.SequenceRepository;

import java.io.File;

import static org.junit.Assert.assertEquals;

public class FastaParserTest {
    private FastaParser fastaParser;

    private SequenceRepository sequenceRepository;

    @Before
    public void setUp() {
        fastaParser = new FastaParser();
    }

    @Test
    public void chargeCorrectFile() {
        Experiment experiment = new Experiment("test");
        ClassLoader classLoader = getClass().getClassLoader();
        File testFile = new File(classLoader.getResource("files/tests.fasta").getFile());
        fastaParser.treatFasta(testFile.toPath(), experiment);
        Sequence actual = sequenceRepository.findOne("comp6_c0_seq1");
        assertEquals("comp6_c0_seq1", actual.getTrinityId());
        assertEquals(4, actual.getLength());
        assertEquals("GGTT", actual.getTranscript());
    }

    @Test(expected = NullPointerException.class)
    public void chargeIncorrectFile() {
        Experiment experiment = new Experiment("test");
        fastaParser.treatFasta(new File("asd").toPath(), experiment);
    }
}
