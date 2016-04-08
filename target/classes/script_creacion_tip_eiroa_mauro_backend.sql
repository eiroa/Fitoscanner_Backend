
CREATE DATABASE  IF NOT EXISTS `tip_eiroa_mauro_server_backend`  CHARACTER SET utf8 COLLATE utf8_general_ci;
USE `tip_eiroa_mauro_server_backend`;

DROP TABLE IF EXISTS `image`;
DROP TABLE IF EXISTS `sample`;
DROP TABLE IF EXISTS `field`;
DROP TABLE IF EXISTS `user`;
DROP TABLE IF EXISTS `client`;
DROP TABLE IF EXISTS `zone`;
DROP TABLE IF EXISTS `profile`;
DROP TABLE IF EXISTS `treatment`;
DROP TABLE IF EXISTS `crop`;
DROP TABLE IF EXISTS `specie`;



CREATE TABLE `client` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `zone` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `field` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `id_zone` bigint(20) ,
  `id_client` bigint(20) ,
  PRIMARY KEY (`id`),
  KEY `FK_field_id_zone` (`id_zone`),
  KEY `FK_field_id_client` (`id_client`),
 CONSTRAINT `FK_field_id_zone` FOREIGN KEY (`id_zone`) REFERENCES `zone` (`id`),
 CONSTRAINT `FK_field_id_client` FOREIGN KEY (`id_client`) REFERENCES `client` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `profile` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `imei` varchar(45) ,
  `name` varchar(45) ,
  `surname` varchar(45) ,
  `nick` varchar(45) ,
  `code` varchar(45) ,
  `anonymous` tinyint(1) ,    
  `admin` tinyint(1) ,  
  `password` varchar(1024) ,
  `id_client` bigint(20) ,
  `id_profile` bigint(20) ,
  PRIMARY KEY (`id`),
  KEY `FK_user_id_client` (`id_client`),
  KEY `FK_user_id_profile` (`id_profile`),
 CONSTRAINT `FK_user_id_client` FOREIGN KEY (`id_client`) REFERENCES `client` (`id`),
 CONSTRAINT `FK_user_id_profile` FOREIGN KEY (`id_profile`) REFERENCES `profile` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `specie` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `isCrop` tinyint(1),
  `isPlague` tinyint(1) , 
  `scientific_name` varchar(45),
  `description` MEDIUMTEXT ,
  `active` tinyint(1),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



CREATE TABLE `treatment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(500) NOT NULL,
  `description` varchar(500) NOT NULL,
  `typeQuantity` varchar(500) NOT NULL,
  `quantity` varchar(500) NOT NULL,
  `typeFrequency` varchar(500) NOT NULL,
  `frequency` varchar(500) NOT NULL,
  `useExplanation` varchar(500) NOT NULL,
  `extraLink1` varchar(500) NOT NULL,
  `extraLink2` varchar(500) NOT NULL,
  `extraLink3` varchar(500) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `sample` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `sample_date` DATETIME ,
  `images_hash` VARCHAR(1024) NOT NULL,
  `lat` varchar(45) ,
  `lon` varchar(45) ,
  `region` varchar(45) ,
  `country` varchar(45) ,
  `date_treatment_obtained` DATETIME ,
  `resolved` tinyint(1) , 
  `specie_name_resolved` varchar(512),
  `treatment_names_resolved` varchar(512),
  `id_user_origin` bigint(20) ,
  `id_specie_resolved` bigint(20) ,  
  PRIMARY KEY (`id`),
  KEY `FK_sample_id_user_origin` (`id_user_origin`), 
  KEY `FK_sample_id_specie_resolved` (`id_specie_resolved`),
  CONSTRAINT `FK_sample_id_user_origin` FOREIGN KEY (`id_user_origin`) REFERENCES `user` (`id`),
  CONSTRAINT `FK_sample_id_specie_resolved` FOREIGN KEY (`id_specie_resolved`) REFERENCES `specie` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `image` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(200) NOT NULL,
  `id_sample` bigint(20) ,
  `base64` MEDIUMTEXT ,
  PRIMARY KEY (`id`),
  KEY `FK_image_id_sample` (`id_sample`),
 CONSTRAINT `FK_image_id_sample` FOREIGN KEY (`id_sample`) REFERENCES `sample` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `specieximage` (
  `id_specie` bigint(20) NOT NULL,
  `id_image` bigint(20) NOT NULL,
  PRIMARY KEY (`id_specie`,`id_image`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `treatmentximage` (
  `id_treatment` bigint(20) NOT NULL,
  `id_image` bigint(20) NOT NULL,
  PRIMARY KEY (`id_treatment`,`id_image`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `treatmentxspecie` (
  `id_treatment` bigint(20) NOT NULL,
  `id_specie` bigint(20) NOT NULL,
  PRIMARY KEY (`id_treatment`,`id_specie`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
