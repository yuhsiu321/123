package org.example.application.Gaming.model;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(with = JsonFormat.Feature.ACCEPT_CASE_INSENSITIVE_PROPERTIES)
public class Card {

    private String id;

    private String type;

    private String name;

    private Integer damage;

    public Card(){

    }

    public Card(String id, String name, Integer damage){
        this.id = id;
        this.name = name;
        this.damage = damage;
    }

    public String getId(){return id;}

    public void setId(String id){this.id = id;}

    public String getName(){return name;}

    public void setName(String name){this.name = name;}

    public Integer getDamage(){return damage;}

    public void setDamage(int damage){this.damage = damage;}
}
