package repository;

import domain.Family;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "families", path = "families")
public interface FamilyRepository extends MongoRepository <Family, Long>{
    public Family findByInterproId(@Param("interproId") String interproId);
}
