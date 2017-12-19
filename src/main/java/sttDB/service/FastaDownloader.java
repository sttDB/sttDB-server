package sttDB.service;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import sttDB.domain.Sequence;
import sttDB.repository.SequenceRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.Response;
import java.io.*;
import java.util.Iterator;
import java.util.List;

@Controller
public class FastaDownloader {

    @Autowired
    private SequenceRepository sequenceRepository;

    @RequestMapping(value = "/downloadFasta", method = RequestMethod.GET)
    public void download(@RequestParam("trinityId") String sequenceId, @RequestParam("experiment") String experiment,
                         HttpServletResponse response) throws IOException {
        File file = createFasta(sequenceId, experiment);

        InputStream fileInputStream = new FileInputStream(file);
        OutputStream output = response.getOutputStream();

        response.setContentType("txt/plain");
        response.setContentLength((int) (file.length()));
        response.setHeader("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"");

        IOUtils.copyLarge(fileInputStream, output);
        response.flushBuffer();
    }

    private File createFasta(String sequenceId, String experiment) throws IOException {
        PrintWriter writer = new PrintWriter("searchedQuery.fasta", "UTF-8");
        Iterator<Sequence> sequenceIterator = getSequencesAsIterator(sequenceId, experiment);
        while(sequenceIterator.hasNext()){
            Sequence resultSequence = sequenceIterator.next();
            writer.println(">" + resultSequence.getTrinityId() + " len= " + resultSequence.getLength());
            writer.println(resultSequence.getTranscript());
        }
        writer.close();
        return new File("./searchedQuery.fasta");
    }

    public Iterator<Sequence> getSequencesAsIterator(String sequenceId, String experiment) {
        return experiment.equals("") ? sequenceRepository.findByTrinityIdLike(sequenceId).iterator() :
                    sequenceRepository.findByTrinityIdAndExperiment(sequenceId, experiment).iterator();
    }
}
