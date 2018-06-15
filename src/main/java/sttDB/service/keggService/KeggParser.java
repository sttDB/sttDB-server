package sttDB.service.keggService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sttDB.domain.Experiment;
import sttDB.exception.KeggParsingException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Path;
import java.util.Scanner;
import java.util.Stack;

//God bless this monster
@Service
public class KeggParser {

    @Autowired
    private KeggSaver keggSaver;

    private Scanner keggScanner = null;

    private Experiment experiment;

    void parseFile(Path filePath, Experiment experiment) {
        this.experiment = experiment;
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
        while (keggScanner.hasNextLine() && pathCount < 3) {
            String line = keggScanner.nextLine();
            keggPaths[pathCount] = line;
            if (pathCount == 2) {
                savePaths(keggPaths);
            }
            pathCount++;
        }
    }

    private void savePaths(String[] keggPaths) {
        keggSaver.setFirstPath(keggPaths[0]);
        keggSaver.setSecondPath(keggPaths[1]);
        keggSaver.setThirdPath(keggPaths[2]);
    }

    private void treatRestOfFile() {
        String lastLine = saveFirstKeggAndSequences();
        while (keggScanner.hasNextLine()) {
            String line = keggScanner.nextLine();
            line = parseLine(lastLine, line);
            lastLine = line;
        }
    }

    private String parseLine(String lastLine, String line) {
        if (lastLine.endsWith(",")) {
            treatSequenceLine(line);
        } else {
            line = createNewKeggStatement(line);
        }
        return line;
    }

    private String createNewKeggStatement(String line) {
        if (line.startsWith(" ")) {
            treatKeggAndFirstSequencesLine(line);
        } else {//create new kegg paths
            Stack<String> keggPaths = new Stack<>();
            while (!line.startsWith(" ") && keggScanner.hasNextLine()) {
                keggPaths.add(line);
                line = keggScanner.nextLine();
            }
            saveNewKeggPaths(keggPaths);
            treatKeggAndFirstSequencesLine(line);
        }
        return line;
    }


    //We asume that the first line always exists, if not, we need an error to occur.
    private String saveFirstKeggAndSequences() {
        String lastLine = keggScanner.nextLine();
        saveKeggId(lastLine);//first keggId
        lastLine = keggScanner.nextLine();
        treatSequenceLine(lastLine);//first sequences
        return lastLine;
    }

    private void treatKeggAndFirstSequencesLine(String line) {
        saveKeggId(line);
        if (keggScanner.hasNextLine()) {
            treatSequenceLine(keggScanner.nextLine());
        } else {
            throw new KeggParsingException("There is a kegg id without sequences related to it");
        }
    }

    private void saveNewKeggPaths(Stack<String> keggPaths) {
        switch (keggPaths.size()) {
            case 3:
                keggSaver.setThirdPath(keggPaths.pop());
                keggSaver.setSecondPath(keggPaths.pop());
                keggSaver.setFirstPath(keggPaths.pop());
                break;
            case 2:
                keggSaver.setThirdPath(keggPaths.pop());
                keggSaver.setSecondPath(keggPaths.pop());
                break;
            case 1:
                keggSaver.setThirdPath(keggPaths.pop());
                break;
            default:
                throw new KeggParsingException("KeggPaths variable contains no paths");
        }
    }

    private void saveKeggId(String keggIdLine) {
        keggSaver.setKeggId(keggIdLine.trim());
    }

    private void treatSequenceLine(String sequencesLine) {
        sequencesLine = sequencesLine.trim();
        String[] sequences = sequencesLine.split(", *");
        keggSaver.saveSequences(sequences, experiment);
    }
}
