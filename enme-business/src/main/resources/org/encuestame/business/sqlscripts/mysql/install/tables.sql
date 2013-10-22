--
-- Table structure for table `access_rate`
--

CREATE TABLE IF NOT EXISTS `access_rate` (
  `rateId` bigint(20) NOT NULL AUTO_INCREMENT,
  `ipAddress` varchar(255) NOT NULL,
  `rate` bit(1) DEFAULT NULL,
  `created_at` date DEFAULT NULL,
  `poll_poll_id` bigint(20) DEFAULT NULL,
  `survey_sid` bigint(20) DEFAULT NULL,
  `tweetPoll_tweet_poll_id` bigint(20) DEFAULT NULL,
  `user_uid` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`rateId`),
  UNIQUE KEY `rateId` (`rateId`),
  KEY `FKC2760EDB63976E9` (`poll_poll_id`),
  KEY `FKC2760EDBE4669675` (`user_uid`),
  KEY `FKC2760EDB51153812` (`survey_sid`),
  KEY `FKC2760EDB953C854B` (`tweetPoll_tweet_poll_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

--
-- Table structure for table `account`
--

CREATE TABLE IF NOT EXISTS `account` (
  `uid` bigint(20) NOT NULL AUTO_INCREMENT,
  `account_created_date` datetime NOT NULL,
  `account_enabled` bit(1) DEFAULT NULL,
  PRIMARY KEY (`uid`),
  UNIQUE KEY `uid` (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

--
-- Table structure for table `application`
--

CREATE TABLE IF NOT EXISTS `application` (
  `application_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `api_key` varchar(255) DEFAULT NULL,
  `callback_url` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `icon_url` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `secret` varchar(255) DEFAULT NULL,
  `slug` varchar(255) DEFAULT NULL,
  `account_uid` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`application_id`),
  UNIQUE KEY `application_id` (`application_id`),
  KEY `FK5CA405505ECE45A2` (`account_uid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

--
-- Table structure for table `application_connection`
--

CREATE TABLE IF NOT EXISTS `application_connection` (
  `connection_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `access_token` varchar(255) DEFAULT NULL,
  `api_key` varchar(255) DEFAULT NULL,
  `secret` varchar(255) DEFAULT NULL,
  `account_uid` bigint(20) DEFAULT NULL,
  `application_application_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`connection_id`),
  UNIQUE KEY `connection_id` (`connection_id`),
  KEY `FK73D5D2D27E933D7` (`account_uid`),
  KEY `FK73D5D2D4402BE26` (`application_application_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

--
-- Table structure for table `attachment`
--

CREATE TABLE IF NOT EXISTS `attachment` (
  `attachment_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `filename` varchar(255) NOT NULL,
  `uploadDate` datetime DEFAULT NULL,
  `project_id` bigint(20) NOT NULL,
  PRIMARY KEY (`attachment_id`),
  UNIQUE KEY `attachment_id` (`attachment_id`),
  KEY `FK8AF75923225A055` (`project_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

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
  KEY `FKAF12F3CB225A055` (`project_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

--
-- Table structure for table `comments`
--

CREATE TABLE IF NOT EXISTS `comments` (
  `commentId` bigint(20) NOT NULL AUTO_INCREMENT,
  `comment` longtext NOT NULL,
  `created_at` date DEFAULT NULL,
  `dislikeVote` bigint(20) DEFAULT NULL,
  `likeVote` bigint(20) DEFAULT NULL,
  `parentId` bigint(20) DEFAULT NULL,
  `pollId` bigint(20) DEFAULT NULL,
  `sid` bigint(20) DEFAULT NULL,
  `tweetPollId` bigint(20) DEFAULT NULL,
  `uid` bigint(20) NOT NULL,
  `is_published` bit(1) DEFAULT NULL,
  `is_spam` bit(1) DEFAULT NULL,
  `comment_status` int(11) DEFAULT NULL,
  PRIMARY KEY (`commentId`),
  UNIQUE KEY `commentId` (`commentId`),
  KEY `FKDC17DDF4F44558E9` (`uid`),
  KEY `FKDC17DDF4793D9E77` (`sid`),
  KEY `FKDC17DDF4CE12CAE8` (`pollId`),
  KEY `FKDC17DDF4D9AA8E98` (`tweetPollId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

--
-- Table structure for table `dashboard`
--

CREATE TABLE IF NOT EXISTS `dashboard` (
  `dashboardId` bigint(20) NOT NULL AUTO_INCREMENT,
  `sequence` int(11) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `favorite` bit(1) DEFAULT NULL,
  `favorite_counter` int(11) DEFAULT NULL,
  `dashboardName` varchar(255) NOT NULL,
  `dashboad_layout` int(11) DEFAULT NULL,
  `dashboard_selected` bit(1) DEFAULT NULL,
  `userBoard_uid` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`dashboardId`),
  UNIQUE KEY `dashboardId` (`dashboardId`),
  KEY `FKC18AEA949229BCA5` (`userBoard_uid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

--
-- Table structure for table `email`
--

CREATE TABLE IF NOT EXISTS `email` (
  `email_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_at` datetime DEFAULT NULL,
  `email` varchar(255) NOT NULL,
  `emailAccount` varchar(255) DEFAULT NULL,
  `subscribed` bit(1) NOT NULL,
  `id_list` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`email_id`),
  UNIQUE KEY `email_id` (`email_id`),
  UNIQUE KEY `email` (`email`),
  KEY `FK5C24B9CED78E617` (`id_list`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

--
-- Table structure for table `emailList`
--

CREATE TABLE IF NOT EXISTS `emailList` (
  `id_list` bigint(20) NOT NULL AUTO_INCREMENT,
  `createdAt` datetime DEFAULT NULL,
  `descripcionList` varchar(255) DEFAULT NULL,
  `list_name` varchar(255) DEFAULT NULL,
  `listState` varchar(255) DEFAULT NULL,
  `uid` bigint(20) NOT NULL,
  PRIMARY KEY (`id_list`),
  UNIQUE KEY `id_list` (`id_list`),
  KEY `FK7E5F425A2B2A6AB4` (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

--
-- Table structure for table `emailSubscribe`
--

CREATE TABLE IF NOT EXISTS `emailSubscribe` (
  `id_subscribe` bigint(20) NOT NULL AUTO_INCREMENT,
  `hashCode` varchar(255) NOT NULL,
  `email_id` bigint(20) NOT NULL,
  `id_list` bigint(20) NOT NULL,
  PRIMARY KEY (`id_subscribe`),
  UNIQUE KEY `id_subscribe` (`id_subscribe`),
  KEY `FK4B85010EED78E617` (`id_list`),
  KEY `FK4B85010EE824035` (`email_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

--
-- Table structure for table `gadget`
--

CREATE TABLE IF NOT EXISTS `gadget` (
  `gadgetId` bigint(20) NOT NULL AUTO_INCREMENT,
  `gadgetColor` varchar(255) DEFAULT NULL,
  `gadgetColumn` int(11) NOT NULL,
  `gadgetName` varchar(255) NOT NULL,
  `gadgetPosition` int(11) DEFAULT NULL,
  `gadgetType` int(11) NOT NULL,
  `status` bit(1) DEFAULT NULL,
  `dashboard_dashboardId` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`gadgetId`),
  UNIQUE KEY `gadgetId` (`gadgetId`),
  KEY `FKB549144CB975B5F9` (`dashboard_dashboardId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

--
-- Table structure for table `gadget_properties`
--

CREATE TABLE IF NOT EXISTS `gadget_properties` (
  `propertyId` bigint(20) NOT NULL AUTO_INCREMENT,
  `gadget_prop_name` varchar(255) NOT NULL,
  `gadget_prop_value` varchar(255) NOT NULL,
  `gadget_gadgetId` bigint(20) DEFAULT NULL,
  `userAccount_uid` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`propertyId`),
  UNIQUE KEY `propertyId` (`propertyId`),
  KEY `FK866B6706369F8B2C` (`userAccount_uid`),
  KEY `FK866B670629091B05` (`gadget_gadgetId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

--
-- Table structure for table `geoPoint`
--

CREATE TABLE IF NOT EXISTS `geoPoint` (
  `locate_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `lat` float DEFAULT NULL,
  `lng` float DEFAULT NULL,
  `accuracy` int(11) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `country_code` varchar(255) DEFAULT NULL,
  `country_name` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `location_status` varchar(255) DEFAULT NULL,
  `account_uid` bigint(20) DEFAULT NULL,
  `geoPointFolder_locate_folder_id` bigint(20) DEFAULT NULL,
  `loc_id_type` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`locate_id`),
  UNIQUE KEY `locate_id` (`locate_id`),
  KEY `FK6C73C0BFBD91661D` (`loc_id_type`),
  KEY `FK6C73C0BF5ECE45A2` (`account_uid`),
  KEY `FK6C73C0BF34EF9A43` (`geoPointFolder_locate_folder_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

--
-- Table structure for table `geoPoint_folder`
--

CREATE TABLE IF NOT EXISTS `geoPoint_folder` (
  `locate_folder_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_at` date DEFAULT NULL,
  `folderName` varchar(255) NOT NULL,
  `folder_status` int(11) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `createdBy_uid` bigint(20) DEFAULT NULL,
  `uid` bigint(20) NOT NULL,
  `subLocationFolder_locate_folder_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`locate_folder_id`),
  UNIQUE KEY `locate_folder_id` (`locate_folder_id`),
  KEY `FKF4A1D3EE2B2A6AB4` (`uid`),
  KEY `FKF4A1D3EE6EF241E9` (`createdBy_uid`),
  KEY `FKF4A1D3EE6E4ED46D` (`subLocationFolder_locate_folder_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

--
-- Table structure for table `geoPoint_type`
--

CREATE TABLE IF NOT EXISTS `geoPoint_type` (
  `loc_id_type` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `level` int(11) DEFAULT NULL,
  `users_uid` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`loc_id_type`),
  UNIQUE KEY `loc_id_type` (`loc_id_type`),
  KEY `FK514326BA4075E3FD` (`users_uid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

--
-- Table structure for table `groups`
--

CREATE TABLE IF NOT EXISTS `groups` (
  `group_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `des_info` varchar(255) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `id_state` bigint(20) DEFAULT NULL,
  `account_uid` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`group_id`),
  UNIQUE KEY `group_id` (`group_id`),
  KEY `FKB63DD9D45ECE45A2` (`account_uid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

--
-- Table structure for table `groups_permission`
--

CREATE TABLE IF NOT EXISTS `groups_permission` (
  `sec_id_group` bigint(20) NOT NULL,
  `sec_id_permission` bigint(20) NOT NULL,
  PRIMARY KEY (`sec_id_group`,`sec_id_permission`),
  KEY `FK7F1951A45895AFF` (`sec_id_group`),
  KEY `FK7F1951A43ADB63D` (`sec_id_permission`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Table structure for table `group_permission`
--

CREATE TABLE IF NOT EXISTS `group_permission` (
  `sec_id_permission` bigint(20) NOT NULL,
  `sec_id_group` bigint(20) NOT NULL,
  PRIMARY KEY (`sec_id_permission`,`sec_id_group`),
  KEY `FK362E6F8F45895AFF` (`sec_id_group`),
  KEY `FK362E6F8F43ADB63D` (`sec_id_permission`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Table structure for table `hash_tags`
--

CREATE TABLE IF NOT EXISTS `hash_tags` (
  `hash_tag_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `tag` varchar(255) DEFAULT NULL,
  `hits` bigint(20) DEFAULT NULL,
  `size` bigint(20) DEFAULT NULL,
  `hashtag_updated_date` datetime DEFAULT NULL,
  `created_updated` datetime DEFAULT NULL,
  PRIMARY KEY (`hash_tag_id`),
  UNIQUE KEY `hash_tag_id` (`hash_tag_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

--
-- Table structure for table `hits`
--

CREATE TABLE IF NOT EXISTS `hits` (
  `hit_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_at` date DEFAULT NULL,
  `hits_ip_address` varchar(100) NOT NULL,
  `hashTag_hash_tag_id` bigint(20) DEFAULT NULL,
  `poll_poll_id` bigint(20) DEFAULT NULL,
  `hit_category` int(11) NOT NULL,
  `survey_sid` bigint(20) DEFAULT NULL,
  `tweetPoll_tweet_poll_id` bigint(20) DEFAULT NULL,
  `userAccount_uid` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`hit_id`),
  UNIQUE KEY `hit_id` (`hit_id`),
  KEY `FK30DF4019AA125` (`hashTag_hash_tag_id`),
  KEY `FK30DF4063976E9` (`poll_poll_id`),
  KEY `FK30DF4051153812` (`survey_sid`),
  KEY `FK30DF40953C854B` (`tweetPoll_tweet_poll_id`),
  KEY `FK30DF40369F8B2C` (`userAccount_uid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

--
-- Table structure for table `notification`
--

CREATE TABLE IF NOT EXISTS `notification` (
  `notification_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `additional_description` varchar(255) NOT NULL,
  `created` datetime NOT NULL,
  `description` varchar(255) NOT NULL,
  `group_notification` bit(1) DEFAULT NULL,
  `readed` bit(1) NOT NULL,
  `reference` varchar(255) DEFAULT NULL,
  `uid` bigint(20) NOT NULL,
  PRIMARY KEY (`notification_id`),
  UNIQUE KEY `notification_id` (`notification_id`),
  KEY `FK237A88EB2B2A6AB4` (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

--
-- Table structure for table `permission`
--

CREATE TABLE IF NOT EXISTS `permission` (
  `id_permission` bigint(20) NOT NULL AUTO_INCREMENT,
  `permission` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id_permission`),
  UNIQUE KEY `id_permission` (`id_permission`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

--
-- Table structure for table `poll`
--

CREATE TABLE IF NOT EXISTS `poll` (
  `poll_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `lat` float DEFAULT NULL,
  `lng` float DEFAULT NULL,
  `additional_info` varchar(255) DEFAULT NULL,
  `closeAfterDate` bit(1) DEFAULT NULL,
  `close_after_quota` bit(1) DEFAULT NULL,
  `close_date` datetime DEFAULT NULL,
  `closed_quota` int(11) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `custom_final_message` int(11) DEFAULT NULL,
  `custom_message` bit(1) DEFAULT NULL,
  `custom_start_message` varchar(255) DEFAULT NULL,
  `dislike_vote` bigint(20) DEFAULT NULL,
  `end_date` datetime DEFAULT NULL,
  `favorites` bit(1) DEFAULT NULL,
  `hits` bigint(20) DEFAULT NULL,
  `ip_protection` varchar(255) DEFAULT NULL,
  `ip_restrictions` bit(1) DEFAULT NULL,
  `like_vote` bigint(20) DEFAULT NULL,
  `multiple_response` int(11) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `notifications` bit(1) DEFAULT NULL,
  `number_votes` bigint(20) DEFAULT NULL,
  `optional_title` varchar(255) DEFAULT NULL,
  `password_protection` varchar(255) DEFAULT NULL,
  `password_restrictions` bit(1) DEFAULT NULL,
  `relevance` bigint(20) DEFAULT NULL,
  `showAdditionalInfo` bit(1) DEFAULT NULL,
  `comment_option` int(11) DEFAULT NULL,
  `show_results` bit(1) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `poll_completed` bit(1) NOT NULL,
  `poll_hash` varchar(255) NOT NULL,
  `publish_poll` bit(1) DEFAULT NULL,
  `editor` bigint(20) DEFAULT NULL,
  `owner_id` bigint(20) DEFAULT NULL,
  `poll_folder` bigint(20) DEFAULT NULL,
  `qid` bigint(20) NOT NULL,
  PRIMARY KEY (`poll_id`),
  UNIQUE KEY `poll_id` (`poll_id`),
  UNIQUE KEY `poll_hash` (`poll_hash`),
  KEY `FK3497BF89452CCA` (`poll_folder`),
  KEY `FK3497BF50FE71F5` (`qid`),
  KEY `FK3497BFA64FB606` (`editor`),
  KEY `FK3497BF8E4A448B` (`owner_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

--
-- Table structure for table `poll_folder`
--

CREATE TABLE IF NOT EXISTS `poll_folder` (
  `pollFolderId` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_at` date DEFAULT NULL,
  `folderName` varchar(255) NOT NULL,
  `folder_status` int(11) DEFAULT NULL,
  `createdBy_uid` bigint(20) DEFAULT NULL,
  `uid` bigint(20) NOT NULL,
  PRIMARY KEY (`pollFolderId`),
  UNIQUE KEY `pollFolderId` (`pollFolderId`),
  KEY `FKC5911CEE2B2A6AB4` (`uid`),
  KEY `FKC5911CEE6EF241E9` (`createdBy_uid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

--
-- Table structure for table `poll_hashtags`
--

CREATE TABLE IF NOT EXISTS `poll_hashtags` (
  `poll_id` bigint(20) NOT NULL,
  `hastag_id` bigint(20) NOT NULL,
  PRIMARY KEY (`poll_id`,`hastag_id`),
  KEY `FK9D199EA7DA98FFE1` (`hastag_id`),
  KEY `FK9D199EA7F0ED6769` (`poll_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Table structure for table `poll_result`
--

CREATE TABLE IF NOT EXISTS `poll_result` (
  `poll_resultId` bigint(20) NOT NULL AUTO_INCREMENT,
  `ipAddress` varchar(255) NOT NULL,
  `votation_date` datetime NOT NULL,
  `q_answer_id` bigint(20) NOT NULL,
  `poll_id` bigint(20) NOT NULL,
  PRIMARY KEY (`poll_resultId`),
  UNIQUE KEY `poll_resultId` (`poll_resultId`),
  KEY `FKD981C89DDDD118B5` (`q_answer_id`),
  KEY `FKD981C89DF0ED6769` (`poll_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

--
-- Table structure for table `project`
--

CREATE TABLE IF NOT EXISTS `project` (
  `project_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `hide_project` bit(1) DEFAULT NULL,
  `notify_members` bit(1) DEFAULT NULL,
  `priority` varchar(255) DEFAULT NULL,
  `date_finish` datetime NOT NULL,
  `date_start` datetime NOT NULL,
  `description` longtext,
  `project_info` longtext,
  `project_name` varchar(255) NOT NULL,
  `project_status` varchar(255) DEFAULT NULL,
  `published` bit(1) DEFAULT NULL,
  `lead_uid` bigint(20) DEFAULT NULL,
  `users_uid` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`project_id`),
  UNIQUE KEY `project_id` (`project_id`),
  KEY `FKED904B19514C1986` (`lead_uid`),
  KEY `FKED904B194075E3FD` (`users_uid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

--
-- Table structure for table `project_geoPoint`
--

CREATE TABLE IF NOT EXISTS `project_geoPoint` (
  `cat_id_project` bigint(20) NOT NULL,
  `cat_id_loc` bigint(20) NOT NULL,
  PRIMARY KEY (`cat_id_project`,`cat_id_loc`),
  KEY `FK2599132584536452` (`cat_id_project`),
  KEY `FK2599132535313189` (`cat_id_loc`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Table structure for table `project_group`
--

CREATE TABLE IF NOT EXISTS `project_group` (
  `sec_id_group` bigint(20) NOT NULL,
  `cat_id_project` bigint(20) NOT NULL,
  PRIMARY KEY (`cat_id_project`,`sec_id_group`),
  KEY `FKC7652DD945895AFF` (`sec_id_group`),
  KEY `FKC7652DD984536452` (`cat_id_project`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Table structure for table `project_locations`
--

CREATE TABLE IF NOT EXISTS `project_locations` (
  `cat_id_loc` bigint(20) NOT NULL,
  `cat_id_project` bigint(20) NOT NULL,
  PRIMARY KEY (`cat_id_loc`,`cat_id_project`),
  KEY `FK242951B884536452` (`cat_id_project`),
  KEY `FK242951B835313189` (`cat_id_loc`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Table structure for table `questions`
--

CREATE TABLE IF NOT EXISTS `questions` (
  `qid` bigint(20) NOT NULL AUTO_INCREMENT,
  `question_created_date` datetime DEFAULT NULL,
  `question_hits` bigint(20) DEFAULT NULL,
  `qid_key` varchar(255) DEFAULT NULL,
  `question` varchar(255) NOT NULL,
  `shared_question` bit(1) DEFAULT NULL,
  `question_slug` varchar(255) NOT NULL,
  `uid` bigint(20) NOT NULL,
  `section_ssid` bigint(20) DEFAULT NULL,
  `question_pattern` int(11) DEFAULT NULL,
  PRIMARY KEY (`qid`),
  UNIQUE KEY `qid` (`qid`),
  KEY `FK95C5414D2B2A6AB4` (`uid`),
  KEY `FK95C5414D39E97991` (`section_ssid`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=1;

--
-- Table structure for table `questions_answers`
--

CREATE TABLE IF NOT EXISTS `questions_answers` (
  `q_answer_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `answer` varchar(255) NOT NULL,
  `answerType` int(11) DEFAULT NULL,
  `color` varchar(255) NOT NULL,
  `created_date` datetime DEFAULT NULL,
  `short_url_provider` int(11) DEFAULT NULL,
  `answer_hash` varchar(255) DEFAULT NULL,
  `answer_url` varchar(255) DEFAULT NULL,
  `id_question_answer` bigint(20) NOT NULL,
  PRIMARY KEY (`q_answer_id`),
  UNIQUE KEY `q_answer_id` (`q_answer_id`),
  KEY `FK539703837E6C7BBC` (`id_question_answer`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

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
  KEY `FK92E86ADBDDD118B5` (`q_answer_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

--
-- Table structure for table `questions_pattern`
--

CREATE TABLE IF NOT EXISTS `questions_pattern` (
  `pattenr_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `des_qid` varchar(255) DEFAULT NULL,
  `finallity_patter` varchar(255) DEFAULT NULL,
  `label_qid` varchar(255) NOT NULL,
  `level_patter` int(11) DEFAULT NULL,
  `template_patron` varchar(255) DEFAULT NULL,
  `type_pattern` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`pattenr_id`),
  UNIQUE KEY `pattenr_id` (`pattenr_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

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
-- Table structure for table `question_category_questions`
--

CREATE TABLE IF NOT EXISTS `question_category_questions` (
  `question_category_qCategory` bigint(20) NOT NULL,
  `questionLibrary_qid` bigint(20) NOT NULL,
  PRIMARY KEY (`question_category_qCategory`,`questionLibrary_qid`),
  KEY `FK2FFE1845B10E79BE` (`question_category_qCategory`),
  KEY `FK2FFE18457A068CB` (`questionLibrary_qid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

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
  KEY `FKB4097C972B2A6AB4` (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

--
-- Table structure for table `question_dependence_survey`
--

CREATE TABLE IF NOT EXISTS `question_dependence_survey` (
  `question_dependence_survey` bigint(20) NOT NULL AUTO_INCREMENT,
  `sid` bigint(20) NOT NULL,
  PRIMARY KEY (`question_dependence_survey`),
  UNIQUE KEY `question_dependence_survey` (`question_dependence_survey`),
  KEY `FKBB424D49793D9E77` (`sid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

--
-- Table structure for table `question_relations`
--

CREATE TABLE IF NOT EXISTS `question_relations` (
  `question_id` bigint(20) NOT NULL,
  `id_q_colection` bigint(20) NOT NULL,
  PRIMARY KEY (`question_id`,`id_q_colection`),
  KEY `FK217954DE893521DA` (`id_q_colection`),
  KEY `FK217954DE8A76A0BD` (`question_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Table structure for table `social_account`
--

CREATE TABLE IF NOT EXISTS `social_account` (
  `social_account_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `oauth_token` varchar(255) DEFAULT NULL,
  `type_account` int(11) DEFAULT NULL,
  `oauth_app_key` bigint(20) DEFAULT NULL,
  `oauth2_expires` varchar(255) DEFAULT NULL,
  `picture_profile_url` varchar(255) DEFAULT NULL,
  `public_profile_url` varchar(255) DEFAULT NULL,
  `oauth_refresh_token` varchar(255) DEFAULT NULL,
  `oauth_secret_token` varchar(255) DEFAULT NULL,
  `social_profile_id` varchar(255) NOT NULL,
  `added_account_date` datetime NOT NULL,
  `default_selected` bit(1) DEFAULT NULL,
  `description_profile` varchar(255) DEFAULT NULL,
  `social_account_email` varchar(255) DEFAULT NULL,
  `picture_url` varchar(255) DEFAULT NULL,
  `picture_thumbnail_url` varchar(255) DEFAULT NULL,
  `profile_url` varchar(255) DEFAULT NULL,
  `real_name` varchar(255) DEFAULT NULL,
  `social_account_name` varchar(255) NOT NULL,
  `social_support` int(11) DEFAULT NULL,
  `type_auth` varchar(255) DEFAULT NULL,
  `upgraded_credentials_last_date` datetime NOT NULL,
  `account_verified` bit(1) DEFAULT NULL,
  `account_uid` bigint(20) DEFAULT NULL,
  `userOwner_uid` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`social_account_id`),
  UNIQUE KEY `social_account_id` (`social_account_id`),
  UNIQUE KEY `social_account_name` (`social_account_name`),
  KEY `FK50078B5B5ECE45A2` (`account_uid`),
  KEY `FK50078B5BF2F411F2` (`userOwner_uid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

--
-- Table structure for table `surveys`
--

CREATE TABLE IF NOT EXISTS `surveys` (
  `sid` bigint(20) NOT NULL AUTO_INCREMENT,
  `lat` float DEFAULT NULL,
  `lng` float DEFAULT NULL,
  `additional_info` varchar(255) DEFAULT NULL,
  `closeAfterDate` bit(1) DEFAULT NULL,
  `close_after_quota` bit(1) DEFAULT NULL,
  `close_date` datetime DEFAULT NULL,
  `closed_quota` int(11) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `custom_final_message` int(11) DEFAULT NULL,
  `custom_message` bit(1) DEFAULT NULL,
  `custom_start_message` varchar(255) DEFAULT NULL,
  `dislike_vote` bigint(20) DEFAULT NULL,
  `end_date` datetime DEFAULT NULL,
  `favorites` bit(1) DEFAULT NULL,
  `hits` bigint(20) DEFAULT NULL,
  `ip_protection` varchar(255) DEFAULT NULL,
  `ip_restrictions` bit(1) DEFAULT NULL,
  `like_vote` bigint(20) DEFAULT NULL,
  `multiple_response` int(11) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `notifications` bit(1) DEFAULT NULL,
  `number_votes` bigint(20) DEFAULT NULL,
  `optional_title` varchar(255) DEFAULT NULL,
  `password_protection` varchar(255) DEFAULT NULL,
  `password_restrictions` bit(1) DEFAULT NULL,
  `relevance` bigint(20) DEFAULT NULL,
  `showAdditionalInfo` bit(1) DEFAULT NULL,
  `comment_option` int(11) DEFAULT NULL,
  `show_results` bit(1) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `complete` varchar(2) DEFAULT NULL,
  `date_interview` date DEFAULT NULL,
  `schedule_date_survey` datetime DEFAULT NULL,
  `is_Schedule` bit(1) DEFAULT NULL,
  `show_progress_bar` bit(1) DEFAULT NULL,
  `start_date` datetime DEFAULT NULL,
  `survey_slug_name` varchar(255) DEFAULT NULL,
  `ticket` int(11) DEFAULT NULL,
  `editor` bigint(20) DEFAULT NULL,
  `owner_id` bigint(20) DEFAULT NULL,
  `project_project_id` bigint(20) DEFAULT NULL,
  `survey_folder` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`sid`),
  UNIQUE KEY `sid` (`sid`),
  KEY `FK91914459A3C7A06A` (`survey_folder`),
  KEY `FK9191445973FF13B` (`project_project_id`),
  KEY `FK91914459A64FB606` (`editor`),
  KEY `FK919144598E4A448B` (`owner_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

--
-- Table structure for table `survey_folder`
--

CREATE TABLE IF NOT EXISTS `survey_folder` (
  `survey_folderId` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_at` date DEFAULT NULL,
  `folderName` varchar(255) NOT NULL,
  `folder_status` int(11) DEFAULT NULL,
  `createdBy_uid` bigint(20) DEFAULT NULL,
  `uid` bigint(20) NOT NULL,
  PRIMARY KEY (`survey_folderId`),
  UNIQUE KEY `survey_folderId` (`survey_folderId`),
  KEY `FK7EF958F32B2A6AB4` (`uid`),
  KEY `FK7EF958F36EF241E9` (`createdBy_uid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

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
-- Table structure for table `survey_group`
--

CREATE TABLE IF NOT EXISTS `survey_group` (
  `sg_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `date_create` datetime DEFAULT NULL,
  `group_name` varchar(60) DEFAULT NULL,
  PRIMARY KEY (`sg_id`),
  UNIQUE KEY `sg_id` (`sg_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

--
-- Table structure for table `survey_group_format`
--

CREATE TABLE IF NOT EXISTS `survey_group_format` (
  `sg_id` bigint(20) NOT NULL,
  `id_sid_format` bigint(20) NOT NULL,
  PRIMARY KEY (`id_sid_format`,`sg_id`),
  KEY `FKB4DF867C310E993C` (`sg_id`),
  KEY `FKB4DF867CB1A6912C` (`id_sid_format`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Table structure for table `survey_group_project`
--

CREATE TABLE IF NOT EXISTS `survey_group_project` (
  `id_sid_format` bigint(20) NOT NULL,
  `cat_id_project` bigint(20) NOT NULL,
  PRIMARY KEY (`cat_id_project`,`id_sid_format`),
  KEY `FKFD028D3484536452` (`cat_id_project`),
  KEY `FKFD028D34B75F3482` (`id_sid_format`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Table structure for table `survey_pagination`
--

CREATE TABLE IF NOT EXISTS `survey_pagination` (
  `pagination_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `pageNumber` int(11) DEFAULT NULL,
  `sid` bigint(20) NOT NULL,
  `ssid` bigint(20) NOT NULL,
  PRIMARY KEY (`pagination_id`),
  UNIQUE KEY `pagination_id` (`pagination_id`),
  KEY `FKBEC9A99F793D9E77` (`sid`),
  KEY `FKBEC9A99F1359B877` (`ssid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

--
-- Table structure for table `survey_result`
--

CREATE TABLE IF NOT EXISTS `survey_result` (
  `rid` bigint(20) NOT NULL AUTO_INCREMENT,
  `answer_q_answer_id` bigint(20) DEFAULT NULL,
  `question_qid` bigint(20) DEFAULT NULL,
  `survey_sid` bigint(20) DEFAULT NULL,
  `txtResponse` varchar(255) DEFAULT NULL,
  `hash` varchar(255) NOT NULL,
  PRIMARY KEY (`rid`),
  UNIQUE KEY `rid` (`rid`),
  KEY `FK92EA04A246BF7A1C` (`question_qid`),
  KEY `FK92EA04A2496009B4` (`answer_q_answer_id`),
  KEY `FK92EA04A251153812` (`survey_sid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

--
-- Table structure for table `survey_section`
--

CREATE TABLE IF NOT EXISTS `survey_section` (
  `ssid` bigint(20) NOT NULL AUTO_INCREMENT,
  `desc_section` varchar(255) DEFAULT NULL,
  `survey_sid` bigint(20) DEFAULT NULL,
  `section_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ssid`),
  UNIQUE KEY `ssid` (`ssid`),
  KEY `FKFE5AD30051153812` (`survey_sid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

--
-- Table structure for table `tweetPoll`
--

CREATE TABLE IF NOT EXISTS `tweetPoll` (
  `tweet_poll_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `lat` float DEFAULT NULL,
  `lng` float DEFAULT NULL,
  `allow_live_results` bit(1) DEFAULT NULL,
  `allow_repeated_votes` bit(1) DEFAULT NULL,
  `captcha` bit(1) DEFAULT NULL,
  `close_notification` bit(1) DEFAULT NULL,
  `completed` bit(1) NOT NULL,
  `create_date` datetime DEFAULT NULL,
  `limit_with_date` bit(1) DEFAULT NULL,
  `date_limited` datetime DEFAULT NULL,
  `dislikeVote` bigint(20) DEFAULT NULL,
  `favourite` bit(1) DEFAULT NULL,
  `hits` bigint(20) DEFAULT NULL,
  `likeVote` bigint(20) DEFAULT NULL,
  `limit_votes` int(11) DEFAULT NULL,
  `limits_votes_enabled` bit(1) DEFAULT NULL,
  `max_repeated_votes` int(11) DEFAULT NULL,
  `numberVotes` bigint(20) DEFAULT NULL,
  `publish` bit(1) DEFAULT NULL,
  `relevance` bigint(20) DEFAULT NULL,
  `result_notification` bit(1) DEFAULT NULL,
  `resume_live_results` bit(1) DEFAULT NULL,
  `resume_tweetpoll_dashboard` bit(1) DEFAULT NULL,
  `schedule_date_tweet` datetime DEFAULT NULL,
  `schedule` bit(1) DEFAULT NULL,
  `last_date_updated` datetime DEFAULT NULL,
  `editor` bigint(20) DEFAULT NULL,
  `qid` bigint(20) NOT NULL,
  `uid` bigint(20) NOT NULL,
  `comment_option` int(11) DEFAULT NULL,
  `tweetPollFolderId` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`tweet_poll_id`),
  UNIQUE KEY `tweet_poll_id` (`tweet_poll_id`),
  KEY `FKA65B1D02B2A6AB4` (`uid`),
  KEY `FKA65B1D0D9BA7E54` (`tweetPollFolderId`),
  KEY `FKA65B1D050FE71F5` (`qid`),
  KEY `FKA65B1D0A64FB606` (`editor`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

--
-- Table structure for table `tweetPoll_Folder`
--

CREATE TABLE IF NOT EXISTS `tweetPoll_Folder` (
  `tweetPollFolderId` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_at` date DEFAULT NULL,
  `folderName` varchar(255) NOT NULL,
  `folder_status` int(11) DEFAULT NULL,
  `createdBy_uid` bigint(20) DEFAULT NULL,
  `uid` bigint(20) NOT NULL,
  PRIMARY KEY (`tweetPollFolderId`),
  UNIQUE KEY `tweetTFolderId` (`tweetPollFolderId`),
  KEY `FKA027A9DD2B2A6AB4` (`uid`),
  KEY `FKA027A9DD6EF241E9` (`createdBy_uid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

--
-- Table structure for table `tweetpoll_hashtags`
--

CREATE TABLE IF NOT EXISTS `tweetpoll_hashtags` (
  `tweetpoll_id` bigint(20) NOT NULL,
  `hastag_id` bigint(20) NOT NULL,
  PRIMARY KEY (`hastag_id`,`tweetpoll_id`),
  KEY `FKF8C717D6286705D7` (`tweetpoll_id`),
  KEY `FKF8C717D6DA98FFE1` (`hastag_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Table structure for table `tweetpoll_result`
--

CREATE TABLE IF NOT EXISTS `tweetpoll_result` (
  `tweetpoll_resultId` bigint(20) NOT NULL AUTO_INCREMENT,
  `lat` float DEFAULT NULL,
  `lng` float DEFAULT NULL,
  `ip_vote` varchar(100) NOT NULL,
  `tweet_date_response` datetime NOT NULL,
  `tweetpoll_switch_id` bigint(20) NOT NULL,
  PRIMARY KEY (`tweetpoll_resultId`),
  UNIQUE KEY `tweetpoll_resultId` (`tweetpoll_resultId`),
  KEY `FK8749C18CB9D39F98` (`tweetpoll_switch_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

--
-- Table structure for table `tweetPoll_save_published_status`
--

CREATE TABLE IF NOT EXISTS `tweetPoll_save_published_status` (
  `status_save_poll_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `type` varchar(255) DEFAULT NULL,
  `status_description` varchar(255) DEFAULT NULL,
  `publication_date_tweet` datetime DEFAULT NULL,RT
  `status` int(11) DEFAULT NULL,
  `tweet_content` varchar(255) DEFAULT NULL,
  `tweet_id` varchar(255) DEFAULT NULL,
  `poll_poll_id` bigint(20) DEFAULT NULL,
  `socialAccount_social_account_id` bigint(20) DEFAULT NULL,
  `survey_sid` bigint(20) DEFAULT NULL,
  `tweetPoll_tweet_poll_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`status_save_poll_id`),
  UNIQUE KEY `status_save_poll_id` (`status_save_poll_id`),
  KEY `FKD499A4B65239D117` (`socialAccount_social_account_id`),
  KEY `FKD499A4B663976E9` (`poll_poll_id`),
  KEY `FKD499A4B651153812` (`survey_sid`),
  KEY `FKD499A4B6953C854B` (`tweetPoll_tweet_poll_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

--
-- Table structure for table `tweetpoll_switch`
--

CREATE TABLE IF NOT EXISTS `tweetpoll_switch` (
  `tweetpoll_switch_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `tweet_code` varchar(255) NOT NULL,
  `last_date_updated` datetime NOT NULL,
  `short_url` varchar(255) DEFAULT NULL,
  `q_answer_id` bigint(20) NOT NULL,
  `tweet_poll_id` bigint(20) NOT NULL,
  `relative_url` varchar(400) DEFAULT NULL,
  PRIMARY KEY (`tweetpoll_switch_id`),
  UNIQUE KEY `tweetpoll_switch_id` (`tweetpoll_switch_id`),
  UNIQUE KEY `tweet_code` (`tweet_code`),
  KEY `FK89F7B0A3550299A` (`tweet_poll_id`),
  KEY `FK89F7B0A3DDD118B5` (`q_answer_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

--
-- Table structure for table `userAccount`
--

CREATE TABLE IF NOT EXISTS `userAccount` (
  `uid` bigint(20) NOT NULL AUTO_INCREMENT,
  `lat` float DEFAULT NULL,
  `lng` float DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `date_new` datetime DEFAULT NULL,
  `invite_code` varchar(255) DEFAULT NULL,
  `last_ip_logged` varchar(255) DEFAULT NULL,
  `last_time_logged` datetime DEFAULT NULL,
  `password` varchar(255) NOT NULL,
  `picture_source` int(11) DEFAULT NULL,
  `shared_profile` bit(1) DEFAULT NULL,
  `email` varchar(150) NOT NULL,
  `userProfilePicture` varchar(255) DEFAULT NULL,
  `status` bit(1) DEFAULT NULL,
  `username` varchar(30) NOT NULL,
  `account_uid` bigint(20) DEFAULT NULL,
  `groupId` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`uid`),
  UNIQUE KEY `uid` (`uid`),
  UNIQUE KEY `email` (`email`),
  UNIQUE KEY `username` (`username`),
  UNIQUE KEY `username_2` (`username`,`email`),
  KEY `FKA7D56BE25ECE45A2` (`account_uid`),
  KEY `FKA7D56BE2B8EB1450` (`groupId`),
  KEY `emailIndex` (`email`),
  KEY `usernameIndex` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

--
-- Table structure for table `userAccount_followers`
--

CREATE TABLE IF NOT EXISTS `userAccount_followers` (
  `uid` bigint(20) NOT NULL,
  `uid_follower` bigint(20) NOT NULL,
  PRIMARY KEY (`uid`,`uid_follower`),
  KEY `FK7F1957F8F44558E9` (`uid`),
  KEY `FK7F1957F8E53FBC6` (`uid_follower`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Table structure for table `userAccount_permission`
--

CREATE TABLE IF NOT EXISTS `userAccount_permission` (
  `sec_id_secondary` bigint(20) NOT NULL,
  `sec_id_permission` bigint(20) NOT NULL,
  PRIMARY KEY (`sec_id_permission`,`sec_id_secondary`),
  KEY `FKBE01CE4C43ADB63D` (`sec_id_permission`),
  KEY `FKBE01CE4C5F77A117` (`sec_id_secondary`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Table structure for table `userAccount_project`
--

CREATE TABLE IF NOT EXISTS `userAccount_project` (
  `sec_id_secondary` bigint(20) NOT NULL,
  `cat_id_project` bigint(20) NOT NULL,
  PRIMARY KEY (`cat_id_project`,`sec_id_secondary`),
  KEY `FKFBC45BBC84536452` (`cat_id_project`),
  KEY `FKFBC45BBC5F77A117` (`sec_id_secondary`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


CREATE TABLE IF NOT EXISTS `survey_hashtags` (
  `sid` bigint(20) NOT NULL,
  `hastag_id` bigint(20) NOT NULL,
  PRIMARY KEY (`sid`,`hastag_id`),
  KEY `FK9D62ED6C793D9E77` (`sid`),
  KEY `FK9D62ED6CDA98FFE1` (`hastag_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE IF NOT EXISTS `hash_tags_ranking` (
  `rank_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `average` double DEFAULT NULL,
  `ranking_updated` datetime DEFAULT NULL,
  `hashTag_hash_tag_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`rank_id`),
  UNIQUE KEY `rank_id` (`rank_id`),
  KEY `FK71DECDA119AA125` (`hashTag_hash_tag_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=2026;

--
-- Table structure for table `question_preferences`
--

CREATE TABLE IF NOT EXISTS `question_preferences` (
  `preferenceId` bigint(20) NOT NULL AUTO_INCREMENT,
  `preference` varchar(255) DEFAULT NULL,
  `question_qid` bigint(20) DEFAULT NULL,
  `preference_value` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`preferenceId`),
  UNIQUE KEY `preferenceId` (`preferenceId`),
  KEY `FKD540D01F46BF7A1C` (`question_qid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1;


--
-- Table structure for table `survey_temporal_result`
--
CREATE TABLE IF NOT EXISTS `survey_temporal_result` (
  `IdTempResult` bigint(20) NOT NULL AUTO_INCREMENT,
  `txtResponse` varchar(255) DEFAULT NULL,
  `hash` varchar(255) NOT NULL,
  `answer_q_answer_id` bigint(20) DEFAULT NULL,
  `question_qid` bigint(20) DEFAULT NULL,
  `survey_sid` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`IdTempResult`),
  UNIQUE KEY `IdTempResult` (`IdTempResult`),
  UNIQUE KEY `hash` (`hash`),
  KEY `FK7867CF546BF7A1C` (`question_qid`),
  KEY `FK7867CF5496009B4` (`answer_q_answer_id`),
  KEY `FK7867CF551153812` (`survey_sid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;


--
-- Table structure for table `scheduled`
--

CREATE TABLE IF NOT EXISTS `scheduled` (
	`scheduled_id` bigint(20) NOT NULL AUTO_INCREMENT,
	`tweetPoll_tweet_poll_id` bigint(20) DEFAULT NULL,
	`poll_poll_id` bigint(20) DEFAULT NULL,
 	`survey_sid` bigint(20) DEFAULT NULL,
 	`tweet_text` varchar(255) DEFAULT NULL,
	`scheduled_date` datetime DEFAULT NULL,
	`socialAccount_social_account_id` bigint(20) DEFAULT NULL,
	`status` int(11) DEFAULT NULL,
	`attempts` int(11) DEFAULT NULL,
	`tweetPoll_save_published_status_status_save_poll_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`scheduled_id`),
  UNIQUE KEY `scheduled_id` (`scheduled_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1;
