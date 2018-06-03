package com.ljaskevic.loyaltyone.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.ljaskevic.loyaltyone.model.Comment;

@RestController
@RequestMapping("/api/comments")
public class CommentsController {

    @PostMapping
    public Comment submit(@RequestBody Comment comment) {
        return comment;
    }
}