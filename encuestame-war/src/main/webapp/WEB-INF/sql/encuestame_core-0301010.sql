-- phpMyAdmin SQL Dump
-- version 3.2.3
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Oct 03, 2010 at 05:24 PM
-- Server version: 5.1.41
-- PHP Version: 5.3.2-1ubuntu4.5

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `encuestame_core`
--

-- --------------------------------------------------------

--
-- Table structure for table `cat_emails`
--

CREATE TABLE IF NOT EXISTS `cat_emails` (
  `email_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_at` datetime DEFAULT NULL,
  `email` varchar(255) NOT NULL,
  `emailAccount` varchar(255) DEFAULT NULL,
  `subscribed` bit(1) NOT NULL,
  `id_list` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`email_id`),
  UNIQUE KEY `email_id` (`email_id`),
  UNIQUE KEY `email` (`email`),
  KEY `FKC5448E065AD094F` (`id_list`),
  KEY `FKC5448E0D16F648F` (`id_list`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

--
-- Dumping data for table `cat_emails`
--


-- --------------------------------------------------------

--
-- Table structure for table `cat_list_emails`
--

CREATE TABLE IF NOT EXISTS `cat_list_emails` (
  `id_list` bigint(20) NOT NULL AUTO_INCREMENT,
  `createdAt` datetime DEFAULT NULL,
  `descripcionList` varchar(255) DEFAULT NULL,
  `list_name` varchar(255) DEFAULT NULL,
  `listState` varchar(255) DEFAULT NULL,
  `uid` bigint(20) NOT NULL,
  PRIMARY KEY (`id_list`),
  UNIQUE KEY `id_list` (`id_list`),
  KEY `FK4686F42F4E16F8CA` (`uid`),
  KEY `FK4686F42FE48EC40A` (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

--
-- Dumping data for table `cat_list_emails`
--


-- --------------------------------------------------------

--
-- Table structure for table `cat_location`
--

CREATE TABLE IF NOT EXISTS `cat_location` (
  `locate_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `accuracy` int(11) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `country_code` varchar(255) DEFAULT NULL,
  `country_name` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `lat` float DEFAULT NULL,
  `lng` float DEFAULT NULL,
  `location_status` varchar(255) DEFAULT NULL,
  `catLocationFolder_locate_folder_id` bigint(20) DEFAULT NULL,
  `secUsers_uid` bigint(20) DEFAULT NULL,
  `loc_id_type` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`locate_id`),
  UNIQUE KEY `locate_id` (`locate_id`),
  KEY `FK8C56C1FED9C8CC22` (`loc_id_type`),
  KEY `FK8C56C1FEED100E2` (`secUsers_uid`),
  KEY `FK8C56C1FE46D258DC` (`catLocationFolder_locate_folder_id`),
  KEY `FK8C56C1FE5E615762` (`loc_id_type`),
  KEY `FK8C56C1FEA548CC22` (`secUsers_uid`),
  KEY `FK8C56C1FE775141C` (`catLocationFolder_locate_folder_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=5 ;

--
-- Dumping data for table `cat_location`
--

INSERT INTO `cat_location` (`locate_id`, `accuracy`, `address`, `country_code`, `country_name`, `description`, `lat`, `lng`, `location_status`, `catLocationFolder_locate_folder_id`, `secUsers_uid`, `loc_id_type`) VALUES
(1, 6, 'Pista Las Brisas, Managua, Nicaragua', 'NI', 'Nicaragua', 'Linda Vista', 12.1529, -86.3046, 'ACTIVE', 2, 1, NULL),
(2, 4, 'Valle Dorado, Managua, Nicaragua', 'NI', 'Nicaragua', 'Valle Dorado', 12.1514, -86.3189, 'ACTIVE', 2, 1, NULL),
(3, 6, '7a Calle S.E, Managua, Nicaragua', 'NI', 'Nicaragua', 'dsa dsa dsa', 12.1461, -86.2695, 'ACTIVE', 2, 1, NULL),
(4, 4, 'Ciudad Sandino, Nicaragua', 'NI', 'Nicaragua', 'dsad as das das', 12.177, -86.3567, 'ACTIVE', 2, 1, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `cat_location_folder`
--

CREATE TABLE IF NOT EXISTS `cat_location_folder` (
  `locate_folder_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `type` varchar(255) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `secUsers_uid` bigint(20) DEFAULT NULL,
  `subLocationFolder_locate_folder_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`locate_folder_id`),
  UNIQUE KEY `locate_folder_id` (`locate_folder_id`),
  KEY `FK30F5BDCFED100E2` (`secUsers_uid`),
  KEY `FK30F5BDCF5A44CD32` (`subLocationFolder_locate_folder_id`),
  KEY `FK30F5BDCFA548CC22` (`secUsers_uid`),
  KEY `FK30F5BDCF1AE78872` (`subLocationFolder_locate_folder_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=4 ;

--
-- Dumping data for table `cat_location_folder`
--

INSERT INTO `cat_location_folder` (`locate_folder_id`, `type`, `name`, `secUsers_uid`, `subLocationFolder_locate_folder_id`) VALUES
(1, 'GROUPING', 'Zona 1', 1, NULL),
(2, 'GROUPING', 'Zona 2', 1, NULL),
(3, 'GROUPING', 'Zona 3', 1, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `cat_location_type`
--

CREATE TABLE IF NOT EXISTS `cat_location_type` (
  `loc_id_type` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `level` int(11) DEFAULT NULL,
  `users_uid` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`loc_id_type`),
  UNIQUE KEY `loc_id_type` (`loc_id_type`),
  KEY `FKC79A14DB63627213` (`users_uid`),
  KEY `FKC79A14DBF9DA3D53` (`users_uid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

--
-- Dumping data for table `cat_location_type`
--


-- --------------------------------------------------------

--
-- Table structure for table `cat_state`
--

CREATE TABLE IF NOT EXISTS `cat_state` (
  `id_state` bigint(20) NOT NULL AUTO_INCREMENT,
  `desc_state` varchar(255) DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id_state`),
  UNIQUE KEY `id_state` (`id_state`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

--
-- Dumping data for table `cat_state`
--

INSERT INTO `cat_state` (`id_state`, `desc_state`, `image`) VALUES
(1, 'Enabled', NULL),
(2, 'Disabled', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `cat_subscribe_emails`
--

CREATE TABLE IF NOT EXISTS `cat_subscribe_emails` (
  `id_subscribe` bigint(20) NOT NULL AUTO_INCREMENT,
  `hashCode` varchar(255) NOT NULL,
  `email_id` bigint(20) NOT NULL,
  `id_list` bigint(20) NOT NULL,
  PRIMARY KEY (`id_subscribe`),
  UNIQUE KEY `id_subscribe` (`id_subscribe`),
  KEY `FKF846A3565AD094F` (`id_list`),
  KEY `FKF846A35CED27DA9` (`email_id`),
  KEY `FKF846A35D16F648F` (`id_list`),
  KEY `FKF846A35A68478E9` (`email_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

--
-- Dumping data for table `cat_subscribe_emails`
--


-- --------------------------------------------------------

--
-- Table structure for table `client`
--

CREATE TABLE IF NOT EXISTS `client` (
  `client_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `facebook` varchar(255) DEFAULT NULL,
  `fax` varchar(255) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `telephone` varchar(255) DEFAULT NULL,
  `twitter` varchar(255) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `project_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`client_id`),
  UNIQUE KEY `client_id` (`client_id`),
  KEY `FKAF12F3CB50D897D8` (`project_id`),
  KEY `FKAF12F3CBE7506318` (`project_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

--
-- Dumping data for table `client`
--


-- --------------------------------------------------------

--
-- Table structure for table `hash_tags`
--

CREATE TABLE IF NOT EXISTS `hash_tags` (
  `hash_tag_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `tag` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`hash_tag_id`),
  UNIQUE KEY `hash_tag_id` (`hash_tag_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=52 ;

--
-- Dumping data for table `hash_tags`
--

INSERT INTO `hash_tags` (`hash_tag_id`, `tag`) VALUES
(1, 'juan'),
(2, 'juan 2'),
(3, 'juan 3'),
(4, 'dddasdsadsadsa'),
(5, 'dsadsadasdas'),
(6, 'dasdasdasda'),
(7, 'dsadsa'),
(8, 'tag1'),
(9, 'tag2'),
(10, 'tag3'),
(11, 'nicaragua'),
(12, 'canal10'),
(13, 'dsadas'),
(14, 'dsada'),
(15, 'dasdas'),
(16, 'lenin'),
(17, 'dasda'),
(18, 'sdsadsa'),
(19, 'dsad'),
(20, 'das'),
(21, 'dsa'),
(22, 'dasdasda'),
(23, 'dadas'),
(24, 'dasdsadsa'),
(25, 'dasdsa'),
(26, 'dsadsadsad'),
(27, 'dasdsad'),
(28, 'dsadasdsa'),
(29, 'dadasdsa'),
(30, 'dsadsadsa'),
(31, 'dsadsadas'),
(32, 'dsadsadsadasdsa'),
(33, 'dasddsadas'),
(34, 'nica'),
(35, 'mundial'),
(36, 'abstracto'),
(37, 'bra'),
(38, 'ni'),
(39, 'dsadsadsadsa'),
(40, ''),
(41, 'dasd'),
(42, 'fdsfdsfdsfds'),
(43, 'dasds'),
(44, 'dasdsadas'),
(45, 'dadsdsa'),
(46, 'dsads'),
(47, 'bbva'),
(48, 'ligaespañola'),
(49, 'realmadrid'),
(50, 'realsociedad'),
(51, 'movies');

-- --------------------------------------------------------

--
-- Table structure for table `notification`
--

CREATE TABLE IF NOT EXISTS `notification` (
  `notification_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `additional_description` varchar(255) NOT NULL,
  `created` datetime NOT NULL,
  `description` varchar(255) NOT NULL,
  `readed` bit(1) NOT NULL,
  `uid` bigint(20) NOT NULL,
  PRIMARY KEY (`notification_id`),
  UNIQUE KEY `notification_id` (`notification_id`),
  KEY `FK237A88EB4E16F8CA` (`uid`),
  KEY `FK237A88EBE48EC40A` (`uid`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=11 ;

--
-- Dumping data for table `notification`
--

INSERT INTO `notification` (`notification_id`, `additional_description`, `created`, `description`, `readed`, `uid`) VALUES
(1, ' das dsa dsadas das dsadas a', '2010-10-03 11:59:32', 'TWEETPOL_CREATED', '\0', 1),
(2, ' ds das as da das', '2010-10-03 12:02:42', 'TWEETPOL_CREATED', '\0', 1),
(3, ' ds das as da das', '2010-10-03 12:02:42', 'TWEETPOL_CREATED', '\0', 1),
(4, 'dsa dsa dsa dsadas dsa dasdas dsa das', '2010-10-03 15:30:39', 'TWEETPOL_CREATED', '\0', 1),
(5, 'dsa dsa dsa dsadas dsa dasdas dsa das', '2010-10-03 15:30:39', 'TWEETPOL_CREATED', '\0', 1),
(6, 'Updated to 7a Calle S.E, Managua, Nicaragua', '2010-10-03 15:54:35', 'LOCATION_GMAP_UPDATED', '\0', 1),
(7, 'Updated to Ciudad Sandino, Nicaragua', '2010-10-03 15:56:16', 'LOCATION_GMAP_UPDATED', '\0', 1),
(8, 'admin is the leader of this project.', '2010-10-03 15:59:11', 'PROJECT_CREATED', '\0', 1),
(9, 'New Folder Zona 3 is created.', '2010-10-03 16:00:18', 'LOCATION_FOLDER_NEW', '\0', 1),
(10, 'jpicado is the leader of this project.', '2010-10-03 16:12:51', 'PROJECT_CREATED', '\0', 1);

-- --------------------------------------------------------

--
-- Table structure for table `poll`
--

CREATE TABLE IF NOT EXISTS `poll` (
  `poll_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `closeAfterDate` bit(1) DEFAULT NULL,
  `close_after_quota` bit(1) DEFAULT NULL,
  `close_date` datetime DEFAULT NULL,
  `closed_quota` int(11) DEFAULT NULL,
  `custom_final_message` varchar(255) DEFAULT NULL,
  `custom_message` bit(1) DEFAULT NULL,
  `custom_start_message` varchar(255) DEFAULT NULL,
  `ip_protection` varchar(255) DEFAULT NULL,
  `ip_restrictions` bit(1) DEFAULT NULL,
  `multiple_response` varchar(255) DEFAULT NULL,
  `optional_title` varchar(255) DEFAULT NULL,
  `password_protection` varchar(255) DEFAULT NULL,
  `password_restrictions` bit(1) DEFAULT NULL,
  `show_progress_bar` bit(1) DEFAULT NULL,
  `close_notification` bit(1) DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `end_date` datetime DEFAULT NULL,
  `completed` bit(1) NOT NULL,
  `poll_hash` varchar(255) NOT NULL,
  `publish_poll` bit(1) DEFAULT NULL,
  `show_results` bit(1) DEFAULT NULL,
  `uid` bigint(20) NOT NULL,
  `qid` bigint(20) NOT NULL,
  `additionalInfo` varchar(255) DEFAULT NULL,
  `notifications` bit(1) DEFAULT NULL,
  `showAdditionalInfo` bit(1) DEFAULT NULL,
  `showComments` int(11) DEFAULT NULL,
  `showResults` bit(1) DEFAULT NULL,
  PRIMARY KEY (`poll_id`),
  UNIQUE KEY `poll_id` (`poll_id`),
  UNIQUE KEY `poll_hash` (`poll_hash`),
  KEY `FK3497BF4E16F8CA` (`uid`),
  KEY `FK3497BFF8456034` (`qid`),
  KEY `FK3497BFE48EC40A` (`uid`),
  KEY `FK3497BF30C6FCF4` (`qid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

--
-- Dumping data for table `poll`
--


-- --------------------------------------------------------

--
-- Table structure for table `poll_result`
--

CREATE TABLE IF NOT EXISTS `poll_result` (
  `poll_resultId` bigint(20) NOT NULL AUTO_INCREMENT,
  `ip_address` varchar(255) NOT NULL,
  `votation_date` datetime NOT NULL,
  `q_answer_id` bigint(20) NOT NULL,
  `poll_id` bigint(20) NOT NULL,
  PRIMARY KEY (`poll_resultId`),
  UNIQUE KEY `poll_resultId` (`poll_resultId`),
  KEY `FKD981C89D7500CE58` (`q_answer_id`),
  KEY `FKD981C89D4AC2255C` (`poll_id`),
  KEY `FKD981C89D8379AB18` (`q_answer_id`),
  KEY `FKD981C89D810221C` (`poll_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

--
-- Dumping data for table `poll_result`
--


-- --------------------------------------------------------

--
-- Table structure for table `project`
--

CREATE TABLE IF NOT EXISTS `project` (
  `project_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `hide_project` bit(1) DEFAULT NULL,
  `notify_members` bit(1) DEFAULT NULL,
  `priority` varchar(255) DEFAULT NULL,
  `date_finish` datetime DEFAULT NULL,
  `date_start` datetime NOT NULL,
  `description` varchar(600) NOT NULL,
  `info` text NOT NULL,
  `project_status` varchar(255) DEFAULT NULL,
  `lead_uid` bigint(20) DEFAULT NULL,
  `users_uid` bigint(20) DEFAULT NULL,
  `published` bit(1) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`project_id`),
  UNIQUE KEY `project_id` (`project_id`),
  KEY `FKED904B19B3444B07` (`lead_uid`),
  KEY `FKED904B1963627213` (`users_uid`),
  KEY `FKED904B19C1BD27C7` (`lead_uid`),
  KEY `FKED904B19F9DA3D53` (`users_uid`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=10 ;

--
-- Dumping data for table `project`
--

INSERT INTO `project` (`project_id`, `hide_project`, `notify_members`, `priority`, `date_finish`, `date_start`, `description`, `info`, `project_status`, `lead_uid`, `users_uid`, `published`, `name`) VALUES
(1, '', '', 'HIGH', '2010-09-24 00:00:00', '2010-09-01 12:00:00', 'das dsa dsa dsa dsa das', '<table style="margin: 0px; padding: 0px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: transparent; border-collapse: collapse;" border="0">\n<tbody style="margin: 0px; padding: 0px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: transparent;">\n<tr style="margin: 0px; padding: 0px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: transparent;">\n<td style="margin: 0px; padding: 0px; border-width: 0px; font-size: 11px; vertical-align: top; background-color: transparent; width: 60px;">\n<div style="margin: 0px; padding: 0px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: transparent; text-align: center;"><span style="margin: 0px; padding: 0px; border-width: 0px; font-size: 26px; vertical-align: baseline; background-color: transparent; display: block; color: #808185; font-weight: bold;"><br />2</span><span style="margin: 0px auto; padding: 0px; border-width: 0px; font-size: 1px; vertical-align: baseline; background-image: url(http://sstatic.net/stackoverflow/img/sprites.png); background-color: transparent; overflow: hidden; display: block; width: 41px; height: 25px; cursor: pointer; text-indent: -9999em; color: #808185; font-weight: bold; background-position: 0px -300px;" title="This question is unclear or not useful (click again to undo)">down vote</span><span style="margin: 0px auto; padding: 0px; border-width: 0px; font-size: 1px; vertical-align: baseline; background-image: url(http://sstatic.net/stackoverflow/img/sprites.png); background-color: transparent; overflow: hidden; display: block; width: 33px; height: 31px; cursor: pointer; text-indent: -9999em; color: #808185; font-weight: bold; background-position: 0px -150px;" title="This is a favorite question (click again to undo)">favorite</span>\n<div style="margin: 0px; padding: 0px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: transparent; text-align: center; color: #808185;"><strong style="margin: 0px; padding: 0px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: transparent; font-weight: bold;">2</strong></div>\n</div>\n</td>\n<td style="margin: 0px; padding: 0px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: transparent;">\n<div style="margin: 0px; padding: 0px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: transparent;">\n<div style="margin: 0px 5px 5px 0px; padding: 0px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: transparent; width: 660px; line-height: 14px;">\n<p style="margin: 0px 0px 1em; padding: 0px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: transparent; clear: both; word-wrap: break-word;">I am ha<span style="background-color: #ffff00;">ving trouble setting the type o</span>f a String, it goes like</p>\n<pre style="margin: 0px 0px 10px; padding: 5px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: #eeeeee; font-family: Consolas,Menlo,Monaco,''Lucida Console'',''Liberation Mono'',''DejaVu Sans Mono'',''Bitstream Vera Sans Mono'',''Courier New'',monospace,serif; overflow: auto; width: auto; max-height: 600px;"><code style="margin: 0px; padding: 0px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: #eeeeee; font-family: Consolas,Menlo,Monaco,''Lucida Console'',''Liberation Mono'',''DejaVu Sans Mono'',''Bitstream Vera Sans Mono'',''Courier New'',monospace,serif;"><span style="margin: 0px; padding: 0px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: transparent; color: #00008b;">public</span><span style="margin: 0px; padding: 0px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: transparent; color: black;"> </span><span style="margin: 0px; padding: 0px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: transparent; color: #00008b;">void</span><span style="margin: 0px; padding: 0px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: transparent; color: black;"> setTextDesc</span><span style="margin: 0px; padding: 0px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: transparent; color: black;">(</span><span style="margin: 0px; padding: 0px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: transparent; color: #2b91af;">String</span><span style="margin: 0px; padding: 0px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: transparent; color: black;"> textDesc</span><span style="margin: 0px; padding: 0px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: transparent; color: black;">)</span><span style="margin: 0px; padding: 0px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: transparent; color: black;"> </span><span style="margin: 0px; padding: 0px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: transparent; color: black;">{</span><span style="margin: 0px; padding: 0px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: transparent; color: black;"><br />&nbsp; &nbsp; &nbsp; &nbsp; </span><span style="margin: 0px; padding: 0px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: transparent; color: #00008b;">this</span><span style="margin: 0px; padding: 0px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: transparent; color: black;">.</span><span style="margin: 0px; padding: 0px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: transparent; color: black;">textDesc </span><span style="margin: 0px; padding: 0px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: transparent; color: black;">=</span><span style="margin: 0px; padding: 0px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: transparent; color: black;"> textDesc</span><span style="margin: 0px; padding: 0px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: transparent; color: black;">;</span><span style="margin: 0px; padding: 0px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: transparent; color: black;"><br /></span><span style="margin: 0px; padding: 0px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: transparent; color: black;">}</span><span style="margin: 0px; padding: 0px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: transparent; color: black;"><br /><br /></span><span style="margin: 0px; padding: 0px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: transparent; color: maroon;">@Column</span><span style="margin: 0px; padding: 0px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: transparent; color: black;">(</span><span style="margin: 0px; padding: 0px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: transparent; color: black;">name</span><span style="margin: 0px; padding: 0px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: transparent; color: black;">=</span><span style="margin: 0px; padding: 0px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: transparent; color: maroon;">"DESC"</span><span style="margin: 0px; padding: 0px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: transparent; color: black;">)</span><span style="margin: 0px; padding: 0px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: transparent; color: black;"><br /></span><span style="margin: 0px; padding: 0px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: transparent; color: maroon;">@Lob</span><span style="margin: 0px; padding: 0px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: transparent; color: black;"><br /></span><span style="margin: 0px; padding: 0px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: transparent; color: #00008b;">public</span><span style="margin: 0px; padding: 0px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: transparent; color: black;"> </span><span style="margin: 0px; padding: 0px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: transparent; color: #2b91af;">String</span><span style="margin: 0px; padding: 0px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: transparent; color: black;"> getTextDesc</span><span style="margin: 0px; padding: 0px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: transparent; color: black;">()</span><span style="margin: 0px; padding: 0px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: transparent; color: black;"> </span><span style="margin: 0px; padding: 0px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: transparent; color: black;">{</span><span style="margin: 0px; padding: 0px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: transparent; color: black;"><br />&nbsp; &nbsp; &nbsp; &nbsp; </span><span style="margin: 0px; padding: 0px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: transparent; color: #00008b;">return</span><span style="margin: 0px; padding: 0px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: transparent; color: black;"> textDesc</span><span style="margin: 0px; padding: 0px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: transparent; color: black;">;</span><span style="margin: 0px; padding: 0px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: transparent; color: black;"><br /></span><span style="margin: 0px; padding: 0px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: transparent; color: black;">}</span><span style="margin: 0px; padding: 0px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: transparent; color: black;"><br /></span></code></pre>\n<p style="margin: 0px 0px 1em; padding: 0px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: transparent; clear: both; word-wrap: break-word;">and it didn''t work, I checked the mysql schema and it remains varchar(255), I also tried,</p>\n<pre style="margin: 0px 0px 10px; padding: 5px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: #eeeeee; font-family: Consolas,Menlo,Monaco,''Lucida Console'',''Liberation Mono'',''DejaVu Sans Mono'',''Bitstream Vera Sans Mono'',''Courier New'',monospace,serif; overflow: auto; width: auto; max-height: 600px;"><code style="margin: 0px; padding: 0px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: #eeeeee; font-family: Consolas,Menlo,Monaco,''Lucida Console'',''Liberation Mono'',''DejaVu Sans Mono'',''Bitstream Vera Sans Mono'',''Courier New'',monospace,serif;"><span style="margin: 0px; padding: 0px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: transparent; color: maroon;">@Column</span><span style="margin: 0px; padding: 0px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: transparent; color: black;">(</span><span style="margin: 0px; padding: 0px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: transparent; color: black;">name</span><span style="margin: 0px; padding: 0px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: transparent; color: black;">=</span><span style="margin: 0px; padding: 0px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: transparent; color: maroon;">"DESC"</span><span style="margin: 0px; padding: 0px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: transparent; color: black;">,</span><span style="margin: 0px; padding: 0px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: transparent; color: black;"> length</span><span style="margin: 0px; padding: 0px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: transparent; color: black;">=</span><span style="margin: 0px; padding: 0px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: transparent; color: maroon;">"9000"</span><span style="margin: 0px; padding: 0px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: transparent; color: black;">)</span><span style="margin: 0px; padding: 0px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: transparent; color: black;"><br /></span></code></pre>\n<p style="margin: 0px 0px 1em; padding: 0px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: transparent; clear: both; word-wrap: break-word;">or</p>\n<pre style="margin: 0px 0px 10px; padding: 5px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: #eeeeee; font-family: Consolas,Menlo,Monaco,''Lucida Console'',''Liberation Mono'',''DejaVu Sans Mono'',''Bitstream Vera Sans Mono'',''Courier New'',monospace,serif; overflow: auto; width: auto; max-height: 600px;"><code style="margin: 0px; padding: 0px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: #eeeeee; font-family: Consolas,Menlo,Monaco,''Lucida Console'',''Liberation Mono'',''DejaVu Sans Mono'',''Bitstream Vera Sans Mono'',''Courier New'',monospace,serif;"><span style="margin: 0px; padding: 0px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: transparent; color: maroon;">@Column</span><span style="margin: 0px; padding: 0px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: transparent; color: black;">(</span><span style="margin: 0px; padding: 0px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: transparent; color: black;">name</span><span style="margin: 0px; padding: 0px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: transparent; color: black;">=</span><span style="margin: 0px; padding: 0px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: transparent; color: maroon;">"DESC"</span><span style="margin: 0px; padding: 0px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: transparent; color: black;">)</span><span style="margin: 0px; padding: 0px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: transparent; color: black;"><br /></span><span style="margin: 0px; padding: 0px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: transparent; color: maroon;">@Type</span><span style="margin: 0px; padding: 0px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: transparent; color: black;">(</span><span style="margin: 0px; padding: 0px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: transparent; color: black;">type</span><span style="margin: 0px; padding: 0px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: transparent; color: black;">=</span><span style="margin: 0px; padding: 0px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: transparent; color: maroon;">"text"</span><span style="margin: 0px; padding: 0px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: transparent; color: black;">)</span><span style="margin: 0px; padding: 0px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: transparent; color: black;"><br /></span></code></pre>\n<p style="margin: 0px 0px 1em; padding: 0px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: transparent; clear: both; word-wrap: break-word;">I am trying to make the type to be TEXT, any idea would be well appreciated!</p>\n</div>\n<div style="margin: 0px 0px 10px; padding: 0px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: transparent; clear: both;"><a style="margin: 2px 2px 2px 0px; padding: 3px 4px; border-width: 0px 1px 1px 0px; font-size: 10px; vertical-align: baseline; background-color: #e0eaf1; color: #3e6d8e; text-decoration: none; cursor: pointer; border-bottom: 1px solid #3e6d8e; border-right: 1px solid #7f9fb6; line-height: 2.4; white-space: nowrap;" title="show questions tagged ''hibernate''" rel="tag" href="http://stackoverflow.com/questions/tagged/hibernate">hibernate</a>&nbsp;<a style="margin: 2px 2px 2px 0px; padding: 3px 4px; border-width: 0px 1px 1px 0px; font-size: 10px; vertical-align: baseline; background-color: #e0eaf1; color: #3e6d8e; text-decoration: none; cursor: pointer; border-bottom: 1px solid #3e6d8e; border-right: 1px solid #7f9fb6; line-height: 2.4; white-space: nowrap;" title="show questions tagged ''annotations''" rel="tag" href="http://stackoverflow.com/questions/tagged/annotations">annotations</a>&nbsp;<a style="margin: 2px 2px 2px 0px; padding: 3px 4px; border-width: 0px 1px 1px 0px; font-size: 10px; vertical-align: baseline; background-color: #e0eaf1; color: #3e6d8e; text-decoration: none; cursor: pointer; border-bottom: 1px solid #3e6d8e; border-right: 1px solid #7f9fb6; line-height: 2.4; white-space: nowrap;" title="show questions tagged ''blob''" rel="tag" href="http://stackoverflow.com/questions/tagged/blob">blob</a></div>\n<table style="margin: 0px; padding: 0px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: transparent; border-collapse: collapse; width: 554px;" border="0">\n<tbody style="margin: 0px; padding: 0px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: transparent;">\n<tr style="margin: 0px; padding: 0px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: transparent;">\n<td style="margin: 0px; padding: 0px; border-width: 0px; font-size: 11px; vertical-align: top; background-color: transparent;">\n<div style="margin: 0px; padding: 2px 0px 0px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: transparent;"><a style="margin: 0px; padding: 0px 3px 2px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: transparent; color: #888888; text-decoration: none; cursor: pointer;" title="short permalink to this question" href="http://stackoverflow.com/q/1281188">link</a><span style="margin: 0px; padding: 0px; border-width: 0px; font-size: 12px; vertical-align: baseline; background-color: transparent; color: #cccccc;">|</span><a id="flag-post-1281188" style="margin: 0px; padding: 0px 3px 2px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: transparent; color: #888888; text-decoration: none; cursor: pointer;" title="flag this post for serious problems or moderator attention">flag</a></div>\n</td>\n<td style="margin: 0px; padding: 2px 0px 0px 5px; border-width: 0px; font-size: 11px; vertical-align: top; background-color: #e0eaf1; text-align: left; width: 175px; height: 58px;">\n<div style="margin: 0px; padding: 0px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: transparent; height: 35px; width: 185px;">\n<div style="margin: 2px 0px 4px; padding: 0px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: transparent;">asked&nbsp;<span style="margin: 0px; padding: 0px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: transparent; font-weight: bold; text-decoration: none;" title="2009-08-15 04:51:22Z">Aug 15 ''09 at 4:51</span></div>\n<div style="margin: 0px; padding: 0px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: transparent; float: left; width: 32px; height: 32px;"><a style="margin: 0px; padding: 0px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: transparent; color: #0077cc; text-decoration: none; cursor: pointer;" href="http://stackoverflow.com/users/125935/user125935"><img style="margin: 0px; padding: 0px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: transparent;" src="http://www.gravatar.com/avatar/83be033a578f599e12e25339e32ea2eb?s=32&amp;d=identicon&amp;r=PG" alt="" width="32" height="32" /></a></div>\n<div style="margin: 0px 0px 0px 5px; padding: 0px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: transparent; color: #888888; line-height: 17px; float: left; width: 145px; overflow: hidden; white-space: nowrap;"><a style="margin: 0px; padding: 0px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: transparent; color: #0077cc; text-decoration: none; cursor: pointer;" href="http://stackoverflow.com/users/125935/user125935">user125935</a><br /><span style="margin: 0px 2px 0px 0px; padding: 0px; border-width: 0px; font-size: 13px; vertical-align: baseline; background-color: transparent; font-weight: bold; color: #444444;" title="reputation score">39</span><span style="margin: 0px; padding: 0px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: transparent;" title="1 silver badge"><span style="margin: 0px; padding: 0px 0px 0px 1px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: transparent; color: #808185;">1</span></span><span style="margin: 0px; padding: 0px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: transparent;" title="7 bronze badges"><span style="margin: 0px; padding: 0px 0px 0px 1px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: transparent; color: #808185;">7</span></span></div>\n</div>\n<br style="clear: both;" />\n<div style="margin: 3px 0px 6px 37px; padding: 0px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: transparent; color: #999999 ! important;" title="this user has accepted an answer for 4 of 6 eligible questions">67% accept rate</div>\n</td>\n</tr>\n</tbody>\n</table>\n</div>\n</td>\n</tr>\n<tr style="margin: 0px; padding: 0px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: transparent;">\n<td style="margin: 0px; padding: 0px; border-width: 0px; font-size: 11px; vertical-align: top; background-color: transparent; width: 60px;"><br /></td>\n<td style="margin: 0px; padding: 0px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: transparent;"><br /></td>\n</tr>\n</tbody>\n</table>\n<p><span style="font-family: Arial,''Liberation Sans'',''DejaVu Sans'',sans-serif; font-size: 11px; line-height: 12px; text-align: left;">\n<div id="answers" style="margin: 0px; padding: 10px 0px 0px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: transparent; clear: both; width: 730px;"><a style="margin: 0px; padding: 0px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: transparent; color: #0077cc; text-decoration: none; cursor: pointer;" name="tab-top"></a>\n<div id="answers-header" style="margin: 10px 0px; padding: 0px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: transparent; width: 730px;">\n<div style="margin: 0px 0px -10px; padding: 0px; border-width: 0px 0px 1px; font-size: 11px; vertical-align: baseline; background-color: transparent; border-bottom: 1px solid #666666; height: 34px; clear: both;">\n<h2 style="margin: 0px; padding: 0px; border-width: 0px; font-size: 18px; vertical-align: baseline; background-color: transparent; font-family: ''Trebuchet MS'',''Liberation Sans'',''DejaVu Sans'',sans-serif; font-weight: bold; float: left; line-height: 34px;">1 Answer</h2>\n<div id="tabs" style="margin: 0px; padding: 0px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: transparent; float: right; font-weight: bold;"><a style="margin: 8px 8px 0px 0px; padding: 0px 11px; border: 1px solid #ffffff; font-size: 10px; vertical-align: baseline; color: #777777; text-decoration: none; cursor: pointer; background: inherit; display: block; float: left; height: 24px; line-height: 22px;" title="Answers in the order they were given" href="http://stackoverflow.com/questions/1281188?tab=oldest#tab-top">oldest</a><a style="margin: 8px 8px 0px 0px; padding: 0px 11px; border: 1px solid #ffffff; font-size: 10px; vertical-align: baseline; color: #777777; text-decoration: none; cursor: pointer; background: inherit; display: block; float: left; height: 24px; line-height: 22px;" title="Most recent answers first" href="http://stackoverflow.com/questions/1281188?tab=newest#tab-top">newest</a><a style="margin: 3px 8px 0px 0px; padding: 0px 11px; font-size: 13px; vertical-align: baseline; color: black; text-decoration: none; cursor: pointer; background: inherit; display: block; float: left; height: 30px; line-height: 28px;" title="Answers with the most votes first" href="http://stackoverflow.com/questions/1281188?tab=votes#tab-top">votes</a></div>\n</div>\n</div>\n<a style="margin: 0px; padding: 0px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: transparent; color: #0077cc; text-decoration: none; cursor: pointer;" name="1281216"></a></div>\n</span></p>\n<table style="margin: 0px; padding: 0px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: transparent; border-collapse: collapse;" border="0">\n<tbody style="margin: 0px; padding: 0px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: transparent;">\n<tr style="margin: 0px; padding: 0px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: transparent;">\n<td style="margin: 0px; padding: 0px; border-width: 0px; font-size: 11px; vertical-align: top; background-color: transparent; width: 60px;">\n<div style="margin: 0px; padding: 0px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: transparent; text-align: center;"><span style="margin: 0px auto; padding: 0px; border-width: 0px; font-size: 1px; vertical-align: baseline; background-image: url(http://sstatic.net/stackoverflow/img/sprites.png); background-color: transparent; overflow: hidden; display: block; width: 41px; height: 25px; cursor: pointer; text-indent: -9999em; color: #808185; font-weight: bold; background-position: 0px -265px;" title="This answer is useful (click again to undo)">up vote</span><span style="margin: 0px; padding: 0px; border-width: 0px; font-size: 26px; vertical-align: baseline; background-color: transparent; display: block; color: #808185; font-weight: bold;">7</span><span style="margin: 0px auto; padding: 0px; border-width: 0px; font-size: 1px; vertical-align: baseline; background-image: url(http://sstatic.net/stackoverflow/img/sprites.png); background-color: transparent; overflow: hidden; display: block; width: 41px; height: 25px; cursor: pointer; text-indent: -9999em; color: #808185; font-weight: bold; background-position: 0px -300px;" title="This answer is not useful (click again to undo)">down vote</span><span style="margin: 0px auto; padding: 0px; border-width: 0px; font-size: 1px; vertical-align: baseline; background-image: url(http://sstatic.net/stackoverflow/img/sprites.png); background-color: transparent; overflow: hidden; text-indent: -9999em; width: 39px; height: 40px; display: block; color: #808185; font-weight: bold; background-position: 0px -460px;" title="The question owner accepted this as the best answer Aug 15 ''09 at 5:59">accepted</span></div>\n</td>\n<td style="margin: 0px; padding: 0px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: transparent;">\n<div style="margin: 0px 5px 5px 0px; padding: 0px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: transparent; width: 660px; line-height: 14px;">\n<p style="margin: 0px 0px 1em; padding: 0px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: transparent; clear: both; word-wrap: break-word;">You said "I checked the mysql schema and it remains varchar(255)" - did you expect Hibernate to automatically alter your database? It won''t. Even if you have&nbsp;<code style="margin: 0px; padding: 1px 5px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: #eeeeee; font-family: Consolas,Menlo,Monaco,''Lucida Console'',''Liberation Mono'',''DejaVu Sans Mono'',''Bitstream Vera Sans Mono'',''Courier New'',monospace,serif;">hibernate.hbm2ddl.auto</code>&nbsp;set, I don''t believe Hibernate would alter the existing column definition.</p>\n<p style="margin: 0px 0px 1em; padding: 0px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: transparent; clear: both; word-wrap: break-word;">If you were to generate new database creation script,&nbsp;<code style="margin: 0px; padding: 1px 5px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: #eeeeee; font-family: Consolas,Menlo,Monaco,''Lucida Console'',''Liberation Mono'',''DejaVu Sans Mono'',''Bitstream Vera Sans Mono'',''Courier New'',monospace,serif;">@Lob</code>&nbsp;should generate "TEXT" type column if you don''t specify length explicitly (or if you do and it''s less that 65536). You can always force that by explicitly declaring type in<code style="margin: 0px; padding: 1px 5px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: #eeeeee; font-family: Consolas,Menlo,Monaco,''Lucida Console'',''Liberation Mono'',''DejaVu Sans Mono'',''Bitstream Vera Sans Mono'',''Courier New'',monospace,serif;">@Column</code>&nbsp;annotation, though keep in mind that''s not portable between databases:</p>\n<pre style="margin: 0px 0px 10px; padding: 5px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: #eeeeee; font-family: Consolas,Menlo,Monaco,''Lucida Console'',''Liberation Mono'',''DejaVu Sans Mono'',''Bitstream Vera Sans Mono'',''Courier New'',monospace,serif; overflow: auto; width: auto; max-height: 600px;"><code style="margin: 0px; padding: 0px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: #eeeeee; font-family: Consolas,Menlo,Monaco,''Lucida Console'',''Liberation Mono'',''DejaVu Sans Mono'',''Bitstream Vera Sans Mono'',''Courier New'',monospace,serif;"><span style="margin: 0px; padding: 0px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: transparent; color: maroon;">@Column</span><span style="margin: 0px; padding: 0px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: transparent; color: black;">(</span><span style="margin: 0px; padding: 0px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: transparent; color: black;">name</span><span style="margin: 0px; padding: 0px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: transparent; color: black;">=</span><span style="margin: 0px; padding: 0px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: transparent; color: maroon;">"DESC"</span><span style="margin: 0px; padding: 0px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: transparent; color: black;">,</span><span style="margin: 0px; padding: 0px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: transparent; color: black;"> columnDefinition</span><span style="margin: 0px; padding: 0px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: transparent; color: black;">=</span><span style="margin: 0px; padding: 0px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: transparent; color: maroon;">"TEXT"</span><span style="margin: 0px; padding: 0px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: transparent; color: black;">)</span><span style="margin: 0px; padding: 0px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: transparent; color: black;"><br /></span></code></pre>\n</div>\n<table style="margin: 0px; padding: 0px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: transparent; border-collapse: collapse; width: 554px;" border="0">\n<tbody style="margin: 0px; padding: 0px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: transparent;">\n<tr style="margin: 0px; padding: 0px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: transparent;">\n<td style="margin: 0px; padding: 0px; border-width: 0px; font-size: 11px; vertical-align: top; background-color: transparent;">\n<div style="margin: 0px; padding: 2px 0px 0px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: transparent;"><a style="margin: 0px; padding: 0px 3px 2px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: transparent; color: #888888; text-decoration: none; cursor: pointer;" title="permalink to this answer" href="http://stackoverflow.com/questions/1281188/text-field-using-hibernate-annotation/1281216#1281216">link</a><span style="margin: 0px; padding: 0px; border-width: 0px; font-size: 12px; vertical-align: baseline; background-color: transparent; color: #cccccc;">|</span><a id="flag-post-1281216" style="margin: 0px; padding: 0px 3px 2px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: transparent; color: #888888; text-decoration: none; cursor: pointer;" title="flag this post for serious problems or moderator attention">flag</a></div>\n</td>\n<td style="margin: 0px; padding: 2px 0px 0px 5px; border-width: 0px; font-size: 11px; vertical-align: top; background-color: transparent; text-align: left; width: 175px; height: 58px;" align="right">\n<div style="margin: 0px; padding: 0px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: transparent; height: 35px; width: 185px;">\n<div style="margin: 2px 0px 4px; padding: 0px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: transparent;">answered&nbsp;<span style="margin: 0px; padding: 0px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: transparent; font-weight: bold; text-decoration: none;" title="2009-08-15 05:12:28Z">Aug 15 ''09 at 5:12</span></div>\n<div style="margin: 0px; padding: 0px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: transparent; float: left; width: 32px; height: 32px;"><a style="margin: 0px; padding: 0px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: transparent; color: #0077cc; text-decoration: none; cursor: pointer;" href="http://stackoverflow.com/users/131368/chssply76"><img style="margin: 0px; padding: 0px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: transparent;" src="http://www.gravatar.com/avatar/d659a010156abf18fb2b1f66c19254a6?s=32&amp;d=identicon&amp;r=PG" alt="" width="32" height="32" /></a></div>\n<div style="margin: 0px 0px 0px 5px; padding: 0px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: transparent; color: #888888; line-height: 17px; float: left; width: 145px; overflow: hidden; white-space: nowrap;"><a style="margin: 0px; padding: 0px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: transparent; color: #0077cc; text-decoration: none; cursor: pointer;" href="http://stackoverflow.com/users/131368/chssply76">ChssPly76</a><br /><span style="margin: 0px 2px 0px 0px; padding: 0px; border-width: 0px; font-size: 13px; vertical-align: baseline; background-color: transparent; font-weight: bold; color: #444444;" title="reputation score 23020">23k</span><span style="margin: 0px; padding: 0px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: transparent;" title="3 gold badges"><span style="margin: 0px; padding: 0px 0px 0px 1px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: transparent; color: #808185;">3</span></span><span style="margin: 0px; padding: 0px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: transparent;" title="16 silver badges"><span style="margin: 0px; padding: 0px 0px 0px 1px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: transparent; color: #808185;">16</span></span><span style="margin: 0px; padding: 0px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: transparent;" title="34 bronze badges"><span style="margin: 0px; padding: 0px 0px 0px 1px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: transparent; color: #808185;">34</span></span></div>\n</div>\n</td>\n</tr>\n</tbody>\n</table>\n</td>\n</tr>\n<tr style="margin: 0px; padding: 0px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: transparent;">\n<td style="margin: 0px; padding: 0px; border-width: 0px; font-size: 11px; vertical-align: top; background-color: transparent; width: 60px;"><br /></td>\n<td style="margin: 0px; padding: 0px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: transparent;">\n<div id="comments-1281216" style="margin: 10px 0px 0px; padding: 0px 0px 10px; border-width: 1px 0px 0px; font-size: 11px; vertical-align: baseline; background-color: transparent; width: 660px; border-top: 1px dotted #aaaaaa; line-height: 14px; color: #444444;">\n<table style="margin: 0px; padding: 0px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: transparent; border-collapse: collapse; width: 550px;" border="0">\n<tbody style="margin: 0px; padding: 0px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: transparent;">\n<tr id="comment-1110432" style="margin: 0px; padding: 0px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: transparent;">\n<td style="margin: 0px; padding: 5px 0px; border-width: 0px 0px 1px; font-size: 11px; vertical-align: baseline; background-color: transparent; border-bottom: 1px dotted #dddddd;"><br /></td>\n<td style="margin: 0px; padding: 5px 6px 5px 7px; border-width: 0px 0px 1px; font-size: 11px; vertical-align: text-top; background-color: transparent; border-bottom: 1px dotted #dddddd;">\n<div style="margin: 0px; padding: 0px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: transparent;">right, I dropped the database everytime I change the annotations, with hbm2ddl.auto on, so I expect that it would change when I check again (sorry for the ambiguity). Somehow, columnDefinition didn''t work for me neither. Am I missing something else? thanks for the input, btw. &ndash;&nbsp;<a style="margin: 0px; padding: 2px 5px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: #e0eaf1; color: #0077cc; text-decoration: none; cursor: pointer; white-space: nowrap;" title="39 reputation" href="http://stackoverflow.com/users/125935/user125935">user125935</a>&nbsp;<span style="margin: 0px; padding: 0px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: transparent; color: #999999;"><span style="margin: 0px; padding: 0px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: transparent;" title="2009-08-15 05:23:45Z">Aug 15 ''09 at 5:23</span></span></div>\n</td>\n</tr>\n<tr id="comment-1110458" style="margin: 0px; padding: 0px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: transparent;">\n<td style="margin: 0px; padding: 5px 0px; border-width: 0px 0px 1px; font-size: 11px; vertical-align: baseline; background-color: transparent; border-bottom: 1px dotted #dddddd;"><br /></td>\n<td style="margin: 0px; padding: 5px 6px 5px 7px; border-width: 0px 0px 1px; font-size: 11px; vertical-align: text-top; background-color: transparent; border-bottom: 1px dotted #dddddd;">\n<div style="margin: 0px; padding: 0px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: transparent;">Specifying&nbsp;<code style="margin: 0px; padding: 1px 5px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: #eeeeee; font-family: Consolas,Menlo,Monaco,''Lucida Console'',''Liberation Mono'',''DejaVu Sans Mono'',''Bitstream Vera Sans Mono'',''Courier New'',monospace,serif;">columnDefinition</code>&nbsp;is as explicit as you can get - there must be something else going on. Are you sure your compiled class is getting picked up by Hibernate correctly? Is it extending / being extended by some other class? Can''t think of anything else that might cause this off hand, but if you can post your entire source for this class, your Hibernate configuration and describe how you''re running this, I''ll certainly see if anything jumps at me. Also can you try adding a bogus property to this class and generating schema as SQL file to see if it appears there (sanity check)? &ndash;&nbsp;<a style="margin: 0px; padding: 0px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: transparent; color: #0077cc; text-decoration: none; cursor: pointer; white-space: nowrap;" title="23020 reputation" href="http://stackoverflow.com/users/131368/chssply76">ChssPly76</a>&nbsp;<span style="margin: 0px; padding: 0px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: transparent; color: #999999;"><span style="margin: 0px; padding: 0px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: transparent;" title="2009-08-15 05:49:28Z">Aug 15 ''09 at 5:49</span></span></div>\n</td>\n</tr>\n<tr id="comment-1110469" style="margin: 0px; padding: 0px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: transparent;">\n<td style="margin: 0px; padding: 5px 0px; border-width: 0px 0px 1px; font-size: 11px; vertical-align: baseline; background-color: transparent; border-bottom: 1px dotted #dddddd;"><br /></td>\n<td style="margin: 0px; padding: 5px 6px 5px 7px; border-width: 0px 0px 1px; font-size: 11px; vertical-align: text-top; background-color: transparent; border-bottom: 1px dotted #dddddd;">\n<div style="margin: 0px; padding: 0px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: transparent;">I finally got it to work by putting @Indexed on the top of the class, but I can''t reason to myself why that is.... &ndash;&nbsp;<a style="margin: 0px; padding: 2px 5px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: #e0eaf1; color: #0077cc; text-decoration: none; cursor: pointer; white-space: nowrap;" title="39 reputation" href="http://stackoverflow.com/users/125935/user125935">user125935</a>&nbsp;<span style="margin: 0px; padding: 0px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: transparent; color: #999999;"><span style="margin: 0px; padding: 0px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: transparent;" title="2009-08-15 05:58:17Z">Aug 15 ''09 at 5:58</span></span></div>\n</td>\n</tr>\n<tr id="comment-1110470" style="margin: 0px; padding: 0px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: transparent;">\n<td style="margin: 0px; padding: 5px 0px; border-width: 0px 0px 1px; font-size: 11px; vertical-align: baseline; background-color: transparent; border-bottom: 1px dotted #dddddd;"><br /></td>\n<td style="margin: 0px; padding: 5px 6px 5px 7px; border-width: 0px 0px 1px; font-size: 11px; vertical-align: text-top; background-color: transparent; border-bottom: 1px dotted #dddddd;">\n<div style="margin: 0px; padding: 0px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: transparent;">Thank you so much by the way! &ndash;&nbsp;<a style="margin: 0px; padding: 2px 5px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: #e0eaf1; color: #0077cc; text-decoration: none; cursor: pointer; white-space: nowrap;" title="39 reputation" href="http://stackoverflow.com/users/125935/user125935">user125935</a>&nbsp;<span style="margin: 0px; padding: 0px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: transparent; color: #999999;"><span style="margin: 0px; padding: 0px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: transparent;" title="2009-08-15 05:59:06Z">Aug 15 ''09 at 5:59</span></span></div>\n</td>\n</tr>\n<tr id="comment-1110481" style="margin: 0px; padding: 0px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: transparent;">\n<td style="margin: 0px; padding: 5px 0px; border-width: 0px 0px 1px; font-size: 11px; vertical-align: baseline; background-color: transparent; border-bottom: 1px dotted #dddddd;"><br /></td>\n<td style="margin: 0px; padding: 5px 6px 5px 7px; border-width: 0px 0px 1px; font-size: 11px; vertical-align: text-top; background-color: transparent; border-bottom: 1px dotted #dddddd;">\n<div style="margin: 0px; padding: 0px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: transparent;">You''re very welcome, but you''ve got me very curious now :-) What is&nbsp;<code style="margin: 0px; padding: 1px 5px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: #eeeeee; font-family: Consolas,Menlo,Monaco,''Lucida Console'',''Liberation Mono'',''DejaVu Sans Mono'',''Bitstream Vera Sans Mono'',''Courier New'',monospace,serif;">@Indexed</code>? I''m pretty sure that''s not a Hibernate or JPA annotation. If you meant&nbsp;<code style="margin: 0px; padding: 1px 5px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: #eeeeee; font-family: Consolas,Menlo,Monaco,''Lucida Console'',''Liberation Mono'',''DejaVu Sans Mono'',''Bitstream Vera Sans Mono'',''Courier New'',monospace,serif;">@Index</code>, then I truly fail to see how that would change anything with regard to above issue... If you figure out what the problem was I''d appreciate if you post it as an answer here - I promise to upvote it :-) &ndash;&nbsp;<a style="margin: 0px; padding: 0px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: transparent; color: #0077cc; text-decoration: none; cursor: pointer; white-space: nowrap;" title="23020 reputation" href="http://stackoverflow.com/users/131368/chssply76">ChssPly76</a>&nbsp;<span style="margin: 0px; padding: 0px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: transparent; color: #999999;"><span style="margin: 0px; padding: 0px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: transparent;" title="2009-08-15 06:08:06Z">Aug 15 ''09 at 6:08</span></span></div>\n</td>\n</tr>\n</tbody>\n</table>\n</div>\n<a id="comments-link-1281216" style="margin: 0px; padding: 0px 3px 2px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: transparent; color: #888888; text-decoration: none; cursor: pointer;" title="expand to show all comments on this post">show&nbsp;<strong style="margin: 0px; padding: 0px; border-width: 0px; font-size: 11px; vertical-align: baseline; background-color: transparent; font-weight: bold;">1</strong>&nbsp;more comment</a></td>\n</tr>\n</tbody>\n</table>', 'ACTIVE', 14, 1, '', 'Proyecto Encuestame2');
INSERT INTO `project` (`project_id`, `hide_project`, `notify_members`, `priority`, `date_finish`, `date_start`, `description`, `info`, `project_status`, `lead_uid`, `users_uid`, `published`, `name`) VALUES
(2, '\0', '\0', 'MEDIUM', '2010-09-20 12:00:00', '2010-09-06 12:00:00', 'descr', '<p><span style="font-family: Arial,Helvetica,sans-serif; font-size: 10px;">\n<div style="margin: 0px 0px 0px 12px; padding: 0px; background-color: #ffffff; color: #464646;">\n<h1 style="margin: 0px 10px 0px 0px; padding: 4px 0px; font: 380%/36px Georgia,''Times New Roman'',Times,serif; color: #000000; letter-spacing: -1px;">La alta participaci&oacute;n eleva la incertidumbre sobre el resultado en Madrid</h1>\n<h3 style="margin: 0px 10px 0px 0px; padding: 0px; font-size: 17px; font-weight: normal;">A media tarde ya ha votado el 60% de los 18.000 militantes. - La jornada electoral transcurre con normalidad</h3>\n<div style="margin: 10px 0px 0px; padding: 0px 0px 2px;">\n<p style="margin: 0px; padding: 0px; font: 110% Georgia,''Times New Roman'',Times,serif; color: #414141; text-align: left;"><strong style="margin: 0px; padding: 0px; font-size: 10px; font-weight: bold;">J. S&Eacute;RVULO GONZ&Aacute;LEZ / PILAR &Aacute;LVAREZ</strong><span>&nbsp;</span><em style="margin: 0px; padding: 0px; font-size: 10px; font-style: normal;">- Madrid -<span>&nbsp;</span></em>03/10/2010</p>\n</div>\n<div style="margin: 0px; padding: 0px; clear: both; font-size: 1px; line-height: 1px; height: 1px; font-weight: normal;">&nbsp;</div>\n</div>\n<div style="margin: 0px; padding: 0px;">\n<div style="margin: 0px; padding: 0px; width: 646px; float: left; display: inline; overflow: visible;">\n<div style="margin: 0px 12px; padding: 0px;">\n<div style="margin: 6px 0px 16px; padding: 0px; position: relative; height: 21px;">\n<div id="votosC" style="margin: 0px; padding: 0px;">\n<div style="margin: 0px; padding: 0px; float: left; display: inline; width: 355px; font: 11px/18px Arial,Helvetica,sans-serif; color: #757575;">\n<div style="margin: 0px 5px 0px 0px; padding: 0px; float: left; display: inline;">\n<div style="margin: 0px; padding: 0px; float: left; display: inline;">Vota</div>\n<div style="margin: 0px 4px 0px 3px; padding: 0px; position: relative; background-image: url(http://www.elpais.com/im/ico_vot_vo.gif); width: 76px; height: 20px; float: left; display: inline;"><span id="bhs" style="margin: 0px; padding: 0px;">&nbsp;</span></div>\n<img style="margin: -1px 0px 0px; padding: 0px; border-width: 0px; vertical-align: middle; width: auto; height: auto;" src="http://www.elpais.com/im/ico_separador_horizontal.gif" alt="" width="1" height="13" /></div>\n<div style="margin: 0px; padding: 0px; float: left; display: inline;">Resultado<span>&nbsp;</span><img style="margin: -1px 0px 0px; padding: 0px; border-width: 0px; vertical-align: middle; width: auto; height: auto;" title="Sin inter&eacute;s" src="http://www.elpais.com/im/ico_vot_ok.gif" alt="Sin inter&eacute;s" /><img style="margin: -1px 0px 0px; padding: 0px; border-width: 0px; vertical-align: middle; width: auto; height: auto;" title="Poco interesante" src="http://www.elpais.com/im/ico_vot_ok.gif" alt="Poco interesante" /><img style="margin: -1px 0px 0px; padding: 0px; border-width: 0px; vertical-align: middle; width: auto; height: auto;" title="De inter&eacute;s" src="http://www.elpais.com/im/ico_vot_ok.gif" alt="De inter&eacute;s" /><img style="margin: -1px 0px 0px; padding: 0px; border-width: 0px; vertical-align: middle; width: auto; height: auto;" title="Muy interesante" src="http://www.elpais.com/im/ico_vot_md.gif" alt="Muy interesante" /><img style="margin: -1px 0px 0px; padding: 0px; border-width: 0px; vertical-align: middle; width: auto; height: auto;" title="Imprescindible" src="http://www.elpais.com/im/ico_vot_no.gif" alt="Imprescindible" /><span>&nbsp;</span>21 votos</div>\n</div>\n</div>\n<div style="margin: 0px; padding: 0px; float: right; height: 19px; font: 110% Tahoma,Arial,Helvetica,sans-serif;"><a style="margin: 0px; padding: 0px; color: #4c7094; text-decoration: none; outline-style: none; font-family: Tahoma,Arial,Helvetica,sans-serif;" title="Comentarios" href="http://www.elpais.com/articulo/espana/alta/participacion/eleva/incertidumbre/resultado/Madrid/elpepuesp/20101003elpepunac_2/Tes#EnlaceComentarios"><img style="margin: -1px 0px 0px; padding: 0px; border-width: 0px; vertical-align: middle; width: auto; height: auto;" src="http://www.elpais.com/im/utilidades/ico_comentarios.gif" alt="Comentarios" width="19" height="19" />&nbsp;Comentarios -<span>&nbsp;</span><strong style="margin: 0px; padding: 0px;">39</strong></a><span>&nbsp;</span>&nbsp;<img style="margin: -1px 0px 0px; padding: 0px; border-width: 0px; vertical-align: middle; width: auto; height: auto;" src="http://www.elpais.com/im/ico_separador_horizontal.gif" alt="" width="1" height="13" />&nbsp;	<a style="margin: 0px; padding: 0px; color: #4c7094; text-decoration: none; outline-style: none; font-family: Tahoma,Arial,Helvetica,sans-serif;" href="http://www.elpais.com/articulo/espana/alta/participacion/eleva/incertidumbre/resultado/Madrid/elpepuesp/20101003elpepunac_2/Tes?print=1"><img style="margin: -1px 0px 0px; padding: 0px; border-width: 0px; vertical-align: middle; width: auto; height: auto;" src="http://www.elpais.com/im/utilidades/ico_imprimir.gif" border="0" alt="Imprimir" /></a> &nbsp;<span>&nbsp;</span><a style="margin: 0px; padding: 0px; color: #4c7094; text-decoration: none; outline-style: none; font-family: Tahoma,Arial,Helvetica,sans-serif;" title="Enviar" href="http://www.elpais.com/envios/enviar_noticia/index.html?xref=20101003elpepunac_2.Tes&amp;type=&amp;anchor=elpepuesp&amp;d_date=&amp;aP=modulo%3DEnviar%26params%3Dxref%253D20101003elpepunac_2.Tes%2526type%253D%2526d_date%253D%2526anchor%253D"><img style="margin: -1px 0px 0px; padding: 0px; border-width: 0px; vertical-align: middle; width: auto; height: auto;" src="http://www.elpais.com/im/utilidades/ico_enviar.gif" border="0" alt="Enviar" /></a></div>\n</div>\n<p style="margin: 0px 0px 13px; padding: 0px; font: 160%/140% Georgia,''Times New Roman'',Times,serif; color: #333333;">Durante siete semanas, el secretario general del PSM, Tom&aacute;s G&oacute;mez, y la ministra de Sanidad, Trinidad Jim&eacute;nez, han mantenido un f&eacute;rreo pulso para recabar apoyos para las elecciones primarias que se celebran desde las diez de la ma&ntilde;ana. En consonancia con esa intensa campa&ntilde;a, la jornada electoral est&aacute; registrando una participaci&oacute;n que eleva a&uacute;n m&aacute;s la incertidumbre sobre qui&eacute;n ser&aacute; el candidato socialista en los pr&oacute;ximos comicios a la presidencia de Madrid, en mayo de 2011.</p>\n<div style="margin: 9px 14px 0px 0px; padding: 0px; width: 252px; float: left; display: inline; background-color: #ffffff; clear: left;">\n<div style="margin: 0px; padding: 0px;">\n<ul style="margin: 0px 0px 15px; padding: 0px; list-style-type: none;">\n<li style="margin: 0px 0px 7px; padding: 0px 0px 0px 23px; background-image: url(http://www.elpais.com/im/ico_lista_despiece.gif); font: 140% Georgia,''Times New Roman'',Times,serif; background-position: 8px 5px;"><a style="margin: 0px; padding: 0px; color: #134d86; text-decoration: none; outline-style: none;" href="http://www.elpais.com/articulo/espana/alta/participacion/eleva/incertidumbre/resultado/Madrid/elpepuesp/20101003elpepunac_2/Tes#despiece1">Unos candidatos olvidadizos</a></li>\n</ul>\n</div>\n<div style="margin: 0px; padding: 0px;">\n<ul style="margin: 0px 0px 15px; padding: 0px; list-style-type: none;">\n<li style="margin: 0px 0px 7px; padding: 0px 0px 0px 23px; background-image: url(http://www.elpais.com/im/ico_lista_hermanas.gif); font: 140% Georgia,''Times New Roman'',Times,serif; background-position: 11px 7px;"><a style="margin: 0px; padding: 0px; color: #134d86; text-decoration: none; outline-style: none;" href="http://www.elpais.com/articulo/espana/Maxima/expectacion/PSOE/resultado/Madrid/elpepuesp/20101003elpepunac_1/Tes">M&aacute;xima expectaci&oacute;n en el PSOE por el resultado de Madrid</a></li>\n<li style="margin: 0px 0px 7px; padding: 0px 0px 0px 23px; background-image: url(http://www.elpais.com/im/ico_lista_hermanas.gif); font: 140% Georgia,''Times New Roman'',Times,serif; background-position: 11px 7px;"><a style="margin: 0px; padding: 0px; color: #134d86; text-decoration: none; outline-style: none;" href="http://www.elpais.com/articulo/espana/votaciones/resultados/primarias/directo/elpepuesp/20101002elpepunac_1/Tes">Las votaciones y los resultados de las primarias, en directo</a></li>\n</ul>\n</div>\n<div style="margin: 0px 0px 20px; padding: 0px; border-width: 1px 1px 0px; border-top: 1px solid #cccccc; border-right: 1px solid #cccccc; border-left: 1px solid #cccccc;">\n<div style="margin: 0px; padding: 0px 0px 6px; border-bottom: 1px solid #cccccc;">\n<div style="margin: 6px 0px 0px 6px; padding: 0px; float: left; display: inline; position: relative;"><a style="margin: 0px; padding: 0px; color: #134d86; text-decoration: none; outline-style: none;" href="http://www.elpais.com/todo-sobre/persona/Trinidad/Jimenez/217/"><img style="margin: 0px; padding: 0px; border-width: 0px;" title="Trinidad Jim&eacute;nez Garc&iacute;a-Herrera" src="http://www.elpais.com/fotos/personas/ign/2/151_217.jpg" border="0" alt="Trinidad Jim&eacute;nez Garc&iacute;a-Herrera" width="80" height="80" /></a></div>\n<div style="margin: 3px 5px 0px 0px; padding: 0px 0px 0px 5px; display: table; height: 74px;">\n<h3 style="margin: 0px 0px 1px 2px; padding: 5px 0px 0px; font: 170%/110% Georgia,''Times New Roman'',Times,serif,''Trebuchet MS'';"><a style="margin: 0px; padding: 0px; color: #134d86; text-decoration: none; outline-style: none;" href="http://www.elpais.com/todo-sobre/persona/Trinidad/Jimenez/217/">Trinidad Jim&eacute;nez</a></h3>\n<h4 style="margin: 0px 0px 5px 2px; padding: 0px; font: 110% Georgia,''Times New Roman'',Times,serif,''Trebuchet MS''; color: #5e5b54;">A FONDO</h4>\n<dl style="margin: 0px; padding: 0px; font-size: 12px; color: #646464; height: 0px;"><dt style="margin: 0px; padding: 0px 5px 0px 0px; float: left; clear: both; height: 15px;">Nacimiento:</dt><dd style="margin: 0px; padding: 0px; float: left;">04-06-1962</dd></dl><dl style="margin: 0px; padding: 0px; font-size: 12px; color: #646464; height: 0px;"><dt style="margin: 0px; padding: 0px 5px 0px 0px; float: left; clear: both; height: 15px;">Lugar:</dt><dd style="margin: 0px; padding: 0px; float: left;">M&aacute;laga</dd></dl></div>\n</div>\n<div style="margin: 0px; padding: 0px 0px 6px; border-bottom: 1px solid #cccccc;">\n<div style="margin: 6px 0px 0px 6px; padding: 0px; float: left; display: inline; position: relative;"><a style="margin: 0px; padding: 0px; color: #134d86; text-decoration: none; outline-style: none;" href="http://www.elpais.com/todo-sobre/persona/Tomas/Gomez/Franco/3148/"><img style="margin: 0px; padding: 0px; border-width: 0px;" title="Tom&aacute;s G&oacute;mez Franco" src="http://www.elpais.com/fotos/personas/ign/31/151_3148.jpg" border="0" alt="Tom&aacute;s G&oacute;mez Franco" width="80" height="80" /></a></div>\n<div style="margin: 3px 5px 0px 0px; padding: 0px 0px 0px 5px; display: table; height: 92px;">\n<h3 style="margin: 0px 0px 1px 2px; padding: 5px 0px 0px; font: 170%/110% Georgia,''Times New Roman'',Times,serif,''Trebuchet MS'';"><a style="margin: 0px; padding: 0px; color: #134d86; text-decoration: none; outline-style: none;" href="http://www.elpais.com/todo-sobre/persona/Tomas/Gomez/Franco/3148/">Tom&aacute;s G&oacute;mez Franco</a></h3>\n<h4 style="margin: 0px 0px 5px 2px; padding: 0px; font: 110% Georgia,''Times New Roman'',Times,serif,''Trebuchet MS''; color: #5e5b54;">A FONDO</h4>\n<dl style="margin: 0px; padding: 0px; font-size: 12px; color: #646464; height: 0px;"><dt style="margin: 0px; padding: 0px 5px 0px 0px; float: left; clear: both; height: 15px;">Nacimiento:</dt><dd style="margin: 0px; padding: 0px; float: left;">27-03-1968</dd></dl><dl style="margin: 0px; padding: 0px; font-size: 12px; color: #646464; height: 0px;"><dt style="margin: 0px; padding: 0px 5px 0px 0px; float: left; clear: both; height: 15px;">Lugar:</dt><dd style="margin: 0px; padding: 0px; float: left;">Enschede</dd></dl></div>\n</div>\n<div style="margin: 0px; padding: 0px 0px 6px; border-bottom: 1px solid #cccccc;">\n<div style="margin: 3px 5px 0px 0px; padding: 0px 0px 0px 5px; display: table; height: 95px;">\n<h3 style="margin: 0px 0px 1px 2px; padding: 5px 0px 0px; font: 170%/110% Georgia,''Times New Roman'',Times,serif,''Trebuchet MS'';"><a style="margin: 0px; padding: 0px; color: #134d86; text-decoration: none; outline-style: none;" href="http://www.elpais.com/todo-sobre/organismo/PSOE/Partido/Socialista/Obrero/Espanol/86/">PSOE</a></h3>\n(Partido Socialista Obrero Espa&ntilde;ol)<br style="margin: 0px; padding: 0px;" />\n<h4 style="margin: 0px 0px 5px 2px; padding: 0px; font: 110% Georgia,''Times New Roman'',Times,serif,''Trebuchet MS''; color: #5e5b54;">A FONDO</h4>\n<dl style="margin: 0px; padding: 0px; font-size: 12px; color: #646464; height: 0px;"><dt style="margin: 0px; padding: 0px 5px 0px 0px; float: left; clear: both; height: 15px;">Sede:</dt><dd style="margin: 0px; padding: 0px; float: left;">Madrid (Espa&ntilde;a)</dd></dl>\n<p style="margin: 4px 0px 0px 2px; padding: 5px 0px 0px; font: 110%/128% Tahoma,Arial,Helvetica,sans-serif; color: #616161;"><a style="margin: 0px; padding: 0px; color: #134d86; text-decoration: underline; outline-style: none;" href="http://www.elpais.com/todo-sobre/organismo/PSOE/Partido/Socialista/Obrero/Espanol/86/"><strong style="margin: 0px; padding: 0px;"><img style="margin: 0px; padding: 0px; border-width: 0px;" src="http://www.elpais.com/im/ico_enlace.gif" alt="Enlace" width="8" height="9" /><span>&nbsp;</span>Ver cobertura completa</strong></a></p>\n</div>\n</div>\n<div style="margin: 0px; padding: 0px 0px 6px; border-bottom: 1px solid #cccccc;">\n<div style="margin: 3px 5px 0px 0px; padding: 0px 0px 0px 5px; display: table; height: 75px;">\n<h3 style="margin: 0px 0px 1px 2px; padding: 5px 0px 0px; font: 170%/110% Georgia,''Times New Roman'',Times,serif,''Trebuchet MS'';"><a style="margin: 0px; padding: 0px; color: #134d86; text-decoration: none; outline-style: none;" href="http://www.elpais.com/todo-sobre/organismo/FSM/Federacion/Socialista/Madrilena/482/">FSM</a></h3>\n(Federaci&oacute;n Socialista Madrile&ntilde;a)<br style="margin: 0px; padding: 0px;" />\n<h4 style="margin: 0px 0px 5px 2px; padding: 0px; font: 110% Georgia,''Times New Roman'',Times,serif,''Trebuchet MS''; color: #5e5b54;">A FONDO</h4>\n<p style="margin: 4px 0px 0px 2px; padding: 5px 0px 0px; font: 110%/128% Tahoma,Arial,Helvetica,sans-serif; color: #616161;"><a style="margin: 0px; padding: 0px; color: #134d86; text-decoration: underline; outline-style: none;" href="http://www.elpais.com/todo-sobre/organismo/FSM/Federacion/Socialista/Madrilena/482/"><strong style="margin: 0px; padding: 0px;"><img style="margin: 0px; padding: 0px; border-width: 0px;" src="http://www.elpais.com/im/ico_enlace.gif" alt="Enlace" width="8" height="9" /><span>&nbsp;</span>Ver cobertura completa</strong></a></p>\n</div>\n</div>\n</div>\n<div style="margin: 0px 0px 15px; padding: 0px; border: 1px solid #cccccc;">\n<h3 style="margin: 0px 0px 7px; padding: 6px 8px 3px; font: 110% Tahoma,Arial,Helvetica,sans-serif; background-color: #f2f1ef; border-bottom: 1px solid #cccccc; color: #464646;">La noticia en otros webs</h3>\n<ul style="margin: 0px 0px 5px 25px; padding: 0px; list-style-type: disc; font: 120% Arial,Helvetica,sans-serif; color: #585858;">\n<li style="margin: 0px 0px 5px; padding: 0px;"><a style="margin: 0px; padding: 0px; color: #4c7094; text-decoration: none; outline-style: none;" rel="nofollow" href="http://www.elpais.com/archivo/buscando.html?query=La%20alta%20participaci%F3n%20eleva%20la%20incertidumbre%20sobre%20el%20resultado%20en%20Madrid&amp;donde=enotros&amp;idioma=es">webs en espa&ntilde;ol</a></li>\n<li style="margin: 0px 0px 5px; padding: 0px;"><a style="margin: 0px; padding: 0px; color: #4c7094; text-decoration: none; outline-style: none;" rel="nofollow" href="http://www.elpais.com/archivo/buscando.html?query=La%20alta%20participaci%F3n%20eleva%20la%20incertidumbre%20sobre%20el%20resultado%20en%20Madrid&amp;donde=enotros&amp;idioma=nes">en otros idiomas</a></li>\n</ul>\n</div>\n</div>\n<p style="margin: 0px 0px 13px; padding: 0px; font: 160%/140% Georgia,''Times New Roman'',Times,serif; color: #333333;">Pasadas las cinco y media de la tarde, ya hab&iacute;a votado en las distintas agrupaciones el 60% de los 18.000 afiliados al Partido Socialista de Madrid. Un dato que confirma los pron&oacute;sticos de alta participaci&oacute;n en esta jornada clave para el futuro de los socialistas de la regi&oacute;n, ya que a esa hora a&uacute;n restaban dos horas y media para que el porcentaje de afluencia a las urnas aumentara.</p>\n<p style="margin: 0px 0px 13px; padding: 0px; font: 160%/140% Georgia,''Times New Roman'',Times,serif; color: #333333;">Eusebio Gonz&aacute;lez, portavoz de la plataforma de G&oacute;mez, ha asegurado a mediod&iacute;a que la participaci&oacute;n alcanzaba ya el 25%. A las cuatro era del 50%. "Creemos que el grueso de los votos se producir&aacute; antes de las dos de la tarde", ha declarado Gonz&aacute;lez ante la agrupaci&oacute;n de Tetu&aacute;n, donde la candidatura de G&oacute;mez ha fijado su sede. En agrupaciones como Legan&eacute;s, la m&aacute;s numerosa, con 1.009 militantes, ya hab&iacute;a votado el 47% hacia las dos y media.</p>\n<p style="margin: 0px 0px 13px; padding: 0px; font: 160%/140% Georgia,''Times New Roman'',Times,serif; color: #333333;">En principio, estaba previsto que el resultado del escrutinio se anunciara a partir de las nueve y media de la noche en la sede del PSM en la plaza de Callao; posteriormente, estaba programada la comparecencia de ambos aspirantes. Sin embargo, hace solo unos minutos, se ha conocido que Trinidad Jim&eacute;nez comparecer&aacute; antes, a las ocho y media de la tarde. G&oacute;mez esperar&aacute; hasta que la secretaria de Organizaci&oacute;n, Trinidad Roll&aacute;n, ofrezca los datos definitivos.</p>\n<p style="margin: 0px 0px 13px; padding: 0px; font: 160%/140% Georgia,''Times New Roman'',Times,serif; color: #333333;"><strong style="margin: 0px; padding: 0px;">Jornada tranquila</strong></p>\n<p style="margin: 0px 0px 13px; padding: 0px; font: 160%/140% Georgia,''Times New Roman'',Times,serif; color: #333333;">Portavoces de ambas candidaturas confirman que la jornada electoral se est&aacute; desarrollando con total normalidad. No ha habido ning&uacute;n problema en la constituci&oacute;n de las 145 mesas electorales. "Algunas delegaciones peque&ntilde;as, con seis o siete militantes, han decidido quedar a las doce para votar todos juntos", deslizan fuentes del PSM. En M&oacute;stoles, uno de los focos de pol&eacute;mica durante la campa&ntilde;a de primarias, ha habido cierta tensi&oacute;n entre los seguidores de uno y otro candidato por el control del censo. Pero enseguida se ha constituido la mesa y se est&aacute; votando con normalidad, avanza un portavoz del equipo de la ministra.</p>\n<p style="margin: 0px 0px 13px; padding: 0px; font: 160%/140% Georgia,''Times New Roman'',Times,serif; color: #333333;">Jim&eacute;nez ha votado en la agrupaci&oacute;n de Moncloa acompa&ntilde;ada del candidato a la alcald&iacute;a de Madrid, Jaime Lissavetzky, sobre las once y media de la ma&ntilde;ana. Cuando se dispon&iacute;a a depositar su papeleta, la ministra se ha dado cuenta de que se hab&iacute;a olvidado el DNI en el coche. Al concluir, ha expresado su "convencimiento de que los militantes van a demostrar una gran madurez pol&iacute;tica". En el equipo de Jim&eacute;nez consideran que les beneficia una elevada participaci&oacute;n, por encima del 80%. Por eso, algunos han estado pendientes del tiempo por si llueve en Madrid.</p>\n<p style="margin: 0px 0px 13px; padding: 0px; font: 160%/140% Georgia,''Times New Roman'',Times,serif; color: #333333;">Por su parte, Tom&aacute;s G&oacute;mez ha votado a las 10.40 en Parla, la ciudad del sur donde ha sido alcalde tres mandatos consecutivos. El dirigente socialista ha asegurado que "el PSM toma hoy el liderazgo de si mismo e inicia el de la Comunidad de Madrid".</p>\n<p style="margin: 0px 0px 13px; padding: 0px; font: 160%/140% Georgia,''Times New Roman'',Times,serif; color: #333333;">El &uacute;nico momento de tensi&oacute;n de la jornada se ha vivido en M&oacute;stoles, donde una concejala, Noelia Posse ha denunciado la presencia de militantes en apoyo de G&oacute;mez dentro de la agrupaci&oacute;n, cuando s&oacute;lo pueden acceder personas acreditadas. Durante la campa&ntilde;a, el entorno del secretario general del PSM calific&oacute; de pucherazo la inclusi&oacute;n de los votantes de esta agrupaci&oacute;n, que hab&iacute;a estado cerrada durante dos a&ntilde;os por problemas internos, cuando ya estaba cerrado el censo. La Comisi&oacute;n Ejecutiva Federal del PSOE autoriz&oacute; a los afiliados a votar, pero redujo el censo de 800 a 275 militantes. Hacia las 13.30, 140 de ellos hab&iacute;a depositado ya su apoyo.</p>\n<p style="margin: 0px 0px 13px; padding: 0px; font: 160%/140% Georgia,''Times New Roman'',Times,serif; color: #333333;">Pese al temor del entorno de G&oacute;mez de que esta agrupaci&oacute;n apoyar&iacute;a masivamente a Jim&eacute;nez, varios afiliados aseguraron que hab&iacute;an votado por el l&iacute;der socialista madrile&ntilde;o, responsable de la clausura del grupo. "Lleva tres a&ntilde;os trabajando en un proyecto y con ilusi&oacute;n", explic&oacute; Pilar Rodr&iacute;guez, de 35 a&ntilde;os. Tambi&eacute;n la joven Lidia Garrido, de 20 a&ntilde;os, se decant&oacute; por G&oacute;mez "por su trabajo".</p>\n<p style="margin: 0px 0px 13px; padding: 0px; font: 160%/140% Georgia,''Times New Roman'',Times,serif; color: #333333;">Miguel Dur&aacute;n, de 53 a&ntilde;os, interventor, vot&oacute; por Jim&eacute;nez, que "representa las ganas y el empuje", pero asegur&oacute; que si gana G&oacute;mez, "a partir de ma&ntilde;ana" estar&aacute; con &eacute;l. Este afiliado expres&oacute; el deseo de que ambos candidatos, gane el que gane, formen equipo y demuestren la unidd del partido.</p>\n<a style="margin: 0px; padding: 0px; color: #134d86; text-decoration: none; outline-style: none;" name="despiece1"></a>\n<div style="margin: 35px 0px 17px; padding: 21px 13px 12px; background-image: url(http://www.elpais.com/im/fnd_caja_despiece.gif); background-color: #f2f2f2; border-bottom: 1px solid #dddddd; background-position: 0% 0%;">\n<h3 style="margin: 0px 0px 5px; padding: 0px; font: 230% Georgia,''Times New Roman'',Times,serif,''Trebuchet MS''; color: #333333;">Unos candidatos olvidadizos</h3>\n<div style="margin: 0px; padding: 0px;">\n<div style="margin: 0px; padding: 0px; display: table; height: 191px;">\n<p style="margin: 0px 0px 15px; padding: 0px; font: 150%/151% Arial,Helvetica,sans-serif; color: #333333;">Besos y abrazos en la agrupaci&oacute;n de Parla. Entra el candidato. Saluda. Se palpa el bolsillo. La papeleta no est&aacute;. Tom&aacute;s G&oacute;mez sonr&iacute;e y se da media vuelta. Se la ha olvidado en el coche. Minutos despu&eacute;s, lamentando su despiste, posa ante los medios con el inmaculado sufragio entre las manos. Un olvido. La candidata Trinidad Jim&eacute;nez avanza decidida hacia la urna en la agrupaci&oacute;n de Moncloa. Los militantes hacen un pasillo y le aplauden. &iexcl;Ay, mi carn&eacute;. No lo he tra&iacute;do. Lo he dejado en el coche!, exclama entre risas cuand</p>\n</div>\n</div>\n</div>\n</div>\n</div>\n</div>\n</span></p>', 'ACTIVE', 1, 1, '\0', 'Proyecto encuestas municipios de Occidente'),
(3, '', '', 'MEDIUM', '2010-09-24 12:00:00', '2010-09-14 12:00:00', 'dsadsadsa', '<p>dsadas</p>', 'ACTIVE', 1, 1, '', 'Area de Programadores'),
(4, '\0', '', 'HIGH', '2010-09-21 12:00:00', '2010-09-14 12:00:00', 'dadsa', '<p>a dsadsadsa dsa dsa dsadsadsa dsa dsa dsadsadsa dsa dsa dsadsadsa dsa dsa dsa</p>', 'ACTIVE', 1, 1, '', 'Atenccion al Cliente'),
(7, '\0', '\0', 'MEDIUM', '2010-10-18 12:00:00', '2010-10-12 12:00:00', 'dsa dsa das dsa dsa dsa', '<p>dsa dsa dsa dsa dsa</p>', 'ACTIVE', 1, 1, '\0', 'Area de Contadores'),
(8, NULL, NULL, 'MEDIUM', '2010-10-23 12:00:00', '2010-10-13 12:00:00', ' dsa dsa dsa dsa', '<p>&nbsp;dsa dsa dsa</p>', 'ACTIVE', 1, 1, NULL, ' dsa dsa dsa'),
(9, NULL, NULL, 'MEDIUM', '2010-10-23 12:00:00', '2010-10-14 12:00:00', 's das dsa dsa dsa das', '<p>d sa dsa da</p>', 'ACTIVE', 14, 1, NULL, 'dsads dsa dsa dsa da');

-- --------------------------------------------------------

--
-- Table structure for table `project_locations`
--

CREATE TABLE IF NOT EXISTS `project_locations` (
  `cat_id_loc` bigint(20) NOT NULL,
  `cat_id_project` bigint(20) NOT NULL,
  PRIMARY KEY (`cat_id_project`,`cat_id_loc`),
  KEY `FK242951B8D3065BD5` (`cat_id_project`),
  KEY `FK242951B86C2E620E` (`cat_id_loc`),
  KEY `FK242951B8697E2715` (`cat_id_project`),
  KEY `FK242951B81F4E8D4E` (`cat_id_loc`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `project_locations`
--


-- --------------------------------------------------------

--
-- Table structure for table `questions`
--

CREATE TABLE IF NOT EXISTS `questions` (
  `qid` bigint(20) NOT NULL AUTO_INCREMENT,
  `qid_key` varchar(255) DEFAULT NULL,
  `question` varchar(255) DEFAULT NULL,
  `shared_question` bit(1) DEFAULT NULL,
  `id_state` bigint(20) DEFAULT NULL,
  `id_question_pattern` bigint(20) DEFAULT NULL,
  `uid` bigint(20) NOT NULL,
  PRIMARY KEY (`qid`),
  UNIQUE KEY `qid` (`qid`),
  KEY `FK95C5414D4E16F8CA` (`uid`),
  KEY `FK95C5414D2DBDEDCA` (`id_state`),
  KEY `FK95C5414D2A2E9A63` (`id_question_pattern`),
  KEY `FK95C5414DE48EC40A` (`uid`),
  KEY `FK95C5414D663F8A8A` (`id_state`),
  KEY `FK95C5414DAEC725A3` (`id_question_pattern`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=4 ;

--
-- Dumping data for table `questions`
--

INSERT INTO `questions` (`qid`, `qid_key`, `question`, `shared_question`, `id_state`, `id_question_pattern`, `uid`) VALUES
(1, 'e603fd0b914f54784fbf935f419784d4', ' das dsa dsadas das dsadas a', '\0', NULL, NULL, 1),
(2, '22bf056b76974389be43d29c0ab48e96', ' ds das as da das', '\0', NULL, NULL, 1),
(3, '64cd72b21b592909fbe8785e195c87b8', 'dsa dsa dsa dsadas dsa dasdas dsa das', '\0', NULL, NULL, 1);

-- --------------------------------------------------------

--
-- Table structure for table `questions_answers`
--

CREATE TABLE IF NOT EXISTS `questions_answers` (
  `q_answer_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `answer` varchar(255) DEFAULT NULL,
  `answerType` int(11) DEFAULT NULL,
  `answer_hash` varchar(255) DEFAULT NULL,
  `answer_url` varchar(255) DEFAULT NULL,
  `id_question_answer` bigint(20) NOT NULL,
  PRIMARY KEY (`q_answer_id`),
  UNIQUE KEY `q_answer_id` (`q_answer_id`),
  KEY `FK5397038325B369FB` (`id_question_answer`),
  KEY `FK539703835E3506BB` (`id_question_answer`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=6 ;

--
-- Dumping data for table `questions_answers`
--

INSERT INTO `questions_answers` (`q_answer_id`, `answer`, `answerType`, `answer_hash`, `answer_url`, `id_question_answer`) VALUES
(1, ' dsa dsa', 3, '7504f1bd5084a30f10711bc0a67a9d62', 'http://tinyurl.com/26nfj5b', 1),
(2, ' dsa das', 3, 'e1dd5e63c9367955908d9f01a345e0cd', 'http://tinyurl.com/2by56ss', 2),
(3, ' dsa da', 3, '61cb6f02d587ee85f1437e0405200038', 'http://tinyurl.com/22k2m6r', 2),
(4, ' d dsa', 3, 'fc62917a489b1eb46519678e221681e1', 'http://tinyurl.com/3x78nqe', 3),
(5, ' d dsa dsa das', 3, 'd3d02128ae38d12da0cc510762face51', 'http://tinyurl.com/32kd5hl', 3);

-- --------------------------------------------------------

--
-- Table structure for table `questions_dependencies`
--

CREATE TABLE IF NOT EXISTS `questions_dependencies` (
  `question_dependenceId` bigint(20) NOT NULL AUTO_INCREMENT,
  `descriptionDependence` varchar(255) NOT NULL,
  `questionId_from` bigint(20) NOT NULL,
  `questionId_to` bigint(20) NOT NULL,
  `q_answer_id` bigint(20) NOT NULL,
  PRIMARY KEY (`question_dependenceId`),
  UNIQUE KEY `question_dependenceId` (`question_dependenceId`),
  UNIQUE KEY `descriptionDependence` (`descriptionDependence`),
  UNIQUE KEY `questionId_from` (`questionId_from`),
  UNIQUE KEY `questionId_to` (`questionId_to`),
  KEY `FK92E86ADB7500CE58` (`q_answer_id`),
  KEY `FK92E86ADB8379AB18` (`q_answer_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

--
-- Dumping data for table `questions_dependencies`
--


-- --------------------------------------------------------

--
-- Table structure for table `questions_pattern`
--

CREATE TABLE IF NOT EXISTS `questions_pattern` (
  `pattenr_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `class` varchar(50) NOT NULL,
  `des_qid` varchar(50) NOT NULL,
  `finallity` longtext,
  `label_qid` varchar(255) NOT NULL,
  `level` int(11) DEFAULT NULL,
  `template_patron` varchar(25) DEFAULT NULL,
  `type_pattern` varchar(25) NOT NULL,
  PRIMARY KEY (`pattenr_id`),
  UNIQUE KEY `pattenr_id` (`pattenr_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=60 ;

--
-- Dumping data for table `questions_pattern`
--

INSERT INTO `questions_pattern` (`pattenr_id`, `class`, `des_qid`, `finallity`, `label_qid`, `level`, `template_patron`, `type_pattern`) VALUES
(59, '', '', 'Example', 'What do you do?', 1, 'radio', 'Yes/No');

-- --------------------------------------------------------

--
-- Table structure for table `question_category`
--

CREATE TABLE IF NOT EXISTS `question_category` (
  `qCategory` bigint(20) NOT NULL AUTO_INCREMENT,
  `category` varchar(18) DEFAULT NULL,
  PRIMARY KEY (`qCategory`),
  UNIQUE KEY `qCategory` (`qCategory`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

--
-- Dumping data for table `question_category`
--


-- --------------------------------------------------------

--
-- Table structure for table `question_category_questions`
--

CREATE TABLE IF NOT EXISTS `question_category_questions` (
  `question_category_qCategory` bigint(20) NOT NULL,
  `questionLibrary_qid` bigint(20) NOT NULL,
  PRIMARY KEY (`question_category_qCategory`,`questionLibrary_qid`),
  KEY `FK2FFE1845115F695F` (`question_category_qCategory`),
  KEY `FK2FFE1845E768F3CA` (`questionLibrary_qid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `question_category_questions`
--


-- --------------------------------------------------------

--
-- Table structure for table `question_collection`
--

CREATE TABLE IF NOT EXISTS `question_collection` (
  `id_q_colection` bigint(20) NOT NULL AUTO_INCREMENT,
  `creation_date` datetime NOT NULL,
  `des_coleccion` varchar(255) NOT NULL,
  `uid` bigint(20) NOT NULL,
  PRIMARY KEY (`id_q_colection`),
  UNIQUE KEY `id_q_colection` (`id_q_colection`),
  KEY `FKB4097C974E16F8CA` (`uid`),
  KEY `FKB4097C97E48EC40A` (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

--
-- Dumping data for table `question_collection`
--


-- --------------------------------------------------------

--
-- Table structure for table `question_dependence_survey`
--

CREATE TABLE IF NOT EXISTS `question_dependence_survey` (
  `question_dependence_survey` bigint(20) NOT NULL AUTO_INCREMENT,
  `sid` bigint(20) NOT NULL,
  PRIMARY KEY (`question_dependence_survey`),
  UNIQUE KEY `question_dependence_survey` (`question_dependence_survey`),
  KEY `FKBB424D496A46A9E5` (`sid`),
  KEY `FKBB424D49BE7525` (`sid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

--
-- Dumping data for table `question_dependence_survey`
--


-- --------------------------------------------------------

--
-- Table structure for table `question_relations`
--

CREATE TABLE IF NOT EXISTS `question_relations` (
  `question_id` bigint(20) NOT NULL,
  `id_q_colection` bigint(20) NOT NULL,
  PRIMARY KEY (`question_id`,`id_q_colection`),
  KEY `FK217954DE15ECCA7B` (`id_q_colection`),
  KEY `FK217954DE31BD8EFC` (`question_id`),
  KEY `FK217954DED68F85BB` (`id_q_colection`),
  KEY `FK217954DE6A3F2BBC` (`question_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `question_relations`
--


-- --------------------------------------------------------

--
-- Table structure for table `sec_groups`
--

CREATE TABLE IF NOT EXISTS `sec_groups` (
  `group_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `des_info` varchar(255) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `id_state` bigint(20) DEFAULT NULL,
  `secUsers_uid` bigint(20) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`group_id`),
  UNIQUE KEY `group_id` (`group_id`),
  KEY `FKBFBFCD82ED100E2` (`secUsers_uid`),
  KEY `FKBFBFCD82A548CC22` (`secUsers_uid`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=21 ;

--
-- Dumping data for table `sec_groups`
--

INSERT INTO `sec_groups` (`group_id`, `des_info`, `name`, `id_state`, `secUsers_uid`, `type`) VALUES
(1, 'Demo', 'Demo Group', 1, NULL, NULL),
(2, 'Favourites', 'Favourites', 1, NULL, NULL),
(18, 'sadsadsadassad', 'dasda2131', NULL, 1, NULL),
(19, 'dasdsa', 'dsadsa', NULL, 11, NULL),
(20, 'dsadsa', 'dsa', NULL, 1, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `sec_group_permission`
--

CREATE TABLE IF NOT EXISTS `sec_group_permission` (
  `sec_id_permission` bigint(20) NOT NULL,
  `sec_id_group` bigint(20) NOT NULL,
  PRIMARY KEY (`sec_id_group`,`sec_id_permission`),
  KEY `FK5B8E21BD11E76A39` (`sec_id_group`),
  KEY `FK5B8E21BD7DBA1B43` (`sec_id_permission`),
  KEY `FK5B8E21BD4A6906F9` (`sec_id_group`),
  KEY `FK5B8E21BDE97C7683` (`sec_id_permission`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `sec_group_permission`
--


-- --------------------------------------------------------

--
-- Table structure for table `sec_permission`
--

CREATE TABLE IF NOT EXISTS `sec_permission` (
  `id_permission` bigint(20) NOT NULL AUTO_INCREMENT,
  `permission` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id_permission`),
  UNIQUE KEY `id_permission` (`id_permission`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=7 ;

--
-- Dumping data for table `sec_permission`
--

INSERT INTO `sec_permission` (`id_permission`, `permission`, `description`) VALUES
(2, 'ENCUESTAME_ADMIN', 'ENCUESTAME_ADMIN'),
(3, 'ENCUESTAME_OWNER', 'ENCUESTAME_OWNER'),
(4, 'ENCUESTAME_PUBLISHER', 'ENCUESTAME_PUBLISHER'),
(5, 'ENCUESTAME_EDITOR', 'ENCUESTAME_EDITOR'),
(6, 'ENCUESTAME_USER', 'ENCUESTAME_USER');

-- --------------------------------------------------------

--
-- Table structure for table `sec_project_group`
--

CREATE TABLE IF NOT EXISTS `sec_project_group` (
  `sec_id_group` bigint(20) NOT NULL,
  `cat_id_project` bigint(20) NOT NULL,
  PRIMARY KEY (`cat_id_project`,`sec_id_group`),
  KEY `FK93685A6B11E76A39` (`sec_id_group`),
  KEY `FK93685A6BD3065BD5` (`cat_id_project`),
  KEY `FK93685A6B4A6906F9` (`sec_id_group`),
  KEY `FK93685A6B697E2715` (`cat_id_project`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `sec_project_group`
--


-- --------------------------------------------------------

--
-- Table structure for table `sec_user`
--

CREATE TABLE IF NOT EXISTS `sec_user` (
  `uid` bigint(20) NOT NULL AUTO_INCREMENT,
  `twitter_consumer_key` varchar(255) DEFAULT NULL,
  `twitter_consumer_secret` varchar(255) DEFAULT NULL,
  `twitter_account` varchar(18) DEFAULT NULL,
  `twitter_password` varchar(18) DEFAULT NULL,
  `twitter_pin` int(11) DEFAULT NULL,
  PRIMARY KEY (`uid`),
  UNIQUE KEY `uid` (`uid`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=13 ;

--
-- Dumping data for table `sec_user`
--

INSERT INTO `sec_user` (`uid`, `twitter_consumer_key`, `twitter_consumer_secret`, `twitter_account`, `twitter_password`, `twitter_pin`) VALUES
(1, 'nFboU4T1Zhv8cqMC4cP0ug', 'GwOPUEJEaCbNBiBzq6J8StDhb7FOmwDcjfX6zMe0', 'testEncuesta', 'testEncuesta123', 4189783),
(2, NULL, NULL, NULL, NULL, NULL),
(3, NULL, NULL, NULL, NULL, NULL),
(4, NULL, NULL, NULL, NULL, NULL),
(5, NULL, NULL, NULL, NULL, NULL),
(6, NULL, NULL, NULL, NULL, NULL),
(7, NULL, NULL, NULL, NULL, NULL),
(8, NULL, NULL, NULL, NULL, NULL),
(9, NULL, NULL, NULL, NULL, NULL),
(10, NULL, NULL, NULL, NULL, NULL),
(11, NULL, NULL, NULL, NULL, NULL),
(12, NULL, NULL, NULL, NULL, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `sec_user_group`
--

CREATE TABLE IF NOT EXISTS `sec_user_group` (
  `sec_id_secondary` bigint(20) NOT NULL,
  `sec_id_group` bigint(20) NOT NULL,
  PRIMARY KEY (`sec_id_secondary`,`sec_id_group`),
  KEY `FK3D8D6DB911E76A39` (`sec_id_group`),
  KEY `FK3D8D6DB9C16FD298` (`sec_id_secondary`),
  KEY `FK3D8D6DB94A6906F9` (`sec_id_group`),
  KEY `FK3D8D6DB9CFE8AF58` (`sec_id_secondary`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `sec_user_group`
--


-- --------------------------------------------------------

--
-- Table structure for table `sec_user_permission`
--

CREATE TABLE IF NOT EXISTS `sec_user_permission` (
  `sec_id_secondary` bigint(20) NOT NULL,
  `sec_id_permission` bigint(20) NOT NULL,
  PRIMARY KEY (`sec_id_permission`,`sec_id_secondary`),
  KEY `FK8A4C2D57DBA1B43` (`sec_id_permission`),
  KEY `FK8A4C2D5C16FD298` (`sec_id_secondary`),
  KEY `FK8A4C2D5E97C7683` (`sec_id_permission`),
  KEY `FK8A4C2D5CFE8AF58` (`sec_id_secondary`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `sec_user_permission`
--

INSERT INTO `sec_user_permission` (`sec_id_secondary`, `sec_id_permission`) VALUES
(1, 2),
(2, 2),
(3, 2),
(4, 2),
(5, 2),
(8, 2),
(10, 2),
(11, 2),
(12, 2),
(1, 3),
(2, 3),
(3, 3),
(4, 3),
(5, 3),
(8, 3),
(10, 3),
(11, 3),
(12, 3),
(1, 4),
(2, 4),
(3, 4),
(4, 4),
(5, 4),
(8, 4),
(10, 4),
(11, 4),
(12, 4),
(2, 5),
(3, 5),
(4, 5),
(5, 5),
(8, 5),
(10, 5),
(11, 5),
(12, 5),
(1, 6),
(14, 6);

-- --------------------------------------------------------

--
-- Table structure for table `sec_user_project`
--

CREATE TABLE IF NOT EXISTS `sec_user_project` (
  `sec_id_secondary` bigint(20) NOT NULL,
  `cat_id_project` bigint(20) NOT NULL,
  PRIMARY KEY (`cat_id_project`,`sec_id_secondary`),
  KEY `FKEBFBDBD3D3065BD5` (`cat_id_project`),
  KEY `FKEBFBDBD3C16FD298` (`sec_id_secondary`),
  KEY `FKEBFBDBD3697E2715` (`cat_id_project`),
  KEY `FKEBFBDBD3CFE8AF58` (`sec_id_secondary`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `sec_user_project`
--


-- --------------------------------------------------------

--
-- Table structure for table `sec_user_secondary`
--

CREATE TABLE IF NOT EXISTS `sec_user_secondary` (
  `uid` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `date_new` datetime DEFAULT NULL,
  `invite_code` varchar(255) DEFAULT NULL,
  `last_ip_logged` varchar(255) DEFAULT NULL,
  `last_time_logged` datetime DEFAULT NULL,
  `password` varchar(255) NOT NULL,
  `email` varchar(150) NOT NULL,
  `status` bit(1) DEFAULT NULL,
  `twitter` varchar(255) DEFAULT NULL,
  `username` varchar(16) NOT NULL,
  `secUser_uid` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`uid`),
  UNIQUE KEY `uid` (`uid`),
  UNIQUE KEY `email` (`email`),
  UNIQUE KEY `username` (`username`,`email`),
  KEY `FKE10EBBAED86511A7` (`secUser_uid`),
  KEY `FKE10EBBAE6EDCDCE7` (`secUser_uid`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=15 ;

--
-- Dumping data for table `sec_user_secondary`
--

INSERT INTO `sec_user_secondary` (`uid`, `name`, `date_new`, `invite_code`, `last_ip_logged`, `last_time_logged`, `password`, `email`, `status`, `twitter`, `username`, `secUser_uid`) VALUES
(1, 'Juan Carlos Picado', '2010-01-20 12:47:40', '', NULL, '2010-10-03 16:11:05', '6xAX8siGWDJXfkJUVxWLqsk0rz8U+aG6Y8yA1IokxuhEIZ8+RugleJtLUYbdGxc+', 'juanpicado19@gmail.com', '', NULL, 'admin', 1),
(2, '', '2010-06-19 07:56:02', '', NULL, NULL, 'pDdSocopH0ny7CpmCKJMu19guKthRWyN751c6H2IYnUfjC+tUtAO87pap39B6622', 'juan@jotadeveloper.com', '', NULL, 'jota', 2),
(3, '', '2010-06-19 08:19:44', '', NULL, NULL, 'pKfJHPrLGCUbak2yav0oSvNMYDt3dSDUN8ivYLkCWshhTJtKySHAWY9RXFlVr0L5', 'info@jotadeveloper.comsssssss', '', NULL, 'sadasdasdsa', 3),
(4, '', '2010-06-19 08:21:38', '', NULL, NULL, '3wi3Oy6i9zT2zLTD5ewWqHCG5xpPcwSj/xSpSrLMAgXY3wLVidiV3cKkpbfAP7ar', '2juanpicado19@gmail.com', '', NULL, 'dsadsadkajdlksa', 4),
(5, '', '2010-06-19 08:49:33', '', NULL, NULL, 'Th04R8bHe37/E6ugUpaJZokmV7MIpKAsY4fqgolmhcjosfWTCKbZ3mjeRWcFhRWz', 'jotadeveloper@gmail.com', '', NULL, 'jotadasdsa', 5),
(6, '', '2010-06-19 09:15:39', '', NULL, NULL, 'CnYJswTOBkrF9vU4kY7PvEbA5PFJf1gKdWwGSOIaUFm3ZdD2m84reYp6Lmfl84VM', 'paola@jotadeveloper.com', '', NULL, 'sadasdsakjdsal', 6),
(7, '', '2010-06-19 09:17:02', '', NULL, NULL, 'xqHe0RpeBz/3NA3jnc99Il4/tylQDcWWL+JF3jaDl+aB8bPI1A+3u7BGd23w6NaE', 'paola@encuestame.org', '', NULL, 'paolaaaaaaa', 7),
(8, '', '2010-06-19 09:33:34', '', NULL, NULL, 'scAnsHAW8LgYwVb4AnGGMA4hZsw/ZVMr6MwxsxSh2kPVdL4ArRv16x7aVWiurAps', 'juanpicado19@gmail.comssa', '', NULL, 'josajodasjodas', 8),
(10, '', '2010-06-19 09:45:53', '', NULL, NULL, 'lKTk7hgn57Gj1dPulC4os5hTqpwUMJh1jspj335H6a6mGN77aj85/ojJFbLfWYvI', 'sdadsa', '', NULL, 'dasdasdsad', 10),
(11, '', '2010-06-19 09:56:36', '', NULL, NULL, 'ogae9Ih7UdJMS9+mSL2/62bRCYItdm0iRUSHzSCxB3RdxrXs+QvG7FFphELh/XDZ', 'juanpicado19@gmail.comdasdas', '', NULL, 'dasdsa2131', 11),
(12, '', '2010-06-26 08:27:30', '', NULL, '2010-06-26 08:27:34', '5JzT+UZRKdRHKD251b1tcezb/qGXLcmXPEvgEiLnXskYd48QmrLye0tMoU3sJaEw', 'webmaster@encuestame.org', '', NULL, 'juancarlospicado', 12),
(14, '', '2010-10-03 11:22:38', NULL, NULL, NULL, '9smU18cpJ4wNiEHQArqUaYkSwC76S/MIYQpWzVMR6MHjtpMJLYXfycwpV4hfyILR', 'info@jotadeveloper.com', '', NULL, 'jpicado', 1);

-- --------------------------------------------------------

--
-- Table structure for table `sec_user_twitter_account`
--

CREATE TABLE IF NOT EXISTS `sec_user_twitter_account` (
  `sec_user_twitter_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `twitter_consumer_key` varchar(255) DEFAULT NULL,
  `twitter_consumer_secret` varchar(255) DEFAULT NULL,
  `twitter_secret_token` varchar(255) DEFAULT NULL,
  `twitter_token` varchar(255) DEFAULT NULL,
  `twitter_account` varchar(255) NOT NULL,
  `twitter_password` varchar(255) NOT NULL,
  `twitter_pin` int(11) DEFAULT NULL,
  `type_auth` varchar(255) DEFAULT NULL,
  `twitter_verified` bit(1) DEFAULT NULL,
  `secUsers_uid` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`sec_user_twitter_id`),
  UNIQUE KEY `sec_user_twitter_id` (`sec_user_twitter_id`),
  KEY `FKC6CCEF1BED100E2` (`secUsers_uid`),
  KEY `FKC6CCEF1BA548CC22` (`secUsers_uid`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=6 ;

--
-- Dumping data for table `sec_user_twitter_account`
--

INSERT INTO `sec_user_twitter_account` (`sec_user_twitter_id`, `twitter_consumer_key`, `twitter_consumer_secret`, `twitter_secret_token`, `twitter_token`, `twitter_account`, `twitter_password`, `twitter_pin`, `type_auth`, `twitter_verified`, `secUsers_uid`) VALUES
(1, 'nFboU4T1Zhv8cqMC4cP0ug', 'GwOPUEJEaCbNBiBzq6J8StDhb7FOmwDcjfX6zMe0', NULL, NULL, 'testEncuesta', 'testEncuesta123', 4129201, 'PASSWORD', '', 1),
(4, NULL, NULL, NULL, NULL, 'dasdas', '', NULL, 'PASSWORD', '\0', 1),
(5, NULL, NULL, NULL, NULL, 'testEncuesta2', 'testEncuesta2123', NULL, 'PASSWORD', '', 1);

-- --------------------------------------------------------

--
-- Table structure for table `surveys`
--

CREATE TABLE IF NOT EXISTS `surveys` (
  `sid` bigint(20) NOT NULL AUTO_INCREMENT,
  `closeAfterDate` bit(1) DEFAULT NULL,
  `close_after_quota` bit(1) DEFAULT NULL,
  `close_date` datetime DEFAULT NULL,
  `closed_quota` int(11) DEFAULT NULL,
  `custom_final_message` varchar(255) DEFAULT NULL,
  `custom_message` bit(1) DEFAULT NULL,
  `custom_start_message` varchar(255) DEFAULT NULL,
  `ip_protection` varchar(255) DEFAULT NULL,
  `ip_restrictions` bit(1) DEFAULT NULL,
  `multiple_response` varchar(255) DEFAULT NULL,
  `optional_title` varchar(255) DEFAULT NULL,
  `password_protection` varchar(255) DEFAULT NULL,
  `password_restrictions` bit(1) DEFAULT NULL,
  `show_progress_bar` bit(1) DEFAULT NULL,
  `complete` varchar(2) DEFAULT NULL,
  `date_interview` date DEFAULT NULL,
  `end_date` datetime NOT NULL,
  `start_date` datetime NOT NULL,
  `ticket` int(11) NOT NULL,
  `uid` bigint(20) NOT NULL,
  `id_sid_format` bigint(20) NOT NULL,
  `additionalInfo` varchar(255) DEFAULT NULL,
  `notifications` bit(1) DEFAULT NULL,
  `showAdditionalInfo` bit(1) DEFAULT NULL,
  `showComments` int(11) DEFAULT NULL,
  `showResults` bit(1) DEFAULT NULL,
  PRIMARY KEY (`sid`),
  UNIQUE KEY `sid` (`sid`),
  KEY `FK919144594E16F8CA` (`uid`),
  KEY `FK919144593A63AC1F` (`id_sid_format`),
  KEY `FK91914459E48EC40A` (`uid`),
  KEY `FK91914459EB48E8DF` (`id_sid_format`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

--
-- Dumping data for table `surveys`
--


-- --------------------------------------------------------

--
-- Table structure for table `survey_folder`
--

CREATE TABLE IF NOT EXISTS `survey_folder` (
  `survey_folderId` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_at` datetime DEFAULT NULL,
  `folderName` varchar(255) NOT NULL,
  `sid` bigint(20) NOT NULL,
  `uid` bigint(20) NOT NULL,
  PRIMARY KEY (`survey_folderId`),
  UNIQUE KEY `survey_folderId` (`survey_folderId`),
  UNIQUE KEY `folderName` (`folderName`),
  KEY `FK7EF958F34E16F8CA` (`uid`),
  KEY `FK7EF958F36A46A9E5` (`sid`),
  KEY `FK7EF958F3E48EC40A` (`uid`),
  KEY `FK7EF958F3BE7525` (`sid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

--
-- Dumping data for table `survey_folder`
--


-- --------------------------------------------------------

--
-- Table structure for table `survey_format`
--

CREATE TABLE IF NOT EXISTS `survey_format` (
  `id_sid_format` bigint(20) NOT NULL AUTO_INCREMENT,
  `date_created` datetime DEFAULT NULL,
  `name` varchar(60) DEFAULT NULL,
  PRIMARY KEY (`id_sid_format`),
  UNIQUE KEY `id_sid_format` (`id_sid_format`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

--
-- Dumping data for table `survey_format`
--


-- --------------------------------------------------------

--
-- Table structure for table `survey_group`
--

CREATE TABLE IF NOT EXISTS `survey_group` (
  `sg_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `date_create` datetime DEFAULT NULL,
  `group_name` varchar(60) DEFAULT NULL,
  `cat_state_id_survey_group` bigint(20) NOT NULL,
  PRIMARY KEY (`sg_id`),
  UNIQUE KEY `sg_id` (`sg_id`),
  KEY `FK4638955A697F0404` (`cat_state_id_survey_group`),
  KEY `FK4638955AA200A0C4` (`cat_state_id_survey_group`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

--
-- Dumping data for table `survey_group`
--


-- --------------------------------------------------------

--
-- Table structure for table `survey_group_format`
--

CREATE TABLE IF NOT EXISTS `survey_group_format` (
  `sg_id` bigint(20) NOT NULL,
  `id_sid_format` bigint(20) NOT NULL,
  PRIMARY KEY (`id_sid_format`,`sg_id`),
  KEY `FKB4DF867CB998D3E9` (`sg_id`),
  KEY `FKB4DF867C3A63AC1F` (`id_sid_format`),
  KEY `FKB4DF867C6CB8FF29` (`sg_id`),
  KEY `FKB4DF867CEB48E8DF` (`id_sid_format`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `survey_group_format`
--


-- --------------------------------------------------------

--
-- Table structure for table `survey_group_project`
--

CREATE TABLE IF NOT EXISTS `survey_group_project` (
  `id_sid_format` bigint(20) NOT NULL,
  `cat_id_project` bigint(20) NOT NULL,
  PRIMARY KEY (`cat_id_project`,`id_sid_format`),
  KEY `FKFD028D34D3065BD5` (`cat_id_project`),
  KEY `FKFD028D343FE96F2F` (`id_sid_format`),
  KEY `FKFD028D34697E2715` (`cat_id_project`),
  KEY `FKFD028D34F3099A6F` (`id_sid_format`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `survey_group_project`
--


-- --------------------------------------------------------

--
-- Table structure for table `survey_pagination`
--

CREATE TABLE IF NOT EXISTS `survey_pagination` (
  `pagination_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_at` bigint(20) DEFAULT NULL,
  `sid` bigint(20) NOT NULL,
  PRIMARY KEY (`pagination_id`),
  UNIQUE KEY `pagination_id` (`pagination_id`),
  KEY `FKBEC9A99FA20BB537` (`sid`),
  KEY `FKBEC9A99FDCE1077` (`sid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

--
-- Dumping data for table `survey_pagination`
--


-- --------------------------------------------------------

--
-- Table structure for table `survey_result`
--

CREATE TABLE IF NOT EXISTS `survey_result` (
  `rid` bigint(20) NOT NULL AUTO_INCREMENT,
  `resp` varchar(255) NOT NULL,
  `survey_id` bigint(20) NOT NULL,
  PRIMARY KEY (`rid`),
  UNIQUE KEY `rid` (`rid`),
  KEY `FK92EA04A2DC964137` (`survey_id`),
  KEY `FK92EA04A2730E0C77` (`survey_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

--
-- Dumping data for table `survey_result`
--


-- --------------------------------------------------------

--
-- Table structure for table `survey_section`
--

CREATE TABLE IF NOT EXISTS `survey_section` (
  `ssid` bigint(20) NOT NULL AUTO_INCREMENT,
  `desc_section` varchar(255) DEFAULT NULL,
  `id_state` bigint(20) NOT NULL,
  PRIMARY KEY (`ssid`),
  UNIQUE KEY `ssid` (`ssid`),
  KEY `FKFE5AD3002DBDEDCA` (`id_state`),
  KEY `FKFE5AD300663F8A8A` (`id_state`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

--
-- Dumping data for table `survey_section`
--


-- --------------------------------------------------------

--
-- Table structure for table `survey_section_questions`
--

CREATE TABLE IF NOT EXISTS `survey_section_questions` (
  `survey_section_ssid` bigint(20) NOT NULL,
  `questionSection_qid` bigint(20) NOT NULL,
  PRIMARY KEY (`survey_section_ssid`,`questionSection_qid`),
  KEY `FK12354ECE9FEBC1C3` (`survey_section_ssid`),
  KEY `FK12354ECEF5818C34` (`questionSection_qid`),
  KEY `FK12354ECEBAE1D03` (`survey_section_ssid`),
  KEY `FK12354ECE2E0328F4` (`questionSection_qid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `survey_section_questions`
--


-- --------------------------------------------------------

--
-- Table structure for table `tweetPoll`
--

CREATE TABLE IF NOT EXISTS `tweetPoll` (
  `tweet_poll_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `allow_live_results` bit(1) DEFAULT NULL,
  `allow_repeated_votes` bit(1) DEFAULT NULL,
  `captcha` bit(1) DEFAULT NULL,
  `close_notification` bit(1) DEFAULT NULL,
  `completed` bit(1) NOT NULL,
  `create_date` datetime DEFAULT NULL,
  `enabled` bit(1) NOT NULL,
  `limit_votes` int(11) DEFAULT NULL,
  `publish` bit(1) DEFAULT NULL,
  `result_notification` bit(1) DEFAULT NULL,
  `resume_live_results` bit(1) DEFAULT NULL,
  `schedule_date_tweet` datetime DEFAULT NULL,
  `schedule` bit(1) DEFAULT NULL,
  `qid` bigint(20) NOT NULL,
  `uid` bigint(20) NOT NULL,
  PRIMARY KEY (`tweet_poll_id`),
  UNIQUE KEY `tweet_poll_id` (`tweet_poll_id`),
  KEY `FKA65B1D04E16F8CA` (`uid`),
  KEY `FKA65B1D0F8456034` (`qid`),
  KEY `FKA65B1D0E48EC40A` (`uid`),
  KEY `FKA65B1D030C6FCF4` (`qid`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=4 ;

--
-- Dumping data for table `tweetPoll`
--

INSERT INTO `tweetPoll` (`tweet_poll_id`, `allow_live_results`, `allow_repeated_votes`, `captcha`, `close_notification`, `completed`, `create_date`, `enabled`, `limit_votes`, `publish`, `result_notification`, `resume_live_results`, `schedule_date_tweet`, `schedule`, `qid`, `uid`) VALUES
(1, '\0', NULL, '', '\0', '\0', '2010-10-03 11:59:32', '', 17619, '', '\0', '\0', NULL, '\0', 1, 1),
(2, '', NULL, '', '\0', '\0', '2010-10-03 12:02:42', '', 100, '', '\0', '\0', NULL, '', 2, 1),
(3, '', NULL, '', '\0', '\0', '2010-10-03 15:30:39', '', 100, '', '\0', '\0', NULL, '\0', 3, 1);

-- --------------------------------------------------------

--
-- Table structure for table `tweetPoll_hash_tags`
--

CREATE TABLE IF NOT EXISTS `tweetPoll_hash_tags` (
  `tweetPoll_tweet_poll_id` bigint(20) NOT NULL,
  `hashTags_hash_tag_id` bigint(20) NOT NULL,
  PRIMARY KEY (`tweetPoll_tweet_poll_id`,`hashTags_hash_tag_id`),
  KEY `FKE3434CFB7EBFB88C` (`tweetPoll_tweet_poll_id`),
  KEY `FKE3434CFB863DEA83` (`hashTags_hash_tag_id`),
  KEY `FKE3434CFB5671B3CC` (`tweetPoll_tweet_poll_id`),
  KEY `FKE3434CFB1CB5B5C3` (`hashTags_hash_tag_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tweetPoll_hash_tags`
--

INSERT INTO `tweetPoll_hash_tags` (`tweetPoll_tweet_poll_id`, `hashTags_hash_tag_id`) VALUES
(1, 13),
(1, 17),
(1, 28),
(2, 7),
(2, 25),
(3, 30),
(3, 39);

-- --------------------------------------------------------

--
-- Table structure for table `tweetpoll_result`
--

CREATE TABLE IF NOT EXISTS `tweetpoll_result` (
  `tweetpoll_resultId` bigint(20) NOT NULL AUTO_INCREMENT,
  `ip_vote` varchar(100) NOT NULL,
  `tweet_date_response` datetime NOT NULL,
  `tweetpoll_switch_id` bigint(20) NOT NULL,
  PRIMARY KEY (`tweetpoll_resultId`),
  UNIQUE KEY `tweetpoll_resultId` (`tweetpoll_resultId`),
  KEY `FK8749C18C81599E19` (`tweetpoll_switch_id`),
  KEY `FK8749C18C5F22959` (`tweetpoll_switch_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

--
-- Dumping data for table `tweetpoll_result`
--


-- --------------------------------------------------------

--
-- Table structure for table `tweetPoll_save_published_status`
--

CREATE TABLE IF NOT EXISTS `tweetPoll_save_published_status` (
  `status_save_poll_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `type` varchar(255) DEFAULT NULL,
  `status_description` varchar(255) DEFAULT NULL,
  `publication_date_tweet` datetime DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `tweet_id` bigint(20) DEFAULT NULL,
  `tweetPoll_tweet_poll_id` bigint(20) DEFAULT NULL,
  `twitterAccount_sec_user_twitter_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`status_save_poll_id`),
  UNIQUE KEY `status_save_poll_id` (`status_save_poll_id`),
  KEY `FKD499A4B62921D7A7` (`twitterAccount_sec_user_twitter_id`),
  KEY `FKD499A4B67EBFB88C` (`tweetPoll_tweet_poll_id`),
  KEY `FKD499A4B65322467` (`twitterAccount_sec_user_twitter_id`),
  KEY `FKD499A4B65671B3CC` (`tweetPoll_tweet_poll_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

--
-- Dumping data for table `tweetPoll_save_published_status`
--

INSERT INTO `tweetPoll_save_published_status` (`status_save_poll_id`, `type`, `status_description`, `publication_date_tweet`, `status`, `tweet_id`, `tweetPoll_tweet_poll_id`, `twitterAccount_sec_user_twitter_id`) VALUES
(1, 'TWITTER', NULL, NULL, 'FAILED', NULL, 1, 1),
(2, 'TWITTER', NULL, NULL, 'FAILED', NULL, 3, 1);

-- --------------------------------------------------------

--
-- Table structure for table `tweetpoll_switch`
--

CREATE TABLE IF NOT EXISTS `tweetpoll_switch` (
  `tweetpoll_switch_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `tweet_code` varchar(255) NOT NULL,
  `q_answer_id` bigint(20) NOT NULL,
  `tweet_poll_id` bigint(20) NOT NULL,
  PRIMARY KEY (`tweetpoll_switch_id`),
  UNIQUE KEY `tweetpoll_switch_id` (`tweetpoll_switch_id`),
  UNIQUE KEY `tweet_code` (`tweet_code`),
  KEY `FK89F7B0A3EED35CDB` (`tweet_poll_id`),
  KEY `FK89F7B0A37500CE58` (`q_answer_id`),
  KEY `FK89F7B0A3C685581B` (`tweet_poll_id`),
  KEY `FK89F7B0A38379AB18` (`q_answer_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=6 ;

--
-- Dumping data for table `tweetpoll_switch`
--

INSERT INTO `tweetpoll_switch` (`tweetpoll_switch_id`, `tweet_code`, `q_answer_id`, `tweet_poll_id`) VALUES
(1, '7504f1bd5084a30f10711bc0a67a9d62', 1, 1),
(2, 'e1dd5e63c9367955908d9f01a345e0cd', 2, 2),
(3, '61cb6f02d587ee85f1437e0405200038', 3, 2),
(4, 'fc62917a489b1eb46519678e221681e1', 4, 3),
(5, 'd3d02128ae38d12da0cc510762face51', 5, 3);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `cat_emails`
--
ALTER TABLE `cat_emails`
  ADD CONSTRAINT `FKC5448E0D16F648F` FOREIGN KEY (`id_list`) REFERENCES `cat_list_emails` (`id_list`),
  ADD CONSTRAINT `FKC5448E065AD094F` FOREIGN KEY (`id_list`) REFERENCES `cat_list_emails` (`id_list`);

--
-- Constraints for table `cat_list_emails`
--
ALTER TABLE `cat_list_emails`
  ADD CONSTRAINT `FK4686F42FE48EC40A` FOREIGN KEY (`uid`) REFERENCES `sec_user` (`uid`),
  ADD CONSTRAINT `FK4686F42F4E16F8CA` FOREIGN KEY (`uid`) REFERENCES `sec_user` (`uid`);

--
-- Constraints for table `cat_location`
--
ALTER TABLE `cat_location`
  ADD CONSTRAINT `FK8C56C1FE775141C` FOREIGN KEY (`catLocationFolder_locate_folder_id`) REFERENCES `cat_location_folder` (`locate_folder_id`),
  ADD CONSTRAINT `FK8C56C1FE46D258DC` FOREIGN KEY (`catLocationFolder_locate_folder_id`) REFERENCES `cat_location_folder` (`locate_folder_id`),
  ADD CONSTRAINT `FK8C56C1FE5E615762` FOREIGN KEY (`loc_id_type`) REFERENCES `cat_location_type` (`loc_id_type`),
  ADD CONSTRAINT `FK8C56C1FEA548CC22` FOREIGN KEY (`secUsers_uid`) REFERENCES `sec_user` (`uid`),
  ADD CONSTRAINT `FK8C56C1FED9C8CC22` FOREIGN KEY (`loc_id_type`) REFERENCES `cat_location_type` (`loc_id_type`),
  ADD CONSTRAINT `FK8C56C1FEED100E2` FOREIGN KEY (`secUsers_uid`) REFERENCES `sec_user` (`uid`);

--
-- Constraints for table `cat_location_folder`
--
ALTER TABLE `cat_location_folder`
  ADD CONSTRAINT `FK30F5BDCF1AE78872` FOREIGN KEY (`subLocationFolder_locate_folder_id`) REFERENCES `cat_location_folder` (`locate_folder_id`),
  ADD CONSTRAINT `FK30F5BDCF5A44CD32` FOREIGN KEY (`subLocationFolder_locate_folder_id`) REFERENCES `cat_location_folder` (`locate_folder_id`),
  ADD CONSTRAINT `FK30F5BDCFA548CC22` FOREIGN KEY (`secUsers_uid`) REFERENCES `sec_user` (`uid`),
  ADD CONSTRAINT `FK30F5BDCFED100E2` FOREIGN KEY (`secUsers_uid`) REFERENCES `sec_user` (`uid`);

--
-- Constraints for table `cat_location_type`
--
ALTER TABLE `cat_location_type`
  ADD CONSTRAINT `FKC79A14DBF9DA3D53` FOREIGN KEY (`users_uid`) REFERENCES `sec_user` (`uid`),
  ADD CONSTRAINT `FKC79A14DB63627213` FOREIGN KEY (`users_uid`) REFERENCES `sec_user` (`uid`);

--
-- Constraints for table `cat_subscribe_emails`
--
ALTER TABLE `cat_subscribe_emails`
  ADD CONSTRAINT `FKF846A35A68478E9` FOREIGN KEY (`email_id`) REFERENCES `cat_emails` (`email_id`),
  ADD CONSTRAINT `FKF846A3565AD094F` FOREIGN KEY (`id_list`) REFERENCES `cat_list_emails` (`id_list`),
  ADD CONSTRAINT `FKF846A35CED27DA9` FOREIGN KEY (`email_id`) REFERENCES `cat_emails` (`email_id`),
  ADD CONSTRAINT `FKF846A35D16F648F` FOREIGN KEY (`id_list`) REFERENCES `cat_list_emails` (`id_list`);

--
-- Constraints for table `client`
--
ALTER TABLE `client`
  ADD CONSTRAINT `FKAF12F3CBE7506318` FOREIGN KEY (`project_id`) REFERENCES `project` (`project_id`),
  ADD CONSTRAINT `FKAF12F3CB50D897D8` FOREIGN KEY (`project_id`) REFERENCES `project` (`project_id`);

--
-- Constraints for table `notification`
--
ALTER TABLE `notification`
  ADD CONSTRAINT `FK237A88EBE48EC40A` FOREIGN KEY (`uid`) REFERENCES `sec_user` (`uid`),
  ADD CONSTRAINT `FK237A88EB4E16F8CA` FOREIGN KEY (`uid`) REFERENCES `sec_user` (`uid`);

--
-- Constraints for table `poll`
--
ALTER TABLE `poll`
  ADD CONSTRAINT `FK3497BF30C6FCF4` FOREIGN KEY (`qid`) REFERENCES `questions` (`qid`),
  ADD CONSTRAINT `FK3497BF4E16F8CA` FOREIGN KEY (`uid`) REFERENCES `sec_user` (`uid`),
  ADD CONSTRAINT `FK3497BFE48EC40A` FOREIGN KEY (`uid`) REFERENCES `sec_user` (`uid`),
  ADD CONSTRAINT `FK3497BFF8456034` FOREIGN KEY (`qid`) REFERENCES `questions` (`qid`);

--
-- Constraints for table `poll_result`
--
ALTER TABLE `poll_result`
  ADD CONSTRAINT `FKD981C89D810221C` FOREIGN KEY (`poll_id`) REFERENCES `poll` (`poll_id`),
  ADD CONSTRAINT `FKD981C89D4AC2255C` FOREIGN KEY (`poll_id`) REFERENCES `poll` (`poll_id`),
  ADD CONSTRAINT `FKD981C89D7500CE58` FOREIGN KEY (`q_answer_id`) REFERENCES `questions_answers` (`q_answer_id`),
  ADD CONSTRAINT `FKD981C89D8379AB18` FOREIGN KEY (`q_answer_id`) REFERENCES `questions_answers` (`q_answer_id`);

--
-- Constraints for table `project`
--
ALTER TABLE `project`
  ADD CONSTRAINT `FKED904B1963627213` FOREIGN KEY (`users_uid`) REFERENCES `sec_user` (`uid`),
  ADD CONSTRAINT `FKED904B19B3444B07` FOREIGN KEY (`lead_uid`) REFERENCES `sec_user_secondary` (`uid`),
  ADD CONSTRAINT `FKED904B19C1BD27C7` FOREIGN KEY (`lead_uid`) REFERENCES `sec_user_secondary` (`uid`),
  ADD CONSTRAINT `FKED904B19F9DA3D53` FOREIGN KEY (`users_uid`) REFERENCES `sec_user` (`uid`);

--
-- Constraints for table `project_locations`
--
ALTER TABLE `project_locations`
  ADD CONSTRAINT `FK242951B81F4E8D4E` FOREIGN KEY (`cat_id_loc`) REFERENCES `cat_location` (`locate_id`),
  ADD CONSTRAINT `FK242951B8697E2715` FOREIGN KEY (`cat_id_project`) REFERENCES `project` (`project_id`),
  ADD CONSTRAINT `FK242951B86C2E620E` FOREIGN KEY (`cat_id_loc`) REFERENCES `cat_location` (`locate_id`),
  ADD CONSTRAINT `FK242951B8D3065BD5` FOREIGN KEY (`cat_id_project`) REFERENCES `project` (`project_id`);

--
-- Constraints for table `questions`
--
ALTER TABLE `questions`
  ADD CONSTRAINT `FK95C5414DAEC725A3` FOREIGN KEY (`id_question_pattern`) REFERENCES `questions_pattern` (`pattenr_id`),
  ADD CONSTRAINT `FK95C5414D2A2E9A63` FOREIGN KEY (`id_question_pattern`) REFERENCES `questions_pattern` (`pattenr_id`),
  ADD CONSTRAINT `FK95C5414D2DBDEDCA` FOREIGN KEY (`id_state`) REFERENCES `cat_state` (`id_state`),
  ADD CONSTRAINT `FK95C5414D4E16F8CA` FOREIGN KEY (`uid`) REFERENCES `sec_user` (`uid`),
  ADD CONSTRAINT `FK95C5414D663F8A8A` FOREIGN KEY (`id_state`) REFERENCES `cat_state` (`id_state`),
  ADD CONSTRAINT `FK95C5414DE48EC40A` FOREIGN KEY (`uid`) REFERENCES `sec_user` (`uid`);

--
-- Constraints for table `questions_answers`
--
ALTER TABLE `questions_answers`
  ADD CONSTRAINT `FK539703835E3506BB` FOREIGN KEY (`id_question_answer`) REFERENCES `questions` (`qid`),
  ADD CONSTRAINT `FK5397038325B369FB` FOREIGN KEY (`id_question_answer`) REFERENCES `questions` (`qid`);

--
-- Constraints for table `questions_dependencies`
--
ALTER TABLE `questions_dependencies`
  ADD CONSTRAINT `FK92E86ADB8379AB18` FOREIGN KEY (`q_answer_id`) REFERENCES `questions_answers` (`q_answer_id`),
  ADD CONSTRAINT `FK92E86ADB7500CE58` FOREIGN KEY (`q_answer_id`) REFERENCES `questions_answers` (`q_answer_id`);

--
-- Constraints for table `question_category_questions`
--
ALTER TABLE `question_category_questions`
  ADD CONSTRAINT `FK2FFE1845E768F3CA` FOREIGN KEY (`questionLibrary_qid`) REFERENCES `questions` (`qid`),
  ADD CONSTRAINT `FK2FFE1845115F695F` FOREIGN KEY (`question_category_qCategory`) REFERENCES `question_category` (`qCategory`);

--
-- Constraints for table `question_collection`
--
ALTER TABLE `question_collection`
  ADD CONSTRAINT `FKB4097C97E48EC40A` FOREIGN KEY (`uid`) REFERENCES `sec_user` (`uid`),
  ADD CONSTRAINT `FKB4097C974E16F8CA` FOREIGN KEY (`uid`) REFERENCES `sec_user` (`uid`);

--
-- Constraints for table `question_dependence_survey`
--
ALTER TABLE `question_dependence_survey`
  ADD CONSTRAINT `FKBB424D49BE7525` FOREIGN KEY (`sid`) REFERENCES `surveys` (`sid`),
  ADD CONSTRAINT `FKBB424D496A46A9E5` FOREIGN KEY (`sid`) REFERENCES `surveys` (`sid`);

--
-- Constraints for table `question_relations`
--
ALTER TABLE `question_relations`
  ADD CONSTRAINT `FK217954DE6A3F2BBC` FOREIGN KEY (`question_id`) REFERENCES `questions` (`qid`),
  ADD CONSTRAINT `FK217954DE15ECCA7B` FOREIGN KEY (`id_q_colection`) REFERENCES `question_collection` (`id_q_colection`),
  ADD CONSTRAINT `FK217954DE31BD8EFC` FOREIGN KEY (`question_id`) REFERENCES `questions` (`qid`),
  ADD CONSTRAINT `FK217954DED68F85BB` FOREIGN KEY (`id_q_colection`) REFERENCES `question_collection` (`id_q_colection`);

--
-- Constraints for table `sec_groups`
--
ALTER TABLE `sec_groups`
  ADD CONSTRAINT `FKBFBFCD82A548CC22` FOREIGN KEY (`secUsers_uid`) REFERENCES `sec_user` (`uid`),
  ADD CONSTRAINT `FKBFBFCD82ED100E2` FOREIGN KEY (`secUsers_uid`) REFERENCES `sec_user` (`uid`);

--
-- Constraints for table `sec_group_permission`
--
ALTER TABLE `sec_group_permission`
  ADD CONSTRAINT `FK5B8E21BDE97C7683` FOREIGN KEY (`sec_id_permission`) REFERENCES `sec_permission` (`id_permission`),
  ADD CONSTRAINT `FK5B8E21BD11E76A39` FOREIGN KEY (`sec_id_group`) REFERENCES `sec_groups` (`group_id`),
  ADD CONSTRAINT `FK5B8E21BD4A6906F9` FOREIGN KEY (`sec_id_group`) REFERENCES `sec_groups` (`group_id`),
  ADD CONSTRAINT `FK5B8E21BD7DBA1B43` FOREIGN KEY (`sec_id_permission`) REFERENCES `sec_permission` (`id_permission`);

--
-- Constraints for table `sec_project_group`
--
ALTER TABLE `sec_project_group`
  ADD CONSTRAINT `FK93685A6B697E2715` FOREIGN KEY (`cat_id_project`) REFERENCES `project` (`project_id`),
  ADD CONSTRAINT `FK93685A6B11E76A39` FOREIGN KEY (`sec_id_group`) REFERENCES `sec_groups` (`group_id`),
  ADD CONSTRAINT `FK93685A6B4A6906F9` FOREIGN KEY (`sec_id_group`) REFERENCES `sec_groups` (`group_id`),
  ADD CONSTRAINT `FK93685A6BD3065BD5` FOREIGN KEY (`cat_id_project`) REFERENCES `project` (`project_id`);

--
-- Constraints for table `sec_user_group`
--
ALTER TABLE `sec_user_group`
  ADD CONSTRAINT `FK3D8D6DB9CFE8AF58` FOREIGN KEY (`sec_id_secondary`) REFERENCES `sec_user_secondary` (`uid`),
  ADD CONSTRAINT `FK3D8D6DB911E76A39` FOREIGN KEY (`sec_id_group`) REFERENCES `sec_groups` (`group_id`),
  ADD CONSTRAINT `FK3D8D6DB94A6906F9` FOREIGN KEY (`sec_id_group`) REFERENCES `sec_groups` (`group_id`),
  ADD CONSTRAINT `FK3D8D6DB9C16FD298` FOREIGN KEY (`sec_id_secondary`) REFERENCES `sec_user_secondary` (`uid`);

--
-- Constraints for table `sec_user_permission`
--
ALTER TABLE `sec_user_permission`
  ADD CONSTRAINT `FK8A4C2D5CFE8AF58` FOREIGN KEY (`sec_id_secondary`) REFERENCES `sec_user_secondary` (`uid`),
  ADD CONSTRAINT `FK8A4C2D57DBA1B43` FOREIGN KEY (`sec_id_permission`) REFERENCES `sec_permission` (`id_permission`),
  ADD CONSTRAINT `FK8A4C2D5C16FD298` FOREIGN KEY (`sec_id_secondary`) REFERENCES `sec_user_secondary` (`uid`),
  ADD CONSTRAINT `FK8A4C2D5E97C7683` FOREIGN KEY (`sec_id_permission`) REFERENCES `sec_permission` (`id_permission`);

--
-- Constraints for table `sec_user_project`
--
ALTER TABLE `sec_user_project`
  ADD CONSTRAINT `FKEBFBDBD3CFE8AF58` FOREIGN KEY (`sec_id_secondary`) REFERENCES `sec_user_secondary` (`uid`),
  ADD CONSTRAINT `FKEBFBDBD3697E2715` FOREIGN KEY (`cat_id_project`) REFERENCES `project` (`project_id`),
  ADD CONSTRAINT `FKEBFBDBD3C16FD298` FOREIGN KEY (`sec_id_secondary`) REFERENCES `sec_user_secondary` (`uid`),
  ADD CONSTRAINT `FKEBFBDBD3D3065BD5` FOREIGN KEY (`cat_id_project`) REFERENCES `project` (`project_id`);

--
-- Constraints for table `sec_user_secondary`
--
ALTER TABLE `sec_user_secondary`
  ADD CONSTRAINT `FKE10EBBAE6EDCDCE7` FOREIGN KEY (`secUser_uid`) REFERENCES `sec_user` (`uid`),
  ADD CONSTRAINT `FKE10EBBAED86511A7` FOREIGN KEY (`secUser_uid`) REFERENCES `sec_user` (`uid`);

--
-- Constraints for table `sec_user_twitter_account`
--
ALTER TABLE `sec_user_twitter_account`
  ADD CONSTRAINT `FKC6CCEF1BA548CC22` FOREIGN KEY (`secUsers_uid`) REFERENCES `sec_user` (`uid`),
  ADD CONSTRAINT `FKC6CCEF1BED100E2` FOREIGN KEY (`secUsers_uid`) REFERENCES `sec_user` (`uid`);

--
-- Constraints for table `surveys`
--
ALTER TABLE `surveys`
  ADD CONSTRAINT `FK91914459EB48E8DF` FOREIGN KEY (`id_sid_format`) REFERENCES `survey_format` (`id_sid_format`),
  ADD CONSTRAINT `FK919144593A63AC1F` FOREIGN KEY (`id_sid_format`) REFERENCES `survey_format` (`id_sid_format`),
  ADD CONSTRAINT `FK919144594E16F8CA` FOREIGN KEY (`uid`) REFERENCES `sec_user` (`uid`),
  ADD CONSTRAINT `FK91914459E48EC40A` FOREIGN KEY (`uid`) REFERENCES `sec_user` (`uid`);

--
-- Constraints for table `survey_folder`
--
ALTER TABLE `survey_folder`
  ADD CONSTRAINT `FK7EF958F3BE7525` FOREIGN KEY (`sid`) REFERENCES `surveys` (`sid`),
  ADD CONSTRAINT `FK7EF958F34E16F8CA` FOREIGN KEY (`uid`) REFERENCES `sec_user` (`uid`),
  ADD CONSTRAINT `FK7EF958F36A46A9E5` FOREIGN KEY (`sid`) REFERENCES `surveys` (`sid`),
  ADD CONSTRAINT `FK7EF958F3E48EC40A` FOREIGN KEY (`uid`) REFERENCES `sec_user` (`uid`);

--
-- Constraints for table `survey_group`
--
ALTER TABLE `survey_group`
  ADD CONSTRAINT `FK4638955AA200A0C4` FOREIGN KEY (`cat_state_id_survey_group`) REFERENCES `cat_state` (`id_state`),
  ADD CONSTRAINT `FK4638955A697F0404` FOREIGN KEY (`cat_state_id_survey_group`) REFERENCES `cat_state` (`id_state`);

--
-- Constraints for table `survey_group_format`
--
ALTER TABLE `survey_group_format`
  ADD CONSTRAINT `FKB4DF867CEB48E8DF` FOREIGN KEY (`id_sid_format`) REFERENCES `survey_format` (`id_sid_format`),
  ADD CONSTRAINT `FKB4DF867C3A63AC1F` FOREIGN KEY (`id_sid_format`) REFERENCES `survey_format` (`id_sid_format`),
  ADD CONSTRAINT `FKB4DF867C6CB8FF29` FOREIGN KEY (`sg_id`) REFERENCES `survey_group` (`sg_id`),
  ADD CONSTRAINT `FKB4DF867CB998D3E9` FOREIGN KEY (`sg_id`) REFERENCES `survey_group` (`sg_id`);

--
-- Constraints for table `survey_group_project`
--
ALTER TABLE `survey_group_project`
  ADD CONSTRAINT `FKFD028D34F3099A6F` FOREIGN KEY (`id_sid_format`) REFERENCES `survey_group` (`sg_id`),
  ADD CONSTRAINT `FKFD028D343FE96F2F` FOREIGN KEY (`id_sid_format`) REFERENCES `survey_group` (`sg_id`),
  ADD CONSTRAINT `FKFD028D34697E2715` FOREIGN KEY (`cat_id_project`) REFERENCES `project` (`project_id`),
  ADD CONSTRAINT `FKFD028D34D3065BD5` FOREIGN KEY (`cat_id_project`) REFERENCES `project` (`project_id`);

--
-- Constraints for table `survey_pagination`
--
ALTER TABLE `survey_pagination`
  ADD CONSTRAINT `FKBEC9A99FDCE1077` FOREIGN KEY (`sid`) REFERENCES `survey_section` (`ssid`),
  ADD CONSTRAINT `FKBEC9A99FA20BB537` FOREIGN KEY (`sid`) REFERENCES `survey_section` (`ssid`);

--
-- Constraints for table `survey_result`
--
ALTER TABLE `survey_result`
  ADD CONSTRAINT `FK92EA04A2730E0C77` FOREIGN KEY (`survey_id`) REFERENCES `surveys` (`sid`),
  ADD CONSTRAINT `FK92EA04A2DC964137` FOREIGN KEY (`survey_id`) REFERENCES `surveys` (`sid`);

--
-- Constraints for table `survey_section`
--
ALTER TABLE `survey_section`
  ADD CONSTRAINT `FKFE5AD300663F8A8A` FOREIGN KEY (`id_state`) REFERENCES `cat_state` (`id_state`),
  ADD CONSTRAINT `FKFE5AD3002DBDEDCA` FOREIGN KEY (`id_state`) REFERENCES `cat_state` (`id_state`);

--
-- Constraints for table `survey_section_questions`
--
ALTER TABLE `survey_section_questions`
  ADD CONSTRAINT `FK12354ECE2E0328F4` FOREIGN KEY (`questionSection_qid`) REFERENCES `questions` (`qid`),
  ADD CONSTRAINT `FK12354ECE9FEBC1C3` FOREIGN KEY (`survey_section_ssid`) REFERENCES `survey_section` (`ssid`),
  ADD CONSTRAINT `FK12354ECEBAE1D03` FOREIGN KEY (`survey_section_ssid`) REFERENCES `survey_section` (`ssid`),
  ADD CONSTRAINT `FK12354ECEF5818C34` FOREIGN KEY (`questionSection_qid`) REFERENCES `questions` (`qid`);

--
-- Constraints for table `tweetPoll`
--
ALTER TABLE `tweetPoll`
  ADD CONSTRAINT `FKA65B1D030C6FCF4` FOREIGN KEY (`qid`) REFERENCES `questions` (`qid`),
  ADD CONSTRAINT `FKA65B1D04E16F8CA` FOREIGN KEY (`uid`) REFERENCES `sec_user` (`uid`),
  ADD CONSTRAINT `FKA65B1D0E48EC40A` FOREIGN KEY (`uid`) REFERENCES `sec_user` (`uid`),
  ADD CONSTRAINT `FKA65B1D0F8456034` FOREIGN KEY (`qid`) REFERENCES `questions` (`qid`);

--
-- Constraints for table `tweetPoll_hash_tags`
--
ALTER TABLE `tweetPoll_hash_tags`
  ADD CONSTRAINT `FKE3434CFB1CB5B5C3` FOREIGN KEY (`hashTags_hash_tag_id`) REFERENCES `hash_tags` (`hash_tag_id`),
  ADD CONSTRAINT `FKE3434CFB5671B3CC` FOREIGN KEY (`tweetPoll_tweet_poll_id`) REFERENCES `tweetPoll` (`tweet_poll_id`),
  ADD CONSTRAINT `FKE3434CFB7EBFB88C` FOREIGN KEY (`tweetPoll_tweet_poll_id`) REFERENCES `tweetPoll` (`tweet_poll_id`),
  ADD CONSTRAINT `FKE3434CFB863DEA83` FOREIGN KEY (`hashTags_hash_tag_id`) REFERENCES `hash_tags` (`hash_tag_id`);

--
-- Constraints for table `tweetpoll_result`
--
ALTER TABLE `tweetpoll_result`
  ADD CONSTRAINT `FK8749C18C5F22959` FOREIGN KEY (`tweetpoll_switch_id`) REFERENCES `tweetpoll_switch` (`tweetpoll_switch_id`),
  ADD CONSTRAINT `FK8749C18C81599E19` FOREIGN KEY (`tweetpoll_switch_id`) REFERENCES `tweetpoll_switch` (`tweetpoll_switch_id`);

--
-- Constraints for table `tweetPoll_save_published_status`
--
ALTER TABLE `tweetPoll_save_published_status`
  ADD CONSTRAINT `FKD499A4B65671B3CC` FOREIGN KEY (`tweetPoll_tweet_poll_id`) REFERENCES `tweetPoll` (`tweet_poll_id`),
  ADD CONSTRAINT `FKD499A4B62921D7A7` FOREIGN KEY (`twitterAccount_sec_user_twitter_id`) REFERENCES `sec_user_twitter_account` (`sec_user_twitter_id`),
  ADD CONSTRAINT `FKD499A4B65322467` FOREIGN KEY (`twitterAccount_sec_user_twitter_id`) REFERENCES `sec_user_twitter_account` (`sec_user_twitter_id`),
  ADD CONSTRAINT `FKD499A4B67EBFB88C` FOREIGN KEY (`tweetPoll_tweet_poll_id`) REFERENCES `tweetPoll` (`tweet_poll_id`);

--
-- Constraints for table `tweetpoll_switch`
--
ALTER TABLE `tweetpoll_switch`
  ADD CONSTRAINT `FK89F7B0A38379AB18` FOREIGN KEY (`q_answer_id`) REFERENCES `questions_answers` (`q_answer_id`),
  ADD CONSTRAINT `FK89F7B0A37500CE58` FOREIGN KEY (`q_answer_id`) REFERENCES `questions_answers` (`q_answer_id`),
  ADD CONSTRAINT `FK89F7B0A3C685581B` FOREIGN KEY (`tweet_poll_id`) REFERENCES `tweetPoll` (`tweet_poll_id`),
  ADD CONSTRAINT `FK89F7B0A3EED35CDB` FOREIGN KEY (`tweet_poll_id`) REFERENCES `tweetPoll` (`tweet_poll_id`);
