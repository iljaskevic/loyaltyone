package com.ljaskevic.loyaltyone.repositories;

import java.util.List;
import com.ljaskevic.loyaltyone.models.Comment;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface CommentsRepository extends MongoRepository<Comment, String> {

    public Comment findById(String id, Sort sort);

    public List<Comment> findByParentId(String parentId, Sort sort);

    @Query(value = "{ 'parentId' : ?0, 'user.username' : ?1 }")
    public List<Comment> findByParentIdAndUsername(String parentId, String username, Sort sort);

    @Query(value = "{ 'parentId' : ?0, 'user.id' : ?1 }")
    public List<Comment> findByParentIdAndUserId(String parentId, String userId, Sort sort);
}