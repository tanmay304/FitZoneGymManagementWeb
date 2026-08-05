package com.fitzone.dao.impl;

import com.fitzone.dao.NotificationDAO;
import com.fitzone.database.DatabaseConnection;
import com.fitzone.model.Notification;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NotificationDAOImpl implements NotificationDAO {
    private static final Logger logger = LoggerFactory.getLogger(NotificationDAOImpl.class);

    @Override
    public List<Notification> getAllNotifications() {
        List<Notification> list = new ArrayList<>();
        String sql = "SELECT * FROM tblnotification ORDER BY id DESC LIMIT 50";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                list.add(new Notification(
                    rs.getInt("id"),
                    rs.getString("title"),
                    rs.getString("message"),
                    rs.getObject("target_user_id") != null ? rs.getInt("target_user_id") : null,
                    rs.getString("type"),
                    rs.getTimestamp("created_at"),
                    rs.getBoolean("is_read")
                ));
            }
        } catch (SQLException e) {
            logger.error("Failed to fetch notifications", e);
        }
        return list;
    }

    @Override
    public boolean addNotification(Notification n) {
        String sql = "INSERT INTO tblnotification (title, message, target_user_id, type) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, n.getTitle());
            stmt.setString(2, n.getMessage());
            if (n.getTargetUserId() != null) {
                stmt.setInt(3, n.getTargetUserId());
            } else {
                stmt.setNull(3, Types.INTEGER);
            }
            stmt.setString(4, n.getType());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error("Failed to add notification", e);
            return false;
        }
    }

    @Override
    public boolean markAsRead(int id) {
        String sql = "UPDATE tblnotification SET is_read = 1 WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error("Failed to mark notification as read", e);
            return false;
        }
    }
}
