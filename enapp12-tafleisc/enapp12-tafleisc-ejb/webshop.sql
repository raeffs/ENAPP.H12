CREATE DATABASE `enappwebshop`;
 
CREATE TABLE `enappwebshop`.`product` (
  `id` INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `description` VARCHAR(250),
  `mediapath` VARCHAR(180),
  `unitprice` DECIMAL NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=INNODB;

CREATE TABLE `enappwebshop`.`group` ( 
  `id` INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(15) NOT NULL,
  `description` VARCHAR(200) NULL,
  PRIMARY KEY (`id`)
) ENGINE=INNODB;
 
CREATE TABLE `enappwebshop`.`customer` (
  `id` INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(15) NOT NULL UNIQUE,
  `password` VARCHAR(32) NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  `address` VARCHAR(45) NOT NULL,
  `email` VARCHAR(90) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=INNODB;

CREATE TABLE `enappwebshop`.`customer_group` (
  `customer_id` INTEGER UNSIGNED NOT NULL,
  `group_id` INTEGER UNSIGNED NOT NULL,
  PRIMARY KEY (`customer_id`,`group_id`)
) ENGINE=INNODB;
 
CREATE TABLE `enappwebshop`.`purchase` (
  `id` INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  `customerid` INTEGER UNSIGNED NOT NULL,
  `datetime` DATETIME NOT NULL,
  `status` VARCHAR(15) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=INNODB;
 
CREATE TABLE `enappwebshop`.`purchaseitem` (
  `id` INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  `purchaseid` INTEGER UNSIGNED NOT NULL,
  `productid` INTEGER UNSIGNED NOT NULL,
  `quantity` DECIMAL NOT NULL,
  `unitprice` DECIMAL NOT NULL,
  `lineamount` DECIMAL NOT NULL,
  `description` VARCHAR(90),
  PRIMARY KEY (`id`)
) ENGINE=INNODB;

CREATE VIEW `enappwebshop`.`v_customer_group` AS
SELECT  c.`username`, c.`password`, g.`name`
 FROM `enappwebshop`.`customer_group` cg
 INNER JOIN `enappwebshop`.`customer` c ON c.`id` = cg.`customer_id`
 INNER JOIN `enappwebshop`.`group` g ON g.`id` =  cg.`group_id`;
