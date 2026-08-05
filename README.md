# FitZone Gym Management System - Spring Boot 3 Web Application

![Java 21](https://img.shields.io/badge/Java-21-orange.svg)
![Spring Boot 3](https://img.shields.io/badge/Spring_Boot-3.2.4-green.svg)
![Thymeleaf](https://img.shields.io/badge/Thymeleaf-HTML5-blue.svg)
![MySQL](https://img.shields.io/badge/MySQL-gymdb-blue.svg)

FitZone Gym Management Web Application is a modern enterprise web application built using **Java 21 LTS**, **Spring Boot 3**, **Spring MVC**, **Spring Security**, **Thymeleaf**, **Bootstrap 5**, **Material Design CSS**, and **MySQL (`gymdb`)**. It reuses the entire business logic, entity model, and service layer from the original system while providing a responsive web interface running locally at `http://localhost:8080`.

---

## 📌 Features

- **Spring Security Authentication**: Login at `http://localhost:8080/login` with BCrypt password hashing and automatic MD5 migration.
- **Web Dashboard**: Live KPI cards, Chart.js monthly revenue trend line, package distribution pie chart, and recent payment logs.
- **Member Management**: Registration, real-time search, photo handling, and downloadable **PDF Member ID Cards** with embedded ZXing QR code.
- **Trainer Management**: Instructor directory, specialties, salary tracking, and status management.
- **Packages & Pricing**: Gym package management, pricing tiers, and duration definitions.
- **Bookings & Subscriptions**: Subscription management, membership renewals with automatic date extension, and cancellations.
- **Payments & Invoicing**: Cash, Card, and UPI payments with branded **iText PDF Receipts**.
- **Attendance Tracker**: Manual check-in, QR/Barcode scanner listener, and daily attendance logs.
- **Analytics & Exports**: Downloadable **Excel (.xlsx)** and **CSV** reports.
- **System Settings & Maintenance**: MySQL SQL dump backup creator & restore file runner.

---

## ⚙️ Quick Start

1. Ensure MySQL server is running and database `gymdb` is imported (`sql/gymdb_upgrade.sql`).
2. Run the application:
   ```bash
   mvn spring-boot:run
   ```
   Or launch the executable FAT JAR:
   ```bash
   java -jar target/FitZoneGymManagementWeb-1.0-SNAPSHOT.jar
   ```
3. Open browser at:
   **[http://localhost:8080](http://localhost:8080)**
4. Sign in credentials:
   - **Username**: `admin` or `admin@gmail.com`
   - **Password**: `admin123`
