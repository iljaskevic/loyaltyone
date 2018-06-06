package com.ljaskevic.loyaltyone.repositories;

import java.util.List;
import com.ljaskevic.loyaltyone.models.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CommentsRepository extends MongoRepository<Comment, String> {

    public List<Comment> findByParentId(String parentId);
}