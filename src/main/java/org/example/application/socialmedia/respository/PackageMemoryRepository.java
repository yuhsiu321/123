package org.example.application.socialmedia.respository;

import org.example.application.socialmedia.model.Package;

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
        if(!this.packages.contains(packages)){
            this.packages.add(packages);
        }
        return null;
    }

}
