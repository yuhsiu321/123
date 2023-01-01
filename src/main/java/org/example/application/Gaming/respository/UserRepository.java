package org.example.application.Gaming.respository;

import org.example.application.Gaming.model.User;

import java.util.List;

public interface UserRepository {

    List<User> findAll();

    User getUser(String username);

    User login(User user);

    User findbyUsername(String username);

    User save(User user);

    User updateCoin( User user);
    User updateUsertoLogin( User user);
    User updateUser(User user,String username);
    User findStatbyUsername(String username);
    User getRankbyUsername(String username);
    User setRank(User user);
    User delete(User user);
}
