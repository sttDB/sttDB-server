package sttDB.service;

import org.springframework.stereotype.Service;
import sttDB.exception.InterproParsingException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

@Service
public class TrapidInterproParser implements InterproParser {

    private File fileToParse;

    @Override
    public List<LineItems> parse() throws InterproParsingException {
        try {
            Scanner scanner = new Scanner(new FileReader(fileToParse));
            List<LineItems> items = new LinkedList<>();
            skipHeader(scanner);
            while (scanner.hasNextLine()) {
                items.add(parseLine(scanner.nextLine()));
            }
            return items;
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
        String interproID = parts.get(2);
        String description = getDescription(parts);
        return new LineItems(trinityID, interproID, description);
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

}
