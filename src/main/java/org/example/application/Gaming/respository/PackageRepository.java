package org.example.application.Gaming.respository;

import org.example.application.Gaming.model.Package;

import java.util.List;

public interface PackageRepository {

    List<Package> findAll();

    Package findById(String id);

    Package save(Package packages);


}
