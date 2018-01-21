package sttDB.controller.API;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sttDB.domain.Sequence;
import sttDB.repository.ExperimentRepository;
import sttDB.repository.SequenceRepository;

import java.util.List;

@RequestMapping(value = "/sequences")
@Controller
public class SequencesRoutes {

    @Autowired
    private ExperimentRepository experimentRepository;

    @Autowired
    private SequenceRepository sequenceRepository;

    @GetMapping(value = "")
    @ResponseBody
    public Page<Sequence> getSequences() {
        return sequenceRepository.findAll(new PageRequest(0, 20));
    }

    @GetMapping(value = "", params = "page")
    @ResponseBody
    public Page<Sequence> getSequences(@RequestParam(defaultValue = "0") int page) {
        return sequenceRepository.findAll(new PageRequest(page, 20));
    }

    @GetMapping(value = "", params = "trinityId")
    @ResponseBody
    public Page<Sequence> getSequencesByTrinityId(@RequestParam String trinityId) {
        return sequenceRepository.findByTrinityIdLike(trinityId, new PageRequest(0, 20));
    }

    @GetMapping(value = "", params = {"trinityId", "page"})
    @ResponseBody
    public Page<Sequence> getSequencesByTrinityId(@RequestParam String trinityId,
                                                  @RequestParam(defaultValue = "0") int page) {
        return sequenceRepository.findByTrinityIdLike(trinityId, new PageRequest(page, 20));
    }

    @GetMapping(value = "", params = "experiment")
    @ResponseBody
    public Page<Sequence> getSequencesByExperiment(@RequestParam String experiment) {
        return sequenceRepository.findByExperiment(experiment, new PageRequest(0, 20));
    }

    @GetMapping(value = "", params = {"experiment", "page"})
    @ResponseBody
    public Page<Sequence> getSequencesByExperiment(@RequestParam String experiment,
                                                   @RequestParam(defaultValue = "0") int page) {
         return sequenceRepository.findByExperiment(experiment, new PageRequest(page, 20));
    }

    @GetMapping(value = "", params = {"trinityId", "experiment"})
    @ResponseBody
    public List<Sequence> getSequenceWithExperiment(@RequestParam String trinityId,
                                                    @RequestParam String experiment) {
        return sequenceRepository.findByTrinityIdAndExperiment(trinityId, experiment);
    }

    @GetMapping("/{id}")
    @ResponseBody
    public Sequence getSequenceById(@PathVariable("id") String id) {
        return sequenceRepository.findOne(id);
    }
}
