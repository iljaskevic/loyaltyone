package com.ljaskevic.loyaltyone.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import com.ljaskevic.loyaltyone.models.Comment;
import com.ljaskevic.loyaltyone.repositories.CommentsRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.ArrayList;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CommentsControllerTest {

  @Autowired
  private CommentsController commentsController;

  @MockBean
  private CommentsRepository commentsRepository;

  @Test
  public void testSubmit() throws Exception {
    String testComment = "Test comment content";
    Comment input = new Comment("0", testComment);

    Comment output = commentsController.submit(input);

    verify(commentsRepository, times(1)).save(input);
    assertThat(output.getParentId()).isEqualTo("0");
    assertThat(output.getContent()).isEqualTo(testComment);
  }

  @Test
  public void testGetAllRootComments() throws Exception {
    List<Comment> result = new ArrayList<Comment>();
    result.add(new Comment("0", "Test comment content 1"));
    result.add(new Comment("0", "Test comment content 2"));

    when(commentsRepository.findByParentId("0", new Sort(Direction.DESC, "dateCreated"))).thenReturn(result);

    List<Comment> output = commentsController.getAllRootComments();

    verify(commentsRepository, times(1)).findByParentId("0", new Sort(Direction.DESC, "dateCreated"));
    assertThat(output.size()).isEqualTo(2);
  }

  @Test
  public void testGetAllComments() throws Exception {
    List<Comment> result = new ArrayList<Comment>();
    result.add(new Comment("1234", "Test comment content 1"));
    result.add(new Comment("1234", "Test comment content 2"));

    when(commentsRepository.findByParentId("1234", new Sort(Direction.DESC, "dateCreated"))).thenReturn(result);

    List<Comment> output = commentsController.getAllComments("1234");

    verify(commentsRepository, times(1)).findByParentId("1234", new Sort(Direction.DESC, "dateCreated"));
    assertThat(output.size()).isEqualTo(2);
    assertThat(output.get(0).getParentId()).isEqualTo("1234");
  }

  @Test
  public void testGetAllComments_missingParam() throws Exception {
    List<Comment> result = new ArrayList<Comment>();
    result.add(new Comment("0", "Test comment content 1"));
    result.add(new Comment("0", "Test comment content 2"));

    when(commentsRepository.findByParentId("0", new Sort(Direction.DESC, "dateCreated"))).thenReturn(result);

    List<Comment> output = commentsController.getAllComments("");

    verify(commentsRepository, times(1)).findByParentId("0", new Sort(Direction.DESC, "dateCreated"));
    assertThat(output.size()).isEqualTo(2);
    assertThat(output.get(0).getParentId()).isEqualTo("0");
  }
}
