package sttDB.repository;

import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import sttDB.domain.Family;


@RepositoryRestResource
public interface FamilyRepository extends CustomQueriesRepository<Family, String> {
    Family findByInterproId(@Param("interproId") String interproId);
}
