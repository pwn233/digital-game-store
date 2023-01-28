CREATE TABLE IF NOT EXISTS `digital-game-store`.`product` (
  `id` VARCHAR(40) NOT NULL,
  `store_branch_id` VARCHAR(40) NOT NULL,
  `name` VARCHAR(255) NOT NULL,
  `price` DOUBLE NOT NULL,
  `description` VARCHAR(255) DEFAULT NULL,
  `category` VARCHAR(40) DEFAULT NULL,
  `brand` VARCHAR (40) DEFAULT NULL,
  `barcode` VARCHAR(40) DEFAULT NULL,
  `deleted` BIT(1) DEFAULT b'1',
  `created_time` DATETIME NOT NULL,
  `created_by` VARCHAR(255) NOT NULL,
  `updated_time` DATETIME DEFAULT NULL,
  `updated_by` VARCHAR(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
    INDEX `PRODUCT_IDX01` (`id`,`created_time`)
  ) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;