CREATE TABLE IF NOT EXISTS `digital-game-store`.`store_branch` (
  `id` VARCHAR(40) NOT NULL,
  `name` VARCHAR(255) NOT NULL,
  `contact_mail` VARCHAR(100) NOT NULL,
  `contact_phone_number` VARCHAR(100) NOT NULL,
  `address` VARCHAR(255) NOT NULL,
  `deleted` BIT(1) DEFAULT b'1',
  `created_time` DATETIME NOT NULL,
  `created_by` VARCHAR(255) NOT NULL,
  `updated_time` DATETIME DEFAULT NULL,
  `updated_by` VARCHAR(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `STORE_BRANCH_IDX01` (`id`,`created_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;