# Changelog

All notable changes to the **FitZone Gym Management System Web Application** are documented in this file.

## [1.0.0] - 2026-08-05

### Added
- **Spring Boot 3 Web Migration**: Migrated from JavaFX desktop application to Spring Boot 3, Spring MVC, Spring Security, and Thymeleaf.
- **Database System of Record (`gymdb`)**: Preserved all original MySQL tables and PHP records without data loss.
- **Firebase Cloud Integration**: Firebase Authentication & Firebase Storage (`fitzonegymmanagement.firebasestorage.app`) for member/trainer profile photos and documents.
- **Executive Web Dashboard**: Live KPIs, Chart.js revenue trend lines, package distribution pie chart, and recent payment logs.
- **Member Management**: CRUD, search, photo handling, duplicate email/mobile validation, and printable PDF Member QR Cards.
- **Trainer Directory**: Instructor CRUD, specialties, salary records, and attendance tracking.
- **Packages & Subscriptions**: Plan creation, renewals with auto-date extension, and cancellations.
- **Payments & Invoicing**: Cash, Card, UPI payments, UPI QR Code generator, and branded iText PDF Receipts.
- **Attendance Tracker**: Manual check-in, QR/Barcode scanner listener, and daily attendance logs.
- **Analytics Center**: One-click **Excel (.xlsx)** and **CSV** report downloads.
- **System Maintenance**: Database SQL dump backup download & restore file runner.
- **CI/CD Pipeline**: GitHub Actions workflow (`.github/workflows/maven.yml`).
- **GitHub Pages Site**: Project site published in `docs/index.html` at `https://tanmay304.github.io/FitZoneGymManagementWeb/`.
