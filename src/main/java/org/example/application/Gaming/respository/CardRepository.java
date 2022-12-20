package org.example.application.Gaming.respository;

import org.example.application.Gaming.model.Card;

import java.util.List;

public interface CardRepository {

    List<Card> findAll();

    Card findById(String id);

    Card save(Card packages,String username);


}
