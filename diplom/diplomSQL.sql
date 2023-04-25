-- MySQL Script generated by MySQL Workbench
-- Sat Apr 15 15:42:48 2023
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema logistics
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema logistics
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `logistics` DEFAULT CHARACTER SET utf8 ;
USE `logistics` ;

-- -----------------------------------------------------
-- Table `logistics`.`User`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `logistics`.`User` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `email` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `type` ENUM('CUSTOMER', 'MANAGER') NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `logistics`.`Cargo`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `logistics`.`Cargo` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `weight` INT NOT NULL,
  `type` ENUM('LIQUID', 'SOLID') NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `logistics`.`Country`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `logistics`.`Country` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `logistics`.`Request`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `logistics`.`Request` (
  `cargoID` INT NOT NULL,
  `dateSending` DATE NOT NULL,
  `dateDelivery` DATE NOT NULL,
  `userID` INT NOT NULL,
  `fromID` INT NOT NULL,
  `toID` INT NOT NULL,
  `status` ENUM('CANCELED', 'IN_PROCESS', 'ACCEPTED') NOT NULL,
  PRIMARY KEY (`cargoID`),
  INDEX `fk_Request_User_idx` (`userID` ASC) VISIBLE,
  INDEX `fk_Request_Countries1_idx` (`fromID` ASC) VISIBLE,
  INDEX `fk_Request_Countries2_idx` (`toID` ASC) VISIBLE,
  INDEX `fk_Request_Cargo1_idx` (`cargoID` ASC) VISIBLE,
  UNIQUE INDEX `cargoID_UNIQUE` (`cargoID` ASC) VISIBLE,
  CONSTRAINT `userID`
    FOREIGN KEY (`userID`)
    REFERENCES `logistics`.`User` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fromID`
    FOREIGN KEY (`fromID`)
    REFERENCES `logistics`.`Country` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `toID`
    FOREIGN KEY (`toID`)
    REFERENCES `logistics`.`Country` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `cargoID`
    FOREIGN KEY (`cargoID`)
    REFERENCES `logistics`.`Cargo` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `logistics`.`Truck`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `logistics`.`Truck` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `weight` INT NOT NULL,
  `type` ENUM('LIQUID', 'SOLID') NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `logistics`.`Driver`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `logistics`.`Driver` (
  `truckID` INT NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  `availability` ENUM('AVAILABLE', 'NOT_AVAILABLE') NOT NULL,
  `price` INT NOT NULL,
  PRIMARY KEY (`truckID`),
  UNIQUE INDEX `truckID_UNIQUE` (`truckID` ASC) VISIBLE,
  CONSTRAINT `truckID`
    FOREIGN KEY (`truckID`)
    REFERENCES `logistics`.`Truck` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `logistics`.`Comment`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `logistics`.`Comment` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `text` LONGTEXT NOT NULL,
  `rating` ENUM('ONE', 'TWO', 'THREE', 'FOUR', 'FIVE', 'NOTHING') NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `logistics`.`OrderHistory`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `logistics`.`OrderHistory` (
  `requestID` INT NOT NULL,
  `status` ENUM('CANCELED', 'PROCESSED', 'SENT', 'DELIVERED', 'COMPLETED') NOT NULL,
  `driverID` INT NOT NULL,
  `commentID` INT NOT NULL,
  PRIMARY KEY (`requestID`),
  UNIQUE INDEX `requestID_UNIQUE` (`requestID` ASC) VISIBLE,
  INDEX `fk_OrderHistory_Driver1_idx` (`driverID` ASC) VISIBLE,
  INDEX `fk_OrderHistory_Comment1_idx` (`commentID` ASC) VISIBLE,
  CONSTRAINT `requestID`
    FOREIGN KEY (`requestID`)
    REFERENCES `logistics`.`Request` (`cargoID`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `driverID`
    FOREIGN KEY (`driverID`)
    REFERENCES `logistics`.`Driver` (`truckID`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `commentID`
    FOREIGN KEY (`commentID`)
    REFERENCES `logistics`.`Comment` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
