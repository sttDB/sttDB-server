package sttDB.service;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import sttDB.domain.Sequence;
import sttDB.repository.SequenceRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Iterator;
import java.util.List;

@Controller
public class FastaDownloader {

    @Autowired
    private SequenceRepository sequenceRepository;

    @RequestMapping(value = "/downloadFasta", method = RequestMethod.GET)
    public void download(@RequestParam("ids") List<String> sequenceId, final HttpServletRequest request, final HttpServletResponse response) throws IOException {
        File file = createFasta(sequenceId);

        InputStream fileInputStream = new FileInputStream(file);
        OutputStream output = response.getOutputStream();

        response.reset();

        response.setContentType("application/octet-stream");
        response.setContentLength((int) (file.length()));
        response.setHeader("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"");

        IOUtils.copyLarge(fileInputStream, output);
        output.flush();
    }

    private File createFasta(List<String> sequenceId) throws IOException {
        PrintWriter writer = new PrintWriter("searchedQuery.fasta", "UTF-8");
        Iterator<String> stringIterator = sequenceId.iterator();
        while(stringIterator.hasNext()){
            List<Sequence> resultSequence = sequenceRepository.findByTrinityId(stringIterator.next());
            writer.println(">" + resultSequence.get(0).getTrinityId() + " len= " + resultSequence.get(0).getLength());
            writer.println(resultSequence.get(0).getTranscript());
        }
        writer.close();
        return new File("./searchedQuery.fasta");
    }
}
