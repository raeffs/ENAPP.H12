CREATE DATABASE `enappwebshop`;
 
CREATE TABLE `enappwebshop`.`product` (
  `id` INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `description` VARCHAR(45),
  `mediapath` VARCHAR(180),
  `unitprice` DECIMAL NOT NULL,
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