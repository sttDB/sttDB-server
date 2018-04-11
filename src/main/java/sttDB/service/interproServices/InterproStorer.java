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

    /**
     * Inserting families and updating sequences. Old versions had a single loop to process families and sequences
     * but there were unnecessary updates to sequences. The O(N) is bigger, but less updates to sequences are done.
     * @param items The items from the parsed lines.
     * @param experiment The experiment of the sequences.
     * @param groupedItems The processed data.
     */
    public void storeItems(List<LineItems> items, Experiment experiment, Map<String, List<Family>> groupedItems) {
        insertFamilies(items);
        updateSequences(experiment, groupedItems);
    }

    private void insertFamilies(List<LineItems> items) {
        for (LineItems item : items) {
            Family family = familyRepository.findByInterproId(item.interproId);
            if(family == null) {
                family = new Family();
                family.setInterproId(item.interproId);
                family.setDescription(item.description);
                familyRepository.save(family);
            }
        }
    }

    private void updateSequences(Experiment experiment, Map<String, List<Family>> groupedItems) {
        for (String trinityID : groupedItems.keySet()) {
            sequenceRepository.sequenceFamiliesUpload(trinityID, experiment, groupedItems.get(trinityID));
        }
    }
}
