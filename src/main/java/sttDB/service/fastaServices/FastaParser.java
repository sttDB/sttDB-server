package sttDB.service.fastaServices;

import org.springframework.stereotype.Service;
import sttDB.domain.Experiment;
import sttDB.domain.Sequence;
import sttDB.exception.FastaParsingException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Path;
import java.util.Scanner;

@Service
public class FastaParser {

    private Experiment experiment;
    private FastaInfoSaver infoSaver;

    public FastaParser(FastaInfoSaver infoSaver) {
        this.infoSaver = infoSaver;
    }

    void parseFile(Path filePath, Experiment experiment) {
        this.experiment = experiment;
        Scanner fastaScanner = null;
        try {
            fastaScanner = new Scanner(new FileReader(filePath.toFile()));
            String[] sequenceLine = new String[2];
            while (fastaScanner.hasNextLine()) {
                String line = fastaScanner.nextLine();
                sequenceLine = treatLine(sequenceLine, line);
            }
            infoSaver.saveInfo(sequenceLine, experiment);
        } catch (FileNotFoundException e) {
            throw new FastaParsingException("File not found: " + filePath, e);
        } finally {
            if (fastaScanner != null)
                fastaScanner.close();
        }
    }

    private String[] treatLine(String[] sequenceLine, String line) {
        if (line.startsWith(">")) {
            infoSaver.saveInfo(sequenceLine, experiment);
            sequenceLine[0] = line;
            sequenceLine[1] = "";
        } else {
            sequenceLine[1] += line;
        }
        return sequenceLine;
    }

}
