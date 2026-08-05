package com.fitzone.dao.impl;

import com.fitzone.dao.CategoryDAO;
import com.fitzone.database.DatabaseConnection;
import com.fitzone.model.Category;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CategoryDAOImpl implements CategoryDAO {
    private static final Logger logger = LoggerFactory.getLogger(CategoryDAOImpl.class);

    @Override
    public List<Category> getAllCategories() {
        List<Category> list = new ArrayList<>();
        String sql = "SELECT * FROM tblcategory ORDER BY id DESC";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                list.add(new Category(rs.getInt("id"), rs.getString("category_name"), rs.getString("status")));
            }
        } catch (SQLException e) {
            logger.error("Failed to fetch all categories", e);
        }
        return list;
    }

    @Override
    public boolean addCategory(Category category) {
        String sql = "INSERT INTO tblcategory (category_name, status) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, category.getCategoryName());
            stmt.setString(2, category.getStatus() != null ? category.getStatus() : "0");
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error("Failed to add category", e);
            return false;
        }
    }

    @Override
    public boolean updateCategory(Category category) {
        String sql = "UPDATE tblcategory SET category_name = ?, status = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, category.getCategoryName());
            stmt.setString(2, category.getStatus());
            stmt.setInt(3, category.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error("Failed to update category", e);
            return false;
        }
    }

    @Override
    public boolean deleteCategory(int id) {
        String sql = "DELETE FROM tblcategory WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error("Failed to delete category", e);
            return false;
        }
    }
}
