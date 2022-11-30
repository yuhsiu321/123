package org.example.application.Gaming.respository;

import org.example.application.Gaming.Database.Database;
import org.example.application.Gaming.model.Package;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PackageMemoryRepository implements PackageRepository{

    private final List<Package> packages;

    public PackageMemoryRepository() {
        this.packages = new ArrayList<>();
    }

    @Override
    public List<Package> findAll() {
        return this.packages;
    }

    @Override
    public Package findById(String id) {
        for (Package packages: this.packages) {
            if(packages.getId().equals(id)){
                return packages;
            }
        }
        return null;
    }

    @Override
    public Package save(Package packages) {

        try {
            Connection conn = Database.getInstance().getConnection();
            Statement stmt1 = null;
            stmt1 = conn.createStatement();
            stmt1.execute(
                    """
                        CREATE TABLE IF NOT EXISTS packages (
                            id VARCHAR(255) PRIMARY KEY,
                            name VARCHAR(255) NOT NULL,
                            damage INT NOT NULL,
                            FOREIGN KEY (username) REFERENCES users(username) 
                        );
                        """
            );
            stmt1.close();
            PreparedStatement ps = conn.prepareStatement("INSERT INTO packages(id,name,damage,username) VALUES(?,?,?,?) ;");
            ps.setString(1, packages.getId());
            ps.setString(2,packages.getName());
            ps.setInt(3,packages.getDamage());
            //ps.setString(4,);
            ps.execute();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        /*if(!this.packages.contains(packages)){
            this.packages.add(packages);
        }*/
        return null;
    }

}
