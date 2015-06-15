CREATE SCHEMA IF NOT EXISTS `progressweb` DEFAULT CHARACTER SET utf8;
USE `progressweb`;

-- -----------------------------------------------------
-- Table `progressweb`.`Workers`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `progressweb`.`Workers` (
`id` INT NOT NULL AUTO_INCREMENT ,
`FName` VARCHAR(50) CHARACTER SET utf8 NOT NULL ,
`MName` VARCHAR(50) CHARACTER SET utf8 NOT NULL ,
`LName` VARCHAR(50) CHARACTER SET utf8 NOT NULL ,
`PwdHash` VARCHAR(40) CHARACTER SET utf8 NOT NULL ,
`Permissions` INT NOT NULL DEFAULT 1,
`Email` VARCHAR(50) CHARACTER SET utf8 NOT NULL ,
`Deleted` TINYINT(1) NOT NULL DEFAULT false ,
`IsActive` TINYINT(1) NOT NULL DEFAULT true ,
PRIMARY KEY (`id`) ,
UNIQUE INDEX `EmailIndex` (`Email` ASC) );

-- -----------------------------------------------------
-- Table `progressweb`.`Apartaments`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `progressweb`.`Apartaments` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `CityName` varchar(50) NOT NULL,
  `StreetName` varchar(50) NOT NULL,
  `HouseNumber` varchar(50) DEFAULT NULL,
  `BuildingNumber` varchar(50) DEFAULT NULL,
  `KladrId` varchar(50) DEFAULT NULL,
  `ShortAddress` varchar(100) DEFAULT NULL,
  `ApartamentLan` varchar(10) DEFAULT NULL,
  `ApartamentLon` varchar(10) DEFAULT NULL,
  `TypeOfSales` tinyint(1) NOT NULL DEFAULT '0',
  `Rooms` int(11) NOT NULL,
  `Price` int(11) NOT NULL,
  `CityDistrict` int(11) NOT NULL,
  `Floor` int(11) NOT NULL,
  `Floors` int(11) NOT NULL,
  `RoomNumber` int(11) NOT NULL,
  `Material` int(11) NOT NULL,
  `SizeApartament` decimal(5,2) NOT NULL,
  `SizeLiving` decimal(5,2) NOT NULL,
  `SizeKitchen` decimal(5,2) NOT NULL,
  `Balcony` int(11) NOT NULL,
  `Loggia` int(11) NOT NULL,
  `YearOfConstruction` int(11) NOT NULL,
  `Description` mediumtext NOT NULL,
  `MethodOfPurchase_PureSale` tinyint(1) NOT NULL DEFAULT '0',
  `MethodOfPurchase_Mortgage` tinyint(1) NOT NULL DEFAULT '0',
  `MethodOfPurchase_Exchange` tinyint(1) NOT NULL DEFAULT '0',
  `MethodOfPurchase_Rent` tinyint(1) NOT NULL DEFAULT '0',
  `RePlanning` tinyint(1) NOT NULL DEFAULT '0',
  `CreationDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `LastModify` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `idWorker` int(11) NOT NULL,
  `idWorkerTarget` int(11) NOT NULL,
  `idCustomer` int(11) NOT NULL,
  `IsApproved` tinyint(1) NOT NULL DEFAULT '0',
  `Deleted` tinyint(1) NOT NULL DEFAULT '0',
  `idFilespace` int(11) NOT NULL DEFAULT '-1',
  `dwellingType` tinyint(1) NOT NULL,
  `status` tinyint(1) NOT NULL,
  `apartamentUUID` VARCHAR(45) DEFAULT NULL,
  `filespaceUUID` VARCHAR(45) DEFAULT NULL,
  `isAD` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `idCustomer` (`idCustomer`),
  KEY `idCustomerIndex` (`idWorker`),
  CONSTRAINT `Apartaments_ibfk_1` FOREIGN KEY (`idWorker`) REFERENCES `Workers` (`id`)
);

-- -----------------------------------------------------
-- Table `progressweb`.`News`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `progressweb`.`News` (
`id` INT NOT NULL AUTO_INCREMENT ,
`idWorker` INT NOT NULL ,
`Header` MEDIUMTEXT CHARACTER SET utf8 NOT NULL ,
`Text` MEDIUMTEXT CHARACTER SET utf8 NOT NULL ,
`CreationDate` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
`LastModify` TIMESTAMP NOT NULL,
`Deleted` TINYINT(1) NOT NULL DEFAULT false ,
FOREIGN KEY (idWorker) REFERENCES Workers(id),
PRIMARY KEY (`id`) ,
INDEX `idWorkerIndex` (`idWorker` ASC));

-- NULL user --
INSERT INTO progressweb.Workers (FName, MName, LName, PwdHash, Permissions, Email, Deleted, IsActive) 
	VALUES ('Не', '','указан', 'null', 0, 'null', true, true);

-- test users--
INSERT INTO progressweb.Workers (FName, MName, LName, PwdHash, Permissions, Email, Deleted, IsActive) 
	VALUES ('Сергей', 'Викторович','Стопкин', 'f9a7c6df341325822e3ea264cfe39e5ef8c73aa4', 3, 'admin@progress55.com', false, true);
