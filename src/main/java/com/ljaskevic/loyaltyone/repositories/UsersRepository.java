package com.ljaskevic.loyaltyone.repositories;

import com.ljaskevic.loyaltyone.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UsersRepository extends MongoRepository<User, String> {

    public User findByUsername(String username);
}