package sttDB.service.interproServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sttDB.domain.Experiment;
import sttDB.domain.Family;
import sttDB.domain.Sequence;
import sttDB.repository.FamilyRepository;
import sttDB.repository.SequenceRepository;

import java.util.List;

@Service
public class InterproStorer {

    @Autowired
    private SequenceRepository sequenceRepository;

    @Autowired
    private FamilyRepository familyRepository;

    public void storeItems(List<LineItems> items, Experiment experiment) {
        for (LineItems item : items) {
            Family family = getFamilyOrNew(item, experiment);
            Sequence sequence = sequenceRepository.findByTrinityIdAndExperiment(item.trinityID, experiment.getName()).get(0);
            sequence.addIntoDomainInfo("families",family);
            sequenceRepository.save(sequence);
        }
    }

    private Family getFamilyOrNew(LineItems item, Experiment experiment) {
        Family family = familyRepository.findByInterproId(item.interproId);
        if(family == null) {
            family = new Family();
            family.setInterproId(item.interproId);
            family.setDescription(item.description);
            familyRepository.save(family);
        }
        return family;
    }
}
