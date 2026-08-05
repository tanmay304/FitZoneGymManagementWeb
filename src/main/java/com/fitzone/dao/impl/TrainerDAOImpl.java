package com.fitzone.dao.impl;

import com.fitzone.dao.TrainerDAO;
import com.fitzone.database.DatabaseConnection;
import com.fitzone.model.Trainer;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TrainerDAOImpl implements TrainerDAO {
    private static final Logger logger = LoggerFactory.getLogger(TrainerDAOImpl.class);

    @Override
    public List<Trainer> getAllTrainers() {
        List<Trainer> list = new ArrayList<>();
        String sql = "SELECT * FROM tbltrainer ORDER BY id DESC";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                list.add(mapResultSetToTrainer(rs));
            }
        } catch (SQLException e) {
            logger.error("Failed to fetch all trainers", e);
        }
        return list;
    }

    @Override
    public boolean addTrainer(Trainer trainer) {
        String sql = "INSERT INTO tbltrainer (name, email, mobile, specialty, salary, joining_date, status) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, trainer.getName());
            stmt.setString(2, trainer.getEmail());
            stmt.setString(3, trainer.getMobile());
            stmt.setString(4, trainer.getSpecialty());
            stmt.setDouble(5, trainer.getSalary());
            stmt.setDate(6, trainer.getJoiningDate());
            stmt.setString(7, trainer.getStatus());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error("Failed to add trainer", e);
            return false;
        }
    }

    @Override
    public boolean updateTrainer(Trainer trainer) {
        String sql = "UPDATE tbltrainer SET name = ?, email = ?, mobile = ?, specialty = ?, salary = ?, status = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, trainer.getName());
            stmt.setString(2, trainer.getEmail());
            stmt.setString(3, trainer.getMobile());
            stmt.setString(4, trainer.getSpecialty());
            stmt.setDouble(5, trainer.getSalary());
            stmt.setString(6, trainer.getStatus());
            stmt.setInt(7, trainer.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error("Failed to update trainer", e);
            return false;
        }
    }

    @Override
    public boolean deleteTrainer(int id) {
        String sql = "DELETE FROM tbltrainer WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error("Failed to delete trainer", e);
            return false;
        }
    }

    @Override
    public int getTrainerCount() {
        String sql = "SELECT COUNT(*) FROM tbltrainer";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            logger.error("Failed to get trainer count", e);
        }
        return 0;
    }

    private Trainer mapResultSetToTrainer(ResultSet rs) throws SQLException {
        return new Trainer(
            rs.getInt("id"),
            rs.getString("name"),
            rs.getString("email"),
            rs.getString("mobile"),
            rs.getString("specialty"),
            rs.getDouble("salary"),
            rs.getDate("joining_date"),
            rs.getString("status")
        );
    }
}
