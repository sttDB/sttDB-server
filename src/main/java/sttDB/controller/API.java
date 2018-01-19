package sttDB.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sttDB.domain.Family;
import sttDB.domain.Sequence;
import sttDB.repository.ExperimentRepository;
import sttDB.repository.FamilyRepository;
import sttDB.repository.SequenceRepository;

import java.util.List;

@Controller
public class API {

    @Autowired
    private FamilyRepository familyRepository;

    @Autowired
    private ExperimentRepository experimentRepository;

    @Autowired
    private SequenceRepository sequenceRepository;

    @GetMapping("/families/{id}/sequences")
    @ResponseBody
    public Page<Sequence> getFamilySequences(@PathVariable("id") String id,
                                             @RequestParam(value = "page", defaultValue = "0") int page) {
        Family searchedFamily = familyRepository.findByInterproId(id);
        return new PageImpl<>(searchedFamily.getSequences(),
                new PageRequest(page, 20),
                searchedFamily.getSequences().size());
    }

    @GetMapping(value = "/sequences", params = "page")
    @ResponseBody
    public Page<Sequence> getSequences(@RequestParam(required=false, defaultValue = "0") int page) {
        return sequenceRepository.findAll(new PageRequest(page, 20));
    }

    @GetMapping(value = "/sequences", params = {"trinityId", "page"})
    @ResponseBody
    public Page<Sequence> getSequencesByTrinityId(@RequestParam String trinityId,
                                                  @RequestParam(defaultValue = "0") int page) {
        return sequenceRepository.findByTrinityIdLike(trinityId, new PageRequest(page, 20));
    }

    @GetMapping(value = "/sequences", params = {"experiment", "page"})
    @ResponseBody
    public Page<Sequence> getSequencesByExperiment(@RequestParam String experiment,
                                                   @RequestParam(defaultValue = "0") int page) {
        List<Sequence> experimentSequences = sequenceRepository.findByExperiment(experimentRepository.findOne(experiment));
        return new PageImpl<>(experimentSequences,
                new PageRequest(page, 20),
                experimentSequences.size());
    }

    @GetMapping(value = "/sequences", params = {"trinityId", "experiment"})
    @ResponseBody
    public List<Sequence> getSequenceWithExperiment(@RequestParam String trinityId,
                                                    @RequestParam String experiment) {
        return sequenceRepository.findByTrinityIdAndExperiment(trinityId, experimentRepository.findOne(experiment));
    }

    @GetMapping(value = "/families", params = {"descriptionKeyword", "page"})
    @ResponseBody
    public Page<Family> getFamilyByKeyWord(@RequestParam String keyword, @RequestParam(defaultValue = "0") int page){
        return familyRepository.findByDescriptionContaining(keyword, new PageRequest(page, 20));
    }
}
