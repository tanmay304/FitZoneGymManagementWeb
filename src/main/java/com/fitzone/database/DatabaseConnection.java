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
 * Supports Render cloud environment variables (DB_URL, DB_USERNAME, DB_PASSWORD)
 * with robust local development fallbacks (127.0.0.1:3307 / 3306).
 */
public class DatabaseConnection {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseConnection.class);

    private static String dbUrl;
    private static String dbUser;
    private static String dbPassword;

    private static final String DEFAULT_LOCAL_3307 = "jdbc:mysql://127.0.0.1:3307/gymdb?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String DEFAULT_LOCAL_3306 = "jdbc:mysql://127.0.0.1:3306/gymdb?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";

    static {
        // Load application.properties file
        Properties prop = new Properties();
        try (InputStream input = DatabaseConnection.class.getClassLoader().getResourceAsStream("application.properties")) {
            if (input != null) {
                prop.load(input);
            }
        } catch (Exception e) {
            logger.warn("Could not load application.properties file: {}", e.getMessage());
        }

        // Resolve DB_URL (Env Var > System Prop > Config Prop > Fallback)
        dbUrl = resolveProperty("DB_URL", "db.url", prop, DEFAULT_LOCAL_3307);
        dbUser = resolveProperty("DB_USERNAME", "db.user", prop, "root");
        dbPassword = resolveProperty("DB_PASSWORD", "db.password", prop, "root");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            logger.info("MySQL JDBC Driver registered successfully. Initialized Target URL: {}", sanitizeUrl(dbUrl));
        } catch (Exception e) {
            logger.error("Failed to load com.mysql.cj.jdbc.Driver JDBC driver.", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        try {
            return DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        } catch (SQLException e1) {
            logger.warn("Primary DB connection attempt ({}) failed: SQLState={}, ErrorCode={}, Message={}",
                    sanitizeUrl(dbUrl), e1.getSQLState(), e1.getErrorCode(), e1.getMessage());

            // Attempt local fallbacks (3307 <-> 3306)
            if (dbUrl.contains("3307")) {
                logger.info("Attempting local fallback connection on port 3306...");
                try {
                    return DriverManager.getConnection(DEFAULT_LOCAL_3306, dbUser, dbPassword);
                } catch (SQLException e2) {
                    try {
                        return DriverManager.getConnection(DEFAULT_LOCAL_3306, "root", "");
                    } catch (SQLException ignored) {}
                }
            } else if (dbUrl.contains("3306")) {
                logger.info("Attempting local fallback connection on port 3307...");
                try {
                    return DriverManager.getConnection(DEFAULT_LOCAL_3307, "root", "");
                } catch (SQLException ignored) {}
            }

            logger.error("❌ Database Connection Failed! Target: {}, User: {}, SQLState: {}, ErrorCode: {}, Exception: {}",
                    sanitizeUrl(dbUrl), dbUser, e1.getSQLState(), e1.getErrorCode(), e1.getMessage(), e1);
            throw e1;
        }
    }

    public static boolean testConnection() {
        try (Connection conn = getConnection()) {
            return conn != null && !conn.isClosed();
        } catch (SQLException e) {
            logger.error("Database connection test failed: SQLState={}, ErrorCode={}, Message={}",
                    e.getSQLState(), e.getErrorCode(), e.getMessage());
            return false;
        }
    }

    private static String resolveProperty(String envKey, String propKey, Properties prop, String fallbackDefault) {
        String val = System.getenv(envKey);
        if (val != null && !val.trim().isEmpty()) return val.trim();

        val = System.getProperty(propKey);
        if (val != null && !val.trim().isEmpty()) return val.trim();

        val = prop.getProperty(propKey);
        if (val != null && !val.trim().isEmpty()) {
            // Strip Spring syntax ${VAR:default} if present
            if (val.startsWith("${") && val.endsWith("}")) {
                int colonIndex = val.indexOf(":");
                if (colonIndex != -1) {
                    val = val.substring(colonIndex + 1, val.length() - 1);
                } else {
                    val = fallbackDefault;
                }
            }
            return val.trim();
        }
        return fallbackDefault;
    }

    private static String sanitizeUrl(String url) {
        if (url == null) return "null";
        int pwdIndex = url.indexOf("password=");
        if (pwdIndex != -1) {
            int ampersand = url.indexOf("&", pwdIndex);
            if (ampersand != -1) {
                return url.substring(0, pwdIndex) + "password=****" + url.substring(ampersand);
            } else {
                return url.substring(0, pwdIndex) + "password=****";
            }
        }
        return url;
    }
}
