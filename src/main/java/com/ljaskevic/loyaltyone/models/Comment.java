package com.ljaskevic.loyaltyone.models;

import org.apache.commons.lang3.RandomStringUtils;
import java.util.Date;

public class Comment {

    public final String id;

    public String parentId;
    public String content;
    public Date dateCreated;

    public Comment() {
        this.id = RandomStringUtils.randomAlphanumeric(8);
        this.parentId = "0";
        this.content = "";
        this.dateCreated = new Date();
    }

    public Comment(String parentId, String content) {
        this.id = RandomStringUtils.randomAlphanumeric(8);
        this.parentId = parentId;
        this.content = content;
        this.dateCreated = new Date();
    }

    public String getId() {
        return id;
    }

    public String getParentId() {
        return parentId;
    }

    public String getContent() {
        return content;
    }

    public Date getDateCreated() {
        return dateCreated;
    }
}