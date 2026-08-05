package com.fitzone.dao;

import com.fitzone.model.Admin;

public interface AdminDAO {
    Admin authenticate(String emailOrUser, String passwordInput);
    boolean upgradePasswordToBCrypt(int adminId, String plainPassword);
    boolean updatePassword(int adminId, String newPlainPassword);
    boolean updateProfile(Admin admin);
}
