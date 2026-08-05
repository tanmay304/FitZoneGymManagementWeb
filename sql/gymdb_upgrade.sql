-- FitZone Gym Management System Database Migration & Upgrade Script
-- Database Target: gymdb
-- Preserves all original PHP tables and data while extending schema for JavaFX Enterprise features.

USE `gymdb`;

-- Helper procedure to add column if it does not exist
DELIMITER //
CREATE PROCEDURE IF NOT EXISTS AddColumnIfNotExists(
    IN p_tablename VARCHAR(64),
    IN p_columnname VARCHAR(64),
    IN p_columndef TEXT
)
BEGIN
    IF NOT EXISTS (
        SELECT 1 FROM INFORMATION_SCHEMA.COLUMNS
        WHERE TABLE_SCHEMA = DATABASE()
          AND TABLE_NAME = p_tablename
          AND COLUMN_NAME = p_columnname
    ) THEN
        SET @sql = CONCAT('ALTER TABLE `', p_tablename, '` ADD COLUMN `', p_columnname, '` ', p_columndef);
        PREPARE stmt FROM @sql;
        EXECUTE stmt;
        DEALLOCATE PREPARE stmt;
    END IF;
END //
DELIMITER ;

-- 1. Upgrade tbladmin
CALL AddColumnIfNotExists('tbladmin', 'must_change_password', 'TINYINT(1) DEFAULT 0');

-- Ensure default admin exists with credentials admin@gmail.com / admin123 (MD5 legacy hash: 21232f297a57a5a743894a0e4a801fc3)
INSERT IGNORE INTO `tbladmin` (`id`, `name`, `email`, `mobile`, `password`, `must_change_password`, `create_date`) 
VALUES (1, 'Admin User', 'admin@gmail.com', '7887509373', '21232f297a57a5a743894a0e4a801fc3', 1, CURRENT_TIMESTAMP);

-- 2. Upgrade tbluser
CALL AddColumnIfNotExists('tbluser', 'image_path', 'VARCHAR(255) DEFAULT NULL');

-- 3. Upgrade tblbooking
CALL AddColumnIfNotExists('tblbooking', 'status', 'VARCHAR(30) DEFAULT \'Active\'');
CALL AddColumnIfNotExists('tblbooking', 'expiry_date', 'DATE DEFAULT NULL');

-- 4. Upgrade tblpayment
CALL AddColumnIfNotExists('tblpayment', 'status', 'VARCHAR(30) DEFAULT \'Paid\'');
CALL AddColumnIfNotExists('tblpayment', 'transaction_id', 'VARCHAR(100) DEFAULT NULL');
CALL AddColumnIfNotExists('tblpayment', 'receipt_no', 'VARCHAR(50) DEFAULT NULL');
CALL AddColumnIfNotExists('tblpayment', 'payment_reference', 'VARCHAR(100) DEFAULT NULL');

-- 5. Trainer Management Table
CREATE TABLE IF NOT EXISTS `tbltrainer` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(100) NOT NULL,
  `email` VARCHAR(100) NOT NULL,
  `mobile` VARCHAR(20) NOT NULL,
  `specialty` VARCHAR(100) DEFAULT 'General Fitness',
  `salary` DECIMAL(10,2) DEFAULT 0.00,
  `joining_date` DATE DEFAULT NULL,
  `status` VARCHAR(20) DEFAULT 'Active',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 6. Attendance Management Table
CREATE TABLE IF NOT EXISTS `tblattendance` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `user_id` INT(11) NOT NULL,
  `check_in` DATETIME DEFAULT CURRENT_TIMESTAMP,
  `check_out` DATETIME DEFAULT NULL,
  `attendance_date` DATE DEFAULT NULL,
  `status` VARCHAR(20) DEFAULT 'Present',
  `method` VARCHAR(30) DEFAULT 'Manual',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 7. Notification Table
CREATE TABLE IF NOT EXISTS `tblnotification` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(200) NOT NULL,
  `message` TEXT NOT NULL,
  `target_user_id` INT(11) DEFAULT NULL,
  `type` VARCHAR(50) DEFAULT 'System',
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  `is_read` TINYINT(1) DEFAULT 0,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 8. System Settings Table
CREATE TABLE IF NOT EXISTS `tblsettings` (
  `setting_key` VARCHAR(100) NOT NULL,
  `setting_value` TEXT,
  PRIMARY KEY (`setting_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Insert default system settings
INSERT IGNORE INTO `tblsettings` (`setting_key`, `setting_value`) VALUES
('gym_name', 'FitZone Fitness Club'),
('gym_address', '123 Fitness Ave, Health City'),
('gym_contact', '+91 9876543210'),
('gym_email', 'contact@fitzonegym.com'),
('currency_symbol', '₹'),
('theme', 'dark'),
('auto_backup_enabled', 'true');

-- Cleanup stored procedure
DROP PROCEDURE IF EXISTS AddColumnIfNotExists;
