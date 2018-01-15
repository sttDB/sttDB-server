package sttDB.service.interproServices;

import org.springframework.stereotype.Service;
import sttDB.exception.InterproParsingException;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TrapidInterproParser implements InterproParser {

    private BufferedReader reader;

    List<LineItems> items;

    @Override
    public List<LineItems> parse() throws InterproParsingException {
        if (reader == null)
            throw new InterproParsingException("File to parse not set, call 'setFileToParse first'");

        try {
            items = reader.lines()
                    .skip(1)
                    .map(this::parseLine)
                    .collect(Collectors.toList());
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new InterproParsingException("Wrong file format", e);
        }
        return items;
    }

    private LineItems parseLine(String line) {
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
    public void setFileToParse(String path) throws InterproParsingException {
        try {
            File inputF = new File(path);
            InputStream inputFS = new FileInputStream(inputF);
            reader = new BufferedReader(new InputStreamReader(inputFS));
        } catch (FileNotFoundException e) {
            throw new InterproParsingException("File not found", e);
        }
    }
}
