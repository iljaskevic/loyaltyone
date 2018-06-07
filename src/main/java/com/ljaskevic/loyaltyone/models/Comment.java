package com.ljaskevic.loyaltyone.models;

import org.apache.commons.lang3.RandomStringUtils;
import java.util.Date;

public class Comment {

    public final String id;

    public final String parentId;
    public final String username;
    public final String content;
    public final Date dateCreated;

    public Comment() {
        this.id = RandomStringUtils.randomAlphanumeric(8);
        this.parentId = "0";
        this.username = "Anonymous";
        this.content = "";
        this.dateCreated = new Date();
    }

    public Comment(String parentId, String username, String content) {
        this.id = RandomStringUtils.randomAlphanumeric(8);
        this.parentId = parentId;
        this.username = username;
        this.content = content;
        this.dateCreated = new Date();
    }

    public String getId() {
        return id;
    }

    public String getParentId() {
        return parentId;
    }

    public String getUsername() {
        return username;
    }

    public String getContent() {
        return content;
    }

    public Date getDateCreated() {
        return dateCreated;
    }
}