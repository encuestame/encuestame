/*
 ************************************************************************************
 * Copyright (C) 2001-2009 encuestame: system online surveys Copyright (C) 2009
 * encuestame Development Team.
 * Licensed under the Apache Software License version 2.0
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to  in writing,  software  distributed
 * under the License is distributed  on  an  "AS IS"  BASIS,  WITHOUT  WARRANTIES  OR
 * CONDITIONS OF ANY KIND, either  express  or  implied.  See  the  License  for  the
 * specific language governing permissions and limitations under the License.
 ************************************************************************************
 */

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";

--
-- Database: `encuestame`
--
CREATE DATABASE `encuesame` ;

-- --------------------------------------------------------

--
-- Table structure for table `cat_location`
--

CREATE TABLE IF NOT EXISTS `cat_location` (
  `locate_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `active` varchar(2) DEFAULT NULL,
  `description` varchar(255) NOT NULL,
  `lat` float DEFAULT NULL,
  `level` int(11) NOT NULL,
  `lng` float DEFAULT NULL,
  `loc_id_type` bigint(20) NOT NULL,
  PRIMARY KEY (`locate_id`),
  UNIQUE KEY `locate_id` (`locate_id`),
  KEY `FK8C56C1FED9C8CC22` (`loc_id_type`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `cat_locations_user`
--

CREATE TABLE IF NOT EXISTS `cat_locations_user` (
  `sec_id_secondary` bigint(20) NOT NULL,
  `cat_location_id` bigint(20) NOT NULL,
  PRIMARY KEY (`sec_id_secondary`,`cat_location_id`),
  KEY `FK4C9FBF95C16FD298` (`sec_id_secondary`),
  KEY `FK4C9FBF95BDA0EE65` (`cat_location_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `cat_location_type`
--

CREATE TABLE IF NOT EXISTS `cat_location_type` (
  `loc_id_type` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `level` int(11) DEFAULT NULL,
  PRIMARY KEY (`loc_id_type`),
  UNIQUE KEY `loc_id_type` (`loc_id_type`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

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

-- --------------------------------------------------------

--
-- Table structure for table `project`
--

CREATE TABLE IF NOT EXISTS `project` (
  `proyect_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `date_finish` datetime DEFAULT NULL,
  `date_start` datetime NOT NULL,
  `description` varchar(255) NOT NULL,
  `info` varchar(255) NOT NULL,
  `cat_state_id` bigint(20) NOT NULL,
  PRIMARY KEY (`proyect_id`),
  UNIQUE KEY `proyect_id` (`proyect_id`),
  KEY `FKED904B19A965732F` (`cat_state_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `project_locations`
--

CREATE TABLE IF NOT EXISTS `project_locations` (
  `cat_id_loc` bigint(20) NOT NULL,
  `cat_id_project` bigint(20) NOT NULL,
  PRIMARY KEY (`cat_id_project`,`cat_id_loc`),
  KEY `FK242951B8D3065BD5` (`cat_id_project`),
  KEY `FK242951B86C2E620E` (`cat_id_loc`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `questions`
--

CREATE TABLE IF NOT EXISTS `questions` (
  `qid` bigint(20) NOT NULL AUTO_INCREMENT,
  `qid_key` varchar(255) DEFAULT NULL,
  `question` varchar(255) DEFAULT NULL,
  `id_state` bigint(20) NOT NULL,
  `id_question_pattern` bigint(20) NOT NULL,
  PRIMARY KEY (`qid`),
  UNIQUE KEY `qid` (`qid`),
  KEY `FK95C5414D2DBDEDCA` (`id_state`),
  KEY `FK95C5414D2A2E9A63` (`id_question_pattern`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `questions_answers`
--

CREATE TABLE IF NOT EXISTS `questions_answers` (
  `q_answer_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `answer` varchar(255) DEFAULT NULL,
  `id_question_answer` bigint(20) NOT NULL,
  PRIMARY KEY (`q_answer_id`),
  UNIQUE KEY `q_answer_id` (`q_answer_id`),
  KEY `FK539703833DA1719E` (`id_question_answer`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

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
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

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
  KEY `FKB4097C9774931F89` (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `question_relations`
--

CREATE TABLE IF NOT EXISTS `question_relations` (
  `question_id` bigint(20) NOT NULL,
  `id_q_colection` bigint(20) NOT NULL,
  PRIMARY KEY (`question_id`,`id_q_colection`),
  KEY `FK217954DE15ECCA7B` (`id_q_colection`),
  KEY `FK217954DE49AB969F` (`question_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `sec_groups`
--

CREATE TABLE IF NOT EXISTS `sec_groups` (
  `group_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `des_info` varchar(255) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `id_state` bigint(20) NOT NULL,
  PRIMARY KEY (`group_id`),
  UNIQUE KEY `group_id` (`group_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

-- --------------------------------------------------------

--
-- Table structure for table `sec_group_permission`
--

CREATE TABLE IF NOT EXISTS `sec_group_permission` (
  `sec_id_permission` bigint(20) NOT NULL,
  `sec_id_group` bigint(20) NOT NULL,
  PRIMARY KEY (`sec_id_group`,`sec_id_permission`),
  KEY `FK5B8E21BDA3C8594C` (`sec_id_group`),
  KEY `FK5B8E21BD7DBA1B43` (`sec_id_permission`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

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
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

-- --------------------------------------------------------

--
-- Table structure for table `sec_project_group`
--

CREATE TABLE IF NOT EXISTS `sec_project_group` (
  `sec_id_group` bigint(20) NOT NULL,
  `cat_id_project` bigint(20) NOT NULL,
  PRIMARY KEY (`cat_id_project`,`sec_id_group`),
  KEY `FK93685A6BA3C8594C` (`sec_id_group`),
  KEY `FK93685A6BD3065BD5` (`cat_id_project`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `sec_user`
--

CREATE TABLE IF NOT EXISTS `sec_user` (
  `uid` bigint(20) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`uid`),
  UNIQUE KEY `uid` (`uid`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

-- --------------------------------------------------------

--
-- Table structure for table `sec_user_group`
--

CREATE TABLE IF NOT EXISTS `sec_user_group` (
  `sec_id_secondary` bigint(20) NOT NULL,
  `sec_id_group` bigint(20) NOT NULL,
  PRIMARY KEY (`sec_id_group`,`sec_id_secondary`),
  KEY `FK3D8D6DB9A3C8594C` (`sec_id_group`),
  KEY `FK3D8D6DB9C16FD298` (`sec_id_secondary`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `sec_user_permission`
--

CREATE TABLE IF NOT EXISTS `sec_user_permission` (
  `sec_id_secondary` bigint(20) NOT NULL,
  `sec_id_permission` bigint(20) NOT NULL,
  PRIMARY KEY (`sec_id_permission`,`sec_id_secondary`),
  KEY `FK8A4C2D57DBA1B43` (`sec_id_permission`),
  KEY `FK8A4C2D5C16FD298` (`sec_id_secondary`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `sec_user_project`
--

CREATE TABLE IF NOT EXISTS `sec_user_project` (
  `sec_id_secondary` bigint(20) NOT NULL,
  `cat_id_project` bigint(20) NOT NULL,
  PRIMARY KEY (`cat_id_project`,`sec_id_secondary`),
  KEY `FKEBFBDBD3D3065BD5` (`cat_id_project`),
  KEY `FKEBFBDBD3C16FD298` (`sec_id_secondary`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `sec_user_secondary`
--

CREATE TABLE IF NOT EXISTS `sec_user_secondary` (
  `uid` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `date_new` datetime DEFAULT NULL,
  `invite_code` varchar(255) DEFAULT NULL,
  `owner` bit(1) DEFAULT NULL,
  `password` varchar(255) NOT NULL,
  `publisher` bit(1) DEFAULT NULL,
  `email` varchar(100) NOT NULL,
  `status` bit(1) DEFAULT NULL,
  `twitter` varchar(2) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `secUser_uid` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`uid`),
  UNIQUE KEY `uid` (`uid`),
  UNIQUE KEY `email` (`email`),
  KEY `FKE10EBBAEFEE13866` (`secUser_uid`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

-- --------------------------------------------------------

--
-- Table structure for table `surveys`
--

CREATE TABLE IF NOT EXISTS `surveys` (
  `sid` bigint(20) NOT NULL AUTO_INCREMENT,
  `complete` varchar(2) DEFAULT NULL,
  `date_interview` date DEFAULT NULL,
  `end_date` datetime NOT NULL,
  `start_date` datetime NOT NULL,
  `ticket` int(11) NOT NULL,
  `uid` bigint(20) NOT NULL,
  `id_sid_format` bigint(20) NOT NULL,
  PRIMARY KEY (`sid`),
  UNIQUE KEY `sid` (`sid`),
  KEY `FK9191445974931F89` (`uid`),
  KEY `FK919144593A63AC1F` (`id_sid_format`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

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
  KEY `FK4638955A697F0404` (`cat_state_id_survey_group`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `survey_group_format`
--

CREATE TABLE IF NOT EXISTS `survey_group_format` (
  `sg_id` bigint(20) NOT NULL,
  `id_sid_format` bigint(20) NOT NULL,
  PRIMARY KEY (`id_sid_format`,`sg_id`),
  KEY `FKB4DF867CB998D3E9` (`sg_id`),
  KEY `FKB4DF867C3A63AC1F` (`id_sid_format`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `survey_group_project`
--

CREATE TABLE IF NOT EXISTS `survey_group_project` (
  `id_sid_format` bigint(20) NOT NULL,
  `cat_id_project` bigint(20) NOT NULL,
  PRIMARY KEY (`cat_id_project`,`id_sid_format`),
  KEY `FKFD028D34D3065BD5` (`cat_id_project`),
  KEY `FKFD028D343FE96F2F` (`id_sid_format`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

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
  KEY `FK92EA04A2DC964137` (`survey_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

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
  KEY `FKFE5AD3002DBDEDCA` (`id_state`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `cat_location`
--
ALTER TABLE `cat_location`
  ADD CONSTRAINT `FK8C56C1FED9C8CC22` FOREIGN KEY (`loc_id_type`) REFERENCES `cat_location_type` (`loc_id_type`);

--
-- Constraints for table `cat_locations_user`
--
ALTER TABLE `cat_locations_user`
  ADD CONSTRAINT `FK4C9FBF95BDA0EE65` FOREIGN KEY (`cat_location_id`) REFERENCES `cat_location` (`locate_id`),
  ADD CONSTRAINT `FK4C9FBF95C16FD298` FOREIGN KEY (`sec_id_secondary`) REFERENCES `sec_user_secondary` (`uid`);

--
-- Constraints for table `project`
--
ALTER TABLE `project`
  ADD CONSTRAINT `FKED904B19A965732F` FOREIGN KEY (`cat_state_id`) REFERENCES `cat_state` (`id_state`);

--
-- Constraints for table `project_locations`
--
ALTER TABLE `project_locations`
  ADD CONSTRAINT `FK242951B86C2E620E` FOREIGN KEY (`cat_id_loc`) REFERENCES `cat_location` (`locate_id`),
  ADD CONSTRAINT `FK242951B8D3065BD5` FOREIGN KEY (`cat_id_project`) REFERENCES `project` (`proyect_id`);

--
-- Constraints for table `questions`
--
ALTER TABLE `questions`
  ADD CONSTRAINT `FK95C5414D2A2E9A63` FOREIGN KEY (`id_question_pattern`) REFERENCES `questions_pattern` (`pattenr_id`),
  ADD CONSTRAINT `FK95C5414D2DBDEDCA` FOREIGN KEY (`id_state`) REFERENCES `cat_state` (`id_state`);

--
-- Constraints for table `questions_answers`
--
ALTER TABLE `questions_answers`
  ADD CONSTRAINT `FK539703833DA1719E` FOREIGN KEY (`id_question_answer`) REFERENCES `questions` (`qid`);

--
-- Constraints for table `question_collection`
--
ALTER TABLE `question_collection`
  ADD CONSTRAINT `FKB4097C9774931F89` FOREIGN KEY (`uid`) REFERENCES `sec_user` (`uid`);

--
-- Constraints for table `question_relations`
--
ALTER TABLE `question_relations`
  ADD CONSTRAINT `FK217954DE49AB969F` FOREIGN KEY (`question_id`) REFERENCES `questions` (`qid`),
  ADD CONSTRAINT `FK217954DE15ECCA7B` FOREIGN KEY (`id_q_colection`) REFERENCES `question_collection` (`id_q_colection`);

--
-- Constraints for table `sec_group_permission`
--
ALTER TABLE `sec_group_permission`
  ADD CONSTRAINT `FK5B8E21BD7DBA1B43` FOREIGN KEY (`sec_id_permission`) REFERENCES `sec_permission` (`id_permission`),
  ADD CONSTRAINT `FK5B8E21BDA3C8594C` FOREIGN KEY (`sec_id_group`) REFERENCES `sec_groups` (`group_id`);

--
-- Constraints for table `sec_project_group`
--
ALTER TABLE `sec_project_group`
  ADD CONSTRAINT `FK93685A6BD3065BD5` FOREIGN KEY (`cat_id_project`) REFERENCES `project` (`proyect_id`),
  ADD CONSTRAINT `FK93685A6BA3C8594C` FOREIGN KEY (`sec_id_group`) REFERENCES `sec_groups` (`group_id`);

--
-- Constraints for table `sec_user_group`
--
ALTER TABLE `sec_user_group`
  ADD CONSTRAINT `FK3D8D6DB9C16FD298` FOREIGN KEY (`sec_id_secondary`) REFERENCES `sec_user_secondary` (`uid`),
  ADD CONSTRAINT `FK3D8D6DB9A3C8594C` FOREIGN KEY (`sec_id_group`) REFERENCES `sec_groups` (`group_id`);

--
-- Constraints for table `sec_user_permission`
--
ALTER TABLE `sec_user_permission`
  ADD CONSTRAINT `FK8A4C2D5C16FD298` FOREIGN KEY (`sec_id_secondary`) REFERENCES `sec_user_secondary` (`uid`),
  ADD CONSTRAINT `FK8A4C2D57DBA1B43` FOREIGN KEY (`sec_id_permission`) REFERENCES `sec_permission` (`id_permission`);

--
-- Constraints for table `sec_user_project`
--
ALTER TABLE `sec_user_project`
  ADD CONSTRAINT `FKEBFBDBD3C16FD298` FOREIGN KEY (`sec_id_secondary`) REFERENCES `sec_user_secondary` (`uid`),
  ADD CONSTRAINT `FKEBFBDBD3D3065BD5` FOREIGN KEY (`cat_id_project`) REFERENCES `project` (`proyect_id`);

--
-- Constraints for table `sec_user_secondary`
--
ALTER TABLE `sec_user_secondary`
  ADD CONSTRAINT `FKE10EBBAEFEE13866` FOREIGN KEY (`secUser_uid`) REFERENCES `sec_user` (`uid`);

--
-- Constraints for table `surveys`
--
ALTER TABLE `surveys`
  ADD CONSTRAINT `FK919144593A63AC1F` FOREIGN KEY (`id_sid_format`) REFERENCES `survey_format` (`id_sid_format`),
  ADD CONSTRAINT `FK9191445974931F89` FOREIGN KEY (`uid`) REFERENCES `sec_user` (`uid`);

--
-- Constraints for table `survey_group`
--
ALTER TABLE `survey_group`
  ADD CONSTRAINT `FK4638955A697F0404` FOREIGN KEY (`cat_state_id_survey_group`) REFERENCES `cat_state` (`id_state`);

--
-- Constraints for table `survey_group_format`
--
ALTER TABLE `survey_group_format`
  ADD CONSTRAINT `FKB4DF867C3A63AC1F` FOREIGN KEY (`id_sid_format`) REFERENCES `survey_format` (`id_sid_format`),
  ADD CONSTRAINT `FKB4DF867CB998D3E9` FOREIGN KEY (`sg_id`) REFERENCES `survey_group` (`sg_id`);

--
-- Constraints for table `survey_group_project`
--
ALTER TABLE `survey_group_project`
  ADD CONSTRAINT `FKFD028D343FE96F2F` FOREIGN KEY (`id_sid_format`) REFERENCES `survey_group` (`sg_id`),
  ADD CONSTRAINT `FKFD028D34D3065BD5` FOREIGN KEY (`cat_id_project`) REFERENCES `project` (`proyect_id`);

--
-- Constraints for table `survey_result`
--
ALTER TABLE `survey_result`
  ADD CONSTRAINT `FK92EA04A2DC964137` FOREIGN KEY (`survey_id`) REFERENCES `surveys` (`sid`);

--
-- Constraints for table `survey_section`
--
ALTER TABLE `survey_section`
  ADD CONSTRAINT `FKFE5AD3002DBDEDCA` FOREIGN KEY (`id_state`) REFERENCES `cat_state` (`id_state`);
