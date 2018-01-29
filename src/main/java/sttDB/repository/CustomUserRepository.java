package sttDB.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import sttDB.domain.CustomUser;

@RepositoryRestResource
public interface CustomUserRepository extends MongoRepository<CustomUser, String> {
    CustomUser findByUsername(String username);
}
