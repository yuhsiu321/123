package org.example.application.Gaming.respository;

import org.example.application.Gaming.Database.Database;
import org.example.application.Gaming.model.Battle;
import org.example.application.Gaming.model.BattleRound;
import org.example.application.Gaming.model.Card;
import org.example.application.Gaming.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BattleMemoryRepository implements BattleRepository{

    private static BattleMemoryRepository instance;
    private UserMemoryRepository userMemoryRepository;
    private CardMemoryRepository cardMemoryRepository;
    private DeckMemoryRepository deckMemoryRepository;

    public BattleMemoryRepository(){
        userMemoryRepository = UserMemoryRepository.getInstance();
        cardMemoryRepository = CardMemoryRepository.getInstance();
        deckMemoryRepository = DeckMemoryRepository.getInstance();
    }

    public static BattleMemoryRepository getInstance() {
        if (BattleMemoryRepository.instance == null) {
            BattleMemoryRepository.instance = new BattleMemoryRepository();
        }
        return BattleMemoryRepository.instance;
    }


    @Override
    public Battle createOrAddUserToBattle(User user) {
        try {
            Connection conn = Database.getInstance().getConnection();
            Statement sm = conn.createStatement();
            ResultSet rs = sm.executeQuery("SELECT id FROM battles WHERE p1 IS NULL OR p2 IS NULL LIMIT 1;");

            Battle battle;
            if (rs.next()) {
                // Get existing battle
                battle = (Battle) this.getBattle(rs.getInt(1));
            } else {
                // Create new battle
                battle = (Battle) this.addBattle();
            }

            // Now add user to battle
            if (this.addUserToBattle(user, battle)) {
                return this.getBattle(battle.getId());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Battle getBattle(int id) {
        try {
            Connection conn = Database.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT id, p1, p2, winner, finished FROM battles WHERE id=?;");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            List<BattleRound> battleRounds = new ArrayList<>();

            if (rs.next()) {

                int battleId = rs.getInt(1);

                User playerA = (User) userMemoryRepository.getUser(rs.getInt(2));
                User playerB = (User) userMemoryRepository.getUser(rs.getInt(3));
                User winner = (User) userMemoryRepository.getUser(rs.getInt(4));

                PreparedStatement ps2 = conn.prepareStatement("SELECT id, card_1, card_2, winner_card FROM battle_rounds WHERE battle_id=?;");
                ps2.setInt(1, battleId);
                ResultSet rs2 = ps2.executeQuery();

                while (rs2.next()) {
                    Card cardA = cardMemoryRepository.getCard(rs2.getString(2));
                    Card cardB = cardMemoryRepository.getCard(rs2.getString(3));
                    Card winnerCard = cardMemoryRepository.getCard(rs2.getString(4));
                    BattleRound battleRound = new BattleRound();
                    battleRound.setId(rs2.getInt(1));
                    battleRound.setCard1(cardA);
                    battleRound.setCard2(cardB);
                    battleRound.setWinnerCard(winnerCard);
                    battleRounds.add(battleRound);
                }
                Battle battle = new Battle();
                battle.setId(battleId);
                battle.setP1(playerA);
                battle.setP2(playerB);
                battle.setWinner(winner);
                battle.setBattleRounds(battleRounds);
                battle.setFinished(rs.getBoolean(5));

                rs2.close();
                ps2.close();
                rs.close();
                ps.close();
                conn.close();

                return battle;
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
    public Battle addBattle() {
        try {
            Connection conn = Database.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("INSERT INTO battles VALUES(DEFAULT);", Statement.RETURN_GENERATED_KEYS);

            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                return null;
            }

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return this.getBattle(generatedKeys.getInt(1));
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
    public boolean addUserToBattle(User user, Battle battle) {
        Battle battle1 = (Battle) this.getBattle(battle.getId());

        try {
            Connection conn = Database.getInstance().getConnection();
            int affectedRows;

            if (battle1.getP1() == null) {
                // Set user as playerA
                PreparedStatement ps = conn.prepareStatement("UPDATE battles SET p1 = ? WHERE id = ?;");
                ps.setInt(1, user.getId());
                ps.setInt(2, battle1.getId());
                affectedRows = ps.executeUpdate();
                ps.close();
            } else if (battle1.getP2() == null) {
                // Set user as playerB
                PreparedStatement ps = conn.prepareStatement("UPDATE battles SET p2 = ? WHERE id = ?;");
                ps.setInt(1, user.getId());
                ps.setInt(2, battle1.getId());
                affectedRows = ps.executeUpdate();
                ps.close();
            } else {
                conn.close();
                return false;
            }

            conn.close();

            if (affectedRows > 0) {
                battle1 = (Battle) this.getBattle(battle.getId());
                // Check if the battle is full now
                if (battle1.getP1() != null && battle1.getP2() != null) {
                    // Now start the battle
                    this.battle(battle1);
                }
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean addBattleRound(Battle battle, Card card1, Card card2, Card winnerCard) {
        try {
            Connection conn = Database.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("INSERT INTO battle_rounds(battle_id, card_1, card_2, winner_card) VALUES(?,?,?,?);", Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, battle.getId());
            ps.setString(2, card1.getId());
            ps.setString(3, card2.getId());

            if (winnerCard != null) {
                ps.setString(4, winnerCard.getId());
            } else {
                ps.setNull(4, java.sql.Types.NULL);
            }

            int affectedRows = ps.executeUpdate();

            ps.close();
            conn.close();

            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean setWinnerForBattle(User winner, Battle battle) {
        try {
            Connection conn = Database.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("UPDATE battles SET winner = ?, finished = TRUE WHERE id = ?;");
            ps.setInt(2, battle.getId());

            if (winner != null) {
                // Update winner
                ps.setInt(1, winner.getId());
            } else {
                // It's a draw.
                //ps.setNull(1, java.sql.Types.NULL);
                ps.setInt(1,0);
            }

            int affectedRows = ps.executeUpdate();

            ps.close();
            conn.close();

            return affectedRows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Battle waitForBattleToFinish(Battle battle) {
        Battle currentBattle;
        for (int i = 0; i < 60; i++) {
            currentBattle = (Battle) this.getBattle(battle.getId());
            if (currentBattle.isFinished()) {
                return currentBattle;
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public boolean battle(Battle battle) {
        User p1 = (User) battle.getP1();
        User p2 = (User) battle.getP2();
        User winner = null;

        ArrayList<Card> deckA = (ArrayList<Card>) deckMemoryRepository.getDeck(p1);
        ArrayList<Card> deckB = (ArrayList<Card>) deckMemoryRepository.getDeck(p2);

        // Check if decks are complete
        if (deckA.size() != 4 || deckB.size() != 4) {
            return false;
        }

        Card cardA;
        Card cardB;
        Card winnerCard;

        System.out.println(p1.getUsername() + " vs. " + p2.getUsername());

        for (int i = 0; i < 100; i++) {
            // Check for winners
            if (deckA.size() == 0) {
                // Deck A is empty, therefore player B won.
                winner = p2;
                // Update stats
                userMemoryRepository.addStatForUser(p2, 1);
                userMemoryRepository.addStatForUser(p1, -1);
                // Update elo
                userMemoryRepository.updateEloForPlayers(p1, p2, -5, 3);
                System.out.println("Player B won.");
                break;
            } else if (deckB.size() == 0) {
                // Deck B is empty, therefore player A won.
                winner = p1;
                // Update stats
                userMemoryRepository.addStatForUser(p1, 1);
                userMemoryRepository.addStatForUser(p2, -1);
                // Update elo
                userMemoryRepository.updateEloForPlayers(p1, p2, 3, -5);
                System.out.println("Player A won.");
                break;
            }

            cardA = deckA.get(new Random().nextInt(deckA.size()));
            cardB = deckB.get(new Random().nextInt(deckB.size()));
            winnerCard = null;

            if (cardA.winsAgainst(cardB) || cardA.calculateDamage(cardB) > cardB.calculateDamage(cardA)) {
                // Player A wins this round, and gets cardB
                winnerCard = cardA;
                deckB.remove(cardB);
                deckA.add(cardB);
            } else if (cardB.getDamage() > cardA.getDamage()) {
                // Player B wins this round, and gets cardA
                winnerCard = cardB;
                deckA.remove(cardA);
                deckB.add(cardA);
            }

            if (winnerCard != null) {
                System.out.println("Winner: " + winnerCard.getName() + "(" + winnerCard.getDamage() + ")");
            }

            this.addBattleRound(battle, cardA, cardB, winnerCard);
        }

        // Transfer cards from current decks to users
        for (Card card : deckA) {
            cardMemoryRepository.addCardToUser(card, p1);
        }
        for (Card card : deckB) {
            cardMemoryRepository.addCardToUser(card, p2);
        }

        // Clear deck
        deckMemoryRepository.clearDeck(p1);
        deckMemoryRepository.clearDeck(p2);

        // Update stats, tie
        if (winner == null) {
            userMemoryRepository.addStatForUser(p1, 0);
            userMemoryRepository.addStatForUser(p2, 0);
            userMemoryRepository.updateEloForPlayers(p1, p2, 0, 0);
        }

        return this.setWinnerForBattle(winner, battle);
    }
}
