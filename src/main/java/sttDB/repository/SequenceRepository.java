package sttDB.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import sttDB.domain.Experiment;
import sttDB.domain.Sequence;

import java.math.BigInteger;
import java.util.List;

@RepositoryRestResource
public interface SequenceRepository extends MongoRepository<Sequence, String>, CustomSequenceRepository{
    List<Sequence> findByTrinityId(String trinityId);

    Page<Sequence> findByTrinityIdLike(String trinityId, Pageable pageable);

    List<Sequence> findByTrinityIdAndExperiment(String trinityId, String experiment);

    Page<Sequence> findByExperiment( String experiment, Pageable pageable);

    @Query(value = "{'domainInfo.families._id' : ?0}")
    List<Sequence> findByFamilyInterproId(String interproId);

    @Query(value = "{'domainInfo.families._id' : ?0}", fields = "{ trinityId : 1, experiment : 1 }")
    Page<Sequence> findPartialByFamilyInterproId(String interproId, Pageable pageable);

    void deleteByExperiment(String experiment);
}
