package org.example.application.Gaming.respository;

import org.example.application.Gaming.model.User;

import java.util.List;

public interface UserRepository {

    List<User> findAll();

    User getUser(int Id);

    User login(User user);

    User findbyUsername(String username);

    User save(User user);

    User updateCoin( User user);
    User updateUsertoLogin( User user);
    User updateUser(User user,String username);
    User findStatbyUsername(String username);
    User getRankbyUsername(String username);
    User setRank(User user);
    boolean updateEloForPlayers(User playerA, User playerB, int pA, int pB);
    User addStatForUser(User user, int stat);
    User delete(User user);
}
