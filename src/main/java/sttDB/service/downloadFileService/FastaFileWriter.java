package sttDB.service.downloadFileService;

import sttDB.domain.Sequence;

import java.io.InvalidObjectException;
import java.io.PrintWriter;

public class FastaFileWriter implements FileWriter<Sequence> {

    @Override
    public void insertDataLine(PrintWriter writer, Sequence data) throws InvalidObjectException {
        if(data.getTrinityId() == null || data.getTranscript() == null) {
            throw new InvalidObjectException("Sequence information not found");
        }
        writer.println(">" + data.getTrinityId()
                + " " + data.getDynamicFastaInfo());
        writer.println(data.getTranscript());
    }
}
