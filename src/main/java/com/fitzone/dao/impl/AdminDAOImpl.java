package com.fitzone.dao.impl;

import com.fitzone.dao.AdminDAO;
import com.fitzone.database.DatabaseConnection;
import com.fitzone.model.Admin;
import com.fitzone.util.PasswordUtil;
import java.sql.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AdminDAOImpl implements AdminDAO {
    private static final Logger logger = LoggerFactory.getLogger(AdminDAOImpl.class);

    @Override
    public Admin authenticate(String emailOrUser, String passwordInput) {
        String sql = "SELECT * FROM tbladmin WHERE email = ? OR name = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, emailOrUser);
            stmt.setString(2, emailOrUser);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("id");
                    String name = rs.getString("name");
                    String email = rs.getString("email");
                    String mobile = rs.getString("mobile");
                    String dbPassword = rs.getString("password");
                    boolean mustChangePassword = false;
                    try {
                        mustChangePassword = rs.getBoolean("must_change_password");
                    } catch (SQLException ignored) {}
                    Timestamp createDate = rs.getTimestamp("create_date");

                    boolean authenticated = false;
                    boolean needsUpgrade = false;

                    if (PasswordUtil.checkPasswordBCrypt(passwordInput, dbPassword)) {
                        authenticated = true;
                    } else if (PasswordUtil.checkPasswordMD5(passwordInput, dbPassword)) {
                        authenticated = true;
                        needsUpgrade = true;
                    }

                    if (authenticated) {
                        Admin admin = new Admin(id, name, email, mobile, dbPassword, mustChangePassword, createDate);
                        if (needsUpgrade) {
                            upgradePasswordToBCrypt(id, passwordInput);
                            admin.setPassword(PasswordUtil.hashPasswordBCrypt(passwordInput));
                        }
                        return admin;
                    }
                }
            }
        } catch (SQLException e) {
            logger.error("Error during admin authentication", e);
        }
        return null;
    }

    @Override
    public boolean upgradePasswordToBCrypt(int adminId, String plainPassword) {
        String bCryptHash = PasswordUtil.hashPasswordBCrypt(plainPassword);
        String sql = "UPDATE tbladmin SET password = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, bCryptHash);
            stmt.setInt(2, adminId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error("Failed to upgrade admin password to BCrypt", e);
            return false;
        }
    }

    @Override
    public boolean updatePassword(int adminId, String newPlainPassword) {
        String bCryptHash = PasswordUtil.hashPasswordBCrypt(newPlainPassword);
        String sql = "UPDATE tbladmin SET password = ?, must_change_password = 0 WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, bCryptHash);
            stmt.setInt(2, adminId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error("Failed to update admin password", e);
            return false;
        }
    }

    @Override
    public boolean updateProfile(Admin admin) {
        String sql = "UPDATE tbladmin SET name = ?, email = ?, mobile = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, admin.getName());
            stmt.setString(2, admin.getEmail());
            stmt.setString(3, admin.getMobile());
            stmt.setInt(4, admin.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error("Failed to update admin profile", e);
            return false;
        }
    }
}
