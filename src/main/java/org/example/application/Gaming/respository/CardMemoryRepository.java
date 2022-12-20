package org.example.application.Gaming.respository;

import org.example.application.Gaming.Database.Database;
import org.example.application.Gaming.model.Card;
import org.example.application.Gaming.model.Package;
import org.example.application.Gaming.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CardMemoryRepository implements CardRepository{

    private static CardMemoryRepository instance;

    private final List<Card> cards;

    public CardMemoryRepository() {
        this.cards = new ArrayList<>();
    }

    public static CardMemoryRepository getInstance() {
        if (CardMemoryRepository.instance == null) {
            CardMemoryRepository.instance = new CardMemoryRepository();
        }
        return CardMemoryRepository.instance;
    }


    @Override
    public Card getCard(int id) {
        try {
            Connection conn = Database.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT id, name, damage, card_type, element_type, is_locked FROM cards WHERE id=?;");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Card card = new Card();
                card.setId(rs.getString("id"));// id
                card.setName(rs.getString("name"));// name
                card.setDamage(rs.getInt("damage")); // damage
                card.setCard_type(rs.getString("card_type"));// card_type
                card.setElement_type(rs.getString("element_type")); // element_type
                card.lock(rs.getBoolean("is_locked"));// is_locked


                rs.close();
                ps.close();
                conn.close();

                return card;
            }

            rs.close();
            ps.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Card> getCards() {
        return null;
    }

    @Override
    public List<Card> getCardsForUser(User user) {
        return null;
    }

    @Override
    public List<Card> getCardsForPackage(Package cardPackage) {
        return null;
    }

    @Override
    public Card addCard(Card card) {
        return null;
    }

    @Override
    public Card addCardToPackage(Card card, Package cardPackage) {
        return null;
    }

    @Override
    public Card addCardToUser(Card card, User user) {
        return null;
    }

    @Override
    public Card lockCard(Card card, boolean isLocked) {
        return null;
    }

    @Override
    public boolean deleteCard(int id) {
        return false;
    }
}
