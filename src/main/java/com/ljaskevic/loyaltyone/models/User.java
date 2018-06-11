package com.ljaskevic.loyaltyone.models;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document
public class User {

    @Id
    public final String id;

    public final String username;
    public final Date dateCreated;

    public User() {
        this.id = RandomStringUtils.randomAlphanumeric(8);
        this.username = "Anonymous";
        this.dateCreated = new Date();
    }

    public User(String username) {
        this.id = RandomStringUtils.randomAlphanumeric(8);
        this.username = username;
        this.dateCreated = new Date();
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public Date getDateCreated() {
        return dateCreated;
    }
}