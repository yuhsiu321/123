package org.example.application.Gaming.model;

public class BattleRound {
    private Integer id;
    private Card card1;
    private Card card2;
    private Card winnerCard;

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public Card getCard1() {
        return card1;
    }

    public Card getCard2() {
        return card2;
    }

    public Card getWinnerCard() {
        return winnerCard;
    }

    public void setCard1(Card card1) {
        this.card1 = card1;
    }

    public void setCard2(Card card2) {
        this.card2 = card2;
    }

    public void setWinnerCard(Card winnerCard) {
        this.winnerCard = winnerCard;
    }
}
