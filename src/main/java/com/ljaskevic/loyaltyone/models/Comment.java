package com.ljaskevic.loyaltyone.models;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.ljaskevic.loyaltyone.models.User;
import java.util.Date;

@Document
public class Comment {

    @Id
    public final String id;

    public final String parentId;
    public final User user;
    public final String content;
    public final Date dateCreated;
    public int repliesCount;

    public Comment() {
        this.id = RandomStringUtils.randomAlphanumeric(8);
        this.parentId = "0";
        this.user = new User();
        this.content = "";
        this.repliesCount = 0;
        this.dateCreated = new Date();
    }

    public Comment(String parentId, User user, String content) {
        this.id = RandomStringUtils.randomAlphanumeric(8);
        this.parentId = parentId;
        this.user = user;
        this.repliesCount = 0;
        this.content = content;
        this.dateCreated = new Date();
    }

    public String getId() {
        return id;
    }

    public String getParentId() {
        return parentId;
    }

    public int getRepliesCount() {
        return repliesCount;
    }

    public void incrementRepliesCount() {
        this.repliesCount++;
    }

    public User getUser() {
        return user;
    }

    public String getContent() {
        return content;
    }

    public Date getDateCreated() {
        return dateCreated;
    }
}