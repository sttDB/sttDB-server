package sttDB.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import sttDB.domain.Family;

public interface FamilyRepository extends MongoRepository<Family, String> {
    Family findByInterproId(@Param("interproId") String interproId);
}
