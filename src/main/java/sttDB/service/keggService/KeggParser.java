package sttDB.service.keggService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sttDB.domain.Experiment;
import sttDB.exception.KeggParsingException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Path;
import java.util.Scanner;

@Service
public class KeggParser {

    //Not autowired, we are not interested in having a singleton in this case.
    private KeggSaver keggSaver = new KeggSaver();

    private Scanner keggScanner = null;

    void parseFile(Path filePath, Experiment experiment) {
        try {
            keggScanner = new Scanner(new FileReader(filePath.toFile()));
            skipFirstLines();
            saveFirstPathLines();
            treatRestOfFile();
        } catch (FileNotFoundException e) {
            throw new KeggParsingException("File not found: " + filePath, e);
        } catch (NullPointerException e) {
            throw new KeggParsingException("An error ocurred when trying to get the lines from the file", e);
        } finally {
            if (keggScanner != null)
                keggScanner.close();
        }
    }

    /**
     * Evading the first three lines, they must not be saved.
     * If this lines do not exist, then an exception must throw, in this case the null pointer.
     */
    private void skipFirstLines() {
        keggScanner.nextLine();
        keggScanner.nextLine();
        keggScanner.nextLine();
    }

    private void saveFirstPathLines() {
        int pathCount = 0;
        String[] keggPaths = new String[3];
        while(keggScanner.hasNextLine()){
            String line = keggScanner.nextLine();
            if(line.startsWith(" ")){
                return;
            }
            keggPaths[pathCount] = line;
            if(pathCount == 2){
                savePaths(keggPaths);
            }
            pathCount++;
        }
    }

    private void savePaths(String[] keggPaths) {
        keggSaver.setFirstPath(keggPaths[0]);
        keggSaver.setFirstPath(keggPaths[1]);
        keggSaver.setFirstPath(keggPaths[2]);
    }

    private void treatRestOfFile() {
        //Consider the change of keggPaths in the middle of the file reading.
    }
}
