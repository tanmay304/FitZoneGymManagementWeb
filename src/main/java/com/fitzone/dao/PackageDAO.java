package com.fitzone.dao;

import com.fitzone.model.GymPackage;
import java.util.List;

public interface PackageDAO {
    List<GymPackage> getAllPackages();
    GymPackage getPackageById(int id);
    boolean addPackage(GymPackage pkg);
    boolean updatePackage(GymPackage pkg);
    boolean deletePackage(int id);
}
