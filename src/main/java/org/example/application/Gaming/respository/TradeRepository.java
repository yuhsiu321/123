package org.example.application.Gaming.respository;

import org.example.application.Gaming.model.Card;
import org.example.application.Gaming.model.Trade;

public interface TradeRepository {
    Trade getTrade(String id);
    Trade addtrade(Card card);
    Trade deleteTrade(String id);
    Trade addOffer(Card card);
    Trade accept(Trade trade);
}
