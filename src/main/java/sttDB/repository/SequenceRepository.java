package sttDB.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import sttDB.domain.Sequence;

import java.math.BigInteger;
import java.util.List;

public interface SequenceRepository extends MongoRepository<Sequence, BigInteger> {
    List<Sequence> findByTrinityId(@Param("trinityId") String trinityId);

    List<Sequence> findByTranscript(@Param("transcript") String transcript);

    List<Sequence> findByTrinityIdLike(@Param("trinityId") String trinityId);

    List<Sequence> findByTrinityIdAndExperiment(@Param("trinityId") String trinityId, @Param("experiment") String experiment);
}
