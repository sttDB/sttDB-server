package sttDB.controller.API;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sttDB.domain.Experiment;
import sttDB.exception.ExperimentNotFoundException;
import sttDB.repository.ExperimentRepository;
import sttDB.service.experimentManager.ExperimentManager;

import java.util.List;

@RequestMapping("/experiments")
@Controller
public class ExperimentRoutes {

    private ExperimentRepository repository;

    private ExperimentManager manager;

    @Autowired
    public ExperimentRoutes(ExperimentRepository repository, ExperimentManager manager) {
        this.repository = repository;
        this.manager = manager;
    }

    @GetMapping(value = "")
    @ResponseBody
    public Page<Experiment> getExperiments(Pageable page) {
        return repository.findAll(page);
    }

    @GetMapping("/{name}")
    @ResponseBody
    public Experiment getExperimentByName(@PathVariable("name") String name) {
        return repository.findOne(name);
    }

    @GetMapping("/{name}/files")
    @ResponseBody
    public List<String> getFileNamesOfExperiment(@PathVariable("name") String experiment) {
        try {
            return manager.getFilesOfExperiment(experiment);
        } catch (ExperimentNotFoundException e) {
            throw new ExperimentNotFoundException("Experiment '" + experiment + "' not found", e);
        }
    }

}
