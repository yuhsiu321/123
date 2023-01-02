package org.example.application.Gaming.model;

import java.util.List;

public class Battle {
    private Integer id;
    private boolean finished;
    private User p1;
    private User p2;
    private User winner;

    List<BattleRound> battleRounds;

    public Integer getId() {
        return id;
    }

    public boolean isFinished() {
        return finished;
    }

    public User getP1() {
        return p1;
    }

    public User getP2() {
        return p2;
    }

    public User getWinner() {
        return winner;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public void setP1(User p1) {
        this.p1 = p1;
    }

    public void setP2(User p2) {
        this.p2 = p2;
    }

    public void setWinner(User winner) {
        this.winner = winner;
    }

    public List<BattleRound> getBattleRounds() {
        return battleRounds;
    }

    public void setBattleRounds(List<BattleRound> battleRounds) {
        this.battleRounds = battleRounds;
    }
}
