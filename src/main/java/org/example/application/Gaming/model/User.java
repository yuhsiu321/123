package org.example.application.Gaming.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;

@JsonFormat(with = JsonFormat.Feature.ACCEPT_CASE_INSENSITIVE_PROPERTIES)
public class User {

    private Integer id;

    private String username;

    private String token;

    private String status;

    private String password;

    private int coin = 20;

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword(){return password;}

    public String getHashPassword() {
        String passwordHash = Hashing.sha256()
                .hashString(password, StandardCharsets.UTF_8)
                .toString();
        return passwordHash;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getCoin(){return coin;}

    public void setCoin(int coin){this.coin = coin;}

    public void setToken(String username){this.token = token;}

    public String getToken(){
        String ttoken = username+"-mtcgToken";
        return ttoken;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
