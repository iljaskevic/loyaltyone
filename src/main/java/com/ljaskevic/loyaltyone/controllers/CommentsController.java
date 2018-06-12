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

    private static final Sort SORTER_DESC = new Sort(Direction.DESC, "dateCreated");
    private static final Sort SORTER_ASC = new Sort(Direction.DESC, "dateCreated");

    @PostMapping("/comments")
    public Comment submit(@RequestBody Comment comment) {
        if (comment.getParentId().trim() != "0") {
            Comment parent = commentsRepository.findById(comment.getParentId(), SORTER_DESC);
            parent.incrementRepliesCount();
            commentsRepository.save(parent);
        }
        commentsRepository.save(comment);
        return comment;
    }

    @GetMapping("/comments")
    public List<Comment> getAllRootComments(@RequestParam(name="q", required=false) String userId) {
        if (userId == null || userId.trim().isEmpty()) {
            return getCommentsByParentId("0", SORTER_DESC);
        }
        return commentsRepository.findByParentIdAndUserId("0", userId, SORTER_DESC);
    }

    @GetMapping("/comments/{commentId}")
    public Comment getComment(@PathVariable String commentId) {
        return commentsRepository.findById(commentId, SORTER_DESC);
    }

    @GetMapping("/comments/{commentId}/replies")
    public List<Comment> getAllCommentReplies(@PathVariable String commentId, @RequestParam(name="q", required=false) String userId) {
        if (userId == null || userId.trim().isEmpty()) {
            return getCommentsByParentId(commentId, SORTER_ASC);
        }
        return commentsRepository.findByParentIdAndUserId(commentId, userId, SORTER_ASC);
    }

    private List<Comment> getCommentsByParentId(String parentId, Sort sorter) {
        if (parentId == null || parentId.trim().isEmpty()) {
            parentId = "0";
        }
        return commentsRepository.findByParentId(parentId, sorter);
    }
}