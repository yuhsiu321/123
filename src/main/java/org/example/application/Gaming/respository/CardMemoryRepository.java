package org.example.application.Gaming.respository;

import org.example.application.Gaming.Database.Database;
import org.example.application.Gaming.model.Card;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CardMemoryRepository implements CardRepository{

    private final List<Card> cards;

    public CardMemoryRepository() {
        this.cards = new ArrayList<>();
    }

    @Override
    public List<Card> findAll() {
        return this.cards;
    }

    @Override
    public Card findById(String id) {
        for (Card card: this.cards) {
            if(card.getId().equals(id)){
                return card;
            }
        }
        return null;
    }

    @Override
    public Card save(Card cards) {

        try {
            Connection conn = Database.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("INSERT INTO cards(id,name,damage,username) VALUES(?,?,?,?) ;");
            ps.setString(1, cards.getId());
            ps.setString(2,cards.getName());
            ps.setInt(3,cards.getDamage());
            //ps.setString(4,username);
            //ps.setString(4,);
            ps.execute();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        /*if(!this.packages.contains(packages)){
            this.packages.add(packages);
        }*/
        return null;
    }

}
