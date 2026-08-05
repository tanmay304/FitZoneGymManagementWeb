package com.fitzone.dao.impl;

import com.fitzone.dao.AttendanceDAO;
import com.fitzone.database.DatabaseConnection;
import com.fitzone.model.Attendance;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AttendanceDAOImpl implements AttendanceDAO {
    private static final Logger logger = LoggerFactory.getLogger(AttendanceDAOImpl.class);

    @Override
    public List<Attendance> getDailyAttendance(Date date) {
        List<Attendance> list = new ArrayList<>();
        String sql = "SELECT a.*, CONCAT(u.fname, ' ', u.lname) AS member_name FROM tblattendance a " +
                     "LEFT JOIN tbluser u ON a.user_id = u.id " +
                     "WHERE a.attendance_date = ? ORDER BY a.id DESC";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, date != null ? date : new Date(System.currentTimeMillis()));
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Attendance att = new Attendance(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getTimestamp("check_in"),
                        rs.getTimestamp("check_out"),
                        rs.getDate("attendance_date"),
                        rs.getString("status"),
                        rs.getString("method")
                    );
                    att.setMemberName(rs.getString("member_name"));
                    list.add(att);
                }
            }
        } catch (SQLException e) {
            logger.error("Failed to fetch daily attendance", e);
        }
        return list;
    }

    @Override
    public boolean recordCheckIn(int userId, String method) {
        String sql = "INSERT INTO tblattendance (user_id, check_in, attendance_date, status, method) VALUES (?, NOW(), CURDATE(), 'Present', ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setString(2, method != null ? method : "Manual");
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error("Failed to record check in for user: " + userId, e);
            return false;
        }
    }

    @Override
    public boolean recordCheckOut(int attendanceId) {
        String sql = "UPDATE tblattendance SET check_out = NOW() WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, attendanceId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error("Failed to record check out", e);
            return false;
        }
    }
}
