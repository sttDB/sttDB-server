package sttDB.service.interproServices;

import org.junit.Test;
import sttDB.exception.InterproParsingException;

public class TrapidInterproParserTest {

    @Test(expected = InterproParsingException.class)
    public void fileToParseNotSet_throwException() throws InterproParsingException {
        TrapidInterproParser sut = new TrapidInterproParser();
        sut.parse();
    }

}