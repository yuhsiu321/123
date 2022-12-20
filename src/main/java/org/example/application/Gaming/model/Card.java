package org.example.application.Gaming.model;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(with = JsonFormat.Feature.ACCEPT_CASE_INSENSITIVE_PROPERTIES)
public class Card {

    private String id;

    private String card_type;

    private String element_type;

    private String name;

    private Integer damage;

    private boolean isLocked;


    public boolean lock(boolean isLocked) {
        return isLocked;
    }

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

    public String getCard_type() {
        return card_type;
    }

    public void setCard_type(String card_type) {
        this.card_type = card_type;
    }

    public String getElement_type() {
        return element_type;
    }

    public void setElement_type(String element_type) {
        this.element_type = element_type;
    }
}
