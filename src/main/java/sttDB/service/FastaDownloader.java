package sttDB.service;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

@Controller
public class FastaDownloader {

    @RequestMapping(value = "/download", method = RequestMethod.GET)
    public void download(@RequestParam("name") List<String> sequenceId, final HttpServletRequest request, final HttpServletResponse response) throws IOException {
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

    private File createFasta(List<String> sequenceId) {
        return new File("./searchedQuery.fasta");
    }
}
