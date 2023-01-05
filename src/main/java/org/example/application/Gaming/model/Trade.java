package org.example.application.Gaming.model;

public class Trade {
    private String Id;
    private String CardToTrade;
    private String Type;
    private Float MinimumDamage;


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
}
