package com.fitzone.dao.impl;

import com.fitzone.dao.SettingDAO;
import com.fitzone.database.DatabaseConnection;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SettingDAOImpl implements SettingDAO {
    private static final Logger logger = LoggerFactory.getLogger(SettingDAOImpl.class);

    @Override
    public Map<String, String> getAllSettings() {
        Map<String, String> map = new HashMap<>();
        String sql = "SELECT * FROM tblsettings";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                map.put(rs.getString("setting_key"), rs.getString("setting_value"));
            }
        } catch (SQLException e) {
            logger.error("Failed to fetch settings", e);
        }
        return map;
    }

    @Override
    public String getSetting(String key, String defaultValue) {
        String sql = "SELECT setting_value FROM tblsettings WHERE setting_key = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, key);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) return rs.getString("setting_value");
            }
        } catch (SQLException e) {
            logger.error("Failed to fetch setting for key: " + key, e);
        }
        return defaultValue;
    }

    @Override
    public boolean saveSetting(String key, String value) {
        String sql = "INSERT INTO tblsettings (setting_key, setting_value) VALUES (?, ?) ON DUPLICATE KEY UPDATE setting_value = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, key);
            stmt.setString(2, value);
            stmt.setString(3, value);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error("Failed to save setting key: " + key, e);
            return false;
        }
    }
}
