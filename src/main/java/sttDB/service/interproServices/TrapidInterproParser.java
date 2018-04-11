package sttDB.service.interproServices;

import org.springframework.stereotype.Service;
import sttDB.domain.Family;
import sttDB.exception.InterproParsingException;

import java.io.*;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TrapidInterproParser implements InterproParser {

    private File inputFile;

    private List<LineItems> items;

    // Using this structure, the updates over a family will be a lot easier.
    private Map<String, List<Family>> groupedItems = new HashMap<>();

    @Override
    public List<LineItems> parse(Path path) throws InterproParsingException {
        setFileToParse(path);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(inputFile)))) {
            items = reader.lines()
                    .skip(1)
                    .map(this::parseLine)
                    .collect(Collectors.toList());
            reader.close();
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new InterproParsingException("Wrong file format", e);
        } catch (IOException e) {
            throw new InterproParsingException(e);
        }
        return items;
    }

    private void setFileToParse(Path path) throws InterproParsingException {
        inputFile = path.toFile();
    }

    private LineItems parseLine(String line) {
        List<String> parts = Arrays.asList(line.split("\\s+"));
        String trinityID = parts.get(1);
        String interproID = parts.get(2);
        String description = getDescription(parts);
        addIntoGroupedItems(trinityID, new Family(interproID, description));
        return new LineItems(trinityID, interproID, description);
    }

    private String getDescription(List<String> parts) {
        return parts.stream()
                .skip(3)
                .collect(Collectors.joining(" "));
    }

    private void addIntoGroupedItems(String trinityID, Family family) {
        groupedItems.computeIfAbsent(trinityID, k -> new ArrayList<Family>());
        groupedItems.get(trinityID).add(family);
    }

    public Map<String, List<Family>> getGroupedItems(){
        return this.groupedItems;
    }
}
