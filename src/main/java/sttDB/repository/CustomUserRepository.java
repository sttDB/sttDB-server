package sttDB.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import sttDB.domain.CustomUser;

@RepositoryRestResource
public interface CustomUserRepository extends MongoRepository<CustomUser, String> {

    @Override
    @RestResource(exported = false)
    Page<CustomUser> findAll(Pageable pageable);

    @RestResource(exported = false)
    CustomUser findByUsername(String username);
}
