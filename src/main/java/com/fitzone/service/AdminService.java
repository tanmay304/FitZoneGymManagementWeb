package com.fitzone.service;

import com.fitzone.model.Admin;

public interface AdminService {
    Admin authenticate(String emailOrUser, String passwordInput);
    boolean updatePassword(int adminId, String newPlainPassword);
    boolean updateProfile(Admin admin);
}
