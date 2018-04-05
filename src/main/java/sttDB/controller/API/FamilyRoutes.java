package sttDB.controller.API;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sttDB.domain.Family;
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
    public Page<Family> getSequences(Pageable pageable) {
        return familyRepository.findAll(pageable);
    }

    @GetMapping("/{interproId}")
    @ResponseBody
    public Family getFamilyByInterproId(@PathVariable("interproId") String interproId) {
        return familyRepository.findByInterproId(interproId);
    }

    @GetMapping(value = "", params = {"descriptionKeyword"})
    @ResponseBody
    public Page<Family> getFamilyByKeyWord(@RequestParam String descriptionKeyword, Pageable pageable) {
        return familyRepository.findByDescriptionLike(descriptionKeyword, pageable);
    }

    @GetMapping(value="", params = {"descriptionKeyword", "secondKeyword"})
    @ResponseBody
    public Page<Family> getFamilyByKeyWords(@RequestParam String descriptionKeyword,
                                              @RequestParam String secondKeyword, Pageable pageable){
        return familyRepository.findByDescriptionLikeAndLike(descriptionKeyword, secondKeyword, pageable);
    }

    @GetMapping(value="", params = {"descriptionKeyword", "wrongWord"})
    @ResponseBody
    public Page<Family> getFamilyByKeyWordAndWrongWord(@RequestParam String descriptionKeyword,
                                            @RequestParam String wrongWord, Pageable pageable){
        return familyRepository.findByDescriptionLikeAndLike(descriptionKeyword, wrongWord, pageable);
    }

    @GetMapping("/{id}/sequences")
    @ResponseBody
    public Page<Sequence> getFamilySequences(@PathVariable("id") String id,
                                             Pageable pageable) {
        return sequenceRepository.findPartialByFamilyInterproId(id, pageable);
    }
}
