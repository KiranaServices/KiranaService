


ALTER TABLE users
ADD created_at TIMESTAMP DEFAULT '1970-01-01 00:00:00',
ADD updated_at TIMESTAMP DEFAULT '1970-01-01 00:00:00';
CREATE TRIGGER users_create BEFORE INSERT ON `users`
FOR EACH ROW SET NEW.created_at = NOW(), NEW.updated_at = NOW();
CREATE TRIGGER users_update BEFORE UPDATE ON `users`
FOR EACH ROW SET NEW.updated_at = NOW();

ALTER TABLE shop
ADD created_at TIMESTAMP DEFAULT '1970-01-01 00:00:00',
ADD updated_at TIMESTAMP DEFAULT '1970-01-01 00:00:00';
CREATE TRIGGER shop_create BEFORE INSERT ON `shop`
FOR EACH ROW SET NEW.created_at = NOW(), NEW.updated_at = NOW();
CREATE TRIGGER shop_update BEFORE UPDATE ON `shop`
FOR EACH ROW SET NEW.updated_at = NOW();


ALTER TABLE products
ADD created_at TIMESTAMP DEFAULT '1970-01-01 00:00:00',
ADD updated_at TIMESTAMP DEFAULT '1970-01-01 00:00:00';
CREATE TRIGGER products_create BEFORE INSERT ON `products`
FOR EACH ROW SET NEW.created_at = NOW(), NEW.updated_at = NOW();
CREATE TRIGGER products_update BEFORE UPDATE ON `products`
FOR EACH ROW SET NEW.updated_at = NOW();



CREATE TABLE `employee_db`.`users` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `first_name` VARCHAR(45) NULL,
  `last_name` VARCHAR(45) NULL,
  `email` VARCHAR(45) NULL,
  `phone` VARCHAR(45) NULL,
  PRIMARY KEY (`id`));
  
  
  CREATE TABLE `employee_db`.`shop` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL,
  `type` VARCHAR(45) NULL,
  `address` VARCHAR(100) NULL,
  `TIN` VARCHAR(45) NULL,
  `service_tax` FLOAT NULL,
  `service_charge` FLOAT NULL,
  `VAT` FLOAT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC));
  
  
  CREATE TABLE `kirana`.`products` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `product_id` VARCHAR(100) NULL,
  `quantity` FLOAT NULL,
  `price` FLOAT NULL,
  `discount` FLOAT NULL,
  `tax_bracket` FLOAT NULL,
  `shop_id` INT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `product_id_UNIQUE` (`product_id` ASC));


ALTER TABLE `kirana`.`users` 
CHANGE COLUMN `updated_at` `updated_at` TIMESTAMP NOT NULL DEFAULT '2000-01-01 00:00:00' ;

ALTER TABLE `kirana`.`products` 
ADD COLUMN `product_property` VARCHAR(350) NULL DEFAULT NULL AFTER `shop_id`;
