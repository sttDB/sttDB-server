package sttDB.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import sttDB.domain.Family;
import sttDB.domain.Sequence;
import sttDB.repository.FamilyRepository;

@Controller
public class API {

    @Autowired
    private FamilyRepository familyRepository;

    @GetMapping("/families/{id}/sequences")
    @ResponseBody
    public Page<Sequence> getFamilySequences(@PathVariable("id") String id, @RequestParam("page") int page) {
        Family searchedFamily = familyRepository.findByInterproId(id);
        return new PageImpl<>(searchedFamily.getSequences(),
                new PageRequest(page, 20),
                searchedFamily.getSequences().size());
    }
}
