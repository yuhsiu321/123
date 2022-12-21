package org.example.application.Gaming.respository;

import org.example.DatabaseInit;
import org.example.application.Gaming.Database.Database;
import org.example.application.Gaming.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserMemoryRepository implements UserRepository {

    private static UserMemoryRepository instance;

    public static UserMemoryRepository getInstance() {
        if (UserMemoryRepository.instance == null) {
            UserMemoryRepository.instance = new UserMemoryRepository();
        }
        return UserMemoryRepository.instance;
    }

    private final List<User> users;

    public UserMemoryRepository() {
        this.users = new ArrayList<>();
    }

    @Override
    public List<User> findAll() {
        try(Connection conn = Database.getInstance().getConnection();
            Statement sm = conn.createStatement();
            ResultSet rs = sm.executeQuery("SELECT id,username,password,token,coins,status FROM users;"))
        {
            while (rs.next()){
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setToken(rs.getString("token"));
                user.setCoin(rs.getInt("coins"));
                user.setStatus(rs.getString("status"));
                users.add(user);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public User getUser(int id) {
        try {
            Connection conn = Database.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT id, username, password, token, coins, status FROM users WHERE id=?;");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setToken(rs.getString("token"));
                user.setCoin(rs.getInt("coins"));
                user.setStatus(rs.getString("status"));
                rs.close();
                ps.close();
                conn.close();

                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public User login(User user) {
        try {
            Connection conn = Database.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT * From users WHERE username=? AND password=?;");
            ps.setString(1,user.getUsername());
            ps.setString(2,user.getHashPassword());
            try(ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    user = new User();
                    user.setId(rs.getInt("id"));
                    user.setUsername(rs.getString("username"));
                    user.setPassword(rs.getString("password"));
                    rs.close();
                    ps.close();
                    conn.close();
                    return user;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public User findbyUsername(String username){
        try {
            Connection conn = Database.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT * From users WHERE username=?;");
            ps.setString(1,username);
            try(ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    User user = new User();
                    user.setUsername(rs.getString("username"));
                    user.setPassword(rs.getString("password"));
                    user.setToken(rs.getString("token"));
                    rs.close();
                    ps.close();
                    conn.close();
                    return user;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public User save(User user) {

        try {
            Connection conn = Database.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("INSERT INTO users(username,password,coins,token) VALUES(?,?,?,?) ;");
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getHashPassword());
            ps.setInt(3, user.getCoin());
            ps.setString(4,user.getToken());
            ps.execute();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public User updateUser(int id, User user) {
        User oldUser = (User) this.getUser(id);
        try {
            Connection conn = Database.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("UPDATE users SET username = ?, password = ?, token = ?, coins = ?, status = ? WHERE id = ?;");

            ps.setString(1, user.getUsername() != null ? user.getUsername() : oldUser.getUsername());
            ps.setString(2, user.getPassword() != null ? user.getPassword() : oldUser.getPassword());
            ps.setString(3, user.getToken() != null ? user.getToken() : oldUser.getToken());
            ps.setInt(4, user.getCoin());
            ps.setString(5, user.getStatus() != null ? user.getStatus() : oldUser.getStatus());
            ps.setInt(6, id);

            int affectedRows = ps.executeUpdate();

            ps.close();
            conn.close();

            if (affectedRows == 0) {
                return null;
            }

            return this.getUser(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public User delete(User user) {
        if (this.users.contains(user)) {
            this.users.remove(user);
        }

        return user;
    }
}
