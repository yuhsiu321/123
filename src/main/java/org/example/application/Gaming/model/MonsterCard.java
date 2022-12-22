package org.example.application.Gaming.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.application.Gaming.type.CardType;
import org.example.application.Gaming.type.ElementType;

public class MonsterCard extends Card{
    @Getter
    CardType cardType = CardType.MONSTER;

    /*@Builder
    public MonsterCard(String id, String name, float damage) {
        super(id, name, damage);
        //this.cardType = CardType.MONSTER;
    }*/
}
