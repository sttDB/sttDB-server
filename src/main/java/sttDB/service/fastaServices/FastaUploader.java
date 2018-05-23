package sttDB.service.fastaServices;

import org.springframework.stereotype.Service;
import sttDB.domain.Experiment;
import sttDB.exception.FastaParsingException;

import java.nio.file.Path;

@Service
public class FastaUploader {

    private FastaInfoSaver infoSaver;
    private FastaParser parser;

    public FastaUploader(FastaInfoSaver infoSaver, FastaParser parser) {
        this.infoSaver = infoSaver;
        this.parser = parser;
    }

    public void treatFasta(Path fastaFile, Experiment experiment) {
        try {
            infoSaver.deleteOldSequences(experiment);
            parser.parseFile(fastaFile, experiment);
        } catch (Exception e) {
            throw new FastaParsingException(e);
        }
    }
}
