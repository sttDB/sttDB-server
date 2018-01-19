package sttDB.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import sttDB.domain.Experiment;
import sttDB.domain.Sequence;

import java.math.BigInteger;
import java.util.List;

@RepositoryRestResource
public interface SequenceRepository extends MongoRepository<Sequence, BigInteger> {
    List<Sequence> findByTrinityId(String trinityId);

    Page<Sequence> findByTrinityIdLike(String trinityId, Pageable pageable);

    List<Sequence> findByTrinityIdAndExperiment(String trinityId, String experiment);

    List<Sequence> findByExperiment( String experiment);

    void deleteByExperiment(String experiment);
}
