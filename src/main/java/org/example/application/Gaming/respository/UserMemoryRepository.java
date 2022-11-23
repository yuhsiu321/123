package org.example.application.Gaming.respository;

import org.example.application.Gaming.Database.Database;
import org.example.application.Gaming.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserMemoryRepository implements UserRepository {

    private final List<User> users;

    public UserMemoryRepository() {
        this.users = new ArrayList<>();
    }

    @Override
    public List<User> findAll() {
        try(Connection conn = Database.getDatabase().getConnection();
            Statement sm = conn.createStatement();
            ResultSet rs = sm.executeQuery("SELECT username,password FROM users;"))
        {
            while (rs.next()){
                User user = new User();
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                users.add(user);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public User findByUsername(String username) {
        Connection conn = Database.getDatabase().getConnection();
        try {
            Statement sm = conn.createStatement();
            ResultSet rs = sm.executeQuery("SELECT username, password From users WHERE username=?;");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        /*for (User user: this.users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }*/
        return null;
    }

    @Override
    public User save(User user) {

        try {
            Connection conn = Database.getDatabase().getConnection();
            PreparedStatement ps = conn.prepareStatement("INSERT INTO users(username,password) VALUES(?,?) ;");
            ps.setString(1, user.getUsername());
            ps.setString(2,user.getPassword());
            ps.execute();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        /*if (!this.users.contains(user)) {
            this.users.add(user);
        }*/

        return user;
    }

    @Override
    public User delete(User user) {
        if (this.users.contains(user)) {
            this.users.remove(user);
        }

        return user;
    }
}
