package com.ljaskevic.loyaltyone.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.ArgumentMatcher;
import org.mockito.ArgumentMatchers;
import org.mockito.Matchers;
import org.mockito.internal.matchers.Any;

import com.ljaskevic.loyaltyone.models.Comment;
import com.ljaskevic.loyaltyone.models.LocationInfo;
import com.ljaskevic.loyaltyone.models.User;
import com.ljaskevic.loyaltyone.repositories.CommentsRepository;
import com.ljaskevic.loyaltyone.repositories.UsersRepository;

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
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import java.util.ArrayList;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsersControllerTest {

  @Autowired
  private UsersController usersController;

  @MockBean
  private UsersRepository mockUsersRepository;

  @MockBean
  private HttpServletResponse mockHttpServletResponse;

  @Test
  public void testAddUser() throws Exception {
    User input = new User("testuser");
    when(mockUsersRepository.findByUsername("testuser")).thenReturn(null);

    User output = usersController.addUser(input, mockHttpServletResponse);

    verify(mockUsersRepository, times(1)).findByUsername("testuser");
    verify(mockUsersRepository, times(1)).save(input);
    verify(mockHttpServletResponse, times(1)).setStatus(201);
    assertThat(output.getId()).isEqualTo(input.getId());
  }

  @Test
  public void testAddUser_existingUser() throws Exception {
    User input = new User("testuser");
    User ret = new User("testuser");
    when(mockUsersRepository.findByUsername("testuser")).thenReturn(ret);

    User output = usersController.addUser(input, mockHttpServletResponse);

    verify(mockUsersRepository, times(1)).findByUsername("testuser");
    verify(mockUsersRepository, times(0)).save(input);
    verify(mockHttpServletResponse, times(1)).setStatus(200);
    assertThat(output.getId()).isEqualTo(ret.getId());
  }

  @Test
  public void testGetUser() throws Exception {
    User out = new User("testuser");
    when(mockUsersRepository.findById("1234")).thenReturn(Optional.of(out));

    User output = usersController.getUser("1234", mockHttpServletResponse);

    verify(mockUsersRepository, times(1)).findById("1234");
    verify(mockHttpServletResponse, times(1)).setStatus(200);
  }

  @Test
  public void testGetUser_userNotFound() throws Exception {
    when(mockUsersRepository.findById("1234")).thenReturn(Optional.empty());

    User output = usersController.getUser("1234", mockHttpServletResponse);

    verify(mockUsersRepository, times(1)).findById("1234");
    verify(mockHttpServletResponse, times(1)).setStatus(404);
  }

  @Test
  public void testDeleteUser() throws Exception {
    User out = new User("testuser");
    when(mockUsersRepository.findById("1234")).thenReturn(Optional.of(out));

    usersController.deleteUser("1234", mockHttpServletResponse);

    verify(mockUsersRepository, times(1)).findById("1234");
    verify(mockUsersRepository, times(1)).delete(out);
    verify(mockHttpServletResponse, times(1)).setStatus(200);
  }

  @Test
  public void testDeleteUser_nonexistingUser() throws Exception {
    User out = new User("testuser");
    when(mockUsersRepository.findById("1234")).thenReturn(Optional.empty());

    usersController.deleteUser("1234", mockHttpServletResponse);

    verify(mockUsersRepository, times(1)).findById("1234");
    verify(mockHttpServletResponse, times(1)).setStatus(404);
  }
}
