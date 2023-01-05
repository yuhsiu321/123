package org.example.application.Gaming.respository;

import org.example.application.Gaming.Database.Database;
import org.example.application.Gaming.model.Card;
import org.example.application.Gaming.model.Trade;
import org.example.application.Gaming.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TradeMemoryRepository implements TradeRepository{

    @Override
    public Trade getTrade(String username) {
        try {
            Connection conn = Database.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT * From trades WHERE tradeStarter=?;");
            ps.setString(1,username);
            try(ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Trade trade = new Trade();
                    trade.setId(rs.getString("id"));
                    trade.setCardToTrade(rs.getString("card_a"));
                    trade.setType(rs.getString("type"));
                    trade.setMinimumDamage(rs.getFloat("mindamage"));
                    trade.setTradeStarter(rs.getString("tradestarter"));
                    rs.close();
                    ps.close();
                    conn.close();
                    return trade;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public Trade getTradebyId(String id) {
        try {
            Connection conn = Database.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT * From trades WHERE id=?;");
            ps.setString(1,id);
            try(ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Trade trade = new Trade();
                    trade.setId(rs.getString("id"));
                    trade.setCardToTrade(rs.getString("card_a"));
                    trade.setType(rs.getString("type"));
                    trade.setMinimumDamage(rs.getFloat("mindamage"));
                    trade.setTradeStarter(rs.getString("tradestarter"));
                    rs.close();
                    ps.close();
                    conn.close();
                    return trade;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public Trade addtrade(Trade trade) {

        try {
            Connection conn = Database.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("INSERT INTO trades(id,card_a,type,mindamage,tradestarter) VALUES(?,?,?,?,?) ;");
            ps.setString(1, trade.getId());
            ps.setString(2, trade.getCardToTrade());
            ps.setString(3, trade.getType());
            ps.setFloat(4,trade.getMinimumDamage());
            ps.setString(5,trade.getTradeStarter());
            ps.execute();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return trade;
    }

    @Override
    public Trade deleteTrade(String id) {
        try {
            Connection conn = Database.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("DELETE FROM trades WHERE id = ?;");
            ps.setString(1, id);

            int affectedRows = ps.executeUpdate();

            ps.close();
            conn.close();

            if (affectedRows == 0) {
                return null;
            }

            ps.close();
            conn.close();

            return null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public Card trade(int userid,String id) {
        try {
            Connection conn = Database.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("UPDATE cards SET user_id = ? WHERE id = ?;");

            ps.setInt(1, userid);

            ps.setString(2,id);

            int affectedRows = ps.executeUpdate();

            ps.close();
            conn.close();

            if (affectedRows == 0) {
                return null;
            }

            return null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
