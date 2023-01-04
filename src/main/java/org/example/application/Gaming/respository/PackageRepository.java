package org.example.application.Gaming.respository;

import org.example.application.Gaming.model.Package;
import org.example.application.Gaming.model.User;
import org.postgresql.shaded.com.ongres.scram.common.bouncycastle.pbkdf2.Pack;

import java.util.List;

public interface PackageRepository {
    Package getPackage(int id);

    Package getPackageforUser();

    List<Package> getPackages();

    Package addPackage();

    boolean deletePackage(int id);

    boolean addPackageToUser(Package cardPackage, User user);
}
