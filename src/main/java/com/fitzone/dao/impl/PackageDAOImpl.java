package com.fitzone.dao.impl;

import com.fitzone.dao.PackageDAO;
import com.fitzone.database.DatabaseConnection;
import com.fitzone.model.GymPackage;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PackageDAOImpl implements PackageDAO {
    private static final Logger logger = LoggerFactory.getLogger(PackageDAOImpl.class);

    @Override
    public List<GymPackage> getAllPackages() {
        List<GymPackage> list = new ArrayList<>();
        String sql = "SELECT * FROM tbladdpackage ORDER BY id DESC";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                list.add(mapResultSetToPackage(rs));
            }
        } catch (SQLException e) {
            logger.error("Failed to fetch all packages", e);
        }
        return list;
    }

    @Override
    public GymPackage getPackageById(int id) {
        String sql = "SELECT * FROM tbladdpackage WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) return mapResultSetToPackage(rs);
            }
        } catch (SQLException e) {
            logger.error("Failed to get package by id: " + id, e);
        }
        return null;
    }

    @Override
    public boolean addPackage(GymPackage pkg) {
        String sql = "INSERT INTO tbladdpackage (category, titlename, PackageType, PackageDuratiobn, Price, uploadphoto, Description) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, pkg.getCategory());
            stmt.setString(2, pkg.getTitlename());
            stmt.setString(3, pkg.getPackageType());
            stmt.setString(4, pkg.getPackageDuration());
            stmt.setString(5, pkg.getPrice());
            stmt.setString(6, pkg.getUploadphoto());
            stmt.setString(7, pkg.getDescription());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error("Failed to add package", e);
            return false;
        }
    }

    @Override
    public boolean updatePackage(GymPackage pkg) {
        String sql = "UPDATE tbladdpackage SET category = ?, titlename = ?, PackageType = ?, PackageDuratiobn = ?, Price = ?, Description = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, pkg.getCategory());
            stmt.setString(2, pkg.getTitlename());
            stmt.setString(3, pkg.getPackageType());
            stmt.setString(4, pkg.getPackageDuration());
            stmt.setString(5, pkg.getPrice());
            stmt.setString(6, pkg.getDescription());
            stmt.setInt(7, pkg.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error("Failed to update package", e);
            return false;
        }
    }

    @Override
    public boolean deletePackage(int id) {
        String sql = "DELETE FROM tbladdpackage WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error("Failed to delete package", e);
            return false;
        }
    }

    private GymPackage mapResultSetToPackage(ResultSet rs) throws SQLException {
        return new GymPackage(
            rs.getInt("id"),
            rs.getString("category"),
            rs.getString("titlename"),
            rs.getString("PackageType"),
            rs.getString("PackageDuratiobn"),
            rs.getString("Price"),
            rs.getString("uploadphoto"),
            rs.getString("Description"),
            rs.getTimestamp("create_date")
        );
    }
}
