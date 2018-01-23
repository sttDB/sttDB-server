package sttDB.controller;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sttDB.service.fastaServices.FastaDownloader;
import sttDB.service.storageService.StorageService;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

@RequestMapping(value = "/download")
@Controller
public class DownloadController {

    private FastaDownloader fastaDownloader;

    private StorageService storage;

    @Autowired
    public DownloadController(FastaDownloader fastaDownloader, StorageService storage) {
        this.fastaDownloader = fastaDownloader;
        this.storage = storage;
    }

    @RequestMapping(value = "/fasta", method = RequestMethod.GET)
    public void download(@RequestParam("trinityId") String sequenceId, @RequestParam("experiment") String experiment,
                         HttpServletResponse response) throws IOException {

        File file = fastaDownloader.createFasta(sequenceId, experiment);
        returnResponseWithFile(response, file);
    }

    @GetMapping(value = "/{experiment}", params = "file")
    public void downloadFile(@PathVariable("experiment") String experiment,
                             @RequestParam("file") String file,
                             HttpServletResponse response) throws IOException {

        File toDownload = storage.loadFileFromExperiment(file, experiment).toFile();
        returnResponseWithFile(response, toDownload);
    }

    private void returnResponseWithFile(HttpServletResponse response, File file) throws IOException {
        InputStream fileInputStream = new FileInputStream(file);
        OutputStream output = response.getOutputStream();

        response.setContentType("txt/plain");
        response.setContentLength((int) (file.length()));
        response.setHeader("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"");

        IOUtils.copyLarge(fileInputStream, output);
        response.flushBuffer();
    }
}
