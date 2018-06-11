package sttDB.repository;

import sttDB.domain.Experiment;
import sttDB.domain.Family;
import sttDB.domain.Go;

import java.util.List;

public interface CustomSequenceRepository {
    void sequenceFamiliesUpload(String trinityId, Experiment experiment, List<Family> family);
    void sequenceGoTermUpload(String trinityId, Experiment experiment, Go go);
}
