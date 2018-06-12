package com.ljaskevic.loyaltyone.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.ljaskevic.loyaltyone.models.User;
import com.ljaskevic.loyaltyone.repositories.UsersRepository;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api")
public class UsersController {

    @Autowired
    UsersRepository usersRepository;

    private static final Sort SORTER = new Sort(Direction.DESC, "dateCreated");

    @PostMapping("/users")
    public User addUser(@RequestBody User user, HttpServletResponse response) {
        User existingUser = usersRepository.findByUsername(user.getUsername());
        if (existingUser != null) {
            response.setStatus(200);
            return existingUser;
        }
        usersRepository.save(user);
        response.setStatus(201);
        return user;
    }

    @GetMapping("/users/{id}")
    public User getUser(@PathVariable("id") String id, HttpServletResponse response) {
        Optional<User> existingUser = usersRepository.findById(id);
        if (!existingUser.isPresent()) {
            response.setStatus(404);
            return null;
        }
        response.setStatus(200);
        return existingUser.get();
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return usersRepository.findAll(SORTER);
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable("id") String id, HttpServletResponse response) {
        Optional<User> existingUser = usersRepository.findById(id);
        if (!existingUser.isPresent()) {
            response.setStatus(404);
            return;
        }
        usersRepository.delete(existingUser.get());
        response.setStatus(200);
    }
}