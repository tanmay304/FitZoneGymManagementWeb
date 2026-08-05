package com.fitzone.database;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * DatabaseConnection provides thread-safe JDBC connections for MySQL/MariaDB.
 * Handles dual port fallback (3306 / 3307) and centralized connection configuration.
 */
public class DatabaseConnection {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseConnection.class);
    private static String urlPrimary = "jdbc:mysql://localhost:3306/gymdb?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static String urlFallback = "jdbc:mysql://localhost:3307/gymdb?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static String user = "root";
    private static String password = "";

    static {
        try (InputStream input = DatabaseConnection.class.getClassLoader().getResourceAsStream("application.properties")) {
            Properties prop = new Properties();
            if (input != null) {
                prop.load(input);
                urlPrimary = prop.getProperty("db.url", urlPrimary);
                user = prop.getProperty("db.user", user);
                password = prop.getProperty("db.password", password);
            }
            Class.forName("com.mysql.cj.jdbc.Driver");
            logger.info("MySQL JDBC Driver registered successfully.");
        } catch (Exception e) {
            logger.error("Failed to load database configuration or driver.", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        try {
            return DriverManager.getConnection(urlPrimary, user, password);
        } catch (SQLException e1) {
            logger.warn("Primary DB connection ({}) failed ({}), attempting fallback port 3307...", urlPrimary, e1.getMessage());
            try {
                return DriverManager.getConnection(urlFallback, user, password);
            } catch (SQLException e2) {
                logger.error("❌ Both primary (3306) and fallback (3307) MySQL database connections failed!", e2);
                throw e2;
            }
        }
    }

    public static boolean testConnection() {
        try (Connection conn = getConnection()) {
            return conn != null && !conn.isClosed();
        } catch (SQLException e) {
            logger.error("Database connection test failed: {}", e.getMessage());
            return false;
        }
    }
}
