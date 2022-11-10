package org.example.application.socialmedia.respository;

import org.example.application.socialmedia.model.Package;

import java.util.List;

public interface PackageRepository {

    List<Package> findAll();

    Package findById(String id);

    Package save(Package packages);


}
