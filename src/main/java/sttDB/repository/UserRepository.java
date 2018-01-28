package sttDB.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import sttDB.domain.User;

@RepositoryRestResource
public interface UserRepository extends MongoRepository<User, String> {
    User findByUsername(String username);
}
