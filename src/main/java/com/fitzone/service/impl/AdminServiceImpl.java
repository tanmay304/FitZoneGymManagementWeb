package com.fitzone.service.impl;

import com.fitzone.dao.AdminDAO;
import com.fitzone.dao.impl.AdminDAOImpl;
import com.fitzone.model.Admin;
import com.fitzone.service.AdminService;

public class AdminServiceImpl implements AdminService {
    private final AdminDAO adminDAO;

    public AdminServiceImpl() {
        this.adminDAO = new AdminDAOImpl();
    }

    public AdminServiceImpl(AdminDAO adminDAO) {
        this.adminDAO = adminDAO;
    }

    @Override
    public Admin authenticate(String emailOrUser, String passwordInput) {
        return adminDAO.authenticate(emailOrUser, passwordInput);
    }

    @Override
    public boolean updatePassword(int adminId, String newPlainPassword) {
        return adminDAO.updatePassword(adminId, newPlainPassword);
    }

    @Override
    public boolean updateProfile(Admin admin) {
        return adminDAO.updateProfile(admin);
    }
}
