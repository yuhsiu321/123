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
    public Card getCard(String id) {
        try {
            Connection conn = Database.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT id, name, damage, card_type, element_type, is_locked FROM cards WHERE id=?;");
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Card card = Card.fromPrimitives(
                        rs.getString(1), // id
                        rs.getString(2), // name
                        rs.getFloat(3), // damage
                        rs.getString(4), // card_type
                        rs.getString(5), // element_type
                        rs.getBoolean(6)); // is_locked


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
        try {
            Connection conn = Database.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("INSERT INTO cards(id,name, damage, element_type, card_type, package_id, user_id) VALUES(?,?,?,?,?,?,?);", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1,card.getId());
            ps.setString(2, card.getName());
            ps.setFloat(3, card.getDamage());
            ps.setNull(4, java.sql.Types.NULL);
            ps.setNull(5, java.sql.Types.NULL);
            ps.setNull(6, java.sql.Types.NULL);
            ps.setNull(7, java.sql.Types.NULL);

            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                return null;
            }

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return this.getCard(generatedKeys.getString(1));
                }
            }
            ps.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Card addCardToPackage(Card card, Package cardPackage) {
        try {
            Connection conn = Database.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("UPDATE cards SET package_id = ? WHERE id = ?;");
            ps.setInt(1, cardPackage.getId());
            ps.setString(2, card.getId());

            int affectedRows = ps.executeUpdate();

            ps.close();
            conn.close();

            if (affectedRows == 0) {
                return null;
            }
            return this.getCard(card.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
