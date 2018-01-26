package sttDB.service.downloadFileService;

import sttDB.domain.Family;

import java.io.PrintWriter;

public class InterproFileWriter implements FileWriter<Family> {

    @Override
    public void insertDataLine(PrintWriter writer, Family data) {
        //Defined for the future
        throw new UnsupportedOperationException();
    }
}
