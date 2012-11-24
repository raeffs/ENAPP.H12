CREATE DATABASE `enappwebshop`;
 
CREATE TABLE `enappwebshop`.`customergroup` ( 
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
  `dynnavid` VARCHAR(10) NULL,
  `groupid` INTEGER UNSIGNED NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=INNODB;

CREATE TABLE `enappwebshop`.`purchase` (
  `id` INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  `customerid` INTEGER UNSIGNED NOT NULL,
  `datetime` DATETIME NOT NULL,
  `status` INTEGER UNSIGNED NOT NULL,
  `totalamount` DECIMAL NOT NULL,
  `paymentid` INTEGER UNSIGNED NOT NULL,
  `correlationid` VARCHAR(20) NULL,
  PRIMARY KEY (`id`)
) ENGINE=INNODB;
 
CREATE TABLE `enappwebshop`.`purchaseitem` (
  `id` INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  `purchaseid` INTEGER UNSIGNED NOT NULL,
  `productid` VARCHAR(20) NOT NULL,
  `quantity` DECIMAL NOT NULL,
  `unitprice` DECIMAL NOT NULL,
  `lineamount` DECIMAL NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=INNODB;

CREATE VIEW `enappwebshop`.`v_customer_group` AS
SELECT  c.`username`, c.`password`, g.`name`
 FROM `enappwebshop`.`customer` c
 LEFT JOIN `enappwebshop`.`customergroup` g ON g.`id` =  c.`groupid`;
