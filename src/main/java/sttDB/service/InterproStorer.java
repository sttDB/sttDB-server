package sttDB.service;

import org.springframework.beans.factory.annotation.Autowired;
import sttDB.domain.Family;
import sttDB.domain.Sequence;
import sttDB.repository.FamilyRepository;
import sttDB.repository.SequenceRepository;

import java.util.List;

public class InterproStorer {

    @Autowired
    private SequenceRepository sequenceRepository;

    @Autowired
    private FamilyRepository familyRepository;

    public void storeItems(List<LineItems> items) {
        for (LineItems item : items) {
            Family family = new Family();
            family.setInterproId(item.interproId);
            family.setDescription(item.description);
            familyRepository.save(family);

            Sequence sequence = sequenceRepository.findByTrinityId(item.tirnityID).get(0); // find by experiment too
            sequence.addFamily(family);
            sequenceRepository.save(sequence);
        }
    }
}
