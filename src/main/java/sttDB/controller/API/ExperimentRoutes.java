package sttDB.controller.API;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import sttDB.domain.Experiment;
import sttDB.repository.ExperimentRepository;

@RequestMapping("/experiments")
@Controller
public class ExperimentRoutes {

    private ExperimentRepository repository;

    @Autowired
    public ExperimentRoutes(ExperimentRepository repository) {
        this.repository = repository;
    }

    @GetMapping("")
    @ResponseBody
    public Page<Experiment> getExperiments() {
        return repository.findAll(new PageRequest(0, 20));
    }

    @GetMapping("/{name}")
    @ResponseBody
    public Experiment getExperimentByName(@PathVariable("name") String name) {
        return repository.findOne(name);
    }

}
