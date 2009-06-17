/*
SQLyog Enterprise - MySQL GUI v6.07
Host - 5.1.33-community-log : Database - encuestame_core
*********************************************************************
Server version : 5.1.33-community-log
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

create database if not exists `encuestame_core`;

USE `encuestame_core`;

/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

/*Table structure for table `cat_location` */

DROP TABLE IF EXISTS `cat_location`;

CREATE TABLE `cat_location` (
  `locate_id` int(11) NOT NULL,
  `tidtype` char(10) NOT NULL,
  `description` varchar(255) NOT NULL DEFAULT '',
  `level` int(5) NOT NULL,
  `active` enum('S','N') DEFAULT NULL,
  `lat` float(10,6) DEFAULT NULL,
  `lng` float(10,6) DEFAULT NULL,
  PRIMARY KEY (`locate_id`),
  KEY `Ref5137` (`tidtype`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `cat_location` */

/*Table structure for table `cat_location_type` */

DROP TABLE IF EXISTS `cat_location_type`;

CREATE TABLE `cat_location_type` (
  `loc_id_type` char(10) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `level` int(4) DEFAULT NULL,
  PRIMARY KEY (`loc_id_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `cat_location_type` */

insert  into `cat_location_type`(`loc_id_type`,`description`,`level`) values ('1','Región',0),('2','Departamento',1),('3','Municipio',2),('4','Distrito',3);

/*Table structure for table `cat_location_user` */

DROP TABLE IF EXISTS `cat_location_user`;

CREATE TABLE `cat_location_user` (
  `location_id` int(11) NOT NULL,
  `uid` int(11) NOT NULL,
  `state` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`location_id`,`uid`),
  KEY `tid` (`location_id`,`uid`),
  KEY `uid` (`uid`),
  CONSTRAINT `FK_cat_location_user` FOREIGN KEY (`uid`) REFERENCES `sec_users` (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `cat_location_user` */

/*Table structure for table `cat_state` */

DROP TABLE IF EXISTS `cat_state`;

CREATE TABLE `cat_state` (
  `id_state` int(11) NOT NULL AUTO_INCREMENT,
  `desc_state` varchar(255) CHARACTER SET latin1 DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id_state`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='InnoDB free: 39936 kB';

/*Data for the table `cat_state` */

insert  into `cat_state`(`id_state`,`desc_state`,`image`) values (1,'Activo','activo'),(2,'Inactivo','inactivo'),(3,'Pendiente','pendiente');

/*Table structure for table `project` */

DROP TABLE IF EXISTS `project`;

CREATE TABLE `project` (
  `proyect_id` int(11) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) NOT NULL DEFAULT '',
  `info` text NOT NULL,
  `date_start` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `id_state` int(11) NOT NULL,
  `date_finish` datetime DEFAULT NULL,
  PRIMARY KEY (`proyect_id`),
  KEY `id_estado` (`id_state`),
  CONSTRAINT `project_ibfk_1` FOREIGN KEY (`id_state`) REFERENCES `cat_state` (`id_state`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;

/*Data for the table `project` */

insert  into `project`(`proyect_id`,`description`,`info`,`date_start`,`id_state`,`date_finish`) values (1,'Encuestame','dsadsad','2009-05-30 01:49:56',2,'2009-05-30 01:49:56'),(2,'Proyecto 2','dsadsad','2009-05-30 02:18:55',2,'2009-05-30 02:18:55'),(3,'Proyecto 3','dsadsa','2009-05-30 02:19:09',2,'2009-05-30 02:19:09'),(4,'dasdasdas','dsadas','2009-05-13 12:00:00',1,'2009-05-13 12:00:00'),(5,'JotaProyect','dadasdasdas','2009-05-05 12:00:00',1,'2009-05-21 12:00:00'),(6,'dsadsa','dasdsad','2009-05-05 12:00:00',1,'2009-05-05 12:00:00'),(7,'dsadsadsa','dsadsadasdsad','2009-05-05 12:00:00',2,'2009-05-12 12:00:00'),(8,'dsadsadsa','dsadsadasdsad','2009-05-05 12:00:00',2,'2009-05-12 12:00:00'),(9,'Proyecto dsadsa','dsadsadsa','2009-05-11 12:00:00',1,'2009-05-11 12:00:00'),(16,'Paola Project','dasdsad','2009-05-05 12:00:00',1,'2009-05-07 12:00:00'),(17,'Proyecto Paola','dasdsadsa','2009-05-04 12:00:00',1,'2009-05-21 12:00:00');

/*Table structure for table `project_group` */

DROP TABLE IF EXISTS `project_group`;

CREATE TABLE `project_group` (
  `group_id` int(11) NOT NULL,
  `proyect_id` int(11) NOT NULL,
  `fecha_ingreso` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`group_id`,`proyect_id`),
  KEY `group_id` (`group_id`,`proyect_id`),
  KEY `group_id_2` (`group_id`),
  KEY `proyect_id` (`proyect_id`),
  CONSTRAINT `project_group_ibfk_1` FOREIGN KEY (`group_id`) REFERENCES `sec_groups` (`group_id`),
  CONSTRAINT `project_group_ibfk_2` FOREIGN KEY (`proyect_id`) REFERENCES `project` (`proyect_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `project_group` */

/*Table structure for table `project_location` */

DROP TABLE IF EXISTS `project_location`;

CREATE TABLE `project_location` (
  `group_id` int(11) NOT NULL,
  `proyect_id` int(11) NOT NULL,
  `id_state` int(11) NOT NULL,
  PRIMARY KEY (`group_id`,`proyect_id`),
  KEY `proyect_id` (`proyect_id`),
  KEY `group_id` (`group_id`),
  CONSTRAINT `project_location_fk` FOREIGN KEY (`group_id`) REFERENCES `sec_groups` (`group_id`),
  CONSTRAINT `project_location_ibfk_2` FOREIGN KEY (`proyect_id`) REFERENCES `project` (`proyect_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `project_location` */

/*Table structure for table `project_user` */

DROP TABLE IF EXISTS `project_user`;

CREATE TABLE `project_user` (
  `uid` int(11) NOT NULL,
  `proyect_id` int(11) NOT NULL,
  `date_new` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`uid`,`proyect_id`),
  KEY `uid` (`uid`,`proyect_id`),
  KEY `siteid` (`proyect_id`),
  CONSTRAINT `project_user_ibfk_1` FOREIGN KEY (`uid`) REFERENCES `sec_users` (`uid`),
  CONSTRAINT `project_user_ibfk_2` FOREIGN KEY (`proyect_id`) REFERENCES `project` (`proyect_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `project_user` */

/*Table structure for table `projet_cat_location` */

DROP TABLE IF EXISTS `projet_cat_location`;

CREATE TABLE `projet_cat_location` (
  `project_id` int(11) NOT NULL,
  `locate_id` int(11) NOT NULL,
  `state` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`locate_id`,`project_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `projet_cat_location` */

/*Table structure for table `question_colettion` */

DROP TABLE IF EXISTS `question_colettion`;

CREATE TABLE `question_colettion` (
  `id_q_colection` int(11) NOT NULL AUTO_INCREMENT,
  `des_coleccion` varchar(255) NOT NULL,
  `uid` int(11) NOT NULL,
  `creation_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id_q_colection`),
  KEY `uid` (`uid`),
  CONSTRAINT `question_colettion_ibfk_1` FOREIGN KEY (`uid`) REFERENCES `sec_users` (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `question_colettion` */

/*Table structure for table `questions` */

DROP TABLE IF EXISTS `questions`;

CREATE TABLE `questions` (
  `qid` int(11) NOT NULL AUTO_INCREMENT,
  `question` longtext,
  `version` int(11) NOT NULL DEFAULT '1',
  `id_state` int(11) NOT NULL DEFAULT '2',
  `qid_key` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`qid`),
  KEY `version` (`version`),
  KEY `id_estado` (`id_state`),
  CONSTRAINT `questions_ibfk_1` FOREIGN KEY (`id_state`) REFERENCES `cat_state` (`id_state`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*Data for the table `questions` */

insert  into `questions`(`qid`,`question`,`version`,`id_state`,`qid_key`) values (1,'¿Porque la gallina cruzó la calle?',1,2,'sadsadq21321321321dsadsa'),(2,'¿Porqué todo junto se escribe separado y separado todo junto?',1,2,'dsadsa321321dsadsa');

/*Table structure for table `questions_answers` */

DROP TABLE IF EXISTS `questions_answers`;

CREATE TABLE `questions_answers` (
  `id_answers` int(11) NOT NULL,
  `qid` int(11) NOT NULL,
  `answer` char(20) DEFAULT NULL,
  PRIMARY KEY (`id_answers`,`qid`),
  KEY `id_answers` (`id_answers`,`qid`),
  KEY `qid` (`qid`),
  CONSTRAINT `FK_questions_answers` FOREIGN KEY (`qid`) REFERENCES `questions` (`qid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `questions_answers` */

/*Table structure for table `questions_patron` */

DROP TABLE IF EXISTS `questions_patron`;

CREATE TABLE `questions_patron` (
  `id_patron` int(11) NOT NULL AUTO_INCREMENT,
  `type_patron` varchar(25) NOT NULL,
  `des_qid` varchar(50) NOT NULL,
  `label_qid` varchar(255) NOT NULL,
  `finallity` mediumtext,
  `template_patron` varchar(25) DEFAULT NULL,
  `class` varchar(50) NOT NULL,
  `nivel` int(2) DEFAULT '1',
  PRIMARY KEY (`id_patron`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

/*Data for the table `questions_patron` */

insert  into `questions_patron`(`id_patron`,`type_patron`,`des_qid`,`label_qid`,`finallity`,`template_patron`,`class`,`nivel`) values (1,'email','Text Email','1','dsadas','dasdsa','asdasda',1),(2,'unique_text','Text Unique','1','dsadsa','dsadas','dsa',1),(3,'paragrahp','Text Paragrahp','1',NULL,'dsadsadsadsa','dsadas',1),(4,'select-multi','Selection Multiple','1',NULL,'dsadsa','hkl',1),(5,'select-unique','Selection Unique','1',NULL,'kjlñkjñl','23j',1),(6,'postal-code','Code Postal','1',NULL,'jklj','kljkl',1),(7,'url','URL Pattern','1',NULL,'jkjkl','jkj',1),(8,'html','HTML Question','1',NULL,'dsajkj','jkl',1),(9,'image-multiple','Image Select Multiple','1',NULL,'hjkl','jk',1),(10,'image-single','Image Single','1',NULL,'jjk','jkl',1),(11,'matrix','Matrix','1',NULL,'kjl','sd',1),(12,'geo','Geo Selected','1',NULL,'dsa','jk',1);

/*Table structure for table `questions_relations` */

DROP TABLE IF EXISTS `questions_relations`;

CREATE TABLE `questions_relations` (
  `id_qid_rel` int(11) NOT NULL AUTO_INCREMENT,
  `qid` int(11) NOT NULL,
  `relation_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `id_q_colection` int(11) NOT NULL,
  PRIMARY KEY (`id_qid_rel`,`qid`),
  KEY `qid` (`qid`),
  KEY `id_q_colection` (`id_q_colection`),
  CONSTRAINT `FK_questions_relations` FOREIGN KEY (`id_q_colection`) REFERENCES `question_colettion` (`id_q_colection`),
  CONSTRAINT `questions_relations_ibfk_1` FOREIGN KEY (`qid`) REFERENCES `questions` (`qid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `questions_relations` */

/*Table structure for table `sec_group_permission` */

DROP TABLE IF EXISTS `sec_group_permission`;

CREATE TABLE `sec_group_permission` (
  `id_permission` int(11) NOT NULL,
  `group_id` int(11) NOT NULL,
  `state` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id_permission`,`group_id`),
  KEY `FK_group_permission` (`group_id`),
  CONSTRAINT `sec_group_permission_ibfk_1` FOREIGN KEY (`id_permission`) REFERENCES `sec_permission` (`id_permission`),
  CONSTRAINT `sec_group_permission_ibfk_2` FOREIGN KEY (`group_id`) REFERENCES `sec_groups` (`group_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `sec_group_permission` */

insert  into `sec_group_permission`(`id_permission`,`group_id`,`state`) values (1,1,1),(2,1,1);

/*Table structure for table `sec_group_user` */

DROP TABLE IF EXISTS `sec_group_user`;

CREATE TABLE `sec_group_user` (
  `group_id` int(11) NOT NULL,
  `uid` int(11) NOT NULL,
  `state` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`group_id`,`uid`),
  KEY `Ref4521` (`group_id`),
  KEY `Ref4030` (`uid`),
  CONSTRAINT `sec_group_user_ibfk_1` FOREIGN KEY (`group_id`) REFERENCES `sec_groups` (`group_id`),
  CONSTRAINT `sec_group_user_ibfk_2` FOREIGN KEY (`uid`) REFERENCES `sec_users` (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `sec_group_user` */

insert  into `sec_group_user`(`group_id`,`uid`,`state`) values (1,1,1),(1,2,1),(2,2,1);

/*Table structure for table `sec_groups` */

DROP TABLE IF EXISTS `sec_groups`;

CREATE TABLE `sec_groups` (
  `group_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `des_info` varchar(255) DEFAULT NULL,
  `id_state` int(11) NOT NULL,
  PRIMARY KEY (`group_id`),
  KEY `id_estado` (`id_state`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

/*Data for the table `sec_groups` */

insert  into `sec_groups`(`group_id`,`name`,`des_info`,`id_state`) values (1,'Administrador','Administrador',1),(2,'Encuestador','Encuestador',1),(3,'Juan','Juan',2),(4,'newGripos','dsadsadassa dsa a',1),(5,'dsadsa','dsadsa',1);

/*Table structure for table `sec_invite` */

DROP TABLE IF EXISTS `sec_invite`;

CREATE TABLE `sec_invite` (
  `code` varchar(36) NOT NULL,
  `email` varchar(50) NOT NULL,
  `createdOn` datetime NOT NULL,
  PRIMARY KEY (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `sec_invite` */

/*Table structure for table `sec_permission` */

DROP TABLE IF EXISTS `sec_permission`;

CREATE TABLE `sec_permission` (
  `id_permission` int(11) NOT NULL,
  `permission` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id_permission`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `sec_permission` */

insert  into `sec_permission`(`id_permission`,`permission`,`description`) values (1,'ENCUESTAME_ADMIN','Administrador'),(2,'ENCUESTAME_USER','Usuario'),(3,'ENCUESTAME_POLLSTER','Encuestador');

/*Table structure for table `sec_user_permission` */

DROP TABLE IF EXISTS `sec_user_permission`;

CREATE TABLE `sec_user_permission` (
  `uid` int(11) NOT NULL,
  `id_permission` int(11) NOT NULL,
  `state` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`uid`,`id_permission`),
  KEY `uid` (`uid`),
  KEY `id_permission` (`id_permission`),
  CONSTRAINT `sec_user_permission_ibfk_1` FOREIGN KEY (`uid`) REFERENCES `sec_users` (`uid`),
  CONSTRAINT `sec_user_permission_ibfk_2` FOREIGN KEY (`id_permission`) REFERENCES `sec_permission` (`id_permission`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=FIXED;

/*Data for the table `sec_user_permission` */

insert  into `sec_user_permission`(`uid`,`id_permission`,`state`) values (1,1,1),(2,1,1),(2,2,1);

/*Table structure for table `sec_users` */

DROP TABLE IF EXISTS `sec_users`;

CREATE TABLE `sec_users` (
  `uid` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `email` varchar(100) NOT NULL,
  `username` varchar(255) DEFAULT NULL,
  `password` varchar(255) NOT NULL,
  `status` tinyint(1) NOT NULL DEFAULT '0',
  `invite_code` varchar(255) DEFAULT NULL,
  `date_new` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `publisher` enum('S','N') NOT NULL DEFAULT 'N',
  `owner` enum('S','N') DEFAULT 'N',
  `twitter` enum('S','N') DEFAULT 'N',
  PRIMARY KEY (`uid`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*Data for the table `sec_users` */

insert  into `sec_users`(`uid`,`name`,`email`,`username`,`password`,`status`,`invite_code`,`date_new`,`publisher`,`owner`,`twitter`) values (1,'Juan Carlos Picado Herreraa','juan@local.com','jpicado','ZGnklBN5BzLrL8Y/oW2h1w2azAaiMIGgWYsbLCKzmU6JB0GetUb7fg+6z8RLWqZK',1,NULL,'2009-05-29 12:26:56','S','N','N'),(2,'Pavel Martinezss','minibota@hotmail.com','pavel','u8cSYZmDmbqXpVk0jaBxhuQzeSyrNYBFTGwuFvfClrnusZqP7UOEsL82AFYQl3a/',1,NULL,'2009-05-29 12:26:41','S','N','N');

/*Table structure for table `survey_detail` */

DROP TABLE IF EXISTS `survey_detail`;

CREATE TABLE `survey_detail` (
  `id_sd` bigint(20) NOT NULL,
  `qid` int(11) NOT NULL,
  `position` int(11) DEFAULT NULL,
  `nopreg` varchar(10) DEFAULT NULL,
  `id_sid_format` int(11) NOT NULL,
  `ssid` int(11) NOT NULL,
  PRIMARY KEY (`id_sd`,`qid`,`id_sid_format`,`ssid`),
  KEY `FK_survey_detail` (`id_sid_format`),
  KEY `FK_survey_detail_qid` (`qid`),
  KEY `FK_survey_detail_ssid` (`ssid`),
  CONSTRAINT `FK_survey_detail` FOREIGN KEY (`id_sid_format`) REFERENCES `survey_format` (`id_sid_format`),
  CONSTRAINT `FK_survey_detail_qid` FOREIGN KEY (`qid`) REFERENCES `questions` (`qid`),
  CONSTRAINT `FK_survey_detail_ssid` FOREIGN KEY (`ssid`) REFERENCES `survey_section` (`ssid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `survey_detail` */

/*Table structure for table `survey_format` */

DROP TABLE IF EXISTS `survey_format`;

CREATE TABLE `survey_format` (
  `id_sid_format` int(11) NOT NULL,
  `name` varchar(60) DEFAULT NULL,
  `date_created` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id_sid_format`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `survey_format` */

insert  into `survey_format`(`id_sid_format`,`name`,`date_created`) values (1,'Encuestame','2008-04-01 15:30:24'),(2,'Proyecto 2','2008-04-01 15:30:24'),(3,'Proyecto 3','2008-04-01 15:30:24'),(4,'Proyecto 4','2009-06-02 10:05:23');

/*Table structure for table `survey_format_group` */

DROP TABLE IF EXISTS `survey_format_group`;

CREATE TABLE `survey_format_group` (
  `id_sid_format` int(11) NOT NULL,
  `sg_id` int(11) NOT NULL,
  `state` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`sg_id`,`id_sid_format`),
  KEY `FK_survey_format_group` (`id_sid_format`),
  KEY `sg_id` (`sg_id`),
  CONSTRAINT `FK_survey_format_group` FOREIGN KEY (`id_sid_format`) REFERENCES `survey_format` (`id_sid_format`),
  CONSTRAINT `survey_format_group_fk` FOREIGN KEY (`sg_id`) REFERENCES `survey_group` (`sg_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `survey_format_group` */

/*Table structure for table `survey_group` */

DROP TABLE IF EXISTS `survey_group`;

CREATE TABLE `survey_group` (
  `sg_id` int(11) NOT NULL,
  `group_name` varchar(60) DEFAULT NULL,
  `date_create` datetime DEFAULT NULL,
  `id_state` int(11) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `id_sid_format` int(11) NOT NULL,
  PRIMARY KEY (`sg_id`),
  KEY `id_sid_format` (`id_sid_format`),
  KEY `id_state` (`id_state`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `survey_group` */

/*Table structure for table `survey_group_project` */

DROP TABLE IF EXISTS `survey_group_project`;

CREATE TABLE `survey_group_project` (
  `sg_id` int(11) NOT NULL,
  `proyect_id` int(11) NOT NULL,
  `state` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`sg_id`,`proyect_id`),
  KEY `proyect_id` (`proyect_id`),
  KEY `sg_id` (`sg_id`),
  CONSTRAINT `survey_group_project_fk` FOREIGN KEY (`proyect_id`) REFERENCES `project` (`proyect_id`),
  CONSTRAINT `survey_group_project_fk1` FOREIGN KEY (`sg_id`) REFERENCES `survey_group` (`sg_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `survey_group_project` */

/*Table structure for table `survey_result` */

DROP TABLE IF EXISTS `survey_result`;

CREATE TABLE `survey_result` (
  `rid` bigint(35) NOT NULL AUTO_INCREMENT,
  `qid` int(11) NOT NULL,
  `sid` bigint(20) NOT NULL,
  `resp` text NOT NULL,
  PRIMARY KEY (`rid`),
  KEY `qid` (`qid`,`sid`),
  KEY `sid` (`sid`),
  KEY `rid` (`rid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `survey_result` */

/*Table structure for table `survey_result_mod` */

DROP TABLE IF EXISTS `survey_result_mod`;

CREATE TABLE `survey_result_mod` (
  `id_mod` int(11) NOT NULL AUTO_INCREMENT,
  `rid` bigint(35) NOT NULL DEFAULT '0',
  `previous_response` longtext NOT NULL,
  `new_response` longtext,
  `mod_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `uid` int(11) NOT NULL,
  PRIMARY KEY (`id_mod`),
  KEY `rid` (`rid`),
  KEY `uid` (`uid`),
  CONSTRAINT `survey_result_mod_ibfk_1` FOREIGN KEY (`uid`) REFERENCES `sec_users` (`uid`),
  CONSTRAINT `survey_result_mod_ibfk_2` FOREIGN KEY (`rid`) REFERENCES `survey_result` (`rid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `survey_result_mod` */

/*Table structure for table `survey_section` */

DROP TABLE IF EXISTS `survey_section`;

CREATE TABLE `survey_section` (
  `ssid` int(11) NOT NULL AUTO_INCREMENT,
  `desc_section` varchar(255) DEFAULT NULL,
  `id_state` int(11) NOT NULL DEFAULT '2',
  PRIMARY KEY (`ssid`),
  KEY `ssid` (`ssid`),
  KEY `id_estado` (`id_state`),
  CONSTRAINT `FK_survey_section` FOREIGN KEY (`id_state`) REFERENCES `cat_state` (`id_state`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `survey_section` */

/*Table structure for table `survey_time` */

DROP TABLE IF EXISTS `survey_time`;

CREATE TABLE `survey_time` (
  `sequence` int(11) NOT NULL DEFAULT '0',
  `sid` bigint(20) NOT NULL DEFAULT '0',
  `elapsed_time` int(11) NOT NULL DEFAULT '0',
  `quitflag` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`sequence`,`sid`),
  KEY `Ref374` (`sid`),
  CONSTRAINT `FK_survey_time` FOREIGN KEY (`sid`) REFERENCES `surveys` (`sid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `survey_time` */

/*Table structure for table `surveys` */

DROP TABLE IF EXISTS `surveys`;

CREATE TABLE `surveys` (
  `sid` bigint(20) NOT NULL,
  `ticket` int(11) NOT NULL,
  `start_date` datetime NOT NULL,
  `end_date` datetime NOT NULL,
  `date_interview` date DEFAULT NULL,
  `uid` int(11) NOT NULL DEFAULT '0',
  `complete` enum('S','N') DEFAULT 'N',
  `id_sid_format` int(11) DEFAULT NULL,
  PRIMARY KEY (`sid`),
  KEY `Ref4029` (`uid`),
  CONSTRAINT `surveys_ibfk_2` FOREIGN KEY (`uid`) REFERENCES `sec_users` (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `surveys` */

/*Table structure for table `versions` */

DROP TABLE IF EXISTS `versions`;

CREATE TABLE `versions` (
  `version` int(11) NOT NULL AUTO_INCREMENT,
  `data_version` float NOT NULL,
  `fecha_version` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`version`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `versions` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
