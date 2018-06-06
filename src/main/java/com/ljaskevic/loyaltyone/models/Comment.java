package com.ljaskevic.loyaltyone.models;

import org.apache.commons.lang3.RandomStringUtils;

public class Comment {

    private final String id;

    private final String parentId;
    private final String content;

    public Comment() {
        this.id = RandomStringUtils.randomAlphanumeric(8);
        this.parentId = "0";
        this.content = "";
    }

    public Comment(String parentId, String content) {
        this.id = RandomStringUtils.randomAlphanumeric(8);
        this.parentId = parentId;
        this.content = content;
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
}