package sttDB.controller;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import sttDB.service.fastaServices.FastaDownloader;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

@RequestMapping(value = "/download")
@Controller
public class DownloadController {

    @Autowired
    FastaDownloader fastaDownloader;

    @RequestMapping(value = "/fasta", method = RequestMethod.GET)
    public void download(@RequestParam("trinityId") String sequenceId, @RequestParam("experiment") String experiment,
                         HttpServletResponse response) throws IOException {
        File file = fastaDownloader.createFasta(sequenceId, experiment);

        InputStream fileInputStream = new FileInputStream(file);
        OutputStream output = response.getOutputStream();

        response.setContentType("txt/plain");
        response.setContentLength((int) (file.length()));
        response.setHeader("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"");

        IOUtils.copyLarge(fileInputStream, output);
        response.flushBuffer();
    }
}
