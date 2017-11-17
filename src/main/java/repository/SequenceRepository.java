package repository;

import domain.Sequence;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

public interface SequenceRepository extends MongoRepository<Sequence, Long> {
    public Sequence findByTrinityId(@Param("trinityId") String trinityId);
}
