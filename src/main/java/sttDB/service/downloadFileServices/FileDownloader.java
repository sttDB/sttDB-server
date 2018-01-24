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

    public File createFile(Iterator<E> dataToWrite, FileCreator<E> fileCreator) throws IOException {
        PrintWriter writer = new PrintWriter(path, "UTF-8");
        while (dataToWrite.hasNext()) {
            fileCreator.insertDataLine(writer, dataToWrite.next());
        }
        writer.close();
        return new File(path);
    }

}
