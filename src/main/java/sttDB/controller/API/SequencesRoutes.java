package sttDB.controller.API;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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

    @GetMapping(value = "", params = "page")
    @ResponseBody
    public Page<Sequence> getSequences(@RequestParam(required=false, defaultValue = "0") int page) {
        return sequenceRepository.findAll(new PageRequest(page, 20));
    }

    @GetMapping(value = "", params = {"trinityId", "page"})
    @ResponseBody
    public Page<Sequence> getSequencesByTrinityId(@RequestParam String trinityId,
                                                  @RequestParam(defaultValue = "0") int page) {
        return sequenceRepository.findByTrinityIdLike(trinityId, new PageRequest(page, 20));
    }

    @GetMapping(value = "", params = {"experiment", "page"})
    @ResponseBody
    public Page<Sequence> getSequencesByExperiment(@RequestParam String experiment,
                                                   @RequestParam(defaultValue = "0") int page) {
        List<Sequence> experimentSequences = sequenceRepository.findByExperiment(experiment);
        return new PageImpl<>(experimentSequences,
                new PageRequest(page, 20),
                experimentSequences.size());
    }

    @GetMapping(value = "", params = {"trinityId", "experiment"})
    @ResponseBody
    public List<Sequence> getSequenceWithExperiment(@RequestParam String trinityId,
                                                    @RequestParam String experiment) {
        return sequenceRepository.findByTrinityIdAndExperiment(trinityId, experiment);
    }
}
