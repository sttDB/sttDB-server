package sttDB.utils;

import org.springframework.stereotype.Service;

import java.io.*;

@Service
public class ReaderFactory {

    public Reader readerFromPath(String path) throws FileNotFoundException {
        File inputF = new File(path);
        InputStream inputFS = new FileInputStream(inputF);
        return new BufferedReader(new InputStreamReader(inputFS));
    }

}
