package com.ljaskevic.loyaltyone.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.ljaskevic.loyaltyone.models.Comment;
import com.ljaskevic.loyaltyone.repositories.CommentsRepository;
import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentsController {

    @Autowired
    CommentsRepository commentsRepository;

    @PostMapping
    public Comment submit(@RequestBody Comment comment) {
        commentsRepository.save(comment);
        return comment;
    }

    @GetMapping("/{parentId}")
    public List<Comment> getAllComments(@PathVariable String parentId) {
        if (parentId == null || parentId.trim().isEmpty()) {
            parentId = "0";
        }
        return commentsRepository.findByParentId(parentId);
    }
}