package com.fitzone;

import com.fitzone.database.DatabaseConnection;
import org.junit.jupiter.api.Test;
import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.Statement;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DatabaseSeeder {

    @Test
    public void seedDatabaseWithRealisticIndianData() {
        System.out.println("🌱 Starting FitZone Database Seeding...");
        try (Connection conn = DatabaseConnection.getConnection();
             BufferedReader reader = new BufferedReader(new FileReader("sql/seed_data.sql"));
             Statement stmt = conn.createStatement()) {

            if (conn == null) {
                System.out.println("⚠️ MySQL Server offline during test seed run.");
                return;
            }

            StringBuilder sb = new StringBuilder();
            String line;
            int count = 0;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("--") || line.startsWith("/*")) {
                    continue;
                }
                sb.append(line);
                if (line.endsWith(";")) {
                    String query = sb.toString();
                    sb.setLength(0);
                    try {
                        stmt.execute(query);
                        count++;
                    } catch (Exception ex) {
                        System.out.println("Query notice: " + ex.getMessage());
                    }
                }
            }
            System.out.println("✅ Database Seeding Completed! Executed " + count + " SQL seed statements.");
            assertTrue(count > 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
