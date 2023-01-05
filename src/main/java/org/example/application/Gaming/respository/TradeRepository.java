package org.example.application.Gaming.respository;

import org.example.application.Gaming.model.Card;
import org.example.application.Gaming.model.Trade;

public interface TradeRepository {
    Trade getTrade(String username);
    Trade getTradebyId(String id);
    Trade addtrade(Trade trade);
    Trade deleteTrade(String id);
    Card trade(int userid, String cardid);
}
