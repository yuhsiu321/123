package org.example.application.Gaming.model;

public class Trade {
    private String Id;

    private String card2_id;
    private String CardToTrade;
    private String Type;
    private Float MinimumDamage;
    private String tradeStarter;


    public Float getMinimumDamage() {
        return MinimumDamage;
    }

    public String getCardToTrade() {
        return CardToTrade;
    }

    public String getId() {
        return Id;
    }

    public String getType() {
        return Type;
    }


    public String getCard2_id() {
        return card2_id;
    }

    public String getTradeStarter() {
        return tradeStarter;
    }

    public void setId(String id) {
        Id = id;
    }

    public void setCardToTrade(String cardToTrade) {
        CardToTrade = cardToTrade;
    }

    public void setMinimumDamage(Float minimumDamage) {
        MinimumDamage = minimumDamage;
    }

    public void setType(String type) {
        Type = type;
    }


    public void setCard2_id(String card2_id) {
        this.card2_id = card2_id;
    }

    public void setTradeStarter(String tradeStarter) {
        this.tradeStarter = tradeStarter;
    }
}
