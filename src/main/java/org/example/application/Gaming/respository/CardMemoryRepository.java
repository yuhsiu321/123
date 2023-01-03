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
            PreparedStatement ps = conn.prepareStatement("SELECT id, name, damage FROM cards WHERE id=?;");
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Card card = new Card();
                card.setId(rs.getString("id"));
                card.setName(rs.getString("name"));
                card.setDamage(rs.getFloat("damage"));
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
        try {
            Connection conn = Database.getInstance().getConnection();
            Statement sm = conn.createStatement();
            ResultSet rs = sm.executeQuery("SELECT id, name, damage, card_type, element_type, is_locked FROM cards;");

            List<Card> cards = new ArrayList<>();
            while (rs.next()) {
                Card card = new Card();
                card.setId(rs.getString("id"));
                card.setName(rs.getString("name"));
                card.setDamage(rs.getInt("damage"));
                card.setCardType(rs.getString("card_type"));
                card.setElementType(rs.getString("element_type"));
                card.setLock(rs.getBoolean("is_locked"));
                cards.add(card);
            }

            rs.close();
            sm.close();
            conn.close();

            return cards;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Card getCardbyid(User user, String id){
        try {
            Connection conn = Database.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT id, name, damage, card_type, element_type, is_locked FROM cards WHERE user_id = ? AND id = ?;");
            ps.setInt(1, user.getId());
            ps.setString(2,id);
            ResultSet rs = ps.executeQuery();

            if(rs.next()) {
                Card card = new Card();
                card.setId(rs.getString("id"));
                card.setName(rs.getString("name"));
                card.setDamage(rs.getInt("damage"));
                card.setCardType(rs.getString("card_type"));
                card.setElementType(rs.getString("element_type"));
                card.setLock(rs.getBoolean("is_locked"));
                rs.close();
                ps.close();
                conn.close();

                return card;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Card> getCardsForUser(User user) {
        try {
            Connection conn = Database.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT id, name, damage, card_type, element_type, is_locked FROM cards WHERE user_id = ?;");
            ps.setInt(1, user.getId());
            ResultSet rs = ps.executeQuery();

            List<Card> cards = new ArrayList<>();
            while (rs.next()) {
                Card card = new Card();
                card.setId(rs.getString("id"));
                card.setName(rs.getString("name"));
                card.setDamage(rs.getInt("damage"));
                card.setCardType(rs.getString("card_type"));
                card.setElementType(rs.getString("element_type"));
                card.setLock(rs.getBoolean("is_locked"));
                cards.add(card);
            }

            rs.close();
            ps.close();
            conn.close();

            return cards;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Card> getCardsForPackage(Package cardPackage) {
        try {
            Connection conn = Database.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT id, name, damage, card_type, element_type, is_locked FROM cards WHERE package_id = ?;");
            ps.setInt(1, cardPackage.getId());
            ResultSet rs = ps.executeQuery();

            List<Card> cards = new ArrayList<>();
            while (rs.next()) {
                Card card = new Card();
                card.setId(rs.getString("id"));
                card.setName(rs.getString("name"));
                card.setDamage(rs.getInt("damage"));
                card.setCardType(rs.getString("card_type"));
                card.setElementType(rs.getString("element_type"));
                card.setLock(rs.getBoolean("is_locked"));
                cards.add(card);
            }

            rs.close();
            ps.close();
            conn.close();

            return cards;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Card addCard(Card card) {
        card.setCardType(card.getName());
        card.setElementType(card.getName());
        try {
            Connection conn = Database.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("INSERT INTO cards(id, name, damage, element_type,card_type) VALUES(?,?,?,?,?);", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1,card.getId());
            ps.setString(2, card.getName());
            ps.setFloat(3, card.getDamage());
            ps.setString(4,card.getElementType());
            ps.setString(5, card.getCardType());

            ps.execute();
            conn.close();
            return card;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Card addCardToPackage(Card card, int pid) {
        try {
            Connection conn = Database.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("UPDATE cards SET package_id = ? WHERE id = ?;");
            ps.setInt(1, pid);
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
        try {
            Connection conn = Database.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("UPDATE cards SET package_id = NULL, user_id = ? WHERE id = ?;");
            ps.setInt(1, user.getId());
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
    public Card lockCard(Card card, boolean isLocked) {

        try {
            Connection conn = Database.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("UPDATE cards SET is_locked = ? WHERE id = ?;");
            ps.setBoolean(1, isLocked);
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
    public boolean deleteCard(String id) {

        try {
            Connection conn = Database.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("DELETE FROM cards WHERE id = ?;");
            ps.setString(1, id);

            int affectedRows = ps.executeUpdate();

            ps.close();
            conn.close();

            if (affectedRows == 0) {
                return false;
            }

            ps.close();
            conn.close();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
