package sttDB.service.interproServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sttDB.exception.InterproParsingException;

import java.io.*;
import java.util.List;

@Service
public class TrapidInterproParser implements InterproParser {

    private BufferedReader reader;

    @Override
    public List<LineItems> parse() throws InterproParsingException {
        if (reader == null)
            throw new InterproParsingException("File to parse not set, call 'setFileToParse first'");
        return null;
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
