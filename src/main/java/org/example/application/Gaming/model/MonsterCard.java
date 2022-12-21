package org.example.application.Gaming.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.application.Gaming.type.CardType;
import org.example.application.Gaming.type.ElementType;

public class MonsterCard extends Card{
    @Getter
    CardType cardType = CardType.MONSTER;

    @Builder
    public MonsterCard(String id, String name, float damage, ElementType elementType, boolean locked) {
        super(id, name, damage, elementType, locked);
        this.cardType = CardType.MONSTER;
    }
}
