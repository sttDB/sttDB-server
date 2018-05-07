package sttDB.service.downloadFileService;

import java.io.*;
import java.util.Iterator;
import java.util.UUID;
import java.util.stream.Stream;

public class FileCreator<E>{

    private String path = "searchedQuery";

    public FileCreator(String typeOfExtension){
        path = path + UUID.randomUUID().toString() + "." + typeOfExtension;
    }

    //Iterable must be changed for a stream in future work, because of big sets of data not working
    public File createFile(Stream<E> dataStructure, FileWriter<E> fileWriter) throws IOException {
        PrintWriter writer = new PrintWriter(path, "UTF-8");
        writeFile(dataStructure.iterator(), fileWriter, writer);
        writer.close();
        dataStructure.close();
        return new File(path);
    }

    private void writeFile(Iterator<E> dataToWrite, FileWriter<E> fileWriter, PrintWriter writer) throws IOException{
        while (dataToWrite.hasNext()) {
            fileWriter.insertDataLine(writer, dataToWrite.next());
        }
    }

}
