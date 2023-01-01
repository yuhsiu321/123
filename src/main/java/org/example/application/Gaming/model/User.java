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

    private String Name;
    private String Bio;
    private String Image;
    private Integer elo = 100;
    private Integer rank;
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

    public String getBio() {
        return Bio;
    }

    public String getImage() {
        return Image;
    }

    public String getName() {
        return Name;
    }

    public void setBio(String bio) {
        this.Bio = bio;
    }

    public void setImage(String image) {
        this.Image = image;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public Integer getElo() {
        return elo;
    }

    public void setElo(Integer elo) {
        this.elo = elo;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }
}
