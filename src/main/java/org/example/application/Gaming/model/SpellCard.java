package org.example.application.Gaming.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.application.Gaming.type.CardType;
import org.example.application.Gaming.type.ElementType;

public class SpellCard extends Card{
    @Getter
    CardType cardType = CardType.SPELL;

    @Builder
    public SpellCard(String id, String name, float damage, ElementType elementType, CardType cardType, boolean locked) {
        super(id, name, damage, elementType, locked);
        this.cardType = CardType.SPELL;
    }
}
