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
public interface FamilyRepository extends MongoRepository<Family, String> {
    Family findByInterproId(@Param("interproId") String interproId);

    Page<Family> findByDescriptionLike(String keyword, Pageable pageable);

    @Query(value = "{$and: [{'description' : {$regex : ?0}}, {'description' : {$regex : ?1}}]}")
    Page<Family> findByDescriptionLikeAndLike(String keyword, String otherword, Pageable pageable);

    @Query(value = "{$and: [{'description' : {$regex : ?0}}, {'description' : {$not : { $regex : ?1}}}]}")
    Page<Family> findByDescriptionLikeAndNotLike(String keyword, String notword, Pageable pageable);

    @Query(value = "{$or: [{'description' : {$regex : ?0}}, {'description' : {$regex : ?1}}]}")
    Page<Family> findByAnyKeyword(String keyword, String otherKeyword, Pageable pageable);

    @Query(value = "{$and: [{'description' : {$regex : ?0}}, {'description' : {$regex : ?1}}, {'description' : {$not : {$regex : ?2}}}]}")
    Page<Family> findByDescriptionLikeAndLikeAndNotLike(String keyword, String otherKeyword, String notword, Pageable pageable);

    @Query(value = "{$or: [{$and: [{'description' : {$regex : ?0}}, {'description' : {$regex : ?1}}]}, {'description' : {$regex : ?2}}]}")
    Page<Family> findByDescriptionLikeAndLikeOrLike(String firstKeyword, String secondKeyword, String otherKeyword, Pageable pageable);
}
