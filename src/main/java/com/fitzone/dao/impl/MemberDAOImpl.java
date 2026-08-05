package com.fitzone.dao.impl;

import com.fitzone.dao.MemberDAO;
import com.fitzone.database.DatabaseConnection;
import com.fitzone.model.Member;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MemberDAOImpl implements MemberDAO {
    private static final Logger logger = LoggerFactory.getLogger(MemberDAOImpl.class);

    @Override
    public List<Member> getAllMembers() {
        List<Member> list = new ArrayList<>();
        String sql = "SELECT * FROM tbluser ORDER BY id DESC";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                list.add(mapResultSetToMember(rs));
            }
        } catch (SQLException e) {
            logger.error("Failed to fetch all members", e);
        }
        return list;
    }

    @Override
    public List<Member> searchMembers(String keyword) {
        List<Member> list = new ArrayList<>();
        String sql = "SELECT * FROM tbluser WHERE fname LIKE ? OR lname LIKE ? OR email LIKE ? OR mobile LIKE ? ORDER BY id DESC";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            String pattern = "%" + keyword + "%";
            stmt.setString(1, pattern);
            stmt.setString(2, pattern);
            stmt.setString(3, pattern);
            stmt.setString(4, pattern);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    list.add(mapResultSetToMember(rs));
                }
            }
        } catch (SQLException e) {
            logger.error("Failed to search members", e);
        }
        return list;
    }

    @Override
    public Member getMemberById(int id) {
        String sql = "SELECT * FROM tbluser WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToMember(rs);
                }
            }
        } catch (SQLException e) {
            logger.error("Failed to get member by id: " + id, e);
        }
        return null;
    }

    @Override
    public Member getMemberByEmail(String email) {
        String sql = "SELECT * FROM tbluser WHERE email = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToMember(rs);
                }
            }
        } catch (SQLException e) {
            logger.error("Failed to get member by email: " + email, e);
        }
        return null;
    }

    @Override
    public Member getMemberByMobile(String mobile) {
        String sql = "SELECT * FROM tbluser WHERE mobile = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, mobile);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToMember(rs);
                }
            }
        } catch (SQLException e) {
            logger.error("Failed to get member by mobile: " + mobile, e);
        }
        return null;
    }

    @Override
    public boolean existsByEmail(String email, int excludeId) {
        String sql = "SELECT COUNT(*) FROM tbluser WHERE email = ? AND id != ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            stmt.setInt(2, excludeId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            logger.error("Error checking email duplicate", e);
        }
        return false;
    }

    @Override
    public boolean existsByMobile(String mobile, int excludeId) {
        String sql = "SELECT COUNT(*) FROM tbluser WHERE mobile = ? AND id != ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, mobile);
            stmt.setInt(2, excludeId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            logger.error("Error checking mobile duplicate", e);
        }
        return false;
    }

    @Override
    public boolean addMember(Member member) {
        String sql = "INSERT INTO tbluser (fname, lname, email, mobile, password, state, city, address, image_path) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, member.getFname());
            stmt.setString(2, member.getLname());
            stmt.setString(3, member.getEmail());
            stmt.setString(4, member.getMobile());
            stmt.setString(5, member.getPassword());
            stmt.setString(6, member.getState());
            stmt.setString(7, member.getCity());
            stmt.setString(8, member.getAddress());
            stmt.setString(9, member.getPhotoPath());
            int affected = stmt.executeUpdate();
            if (affected > 0) {
                try (ResultSet keys = stmt.getGeneratedKeys()) {
                    if (keys.next()) {
                        member.setId(keys.getInt(1));
                    }
                }
                return true;
            }
        } catch (SQLException e) {
            logger.error("Failed to add member", e);
        }
        return false;
    }

    @Override
    public boolean updateMember(Member member) {
        String sql = "UPDATE tbluser SET fname = ?, lname = ?, email = ?, mobile = ?, state = ?, city = ?, address = ?, image_path = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, member.getFname());
            stmt.setString(2, member.getLname());
            stmt.setString(3, member.getEmail());
            stmt.setString(4, member.getMobile());
            stmt.setString(5, member.getState());
            stmt.setString(6, member.getCity());
            stmt.setString(7, member.getAddress());
            stmt.setString(8, member.getPhotoPath());
            stmt.setInt(9, member.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error("Failed to update member", e);
            return false;
        }
    }

    @Override
    public boolean deleteMember(int id) {
        String sql = "DELETE FROM tbluser WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error("Failed to delete member", e);
            return false;
        }
    }

    @Override
    public int getTotalMembersCount() {
        String sql = "SELECT COUNT(*) FROM tbluser";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            logger.error("Failed to get total members count", e);
        }
        return 0;
    }

    private Member mapResultSetToMember(ResultSet rs) throws SQLException {
        Member m = new Member();
        m.setId(rs.getInt("id"));
        m.setFname(rs.getString("fname"));
        m.setLname(rs.getString("lname"));
        m.setEmail(rs.getString("email"));
        m.setMobile(rs.getString("mobile"));
        m.setPassword(rs.getString("password"));
        m.setState(rs.getString("state"));
        m.setCity(rs.getString("city"));
        m.setAddress(rs.getString("address"));
        try {
            m.setPhotoPath(rs.getString("image_path"));
        } catch (SQLException ignored) {}
        m.setCreateDate(rs.getTimestamp("create_date"));
        return m;
    }
}
