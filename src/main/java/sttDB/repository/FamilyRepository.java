package sttDB.repository;

import sttDB.domain.Family;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import sttDB.repository.excerptProjections.FamilyProjection;

import java.math.BigInteger;

@RepositoryRestResource(excerptProjection = FamilyProjection.class)
public interface FamilyRepository extends MongoRepository<Family, String> {
    Family findByInterproId(@Param("interproId") String interproId);
}
