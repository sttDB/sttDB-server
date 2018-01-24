package sttDB.service.downloadFileServices;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;

public class FileDownloader<E>{

    private String path = "searchedQuery";

    public FileDownloader(String typeOfExtension){
        path = path + typeOfExtension;
    }

    public File createFile(Iterator<E> dataToWrite, FileWriter<E> fileWriter) throws IOException {
        PrintWriter writer = new PrintWriter(path, "UTF-8");
        while (dataToWrite.hasNext()) {
            fileWriter.insertDataLine(writer, dataToWrite.next());
        }
        writer.close();
        return new File(path);
    }

}
