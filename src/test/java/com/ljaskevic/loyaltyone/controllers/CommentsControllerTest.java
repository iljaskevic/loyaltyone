package com.ljaskevic.loyaltyone.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import com.ljaskevic.loyaltyone.models.Comment;
import static org.assertj.core.api.Assertions.assertThat;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.beans.factory.annotation.Autowired;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CommentsControllerTest {

  @Autowired
  private CommentsController commentsController;

  @Test
  public void submitComment() throws Exception {
    String testComment = "Test comment content";
    Comment input = new Comment(0L, testComment);
    Comment output = commentsController.submit(input);

    assertThat(output.getId()).isEqualTo(0L);
    assertThat(output.getContent()).isEqualTo(testComment);
  }
}
