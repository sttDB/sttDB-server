package sttDB.repository;

import org.springframework.data.domain.Page;
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
    List<Sequence> findByTrinityId(@Param("trinityId") String trinityId);

    Page<Sequence> findByTrinityIdLike(@Param("trinityId") String trinityId, Pageable pageable);

    List<Sequence> findByTrinityIdAndExperiment(@Param("trinityId") String trinityId, @Param("experiment") String experiment);

    List<Sequence> findByExperiment(@Param("experiment") String experiment);
}
