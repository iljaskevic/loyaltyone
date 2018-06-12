package com.ljaskevic.loyaltyone.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import com.ljaskevic.loyaltyone.models.Comment;
import com.ljaskevic.loyaltyone.models.LocationInfo;
import com.ljaskevic.loyaltyone.models.User;
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
  private CommentsRepository mockCommentsRepository;

  @Test
  public void testSubmit() throws Exception {
    String testComment = "Test comment content";
    Comment input = new Comment("0", new User("test"), testComment, new LocationInfo());

    Comment output = commentsController.submit(input);

    verify(mockCommentsRepository, times(1)).save(input);
    assertThat(output.getParentId()).isEqualTo("0");
    assertThat(output.getContent()).isEqualTo(testComment);
  }

  @Test
  public void testGetAllRootComments() throws Exception {
    List<Comment> result = new ArrayList<Comment>();
    result.add(new Comment());

    when(mockCommentsRepository.findByParentIdAndUsername("0", "testUser", new Sort(Direction.DESC, "dateCreated"))).thenReturn(result);

    List<Comment> output = commentsController.getAllRootComments("testUser");

    verify(mockCommentsRepository, times(1)).findByParentIdAndUsername("0", "testUser", new Sort(Direction.DESC, "dateCreated"));
    assertThat(output.size()).isEqualTo(1);
  }

  @Test
  public void testGetAllRootComments_nullValue() throws Exception {
    List<Comment> result = new ArrayList<Comment>();
    result.add(new Comment());
    result.add(new Comment());

    when(mockCommentsRepository.findByParentId("0", new Sort(Direction.DESC, "dateCreated"))).thenReturn(result);

    List<Comment> output = commentsController.getAllRootComments(null);

    verify(mockCommentsRepository, times(1)).findByParentId("0", new Sort(Direction.DESC, "dateCreated"));
    assertThat(output.size()).isEqualTo(2);
  }

  @Test
  public void testGetAllRootComments_emptyValue() throws Exception {
    List<Comment> result = new ArrayList<Comment>();
    result.add(new Comment());
    result.add(new Comment());

    when(mockCommentsRepository.findByParentId("0", new Sort(Direction.DESC, "dateCreated"))).thenReturn(result);

    List<Comment> output = commentsController.getAllRootComments("");

    verify(mockCommentsRepository, times(1)).findByParentId("0", new Sort(Direction.DESC, "dateCreated"));
    assertThat(output.size()).isEqualTo(2);
  }

  @Test
  public void testGetAllComments() throws Exception {
    List<Comment> result = new ArrayList<Comment>();
    result.add(new Comment("1234", new User("test"), "Test comment content 1", new LocationInfo()));
    result.add(new Comment("1234", new User("test"), "Test comment content 2", new LocationInfo()));

    when(mockCommentsRepository.findByParentId("1234", new Sort(Direction.DESC, "dateCreated"))).thenReturn(result);

    List<Comment> output = commentsController.getAllCommentReplies("1234", null);

    verify(mockCommentsRepository, times(1)).findByParentId("1234", new Sort(Direction.DESC, "dateCreated"));
    assertThat(output.size()).isEqualTo(2);
    assertThat(output.get(0).getParentId()).isEqualTo("1234");
  }

  @Test
  public void testGetAllComments_emptyParam() throws Exception {
    List<Comment> result = new ArrayList<Comment>();
    result.add(new Comment("0", new User("test"), "Test comment content 1", new LocationInfo()));
    result.add(new Comment("0", new User("test"), "Test comment content 2", new LocationInfo()));

    when(mockCommentsRepository.findByParentId("0", new Sort(Direction.DESC, "dateCreated"))).thenReturn(result);

    List<Comment> output = commentsController.getAllCommentReplies("", null);

    verify(mockCommentsRepository, times(1)).findByParentId("0", new Sort(Direction.DESC, "dateCreated"));
    assertThat(output.size()).isEqualTo(2);
    assertThat(output.get(0).getId()).isNotEqualTo("0");
    assertThat(output.get(0).getParentId()).isEqualTo("0");
    assertThat(output.get(0).getContent()).isEqualTo("Test comment content 1");
    assertThat(output.get(0).getDateCreated()).isNotNull();
  }

  @Test
  public void testGetAllComments_nullParam() throws Exception {
    List<Comment> result = new ArrayList<Comment>();
    result.add(new Comment("0", new User("test"), "Test comment content 1", new LocationInfo()));
    result.add(new Comment("0", new User("test"), "Test comment content 2", new LocationInfo()));

    when(mockCommentsRepository.findByParentId("0", new Sort(Direction.DESC, "dateCreated"))).thenReturn(result);

    List<Comment> output = commentsController.getAllCommentReplies(null, null);

    verify(mockCommentsRepository, times(1)).findByParentId("0", new Sort(Direction.DESC, "dateCreated"));
    assertThat(output.size()).isEqualTo(2);
    assertThat(output.get(0).getId()).isNotEqualTo("0");
    assertThat(output.get(0).getParentId()).isEqualTo("0");
    assertThat(output.get(0).getUser().getUsername()).isEqualTo("test");
    assertThat(output.get(0).getContent()).isEqualTo("Test comment content 1");
    assertThat(output.get(0).getDateCreated()).isNotNull();
  }

  @Test
  public void testGetAllComments_withUsername() throws Exception {
    List<Comment> result = new ArrayList<Comment>();
    result.add(new Comment("1234", new User("test"), "Test comment content 1", new LocationInfo()));

    when(mockCommentsRepository.findByParentIdAndUserId("1234", "test", new Sort(Direction.DESC, "dateCreated"))).thenReturn(result);

    List<Comment> output = commentsController.getAllCommentReplies("1234", "test");

    verify(mockCommentsRepository, times(1)).findByParentIdAndUserId("1234", "test", new Sort(Direction.DESC, "dateCreated"));
    assertThat(output.size()).isEqualTo(1);
    assertThat(output.get(0).getParentId()).isEqualTo("1234");
    assertThat(output.get(0).getUser().getUsername()).isEqualTo("test");
  }
}
