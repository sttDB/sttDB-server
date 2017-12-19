package sttDB.repository;

import sttDB.domain.Family;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

public interface FamilyRepository extends MongoRepository <Family, Long>{
    public Family findByInterproId(@Param("interproId") String interproId);
}
