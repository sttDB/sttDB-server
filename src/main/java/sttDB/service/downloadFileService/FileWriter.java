package sttDB.service.downloadFileService;

import java.io.InvalidObjectException;
import java.io.PrintWriter;

public interface FileWriter<E> {
    void insertDataLine(PrintWriter writer, E data) throws InvalidObjectException;
}
