package com.ljaskevic.loyaltyone.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.ljaskevic.loyaltyone.models.Comment;
import com.ljaskevic.loyaltyone.repositories.CommentsRepository;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CommentsController {

    @Autowired
    CommentsRepository commentsRepository;

    private static final String DATE_FIELD_NAME = "dateCreated";

    @PostMapping("/comments/")
    public Comment submit(@RequestBody Comment comment) {
        if (comment.getParentId().trim() != "0") {
            Comment parent = commentsRepository.findById(comment.getParentId(), new Sort(Direction.DESC, DATE_FIELD_NAME));
            parent.incrementRepliesCount();
            commentsRepository.save(parent);
        }
        commentsRepository.save(comment);
        return comment;
    }

    @GetMapping("/comments/")
    public List<Comment> getAllRootComments(@RequestParam(name="username", required=false) String username) {
        if (username == null || username.trim().isEmpty()) {
            return getCommentsByParentId("0");
        }
        return commentsRepository.findByParentIdAndUsername("0", username, new Sort(Direction.DESC, DATE_FIELD_NAME));
    }

    @GetMapping("/comments/{parentId}/")
    public List<Comment> getAllComments(@PathVariable String parentId, @RequestParam(name="username", required=false) String username) {
        if (username == null || username.trim().isEmpty()) {
            return getCommentsByParentId(parentId);
        }
        return commentsRepository.findByParentIdAndUsername(parentId, username, new Sort(Direction.DESC, DATE_FIELD_NAME));
    }

    private List<Comment> getCommentsByParentId(String parentId) {
        if (parentId == null || parentId.trim().isEmpty()) {
            parentId = "0";
        }
        return commentsRepository.findByParentId(parentId, new Sort(Direction.DESC, DATE_FIELD_NAME));
    }
}