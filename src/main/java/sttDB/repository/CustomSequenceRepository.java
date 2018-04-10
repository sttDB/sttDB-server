package sttDB.repository;

import org.springframework.data.repository.NoRepositoryBean;
import sttDB.domain.Experiment;
import sttDB.domain.Family;

public interface CustomSequenceRepository {
    void sequenceFamiliesUpload(String trinityId, Experiment experiment, Family family);
}
