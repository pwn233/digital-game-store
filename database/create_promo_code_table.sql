CREATE TABLE IF NOT EXISTS `digital-game-store`.`promo_code` (
  `id` VARCHAR(40) NOT NULL,
  `store_branch_id` VARCHAR(40) NOT NULL,
  `promo_code` VARCHAR(30) NOT NULL,
  `start_date` DATE NOT NULL,
  `end_date` DATE NOT NULL,
  `deleted` BIT(1) DEFAULT b'1',
  `created_time` DATETIME NOT NULL,
  `created_by` VARCHAR(255) NOT NULL,
  `updated_time` DATETIME DEFAULT NULL,
  `updated_by` VARCHAR(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `PROMO_CODE_IDX01` (`store_branch_id`,`created_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;