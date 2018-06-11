package sttDB.service.goService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sttDB.domain.Experiment;
import sttDB.exception.GoParsingException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Path;
import java.util.Scanner;

@Service
public class GoParser {

    @Autowired
    private GoSaver goSaver;

    void parseFile(Path filePath, Experiment experiment) {
        Scanner goScanner = null;
        try {
            goScanner = new Scanner(new FileReader(filePath.toFile()));
            goScanner.nextLine();
            while (goScanner.hasNextLine()) {
                String line = goScanner.nextLine();
                treatLine(line, experiment);
            }
        } catch (FileNotFoundException e) {
            throw new GoParsingException("File not found: " + filePath, e);
        } finally {
            if (goScanner != null)
                goScanner.close();
        }
    }

    private void treatLine(String line, Experiment experiment) {
        goSaver.save(line.split("\t+"), experiment);
    }
}
