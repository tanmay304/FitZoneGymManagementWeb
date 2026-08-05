# FitZone Gym Management System - Spring Boot Enterprise Web Application

![Java 21](https://img.shields.io/badge/Java-21-orange.svg)
![Spring Boot 3](https://img.shields.io/badge/Spring_Boot-3.2.4-green.svg)
![Thymeleaf](https://img.shields.io/badge/Thymeleaf-HTML5-blue.svg)
![MySQL](https://img.shields.io/badge/MySQL-gymdb-blue.svg)
![Live App](https://img.shields.io/badge/Live_App-Render-brightgreen.svg)

FitZone Gym Management Web Application is a full-stack enterprise web application built using **Java 21 LTS**, **Spring Boot 3**, **Spring MVC**, **Spring Security**, **Thymeleaf**, **Bootstrap 5**, **Material Design CSS**, **MySQL (`gymdb`)**, **Firebase Auth**, and **Firebase Storage**.

---

## 🌐 Live Application & Production Links

- 🚀 **Live Production Application**: **[https://fitzonegymmanagementweb.onrender.com/](https://fitzonegymmanagementweb.onrender.com/)**
- 📚 **GitHub Pages Documentation Site**: **[https://tanmay304.github.io/FitZoneGymManagementWeb/](https://tanmay304.github.io/FitZoneGymManagementWeb/)**
- 🐙 **GitHub Repository**: **[https://github.com/tanmay304/FitZoneGymManagementWeb](https://github.com/tanmay304/FitZoneGymManagementWeb)**
- 🔥 **Firebase Project**: `fitzonegymmanagement`

---

## 📌 Core Features

- **Spring Security Authentication**: Web sign-in with BCrypt password hashing and automatic MD5 migration support.
- **Executive Dashboard**: Live KPIs (50 Members, 40 Active, 8 Expired, 2 Pending Renewal, 2 Trainers), Chart.js monthly revenue trend line (₹45,000 - ₹1,80,000), package distribution pie chart, and payment logs.
- **Member Management**: Registration, real-time search, photo handling, and downloadable **PDF Member ID Cards** with embedded ZXing QR code.
- **Trainer Management**: Instructor directory (Rahul Patil & Priya Sharma), specialties, salary tracking, and status management.
- **Packages & Pricing**: 10 membership packages (Basic, Standard, Premium, Quarterly, Half-Yearly, Annual, PT, Student, Couple, Senior Citizen).
- **Bookings & Subscriptions**: Subscription management, membership renewals with automatic date extension, and cancellations.
- **Payments & Invoicing**: Cash, Card, and UPI payments (**PhonePe**, **Google Pay**, **Paytm**, **BHIM**) with branded **iText PDF Receipts**.
- **Attendance Tracker**: Manual check-in, QR/Barcode scanner listener, and 60 days of daily attendance logs.
- **Analytics & Exports**: Downloadable **Excel (.xlsx)** and **CSV** reports via Apache POI.
- **System Settings & Maintenance**: MySQL SQL dump backup creator & restore file runner.
- **Docker Containerization**: Dockerfile & `.dockerignore` for cloud container hosting.

---

## ⚙️ Quick Start (Local Run)

1. Ensure MySQL server is running and database `gymdb` is imported (`sql/gymdb_upgrade.sql` & `sql/seed_data.sql`).
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
