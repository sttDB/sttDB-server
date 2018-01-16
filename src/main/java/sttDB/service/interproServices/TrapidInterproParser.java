package sttDB.service.interproServices;

import org.springframework.stereotype.Service;
import sttDB.exception.InterproParsingException;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TrapidInterproParser implements InterproParser {

    private File inputFile;

    private List<LineItems> items;

    @Override
    public List<LineItems> parse(String path) throws InterproParsingException {
        setFileToParse(path);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(inputFile)))) {
            items = reader.lines()
                    .skip(1)
                    .map(this::parseLine)
                    .collect(Collectors.toList());
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new InterproParsingException("Wrong file format", e);
        } catch (IOException e) {
            throw new InterproParsingException(e);
        }
        return items;
    }

    private void setFileToParse(String path) throws InterproParsingException {
        inputFile = new File(path);
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
}
