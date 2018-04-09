package sttDB.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import sttDB.domain.Family;
import sttDB.repository.excerptProjections.FamilyProjection;

import java.util.List;


@RepositoryRestResource(excerptProjection = FamilyProjection.class)
public interface FamilyRepository extends CustomQueriesRepository<Family, String> {
    Family findByInterproId(@Param("interproId") String interproId);
}
