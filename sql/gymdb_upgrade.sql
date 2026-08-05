-- FitZone Gym Management System Database Migration & Upgrade Script
-- Database Target: gymdb

USE `gymdb`;

-- 1. Upgrade tbladmin
ALTER TABLE `tbladmin` ADD COLUMN IF NOT EXISTS `must_change_password` TINYINT(1) DEFAULT 0;

-- Ensure default admin exists with credentials admin@gmail.com / admin123 (MD5 legacy hash: 21232f297a57a5a743894a0e4a801fc3)
INSERT IGNORE INTO `tbladmin` (`id`, `name`, `email`, `mobile`, `password`, `must_change_password`, `create_date`) 
VALUES (1, 'Admin User', 'admin@gmail.com', '7887509373', '21232f297a57a5a743894a0e4a801fc3', 1, CURRENT_TIMESTAMP);

-- 2. Upgrade tbluser
ALTER TABLE `tbluser` ADD COLUMN IF NOT EXISTS `image_path` VARCHAR(255) DEFAULT NULL;

-- 3. Upgrade tblpackage
ALTER TABLE `tblpackage` ADD COLUMN IF NOT EXISTS `category_id` INT(11) DEFAULT 1;
ALTER TABLE `tblpackage` ADD COLUMN IF NOT EXISTS `titlename` VARCHAR(100) DEFAULT NULL;
ALTER TABLE `tblpackage` ADD COLUMN IF NOT EXISTS `package_type` VARCHAR(50) DEFAULT '1';
ALTER TABLE `tblpackage` ADD COLUMN IF NOT EXISTS `package_duration` VARCHAR(50) DEFAULT NULL;
ALTER TABLE `tblpackage` ADD COLUMN IF NOT EXISTS `price` VARCHAR(50) DEFAULT NULL;
ALTER TABLE `tblpackage` ADD COLUMN IF NOT EXISTS `description` TEXT DEFAULT NULL;

-- 4. Upgrade tblbooking
ALTER TABLE `tblbooking` ADD COLUMN IF NOT EXISTS `user_id` INT(11) DEFAULT NULL;
ALTER TABLE `tblbooking` ADD COLUMN IF NOT EXISTS `package_id` INT(11) DEFAULT NULL;
ALTER TABLE `tblbooking` ADD COLUMN IF NOT EXISTS `payment_type` VARCHAR(45) DEFAULT NULL;
ALTER TABLE `tblbooking` ADD COLUMN IF NOT EXISTS `status` VARCHAR(30) DEFAULT 'Active';
ALTER TABLE `tblbooking` ADD COLUMN IF NOT EXISTS `expiry_date` DATE DEFAULT NULL;

-- 5. Upgrade tblpayment
ALTER TABLE `tblpayment` ADD COLUMN IF NOT EXISTS `booking_id` INT(11) DEFAULT NULL;
ALTER TABLE `tblpayment` ADD COLUMN IF NOT EXISTS `payment_type` VARCHAR(45) DEFAULT NULL;
ALTER TABLE `tblpayment` ADD COLUMN IF NOT EXISTS `payment_method` VARCHAR(50) DEFAULT 'Cash';
ALTER TABLE `tblpayment` ADD COLUMN IF NOT EXISTS `status` VARCHAR(30) DEFAULT 'Paid';
ALTER TABLE `tblpayment` ADD COLUMN IF NOT EXISTS `transaction_id` VARCHAR(100) DEFAULT NULL;
ALTER TABLE `tblpayment` ADD COLUMN IF NOT EXISTS `receipt_no` VARCHAR(50) DEFAULT NULL;
ALTER TABLE `tblpayment` ADD COLUMN IF NOT EXISTS `payment_reference` VARCHAR(100) DEFAULT NULL;

-- 6. Trainer Management Table
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

-- 7. Attendance Management Table
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

-- 8. Notification Table
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

-- 9. System Settings Table
CREATE TABLE IF NOT EXISTS `tblsettings` (
  `setting_key` VARCHAR(100) NOT NULL,
  `setting_value` TEXT,
  PRIMARY KEY (`setting_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 10. Standalone Members Table
CREATE TABLE IF NOT EXISTS `members` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `full_name` VARCHAR(100) NOT NULL,
  `email` VARCHAR(100) NOT NULL,
  `mobile` VARCHAR(20) NOT NULL,
  `city` VARCHAR(50) DEFAULT NULL,
  `state` VARCHAR(50) DEFAULT NULL,
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
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
