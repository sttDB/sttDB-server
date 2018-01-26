package sttDB.controller;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sttDB.domain.Sequence;
import sttDB.repository.SequenceRepository;
import sttDB.service.downloadFileService.FastaFileWriter;
import sttDB.service.downloadFileService.FileCreator;
import sttDB.service.storageService.StorageService;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

@RequestMapping(value = "/download")
@Controller
public class DownloadController {

    private StorageService storage;

    private SequenceRepository sequenceRepository;

    @Autowired
    public DownloadController(StorageService storage, SequenceRepository sequenceRepository) {
        this.storage = storage;
        this.sequenceRepository = sequenceRepository;
    }

    @RequestMapping(value = "/fasta", params = {"trinityId", "experiment"}, method = RequestMethod.GET)
    public void downloadFastaFromTrinity(@RequestParam("trinityId") String trinityId,
                                         @RequestParam("experiment") String experiment,
                                         HttpServletResponse response) throws IOException {
        FileCreator<Sequence> fileCreator = new FileCreator<>("fasta");
        File file = fileCreator.createFile(selectLikeOrOne(trinityId, experiment), new FastaFileWriter());
        returnResponseWithFile(response, file);
    }

    private Iterable<Sequence> selectLikeOrOne(String sequenceId, String experiment) {
        return experiment.equals("") ?
                sequenceRepository.findByTrinityIdLike(sequenceId, new PageRequest(0, Integer.MAX_VALUE))
                : sequenceRepository.findByTrinityIdAndExperiment(sequenceId, experiment);
    }

    @GetMapping(value = "/fasta", params = "interproId")
    public void downloadFastaFromFamily(@RequestParam("interproId") String interproId, HttpServletResponse response)
            throws IOException {
        FileCreator<Sequence> fileCreator = new FileCreator<>("fasta");
        File file = fileCreator.createFile(sequenceRepository.findByFamilyInterproId(interproId), new FastaFileWriter());
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
