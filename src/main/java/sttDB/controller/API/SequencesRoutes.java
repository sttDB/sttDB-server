package sttDB.controller.API;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    public Page<Sequence> getSequences(Pageable pageable) {
        return sequenceRepository.findAll(pageable);
    }

    @GetMapping(value = "", params = "trinityId")
    @ResponseBody
    public Page<Sequence> getSequencesByTrinityId(@RequestParam String trinityId, Pageable pageable) {
        return sequenceRepository.findByTrinityIdLike(trinityId, pageable);
    }

    @GetMapping(value = "", params = "experiment")
    @ResponseBody
    public Page<Sequence> getSequencesByExperiment(@RequestParam String experiment, Pageable pageable) {
        return sequenceRepository.findByExperiment(experiment, pageable);
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
