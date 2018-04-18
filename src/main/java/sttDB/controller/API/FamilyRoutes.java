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

import java.util.ArrayList;
import java.util.List;

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

    @GetMapping("/{id}/sequences")
    @ResponseBody
    public Page<Sequence> getFamilySequences(@PathVariable("id") String id,
                                             Pageable pageable) {
        return sequenceRepository.findPartialByFamilyInterproId(id, pageable);
    }

    @GetMapping(value = "", params = {"descriptionKeyword"})
    @ResponseBody
    public Page<Family> getFamilyByKeyWord(@RequestParam String descriptionKeyword, Pageable pageable) {
        return familyRepository.findByDescriptionLike(descriptionKeyword, pageable);
    }

    @GetMapping(value = "", params = {"firstKeyword", "secondKeyword"})
    @ResponseBody
    public Page<Family> getFamilyByKeyWords(@RequestParam String firstKeyword,
                                            @RequestParam String secondKeyword, Pageable pageable) {
        return familyRepository.findByDescriptionLikeAndLike(firstKeyword, secondKeyword, pageable);
    }

    @GetMapping(value = "", params = {"orKeyword", "otherOrKeyword"})
    @ResponseBody
    public Page<Family> getFamilyByKeyWordAndOtherKeyWord(@RequestParam String orKeyword,
                                                          @RequestParam String otherOrKeyword, Pageable pageable) {
        return familyRepository.findByAnyKeyword(orKeyword, otherOrKeyword, pageable);
    }

    @GetMapping(value = "", params = {"keyword", "notKeyword"})
    @ResponseBody
    public Page<Family> getFamilyByKeyWordAndNotKeyWord(@RequestParam String keyword,
                                                        @RequestParam String notKeyword, Pageable pageable) {
        return familyRepository.findByDescriptionLikeAndNotLike(keyword, notKeyword, pageable);
    }

    @GetMapping(value = "", params = {"firstKeyword", "secondKeyword", "notKeyword"})
    @ResponseBody
    public Page<Family> getFamilyByKeywordsExcludingOne(@RequestParam String firstKeyword,
                                                        @RequestParam String secondKeyword,
                                                        @RequestParam String notKeyword, Pageable pageable){
        return familyRepository.findByDescriptionLikeAndLikeAndNotLike(firstKeyword, secondKeyword, notKeyword, pageable);
    }

    @GetMapping(value = "", params = {"firstKeyword", "secondKeyword", "otherKeyword"})
    @ResponseBody
    public Page<Family> getFamilyByKeywordAndKeywordOrOther(@RequestParam String firstKeyword,
                                                        @RequestParam String secondKeyword,
                                                        @RequestParam String otherKeyword, Pageable pageable){
        return familyRepository.findByDescriptionLikeAndLikeOrLike(firstKeyword, secondKeyword, otherKeyword, pageable);
    }

    @GetMapping(value = "", params = {"orKeyword", "otherOrKeyword", "notKeyword"})
    @ResponseBody
    public Page<Family> getFamilyByKeywordOrKeywordNotKeyword(@RequestParam String orKeyword,
                                                            @RequestParam String otherOrKeyword,
                                                            @RequestParam String notKeyword, Pageable pageable){
        return familyRepository.findByAnyKeywordNotOther(orKeyword, otherOrKeyword, notKeyword, pageable);
    }

    @GetMapping(value = "", params = {"orKeyword", "otherOrKeyword", "andKeyword"})
    @ResponseBody
    public Page<Family> getFamilyByKeywordOrKeywordAndKeyword(@RequestParam String orKeyword,
                                                              @RequestParam String otherOrKeyword,
                                                              @RequestParam String andKeyword, Pageable pageable){
        return familyRepository.findByAnyKeywordAndOther(orKeyword, otherOrKeyword, andKeyword, pageable);
    }

    @GetMapping(value = "", params = {"orKeyword", "notKeyword", "otherKeyword"})
    @ResponseBody
    public Page<Family> getFamilyByKeywordNotKeywordOrKeyword(@RequestParam String orKeyword,
                                                              @RequestParam String notKeyword,
                                                              @RequestParam String otherKeyword, Pageable pageable){
        return familyRepository.findByDescriptionLikeAndNotLikeAndOther(orKeyword, notKeyword, otherKeyword, pageable);
    }
}
