package com.ljaskevic.loyaltyone.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.ljaskevic.loyaltyone.models.User;
import com.ljaskevic.loyaltyone.repositories.UsersRepository;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/login")
public class AuthController {

    @Autowired
    UsersRepository usersRepository;

    @PostMapping()
    public User submit(@RequestBody User user, HttpServletResponse response) {
        User existingUser = usersRepository.findByUsername(user.getUsername());
        if (existingUser != null) {
            response.setStatus(200);
            return existingUser;
        }
        response.setStatus(201);
        usersRepository.save(user);
        return user;
    }
}