package sttDB.service.downloadFileServices;

import sttDB.domain.Sequence;

import java.io.PrintWriter;

public class FastaFileCreator implements FileCreator<Sequence>{

    @Override
    public void insertDataLine(PrintWriter writer, Sequence data) {
        writer.println(">" + data.getTrinityId()
                + " " + data.getDynamicFastaInfo());
        writer.println(data.getTranscript());
    }
}
