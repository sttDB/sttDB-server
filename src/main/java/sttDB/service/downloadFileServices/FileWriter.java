package sttDB.service.downloadFileServices;

import java.io.PrintWriter;

public interface FileWriter<E> {
    void insertDataLine(PrintWriter writer, E data);
}
