package org.example.application.Gaming.respository;

import org.example.application.Gaming.model.Card;
import org.example.application.Gaming.model.Package;
import org.example.application.Gaming.model.User;

import java.util.List;

public interface CardRepository {

    Card getCard(int id);

    List<Card> getCards();

    List<Card> getCardsForUser(User user);

    List<Card> getCardsForPackage(Package cardPackage);

    Card addCard(Card card);

    Card addCardToPackage(Card card, Package cardPackage);

    Card addCardToUser(Card card, User user);

    Card lockCard(Card card, boolean isLocked);

    boolean deleteCard(int id);


}
