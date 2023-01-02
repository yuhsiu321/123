package org.example.application.Gaming.respository;

import org.example.application.Gaming.model.Battle;
import org.example.application.Gaming.model.Card;
import org.example.application.Gaming.model.User;

public interface BattleRepository {
    Battle createOrAddUserToBattle(User user);

    Battle getBattle(int id);

    Battle addBattle();

    boolean addUserToBattle(User user, Battle battle);

    boolean addBattleRound(Battle battle, Card card1, Card card2, Card winnerCard);

    boolean setWinnerForBattle(User winner, Battle battle);

    Battle waitForBattleToFinish(Battle battle);

    boolean battle(Battle battle);
}
