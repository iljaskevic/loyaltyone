package com.ljaskevic.loyaltyone.models;

import java.util.concurrent.atomic.AtomicLong;

public class Comment {

  private final long id;
  private final long parentId;
  private final String content;

  public Comment() {
    this.id = new AtomicLong().get();
    this.parentId = 0L;
    this.content = "";
  }

  public Comment(long parentId, String content) {
      this.id = new AtomicLong().get();
      this.parentId = parentId;
      this.content = content;
  }

  public long getId() {
      return id;
  }

  public long getParentId() {
      return parentId;
  }

  public String getContent() {
      return content;
  }
}