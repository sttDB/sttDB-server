package sttDB.service;

import org.springframework.stereotype.Service;
import sttDB.exception.InterproParsingException;

import javax.sound.sampled.Line;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

@Service
public class TrapidInterproParser implements InterproParser {

    private File fileToParse;

    @Override
    public void parse() throws InterproParsingException {
        try {
            Scanner scanner = new Scanner(new FileReader(fileToParse));
            LineItems items;
            skipHeader(scanner);
            while (scanner.hasNextLine()) {
                items = parseLine(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            throw new InterproParsingException(e);
        }
    }

    private void skipHeader(Scanner scanner) {
        if (scanner.hasNextLine())
            scanner.nextLine();
    }

    public LineItems parseLine(String line) {
        List<String> parts = Arrays.asList(line.split("\\s+"));
        String trinityID = parts.get(1);
        String family = parts.get(2);
        String description = getDescription(parts);
        return new LineItems(trinityID, family, description);
    }

    private String getDescription(List<String> parts) {
        return parts.stream()
                .skip(3)
                .collect(Collectors.joining(" "));
    }

    @Override
    public File getFileToParse() {
        return fileToParse;
    }

    public void setFileToParse(File fileToParse) {
        this.fileToParse = fileToParse;
    }

    private class LineItems {

        String tirnityID;
        String familyName;
        String description;

        public LineItems(String tirnityID, String familyName, String description) {
            this.tirnityID = tirnityID;
            this.familyName = familyName;
            this.description = description;
        }
    }
}
