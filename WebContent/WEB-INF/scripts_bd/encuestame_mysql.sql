/*
SQLyog Enterprise - MySQL GUI v6.07
Host - 5.1.34-community : Database - encuestame_core
*********************************************************************
Server version : 5.1.34-community
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
  `tid` int(11) NOT NULL,
  `tidtype` char(10) NOT NULL,
  `description` varchar(255) NOT NULL DEFAULT '',
  `level` int(5) NOT NULL,
  `active` enum('S','N') NOT NULL DEFAULT 'S',
  `id_state` int(11) NOT NULL DEFAULT '2',
  `gov_id` int(4) DEFAULT NULL,
  `lat` float(10,6) DEFAULT NULL,
  `lng` float(10,6) DEFAULT NULL,
  `topography_sheet` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`tid`),
  KEY `Ref5137` (`tidtype`),
  KEY `id_estado` (`id_state`),
  CONSTRAINT `FK_cat_location` FOREIGN KEY (`tidtype`) REFERENCES `cat_location_type` (`tidtype`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `cat_location` */

insert  into `cat_location`(`tid`,`tidtype`,`description`,`level`,`active`,`id_state`,`gov_id`,`lat`,`lng`,`topography_sheet`) values (1,'1','Region Atlantica',0,'S',1,1,1.000000,1.000000,'1'),(2,'1','Región Norte',0,'S',1,1,1.000000,1.000000,'1'),(3,'1','Región Pacífico',0,'S',1,1,1.000000,1.000000,'1'),(4,'2','Managua',3,'S',2,NULL,NULL,NULL,NULL),(5,'3','El Crucero',4,'S',2,NULL,NULL,NULL,NULL);

/*Table structure for table `cat_location_type` */

DROP TABLE IF EXISTS `cat_location_type`;

CREATE TABLE `cat_location_type` (
  `tidtype` char(10) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `level` int(4) DEFAULT NULL,
  PRIMARY KEY (`tidtype`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `cat_location_type` */

insert  into `cat_location_type`(`tidtype`,`description`,`level`) values ('1','Región',0),('2','Departamento',1),('3','Municipio',2),('4','Distrito',3);

/*Table structure for table `cat_location_user` */

DROP TABLE IF EXISTS `cat_location_user`;

CREATE TABLE `cat_location_user` (
  `tid` int(11) NOT NULL,
  `proyect_id` int(11) NOT NULL,
  `uid` int(11) NOT NULL,
  `id_state` int(11) NOT NULL,
  PRIMARY KEY (`tid`,`proyect_id`,`uid`),
  KEY `tid` (`tid`,`proyect_id`,`uid`,`id_state`),
  KEY `id_estado` (`id_state`),
  KEY `uid` (`uid`),
  KEY `proyect_id` (`proyect_id`),
  CONSTRAINT `cat_location_user_ibfk_1` FOREIGN KEY (`id_state`) REFERENCES `cat_location` (`id_state`),
  CONSTRAINT `cat_location_user_ibfk_2` FOREIGN KEY (`proyect_id`) REFERENCES `proyect` (`proyect_id`),
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

/*Table structure for table `error_reports` */

DROP TABLE IF EXISTS `error_reports`;

CREATE TABLE `error_reports` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `domain` varchar(255) CHARACTER SET latin1 DEFAULT NULL,
  `page_url` text CHARACTER SET latin1,
  `reported_by` varchar(255) CHARACTER SET latin1 DEFAULT NULL,
  `reported_on` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `fixed_by` varchar(255) CHARACTER SET latin1 DEFAULT NULL,
  `fixed_on` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `time_taken` varchar(20) CHARACTER SET latin1 DEFAULT NULL,
  `location` varchar(255) CHARACTER SET latin1 DEFAULT NULL,
  `category` varchar(255) CHARACTER SET latin1 DEFAULT NULL,
  `description` varchar(255) CHARACTER SET latin1 DEFAULT NULL,
  `is_browser` varchar(10) CHARACTER SET latin1 DEFAULT NULL,
  `cause` text CHARACTER SET latin1,
  `solution` text CHARACTER SET latin1,
  `severity` varchar(10) CHARACTER SET latin1 DEFAULT NULL,
  `incorrect_solution` varchar(10) CHARACTER SET latin1 DEFAULT NULL,
  `owned_by_us` varchar(10) CHARACTER SET latin1 DEFAULT NULL,
  `reporter_comments` text CHARACTER SET latin1,
  `error_details` text CHARACTER SET latin1,
  `fixer_comments` text CHARACTER SET latin1,
  `is_fixed` varchar(10) CHARACTER SET latin1 DEFAULT 'false',
  `time_stamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=535 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `error_reports` */

/*Table structure for table `ip_track` */

DROP TABLE IF EXISTS `ip_track`;

CREATE TABLE `ip_track` (
  `sid` int(11) NOT NULL DEFAULT '0',
  `ip` varchar(15) DEFAULT NULL,
  `completed` int(11) NOT NULL DEFAULT '0',
  `fecha_ingreso` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `uid` int(11) NOT NULL,
  PRIMARY KEY (`sid`,`uid`),
  UNIQUE KEY `sid` (`sid`,`uid`),
  KEY `Ref3717` (`sid`),
  KEY `uid` (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `ip_track` */

/*Table structure for table `proyect` */

DROP TABLE IF EXISTS `proyect`;

CREATE TABLE `proyect` (
  `proyect_id` int(11) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) NOT NULL DEFAULT '',
  `info` text NOT NULL,
  `date_start` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `id_state` int(11) NOT NULL,
  `logo` blob,
  `header` varchar(255) DEFAULT NULL,
  `range` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`proyect_id`),
  KEY `id_estado` (`id_state`),
  CONSTRAINT `proyect_ibfk_1` FOREIGN KEY (`id_state`) REFERENCES `cat_state` (`id_state`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `proyect` */

/*Table structure for table `proyect_group` */

DROP TABLE IF EXISTS `proyect_group`;

CREATE TABLE `proyect_group` (
  `group_id` int(11) NOT NULL,
  `proyect_id` int(11) NOT NULL,
  `fecha_ingreso` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`group_id`,`proyect_id`),
  KEY `group_id` (`group_id`,`proyect_id`),
  KEY `group_id_2` (`group_id`),
  KEY `proyect_id` (`proyect_id`),
  CONSTRAINT `proyect_group_ibfk_1` FOREIGN KEY (`group_id`) REFERENCES `sec_groups` (`group_id`),
  CONSTRAINT `proyect_group_ibfk_2` FOREIGN KEY (`proyect_id`) REFERENCES `proyect` (`proyect_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `proyect_group` */

/*Table structure for table `proyect_location` */

DROP TABLE IF EXISTS `proyect_location`;

CREATE TABLE `proyect_location` (
  `tid` int(11) NOT NULL,
  `proyect_id` int(11) NOT NULL,
  `id_estado` int(11) NOT NULL,
  PRIMARY KEY (`tid`,`proyect_id`),
  KEY `proyect_id` (`proyect_id`),
  CONSTRAINT `proyect_location_ibfk_1` FOREIGN KEY (`tid`) REFERENCES `cat_location` (`tid`),
  CONSTRAINT `proyect_location_ibfk_2` FOREIGN KEY (`proyect_id`) REFERENCES `proyect` (`proyect_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `proyect_location` */

/*Table structure for table `proyect_type_survey` */

DROP TABLE IF EXISTS `proyect_type_survey`;

CREATE TABLE `proyect_type_survey` (
  `proyect_id` int(11) NOT NULL,
  `stid` int(11) NOT NULL,
  `state` tinyint(1) NOT NULL,
  PRIMARY KEY (`proyect_id`,`stid`),
  KEY `stid` (`stid`),
  KEY `proyect_id` (`proyect_id`),
  KEY `proyect_id_2` (`proyect_id`),
  CONSTRAINT `proyect_type_survey_ibfk_1` FOREIGN KEY (`proyect_id`) REFERENCES `proyect` (`proyect_id`),
  CONSTRAINT `proyect_type_survey_ibfk_2` FOREIGN KEY (`stid`) REFERENCES `survey_type` (`stid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `proyect_type_survey` */

/*Table structure for table `proyect_user` */

DROP TABLE IF EXISTS `proyect_user`;

CREATE TABLE `proyect_user` (
  `uid` int(11) NOT NULL,
  `proyect_id` int(11) NOT NULL,
  `date_new` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`uid`,`proyect_id`),
  KEY `uid` (`uid`,`proyect_id`),
  KEY `siteid` (`proyect_id`),
  CONSTRAINT `proyect_user_ibfk_1` FOREIGN KEY (`uid`) REFERENCES `sec_users` (`uid`),
  CONSTRAINT `proyect_user_ibfk_2` FOREIGN KEY (`proyect_id`) REFERENCES `proyect` (`proyect_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `proyect_user` */

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

/*Table structure for table `question_validation` */

DROP TABLE IF EXISTS `question_validation`;

CREATE TABLE `question_validation` (
  `id_validation` int(11) NOT NULL AUTO_INCREMENT,
  `qid` int(11) DEFAULT NULL,
  `id_type_val` int(11) DEFAULT NULL,
  `params` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id_validation`),
  KEY `id_type_val` (`id_type_val`),
  KEY `qid` (`qid`),
  CONSTRAINT `FK_question_validation` FOREIGN KEY (`id_type_val`) REFERENCES `question_validation_type` (`id_type_val`),
  CONSTRAINT `FK_question_validation1` FOREIGN KEY (`qid`) REFERENCES `questions` (`qid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `question_validation` */

/*Table structure for table `question_validation_type` */

DROP TABLE IF EXISTS `question_validation_type`;

CREATE TABLE `question_validation_type` (
  `id_type_val` int(11) NOT NULL,
  `des_val` varchar(255) DEFAULT NULL,
  `param` enum('S','N','A') DEFAULT NULL,
  `help` varchar(255) DEFAULT NULL,
  `rules` varbinary(255) DEFAULT NULL,
  PRIMARY KEY (`id_type_val`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `question_validation_type` */

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `questions` */

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

/*Table structure for table `questions_dependence` */

DROP TABLE IF EXISTS `questions_dependence`;

CREATE TABLE `questions_dependence` (
  `qid` int(11) NOT NULL,
  `label_id` int(11) NOT NULL,
  `conditional` varchar(10) DEFAULT NULL,
  `dependence_start` int(11) DEFAULT NULL,
  `dependence_finalize` int(11) DEFAULT NULL,
  PRIMARY KEY (`qid`,`label_id`),
  KEY `FK_questions_dependence` (`label_id`),
  CONSTRAINT `FK_questions_dependence` FOREIGN KEY (`label_id`) REFERENCES `questions_labels` (`label_id`),
  CONSTRAINT `FK_questions_dependence_Qid` FOREIGN KEY (`qid`) REFERENCES `questions` (`qid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;

/*Data for the table `questions_dependence` */

/*Table structure for table `questions_labels` */

DROP TABLE IF EXISTS `questions_labels`;

CREATE TABLE `questions_labels` (
  `label_id` int(11) NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  `label_order` int(11) DEFAULT NULL,
  PRIMARY KEY (`label_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;

/*Data for the table `questions_labels` */

/*Table structure for table `questions_patron` */

DROP TABLE IF EXISTS `questions_patron`;

CREATE TABLE `questions_patron` (
  `id_patron` int(11) NOT NULL AUTO_INCREMENT,
  `type_patron` varchar(25) NOT NULL,
  `des_qid` mediumtext NOT NULL,
  `label_qid` varchar(255) NOT NULL,
  `finallity` mediumtext NOT NULL,
  `template_patron` varchar(25) DEFAULT NULL,
  `class` varchar(50) NOT NULL,
  `nivel` int(2) DEFAULT '1',
  PRIMARY KEY (`id_patron`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `questions_patron` */

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

insert  into `sec_groups`(`group_id`,`name`,`des_info`,`id_state`) values (1,'Administrador','Administrador',1),(2,'Encuestador','Encuestador',1),(3,'Juan','Juan',2),(4,'newGripo','dsadsadas',1),(5,'dsadsa','dsadsa',1);

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

insert  into `sec_users`(`uid`,`name`,`email`,`username`,`password`,`status`,`invite_code`,`date_new`,`publisher`,`owner`,`twitter`) values (1,'Juan Carlos Picado Herreraa','juan@local.com','jpicado','k5psZT4pPLI6olP+K9gmJTWKpz0jO5YPonapNw/2ekJnqdohEPu5IoEqc6ESSZ9f',1,NULL,'2009-05-25 13:35:36','S','N','N'),(2,'Pavel Martinezss','minibota@hotmail.com','pavel','B+s1MLtO+GdeP6brkeo1dEXvaXtlxpRn64nuDL2MFJub+uZy4dijsrzXlJR+mYJO',1,NULL,'2009-05-22 20:00:04','S','N','N');

/*Table structure for table `survey_detail` */

DROP TABLE IF EXISTS `survey_detail`;

CREATE TABLE `survey_detail` (
  `id_sd` int(11) NOT NULL AUTO_INCREMENT,
  `qid` int(11) NOT NULL,
  `ssid` int(11) NOT NULL,
  `stid` int(11) NOT NULL,
  `position` int(11) DEFAULT '0',
  `noquestion` float NOT NULL DEFAULT '0',
  `template` varchar(100) DEFAULT NULL,
  `id_patron` int(11) DEFAULT '7',
  PRIMARY KEY (`id_sd`,`qid`,`ssid`,`stid`),
  KEY `qid` (`qid`),
  KEY `ssid` (`ssid`),
  KEY `stid` (`stid`),
  KEY `id_patron` (`id_patron`),
  CONSTRAINT `FK_survey_detail_patron` FOREIGN KEY (`id_patron`) REFERENCES `questions_patron` (`id_patron`),
  CONSTRAINT `FK_survey_detail_question` FOREIGN KEY (`qid`) REFERENCES `questions` (`qid`),
  CONSTRAINT `FK_survey_detail_type` FOREIGN KEY (`stid`) REFERENCES `survey_type` (`stid`),
  CONSTRAINT `survey_detail_ibfk_1` FOREIGN KEY (`ssid`) REFERENCES `survey_section` (`ssid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `survey_detail` */

/*Table structure for table `survey_location` */

DROP TABLE IF EXISTS `survey_location`;

CREATE TABLE `survey_location` (
  `stid` int(11) NOT NULL,
  `proyect_id` int(11) NOT NULL DEFAULT '1',
  `tid` int(11) NOT NULL,
  `from` int(11) NOT NULL,
  `to` int(11) NOT NULL,
  `captured` int(11) DEFAULT NULL,
  `reported` int(11) DEFAULT NULL,
  PRIMARY KEY (`stid`,`proyect_id`,`tid`),
  KEY `Ref4860` (`stid`),
  KEY `Ref5061` (`tid`),
  KEY `siteid` (`proyect_id`),
  CONSTRAINT `survey_location_ibfk_1` FOREIGN KEY (`proyect_id`) REFERENCES `proyect` (`proyect_id`),
  CONSTRAINT `survey_location_ibfk_2` FOREIGN KEY (`tid`) REFERENCES `cat_location` (`tid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `survey_location` */

/*Table structure for table `survey_question_dependency` */

DROP TABLE IF EXISTS `survey_question_dependency`;

CREATE TABLE `survey_question_dependency` (
  `qid` int(11) NOT NULL,
  `ssid` int(11) NOT NULL,
  `stid` int(11) NOT NULL,
  `patron_data` varchar(100) CHARACTER SET latin1 NOT NULL DEFAULT '',
  `patron_limit` float NOT NULL,
  `dep_home` float DEFAULT NULL,
  `patron_option` enum('I','D') COLLATE utf8_unicode_ci NOT NULL,
  `qid_lim` int(11) DEFAULT NULL,
  `id_state` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`qid`,`ssid`,`stid`),
  KEY `ssid` (`ssid`),
  KEY `stid` (`stid`),
  KEY `id_state` (`id_state`),
  CONSTRAINT `survey_question_dependency_ibfk_1` FOREIGN KEY (`qid`) REFERENCES `questions` (`qid`),
  CONSTRAINT `survey_question_dependency_ibfk_2` FOREIGN KEY (`ssid`) REFERENCES `survey_section` (`ssid`),
  CONSTRAINT `survey_question_dependency_ibfk_3` FOREIGN KEY (`stid`) REFERENCES `survey_type` (`stid`),
  CONSTRAINT `survey_question_dependency_ibfk_4` FOREIGN KEY (`id_state`) REFERENCES `cat_location` (`id_state`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `survey_question_dependency` */

/*Table structure for table `survey_result` */

DROP TABLE IF EXISTS `survey_result`;

CREATE TABLE `survey_result` (
  `rid` bigint(35) NOT NULL AUTO_INCREMENT,
  `qid` int(11) NOT NULL,
  `ssid` int(11) NOT NULL,
  `sid` int(11) NOT NULL,
  `resp` text NOT NULL,
  PRIMARY KEY (`rid`),
  KEY `qid` (`qid`,`ssid`,`sid`),
  KEY `ssid` (`ssid`),
  KEY `sid` (`sid`),
  KEY `rid` (`rid`),
  CONSTRAINT `survey_result_ibfk_1` FOREIGN KEY (`qid`) REFERENCES `questions` (`qid`),
  CONSTRAINT `survey_result_ibfk_2` FOREIGN KEY (`ssid`) REFERENCES `survey_section` (`ssid`),
  CONSTRAINT `survey_result_ibfk_3` FOREIGN KEY (`sid`) REFERENCES `surveys` (`sid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `survey_result` */

/*Table structure for table `survey_result_mod` */

DROP TABLE IF EXISTS `survey_result_mod`;

CREATE TABLE `survey_result_mod` (
  `id_mod` int(11) NOT NULL AUTO_INCREMENT,
  `rid` bigint(11) NOT NULL DEFAULT '0',
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
  `level` int(11) NOT NULL,
  `template` varchar(100) NOT NULL,
  `version` int(11) NOT NULL DEFAULT '1',
  `id_state` int(11) NOT NULL DEFAULT '2',
  PRIMARY KEY (`ssid`),
  KEY `ssid` (`ssid`,`version`),
  KEY `id_estado` (`id_state`),
  KEY `version` (`version`),
  CONSTRAINT `survey_section_ibfk_1` FOREIGN KEY (`id_state`) REFERENCES `cat_location` (`id_state`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `survey_section` */

/*Table structure for table `survey_section_type` */

DROP TABLE IF EXISTS `survey_section_type`;

CREATE TABLE `survey_section_type` (
  `stid` int(11) NOT NULL,
  `ssid` int(11) NOT NULL,
  `state` tinyint(4) NOT NULL,
  PRIMARY KEY (`stid`,`ssid`),
  KEY `stid` (`stid`),
  KEY `ssid` (`ssid`),
  CONSTRAINT `survey_section_type_ibfk_1` FOREIGN KEY (`stid`) REFERENCES `survey_type` (`stid`),
  CONSTRAINT `survey_section_type_ibfk_2` FOREIGN KEY (`ssid`) REFERENCES `survey_section` (`ssid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `survey_section_type` */

/*Table structure for table `survey_time` */

DROP TABLE IF EXISTS `survey_time`;

CREATE TABLE `survey_time` (
  `sequence` int(11) NOT NULL DEFAULT '0',
  `sid` int(11) NOT NULL DEFAULT '0',
  `elapsed_time` int(11) NOT NULL DEFAULT '0',
  `quitflag` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`sequence`,`sid`),
  KEY `Ref374` (`sid`),
  CONSTRAINT `survey_time_ibfk_1` FOREIGN KEY (`sid`) REFERENCES `surveys` (`sid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `survey_time` */

/*Table structure for table `survey_type` */

DROP TABLE IF EXISTS `survey_type`;

CREATE TABLE `survey_type` (
  `stid` int(11) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `label_graphic` varchar(255) DEFAULT NULL,
  `date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `template` varchar(255) DEFAULT NULL,
  `redirect_page` varchar(255) DEFAULT NULL,
  `number_copy` int(11) DEFAULT '1',
  `id_state` int(11) NOT NULL DEFAULT '2',
  `version` int(11) NOT NULL DEFAULT '1',
  `hash` varchar(255) DEFAULT NULL,
  `public` enum('S','N') NOT NULL DEFAULT 'N',
  PRIMARY KEY (`stid`),
  KEY `id_estado` (`id_state`),
  CONSTRAINT `survey_type_ibfk_1` FOREIGN KEY (`id_state`) REFERENCES `cat_location` (`id_state`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `survey_type` */

/*Table structure for table `surveys` */

DROP TABLE IF EXISTS `surveys`;

CREATE TABLE `surveys` (
  `sid` int(11) NOT NULL,
  `ticket` int(11) NOT NULL,
  `start_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `end_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `date_interview` date DEFAULT NULL,
  `uid` int(11) NOT NULL DEFAULT '0',
  `stid` int(11) NOT NULL,
  `tid` int(11) NOT NULL,
  `proyect_id` int(11) NOT NULL DEFAULT '1',
  `public` enum('S','N') DEFAULT 'N',
  `complete` enum('S','N') DEFAULT 'N',
  `id_state` int(11) NOT NULL,
  PRIMARY KEY (`sid`),
  KEY `Ref4029` (`uid`),
  KEY `Ref4842` (`stid`),
  KEY `tid` (`tid`),
  KEY `proyect_id` (`proyect_id`),
  KEY `id_state` (`id_state`),
  CONSTRAINT `surveys_ibfk_1` FOREIGN KEY (`tid`) REFERENCES `cat_location` (`tid`),
  CONSTRAINT `surveys_ibfk_2` FOREIGN KEY (`uid`) REFERENCES `sec_users` (`uid`),
  CONSTRAINT `surveys_ibfk_3` FOREIGN KEY (`stid`) REFERENCES `survey_type` (`stid`),
  CONSTRAINT `surveys_ibfk_4` FOREIGN KEY (`proyect_id`) REFERENCES `proyect` (`proyect_id`),
  CONSTRAINT `surveys_ibfk_5` FOREIGN KEY (`id_state`) REFERENCES `cat_state` (`id_state`)
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
