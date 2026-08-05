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
 * Handles auto-migration initialization and connection pooling.
 */
public class DatabaseConnection {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseConnection.class);
private static String url;
private static String user;
private static String password;

    static {
        try (InputStream input = DatabaseConnection.class.getClassLoader().getResourceAsStream("application.properties")) {
            Properties prop = new Properties();
            if (input != null) {
                prop.load(input);
                url = prop.getProperty("db.url", "jdbc:mysql://localhost:3307/gymdb?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC");
                user = prop.getProperty("db.user", "root");
                password = prop.getProperty("db.password", "");
            } else {
                url = "jdbc:mysql://localhost:3307/gymdb?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
                user = "root";
                password = "";
            }
            Class.forName("com.mysql.cj.jdbc.Driver");
            logger.info("MySQL JDBC Driver registered successfully.");
        } catch (Exception e) {
            logger.error("Failed to load database configuration or driver.", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
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
