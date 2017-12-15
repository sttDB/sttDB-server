package sttDB.repository;

import sttDB.domain.Sequence;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SequenceRepository extends MongoRepository<Sequence, Long> {
    public List<Sequence> findByTrinityId(@Param("trinityId") String trinityId);
    public List<Sequence> findByTranscript(@Param("transcript") String transcript);
    public List<Sequence> findByTrinityIdLike(@Param("trinityId") String trinityId);
}
