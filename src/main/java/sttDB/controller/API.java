package sttDB.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sttDB.domain.Family;
import sttDB.domain.Sequence;
import sttDB.repository.FamilyRepository;
import sttDB.repository.SequenceRepository;

import java.util.List;

@Controller
public class API {

    @Autowired
    private FamilyRepository familyRepository;

    @Autowired
    private SequenceRepository sequenceRepository;

    @GetMapping("/families/{id}/sequences")
    @ResponseBody
    public Page<Sequence> getFamilySequences(@PathVariable("id") String id, @RequestParam("page") int page) {
        Family searchedFamily = familyRepository.findByInterproId(id);
        return new PageImpl<>(searchedFamily.getSequences(),
                new PageRequest(page, 20),
                searchedFamily.getSequences().size());
    }

    @RequestMapping(value = "/sequences", params = "page")
    @ResponseBody
    public Page<Sequence> getSequences(@RequestParam(defaultValue = "0") int page) {
        return sequenceRepository.findAll(new PageRequest(page, 20));
    }

    @RequestMapping(value = "/sequences", params = {"trinityId", "page"})
    @ResponseBody
    public Page<Sequence> getSequencesByTrinityId(@RequestParam String trinityId,
                                                  @RequestParam int page) {
        return sequenceRepository.findByTrinityIdLike(trinityId, new PageRequest(page, 20));
    }

    @RequestMapping(value = "/sequences", params = "experiment")
    @ResponseBody
    public List<Sequence> getSequencesByExperiment(@RequestParam("experiment") String experiment) {
        return sequenceRepository.findByExperiment(experiment);
    }

    @RequestMapping(value = "/sequences", params = {"trinityId", "experiment"})
    @ResponseBody
    public List<Sequence> getSequenceWithExperiment(@RequestParam String trinityId,
                                                    @RequestParam String experiment) {
        return sequenceRepository.findByTrinityIdAndExperiment(trinityId, experiment);
    }

    @GetMapping("/families")
    @ResponseBody
    public Page<Family> getFamilyByKeyWord(@RequestParam("descriptionKeyword") String keyword, @RequestParam("page") int page){
        return familyRepository.findByDescriptionContaining(keyword, new PageRequest(page, 20));
    }
}
