package com.fitzone.service;

import com.fitzone.model.GymPackage;
import java.util.List;

public interface PackageService {
    List<GymPackage> getAllPackages();
    GymPackage getPackageById(int id);
    boolean addPackage(GymPackage pkg);
    boolean updatePackage(GymPackage pkg);
    boolean deletePackage(int id);
}
