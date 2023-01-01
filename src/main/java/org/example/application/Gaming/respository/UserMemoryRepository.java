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
    public User getUser(String username) {
        try {
            Connection conn = Database.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM users WHERE username=?;");
            ps.setString(1, username);
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
    public User findStatbyUsername(String username){
        try{
            Connection conn = Database.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT username,elo From users WHERE username=?;");
            ps.setString(1,username);
            try(ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    User user = new User();
                    user.setUsername(rs.getString("username"));
                    user.setElo(rs.getInt("elo"));
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
    public User setRankbyUsername(String username){
        try{
            Connection conn = Database.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT RANK() OVER(ORDER BY elo) AS rank From users WHERE username=?;");
            ps.setString(1,username);
            try(ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    User user = new User();
                    user.setRank(rs.getInt("rank"));
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
                    user.setId(rs.getInt("id"));
                    user.setUsername(rs.getString("username"));
                    user.setStatus(rs.getString("status"));
                    user.setPassword(rs.getString("password"));
                    user.setToken(rs.getString("token"));
                    user.setCoin(rs.getInt("coins"));
                    user.setName(rs.getString("name"));
                    user.setBio(rs.getString("bio"));
                    user.setImage(rs.getString("image"));
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
    public User updateCoin(User user) {
        try {
            Connection conn = Database.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("UPDATE users SET coins = ? WHERE username = ?;");

            ps.setInt(1, user.getCoin());

            ps.setString(2,user.getUsername());

            int affectedRows = ps.executeUpdate();

            ps.close();
            conn.close();

            if (affectedRows == 0) {
                return null;
            }

            return this.findbyUsername(user.getUsername());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public User updateUsertoLogin(User user) {
        try {
            Connection conn = Database.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("UPDATE users SET status=? WHERE username = ?;");

            ps.setString(1, "login");

            ps.setString(2,user.getUsername());

            int affectedRows = ps.executeUpdate();

            ps.close();
            conn.close();

            if (affectedRows == 0) {
                return null;
            }

            return this.findbyUsername(user.getUsername());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public User updateUser(User user,String username) {
        try {
            Connection conn = Database.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("UPDATE users SET name = ?,bio = ?, image = ? WHERE username = ?;");

            ps.setString(1, user.getName());
            ps.setString(2, user.getBio());
            ps.setString(3, user.getImage());
            ps.setString(4,username);

            int affectedRows = ps.executeUpdate();

            ps.close();
            conn.close();

            if (affectedRows == 0) {
                return null;
            }

            return this.findbyUsername(user.getUsername());
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
