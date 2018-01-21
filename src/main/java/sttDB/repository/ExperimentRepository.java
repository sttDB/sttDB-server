package sttDB.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import sttDB.domain.Experiment;
import sttDB.repository.excerptProjections.ExperimentProjection;

public interface ExperimentRepository extends MongoRepository<Experiment, String> {
}
