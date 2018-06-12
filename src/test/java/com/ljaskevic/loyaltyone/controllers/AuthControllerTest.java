package com.ljaskevic.loyaltyone.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import com.ljaskevic.loyaltyone.models.Comment;
import com.ljaskevic.loyaltyone.models.LocationInfo;
import com.ljaskevic.loyaltyone.models.User;
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

import javax.servlet.http.HttpServletResponse;

import java.util.ArrayList;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthControllerTest {

  @Autowired
  private AuthController authController;

  @MockBean
  private UsersRepository mockUsersRepository;

  @MockBean
  private HttpServletResponse mockHttpServletResponse;

  @Test
  public void testSubmit() throws Exception {
    User input = new User("testuser");

    when(mockUsersRepository.findByUsername("testuser")).thenReturn(null);

    User output = authController.submit(input, mockHttpServletResponse);

    verify(mockUsersRepository, times(1)).findByUsername("testuser");
    verify(mockUsersRepository, times(1)).save(input);
    verify(mockHttpServletResponse, times(1)).setStatus(201);
    assertThat(output.getDateCreated()).isNotNull();
  }

  @Test
  public void testSubmit_existing() throws Exception {
    User input = new User("testuser");
    User out = new User("testuser");

    when(mockUsersRepository.findByUsername("testuser")).thenReturn(out);

    User output = authController.submit(input, mockHttpServletResponse);

    verify(mockUsersRepository, times(1)).findByUsername("testuser");
    verify(mockUsersRepository, times(0)).save(input);
    verify(mockHttpServletResponse, times(1)).setStatus(200);
    assertThat(output.getDateCreated()).isNotNull();
  }
}
