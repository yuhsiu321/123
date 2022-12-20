package org.example.application.Gaming.respository;

import org.example.application.Gaming.Database.Database;
import org.example.application.Gaming.model.Card;
import org.example.application.Gaming.model.Package;
import org.example.application.Gaming.model.User;
import org.postgresql.shaded.com.ongres.scram.common.bouncycastle.pbkdf2.Pack;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PackageMemoryRepository implements PackageRepository{

    private static PackageMemoryRepository instance;

    private UserMemoryRepository userService;
    private CardMemoryRepository cardService;

    private PackageMemoryRepository() {
        userService = UserMemoryRepository.getInstance();
        cardService = CardMemoryRepository.getInstance();
    }

    public static PackageMemoryRepository getInstance() {
        if (PackageMemoryRepository.instance == null) {
            PackageMemoryRepository.instance = new PackageMemoryRepository();
        }
        return PackageMemoryRepository.instance;
    }
    @Override
    public Package getPackage(int id) {
        try {
            Connection conn = Database.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT id, price FROM packages WHERE id=?;");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Package cardPackage = new Package();
                cardPackage.setId(rs.getInt("id"));
                cardPackage.setPrice(rs.getInt("price"));
                rs.close();
                ps.close();
                conn.close();

                return cardPackage;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Package getRandomPackage() {
        try {
            Connection conn = Database.getInstance().getConnection();
            Statement sm = conn.createStatement();
            ResultSet rs = sm.executeQuery("SELECT id, name, price FROM packages ORDER BY RANDOM() LIMIT 1;");

            if (rs.next()) {
                Package cardPackage = new Package();
                cardPackage.setId(rs.getInt("id"));
                cardPackage.setPrice(rs.getInt("price"));
                rs.close();
                sm.close();
                conn.close();

                return cardPackage;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Package> getPackages() {
        try {
            Connection conn = Database.getInstance().getConnection();
            Statement sm = conn.createStatement();
            ResultSet rs = sm.executeQuery("SELECT id,price FROM packages;");
            List<Package> packages = new ArrayList<>();
            while (rs.next()){
                Package cardPackage = new Package();
                cardPackage.setId(rs.getInt("id"));
                cardPackage.setPrice(rs.getInt("price"));
                packages.add(cardPackage);
            }
            rs.close();
            sm.close();
            conn.close();
            return packages;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Package addPackage(Package cardPackage) {
        try {
            Connection conn = Database.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("INSERT INTO packages(price) VALUES(?,?);", Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, cardPackage.getPrice());

            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                return null;
            }

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return this.getPackage(generatedKeys.getInt(1));
                }
            }
            ps.close();
            conn.close();
        } catch (SQLException ignored) {

        }
        return null;
    }

    @Override
    public boolean deletePackage(int id) {
        try {
            Connection conn = Database.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("DELETE FROM packages WHERE id = ?;");
            ps.setInt(1, id);

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

    @Override
    public boolean addPackageToUser(Package cardPackage, User user) {
        // Not enough coins
        if (user.getCoin() < cardPackage.getPrice()) return false;

        // Update coin balance
        user.setCoin(user.getCoin() - cardPackage.getPrice());

        // Save user
        userService.updateUser(user.getId(), user);

        for (Card card : cardService.getCardsForPackage(cardPackage)) {
            cardService.addCardToUser(card, user);
        }

        this.deletePackage(cardPackage.getId());
        return false;
    }
}
