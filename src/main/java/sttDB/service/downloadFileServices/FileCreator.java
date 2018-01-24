package sttDB.service.downloadFileServices;

import java.io.PrintWriter;

public interface FileCreator<E> {
    void insertDataLine(PrintWriter writer, E data);
}
