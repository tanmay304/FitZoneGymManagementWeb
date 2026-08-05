package com.fitzone.service.impl;

import com.fitzone.dao.PackageDAO;
import com.fitzone.dao.impl.PackageDAOImpl;
import com.fitzone.model.GymPackage;
import com.fitzone.service.PackageService;
import java.util.List;

public class PackageServiceImpl implements PackageService {
    private final PackageDAO packageDAO;

    public PackageServiceImpl() {
        this.packageDAO = new PackageDAOImpl();
    }

    public PackageServiceImpl(PackageDAO packageDAO) {
        this.packageDAO = packageDAO;
    }

    @Override
    public List<GymPackage> getAllPackages() {
        return packageDAO.getAllPackages();
    }

    @Override
    public GymPackage getPackageById(int id) {
        return packageDAO.getPackageById(id);
    }

    @Override
    public boolean addPackage(GymPackage pkg) {
        return packageDAO.addPackage(pkg);
    }

    @Override
    public boolean updatePackage(GymPackage pkg) {
        return packageDAO.updatePackage(pkg);
    }

    @Override
    public boolean deletePackage(int id) {
        return packageDAO.deletePackage(id);
    }
}
