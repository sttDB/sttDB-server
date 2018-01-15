package sttDB.service.interproServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sttDB.exception.InterproParsingException;
import sttDB.utils.ReaderFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

@Service
public class TrapidInterproParser implements InterproParser {

    private Reader fileReader;

    ReaderFactory factory;

    @Override
    public List<LineItems> parse() throws InterproParsingException {
        if (fileReader == null)
            throw new InterproParsingException("File to parse not set, call 'setFileToParse first'");
        return null;
    }

    @Override
    public void setFileToParse(String path) throws InterproParsingException {
        try {
            fileReader = factory.readerFromPath(path);
        } catch (FileNotFoundException e) {
            throw new InterproParsingException("File not found", e);
        }
    }

    @Autowired
    public void setFactory(ReaderFactory factory) {
        this.factory = factory;
    }
}
