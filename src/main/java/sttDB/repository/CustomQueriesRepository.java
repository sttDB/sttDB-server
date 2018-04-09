package sttDB.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

/**
 * Custom Repository to query over the description parameter of an Entity, must extend this interface in the same way as 
 * MongoRepository.
 * @param <T> Type of Entity
 * @param <ID> Type of the ID
 */
@NoRepositoryBean
public interface CustomQueriesRepository<T, ID extends Serializable> extends MongoRepository<T, ID> {
    Page<T> findByDescriptionLike(String keyword, Pageable pageable);

    @Query(value = "{$and: [{'description' : {$regex : ?0}}, {'description' : {$regex : ?1}}]}")
    Page<T> findByDescriptionLikeAndLike(String keyword, String otherword, Pageable pageable);

    @Query(value = "{$and: [{'description' : {$regex : ?0}}, {'description' : {$not : { $regex : ?1}}}]}")
    Page<T> findByDescriptionLikeAndNotLike(String keyword, String notword, Pageable pageable);

    @Query(value = "{$or: [{'description' : {$regex : ?0}}, {'description' : {$regex : ?1}}]}")
    Page<T> findByAnyKeyword(String keyword, String otherKeyword, Pageable pageable);

    @Query(value = "{$and: [{'description' : {$regex : ?0}}, {'description' : {$regex : ?1}}, {'description' : {$not : {$regex : ?2}}}]}")
    Page<T> findByDescriptionLikeAndLikeAndNotLike(String keyword, String otherKeyword, String notWord, Pageable pageable);

    @Query(value = "{$or: [{$and: [{'description' : {$regex : ?0}}, {'description' : {$regex : ?1}}]}, {'description' : {$regex : ?2}}]}")
    Page<T> findByDescriptionLikeAndLikeOrLike(String firstKeyword, String secondKeyword, String otherKeyword, Pageable pageable);

    @Query(value = "{$and: [{$or: [{'description' : {$regex : ?0}}, {'description' : {$regex : ?1}}]}, {'description' : {$not : {$regex : ?2}}}] }")
    Page<T> findByAnyKeywordNotOther(String keyword, String otherKeyword, String notWord, Pageable pageable);

    @Query(value = "{$and: [{$or: [{'description' : {$regex : ?0}}, {'description' : {$regex : ?1}}]}, {'description' : {$regex : ?2}}] }")
    Page<T> findByAnyKeywordAndOther(String keyword, String otherKeyword, String andWord, Pageable pageable);
}
