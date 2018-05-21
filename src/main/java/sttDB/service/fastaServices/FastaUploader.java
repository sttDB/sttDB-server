package sttDB.service.fastaServices;

import sttDB.domain.Experiment;
import sttDB.exception.FastaParsingException;

import java.nio.file.Path;

public class FastaUploader {

    private FastaInfoSaver infoSaver;
    private FastaParser parser;

    public FastaUploader(FastaInfoSaver infoSaver, FastaParser parser) {
        this.infoSaver = infoSaver;
        this.parser = parser;
    }

    public void treatFasta(Path fastaFile, Experiment experiment) {

    }
}
