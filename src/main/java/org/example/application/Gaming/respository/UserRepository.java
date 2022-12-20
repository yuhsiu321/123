package org.example.application.Gaming.respository;

import org.example.application.Gaming.model.User;

import java.util.List;

public interface UserRepository {

    List<User> findAll();

    User getUser(int id);

    User login(User user);

    User findbyUsername(String username);

    User save(User user);

    User updateUser(int id, User user);

    User delete(User user);
}
