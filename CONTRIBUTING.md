# Contributing Guidelines

Thank you for contributing to the **FitZone Gym Management System Web Application**!

## Getting Started

1. **Fork the Repository**: `https://github.com/tanmay304/FitZoneGymManagementWeb`
2. **Setup Local Environment**:
   - Install JDK 21 LTS and Apache Maven 3.9+.
   - Import `sql/gymdb_upgrade.sql` into local MySQL database `gymdb`.
3. **Run Locally**:
   ```bash
   mvn spring-boot:run
   ```
4. **Code Standards**:
   - Follow standard Java & Spring Boot conventions.
   - Maintain strict separation of concerns (Controllers, Services, Repositories, Entities).
   - Ensure all unit tests pass before submitting a Pull Request.
