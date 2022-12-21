package org.example.application.Gaming.model;

import com.fasterxml.jackson.annotation.JsonFormat;
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

    public Card(String id, String name, float damage, ElementType elementType, boolean locked) {
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



    public static Card fromPrimitives(String id, String name, float damage, String cardTypeString, String elementTypeString, boolean locked) {
        CardType cardType;
        ElementType elementType;
        Card card;

        try {
            cardType = CardType.valueOf(cardTypeString);
        } catch (IllegalArgumentException e) {
            cardType = CardType.MONSTER;
        }

        try {
            elementType = ElementType.valueOf(elementTypeString);
        } catch (IllegalArgumentException e) {
            elementType = ElementType.REGULAR;
        }

        if (CardType.MONSTER.equals(cardType)) {
            // Monster Card
            card = MonsterCard.builder()
                    .id(id)
                    .name(name)
                    .damage(damage)
                    .elementType(elementType)
                    .build();
        } else {
            // Otherwise it is a Spell Card
            card = SpellCard.builder()
                    .id(id)
                    .name(name)
                    .damage(damage)
                    .elementType(elementType)
                    .build();
        }

        return card;
    }
}
