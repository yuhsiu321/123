package org.example.application.Gaming.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import org.example.application.Gaming.type.CardType;
import org.example.application.Gaming.type.ElementType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@JsonFormat(with = JsonFormat.Feature.ACCEPT_CASE_INSENSITIVE_PROPERTIES)
public class Card {


    @Getter
    String id;

    @Getter
    String name;

    @Getter
    float damage;

    @Getter
    ElementType elementType;

    @Getter
    CardType cardType;

    @Getter
    boolean isLocked;
    @Builder
    public Card(String id, String name, float damage) {
        this.id = id;
        this.name = name;
        this.damage = damage;
    }


    public boolean lock(boolean isLocked) {
        return isLocked;
    }

    public Card(){}


    public String getId(){return id;}

    public void setId(String id){this.id = id;}

    public String getName(){return name;}

    public void setName(String name){this.name = name;}

    public float getDamage(){return damage;}

    public void setDamage(int damage){this.damage = damage;}



    public static Card fromPrimitives(String id, String name, float damage) {
        Card card;


        card = Card.builder()
                .id(id)
                .name(name)
                .damage(damage)
                .build();

        return card;
    }
}
