package sttDB.controller.API;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sttDB.domain.Family;
import sttDB.domain.PartialSequence;
import sttDB.domain.Sequence;
import sttDB.repository.FamilyRepository;
import sttDB.repository.SequenceRepository;

@RequestMapping(value = "/families")
@Controller
public class FamilyRoutes {

    @Autowired
    private FamilyRepository familyRepository;

    @Autowired
    private SequenceRepository sequenceRepository;

    @GetMapping(value = "")
    @ResponseBody
    public Page<Family> getSequences() {
        return familyRepository.findAll(new PageRequest(0, 20));
    }

    @GetMapping(value = "", params = "page")
    @ResponseBody
    public Page<Family> getSequences(@RequestParam(required = false, defaultValue = "0") int page) {
        return familyRepository.findAll(new PageRequest(page, 20));
    }

    @GetMapping(value = "", params = {"descriptionKeyword"})
    @ResponseBody
    public Page<Family> getFamilyByKeyWord(@RequestParam String descriptionKeyword) {
        return familyRepository.findByDescriptionLike(descriptionKeyword, new PageRequest(0, 20));
    }

    @GetMapping(value = "", params = {"descriptionKeyword", "page"})
    @ResponseBody
    public Page<Family> getFamilyByKeyWord(@RequestParam String descriptionKeyword, @RequestParam(defaultValue = "0") int page) {
        return familyRepository.findByDescriptionLike(descriptionKeyword, new PageRequest(page, 20));
    }

    @GetMapping("/{interproId}")
    @ResponseBody
    public Family getFamilyByInterproId(@PathVariable("interproId") String interproId) {
        return familyRepository.findByInterproId(interproId);
    }

    @GetMapping("/{id}/sequences")
    @ResponseBody
    public Page<Sequence> getFamilySequences(@PathVariable("id") String id,
                                             @RequestParam(value = "page", defaultValue = "0") int page) {
        return sequenceRepository.findPartialByFamilyInterproId(id, new PageRequest(page, 20));
    }
}
