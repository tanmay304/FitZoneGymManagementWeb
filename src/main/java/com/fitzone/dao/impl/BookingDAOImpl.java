package com.fitzone.dao.impl;

import com.fitzone.dao.BookingDAO;
import com.fitzone.database.DatabaseConnection;
import com.fitzone.model.Booking;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BookingDAOImpl implements BookingDAO {
    private static final Logger logger = LoggerFactory.getLogger(BookingDAOImpl.class);

    @Override
    public List<Booking> getAllBookings() {
        List<Booking> list = new ArrayList<>();
        String sql = "SELECT b.*, CONCAT(u.fname, ' ', u.lname) AS member_name, p.titlename AS package_name " +
                     "FROM tblbooking b " +
                     "LEFT JOIN tbluser u ON b.userid = u.id " +
                     "LEFT JOIN tbladdpackage p ON b.package_id = p.id " +
                     "ORDER BY b.id DESC";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Booking b = new Booking(
                    rs.getInt("id"),
                    rs.getString("package_id"),
                    rs.getString("userid"),
                    rs.getTimestamp("booking_date"),
                    rs.getString("payment"),
                    rs.getString("paymentType")
                );
                try {
                    b.setStatus(rs.getString("status"));
                    b.setExpiryDate(rs.getDate("expiry_date"));
                } catch (SQLException ignored) {}
                b.setMemberName(rs.getString("member_name"));
                b.setPackageName(rs.getString("package_name"));
                list.add(b);
            }
        } catch (SQLException e) {
            logger.error("Failed to fetch all bookings", e);
        }
        return list;
    }

    @Override
    public boolean addBooking(Booking booking) {
        String sql = "INSERT INTO tblbooking (package_id, userid, payment, paymentType, status, expiry_date) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, booking.getPackageId());
            stmt.setString(2, booking.getUserId());
            stmt.setString(3, booking.getPayment());
            stmt.setString(4, booking.getPaymentType());
            stmt.setString(5, booking.getStatus() != null ? booking.getStatus() : "Active");
            stmt.setDate(6, booking.getExpiryDate());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error("Failed to add booking", e);
            return false;
        }
    }

    @Override
    public boolean updateBookingStatus(int bookingId, String status) {
        String sql = "UPDATE tblbooking SET status = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, status);
            stmt.setInt(2, bookingId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error("Failed to update booking status", e);
            return false;
        }
    }

    @Override
    public boolean renewBooking(int bookingId, java.sql.Date newExpiryDate) {
        String sql = "UPDATE tblbooking SET expiry_date = ?, status = 'Active' WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, newExpiryDate);
            stmt.setInt(2, bookingId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error("Failed to renew booking", e);
            return false;
        }
    }

    @Override
    public boolean deleteBooking(int id) {
        String sql = "DELETE FROM tblbooking WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error("Failed to delete booking", e);
            return false;
        }
    }
}
