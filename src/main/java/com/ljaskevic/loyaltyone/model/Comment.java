package com.ljaskevic.loyaltyone.model;

public class Comment {

  private final long id;
  private final String content;

  public Comment() {
    this.id = 0L;
    this.content = "";
  }

  public Comment(long id, String content) {
      this.id = id;
      this.content = content;
  }

  public long getId() {
      return id;
  }

  public String getContent() {
      return content;
  }
}