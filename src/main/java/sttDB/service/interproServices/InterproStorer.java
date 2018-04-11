package sttDB.service.interproServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sttDB.domain.Experiment;
import sttDB.domain.Family;
import sttDB.domain.Sequence;
import sttDB.repository.FamilyRepository;
import sttDB.repository.SequenceRepository;

import java.util.List;
import java.util.Map;

@Service
public class InterproStorer {

    @Autowired
    private SequenceRepository sequenceRepository;

    @Autowired
    private FamilyRepository familyRepository;

    public void storeItems(List<LineItems> items, Experiment experiment, Map<String, List<Family>> groupedItems) {
        for (LineItems item : items) {
            insertFamily(item);
            //this could be even more optimized, first a for each to update families, then a foreach in the map.
            sequenceRepository.sequenceFamiliesUpload(item.trinityID, experiment, groupedItems.get(item.trinityID));
        }
    }

    private void insertFamily(LineItems item) {
        Family family = familyRepository.findByInterproId(item.interproId);
        if(family == null) {
            family = new Family();
            family.setInterproId(item.interproId);
            family.setDescription(item.description);
            familyRepository.save(family);
        }
    }
}
