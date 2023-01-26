CREATE TABLE IF NOT EXISTS `digital-game-store`.`promo_code_product` (
  `promo_code_id` VARCHAR(40) NOT NULL,
  `product_id` VARCHAR(40) NOT NULL,
  `created_time` DATETIME NOT NULL,
  `created_by` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`promo_code_id`, `product_id`),
  INDEX `PROMO_CODE_PRODUCT_IDX01` (`promo_code_id`),
  INDEX `PROMO_CODE_PRODUCT_IDX02` (`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;