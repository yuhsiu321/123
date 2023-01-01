package org.example.application.Gaming.respository;

import org.example.application.Gaming.model.Card;
import org.example.application.Gaming.model.User;

import java.util.List;

public interface DeckRepository {
    List<Card> getDeck(User user);

    boolean addCardsWithIdsToDeck(String[] ids, User user);

    boolean addCardToDeck(Card card, User user);

    boolean clearDeck(User user);
}
