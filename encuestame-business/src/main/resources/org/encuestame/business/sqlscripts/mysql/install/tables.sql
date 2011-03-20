
CREATE TABLE IF NOT EXISTS `account` (
  `uid` bigint(20) NOT NULL AUTO_INCREMENT,
  `twitter_consumer_key` varchar(255) DEFAULT NULL,
  `twitter_consumer_secret` varchar(255) DEFAULT NULL,
  `twitter_account` varchar(18) DEFAULT NULL,
  `twitter_password` varchar(18) DEFAULT NULL,
  `twitter_pin` int(11) DEFAULT NULL,
  PRIMARY KEY (`uid`),
  UNIQUE KEY `uid` (`uid`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;


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


CREATE TABLE IF NOT EXISTS `geoPoint` (
  `locate_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `accuracy` int(11) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `country_code` varchar(255) DEFAULT NULL,
  `country_name` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `lat` float DEFAULT NULL,
  `lng` float DEFAULT NULL,
  `location_status` varchar(255) DEFAULT NULL,
  `account_uid` bigint(20) DEFAULT NULL,
  `catLocationFolder_locate_folder_id` bigint(20) DEFAULT NULL,
  `loc_id_type` bigint(20) DEFAULT NULL,
  `geoPointFolder_locate_folder_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`locate_id`),
  UNIQUE KEY `locate_id` (`locate_id`),
  KEY `FK6C73C0BFBD91661D` (`loc_id_type`),
  KEY `FK6C73C0BF5ECE45A2` (`account_uid`),
  KEY `FK6C73C0BF5ADC6017` (`catLocationFolder_locate_folder_id`),
  KEY `FK6C73C0BF34EF9A43` (`geoPointFolder_locate_folder_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;


CREATE TABLE IF NOT EXISTS `geoPoint_folder` (
  `locate_folder_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `type` varchar(255) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `account_uid` bigint(20) DEFAULT NULL,
  `subLocationFolder_locate_folder_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`locate_folder_id`),
  UNIQUE KEY `locate_folder_id` (`locate_folder_id`),
  KEY `FKF4A1D3EE5ECE45A2` (`account_uid`),
  KEY `FKF4A1D3EE6E4ED46D` (`subLocationFolder_locate_folder_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;


CREATE TABLE IF NOT EXISTS `geoPoint_type` (
  `loc_id_type` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `level` int(11) DEFAULT NULL,
  `users_uid` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`loc_id_type`),
  UNIQUE KEY `loc_id_type` (`loc_id_type`),
  KEY `FK514326BA4075E3FD` (`users_uid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;


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



CREATE TABLE IF NOT EXISTS `groups_permission` (
  `sec_id_group` bigint(20) NOT NULL,
  `sec_id_permission` bigint(20) NOT NULL,
  PRIMARY KEY (`sec_id_group`,`sec_id_permission`),
  KEY `FK7F1951A45895AFF` (`sec_id_group`),
  KEY `FK7F1951A43ADB63D` (`sec_id_permission`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



CREATE TABLE IF NOT EXISTS `group_permission` (
  `sec_id_permission` bigint(20) NOT NULL,
  `sec_id_group` bigint(20) NOT NULL,
  PRIMARY KEY (`sec_id_permission`,`sec_id_group`),
  KEY `FK362E6F8F45895AFF` (`sec_id_group`),
  KEY `FK362E6F8F43ADB63D` (`sec_id_permission`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



CREATE TABLE IF NOT EXISTS `hash_tags` (
  `hash_tag_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `tag` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`hash_tag_id`),
  UNIQUE KEY `hash_tag_id` (`hash_tag_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;



CREATE TABLE IF NOT EXISTS `notification` (
  `notification_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `additional_description` varchar(255) NOT NULL,
  `created` datetime NOT NULL,
  `description` varchar(255) NOT NULL,
  `readed` bit(1) NOT NULL,
  `uid` bigint(20) NOT NULL,
  PRIMARY KEY (`notification_id`),
  UNIQUE KEY `notification_id` (`notification_id`),
  KEY `FK237A88EB2B2A6AB4` (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;



CREATE TABLE IF NOT EXISTS `oauth_account_connection` (
  `account_connection_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `access_token` varchar(255) DEFAULT NULL,
  `profile_url` varchar(255) DEFAULT NULL,
  `secret` varchar(255) DEFAULT NULL,
  `socialAccountId` varchar(255) DEFAULT NULL,
  `accountProvider_social_provider_id` bigint(20) DEFAULT NULL,
  `userAccout_uid` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`account_connection_id`),
  UNIQUE KEY `account_connection_id` (`account_connection_id`),
  KEY `FK222E06D86926A720` (`accountProvider_social_provider_id`),
  KEY `FK222E06D87AD3EEE2` (`userAccout_uid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;



CREATE TABLE IF NOT EXISTS `oauth_account_social_provider` (
  `social_provider_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `access_token_url` varchar(255) DEFAULT NULL,
  `api_key` varchar(255) DEFAULT NULL,
  `app_id` bigint(20) DEFAULT NULL,
  `authorize_url` varchar(255) DEFAULT NULL,
  `implementation` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `request_token_url` varchar(255) DEFAULT NULL,
  `secret` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`social_provider_id`),
  UNIQUE KEY `social_provider_id` (`social_provider_id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;


CREATE TABLE IF NOT EXISTS `permission` (
  `id_permission` bigint(20) NOT NULL AUTO_INCREMENT,
  `permission` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id_permission`),
  UNIQUE KEY `id_permission` (`id_permission`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=7 ;


CREATE TABLE IF NOT EXISTS `poll` (
  `poll_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `additionalInfo` varchar(255) DEFAULT NULL,
  `closeAfterDate` bit(1) DEFAULT NULL,
  `close_after_quota` bit(1) DEFAULT NULL,
  `close_date` datetime DEFAULT NULL,
  `closed_quota` int(11) DEFAULT NULL,
  `custom_final_message` varchar(255) DEFAULT NULL,
  `custom_message` bit(1) DEFAULT NULL,
  `custom_start_message` varchar(255) DEFAULT NULL,
  `hits` int(11) DEFAULT NULL,
  `ip_protection` varchar(255) DEFAULT NULL,
  `ip_restrictions` bit(1) DEFAULT NULL,
  `multiple_response` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `notifications` bit(1) DEFAULT NULL,
  `numbervotes` int(11) DEFAULT NULL,
  `optional_title` varchar(255) DEFAULT NULL,
  `password_protection` varchar(255) DEFAULT NULL,
  `password_restrictions` bit(1) DEFAULT NULL,
  `showAdditionalInfo` bit(1) DEFAULT NULL,
  `showComments` int(11) DEFAULT NULL,
  `show_progress_bar` bit(1) DEFAULT NULL,
  `showResults` bit(1) DEFAULT NULL,
  `close_notification` bit(1) DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `end_date` datetime DEFAULT NULL,
  `completed` bit(1) NOT NULL,
  `poll_hash` varchar(255) NOT NULL,
  `publish_poll` bit(1) DEFAULT NULL,
  `show_results` bit(1) DEFAULT NULL,
  `editor` bigint(20) DEFAULT NULL,
  `poll_folder` bigint(20) DEFAULT NULL,
  `uid` bigint(20) NOT NULL,
  `qid` bigint(20) NOT NULL,
  PRIMARY KEY (`poll_id`),
  UNIQUE KEY `poll_id` (`poll_id`),
  UNIQUE KEY `poll_hash` (`poll_hash`),
  KEY `FK3497BF2B2A6AB4` (`uid`),
  KEY `FK3497BF89452CCA` (`poll_folder`),
  KEY `FK3497BF70996757` (`qid`),
  KEY `FK3497BFA64FB606` (`editor`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;


CREATE TABLE IF NOT EXISTS `poll_folder` (
  `pollFolderId` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_at` date DEFAULT NULL,
  `folderName` varchar(255) NOT NULL,
  `uid` bigint(20) NOT NULL,
  PRIMARY KEY (`pollFolderId`),
  UNIQUE KEY `pollFolderId` (`pollFolderId`),
  KEY `FKC5911CEE2B2A6AB4` (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;


CREATE TABLE IF NOT EXISTS `poll_result` (
  `poll_resultId` bigint(20) NOT NULL AUTO_INCREMENT,
  `ip_address` varchar(255) NOT NULL,
  `votation_date` datetime NOT NULL,
  `q_answer_id` bigint(20) NOT NULL,
  `poll_id` bigint(20) NOT NULL,
  PRIMARY KEY (`poll_resultId`),
  UNIQUE KEY `poll_resultId` (`poll_resultId`),
  KEY `FKD981C89D3A73F181` (`q_answer_id`),
  KEY `FKD981C89DF0ED6769` (`poll_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;



CREATE TABLE IF NOT EXISTS `project` (
  `project_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `hide_project` bit(1) DEFAULT NULL,
  `notify_members` bit(1) DEFAULT NULL,
  `priority` varchar(255) DEFAULT NULL,
  `date_finish` datetime NOT NULL,
  `date_start` datetime NOT NULL,
  `description` longtext,
  `project_info` longtext,
  `name` varchar(255) NOT NULL,
  `project_status` varchar(255) DEFAULT NULL,
  `published` bit(1) DEFAULT NULL,
  `lead_uid` bigint(20) DEFAULT NULL,
  `users_uid` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`project_id`),
  UNIQUE KEY `project_id` (`project_id`),
  KEY `FKED904B19514C1986` (`lead_uid`),
  KEY `FKED904B194075E3FD` (`users_uid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;


CREATE TABLE IF NOT EXISTS `project_geoPoint` (
  `cat_id_project` bigint(20) NOT NULL,
  `cat_id_loc` bigint(20) NOT NULL,
  PRIMARY KEY (`cat_id_project`,`cat_id_loc`),
  KEY `FK2599132584536452` (`cat_id_project`),
  KEY `FK2599132535313189` (`cat_id_loc`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



CREATE TABLE IF NOT EXISTS `project_group` (
  `sec_id_group` bigint(20) NOT NULL,
  `cat_id_project` bigint(20) NOT NULL,
  PRIMARY KEY (`cat_id_project`,`sec_id_group`),
  KEY `FKC7652DD945895AFF` (`sec_id_group`),
  KEY `FKC7652DD984536452` (`cat_id_project`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



CREATE TABLE IF NOT EXISTS `project_locations` (
  `cat_id_loc` bigint(20) NOT NULL,
  `cat_id_project` bigint(20) NOT NULL,
  PRIMARY KEY (`cat_id_loc`,`cat_id_project`),
  KEY `FK242951B884536452` (`cat_id_project`),
  KEY `FK242951B835313189` (`cat_id_loc`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



CREATE TABLE IF NOT EXISTS `questions` (
  `qid` bigint(20) NOT NULL AUTO_INCREMENT,
  `qid_key` varchar(255) DEFAULT NULL,
  `question` varchar(255) DEFAULT NULL,
  `shared_question` bit(1) DEFAULT NULL,
  `uid` bigint(20) NOT NULL,
  `id_question_pattern` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`qid`),
  UNIQUE KEY `qid` (`qid`),
  KEY `FK95C5414D2B2A6AB4` (`uid`),
  KEY `FK95C5414DBBCB7E36` (`id_question_pattern`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;



CREATE TABLE IF NOT EXISTS `questions_answers` (
  `q_answer_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `answer` varchar(255) DEFAULT NULL,
  `answerType` int(11) DEFAULT NULL,
  `answer_hash` varchar(255) DEFAULT NULL,
  `answer_url` varchar(255) DEFAULT NULL,
  `id_question_answer` bigint(20) NOT NULL,
  PRIMARY KEY (`q_answer_id`),
  UNIQUE KEY `q_answer_id` (`q_answer_id`),
  KEY `FK539703839E07711E` (`id_question_answer`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;



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
  KEY `FK92E86ADB3A73F181` (`q_answer_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;



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



CREATE TABLE IF NOT EXISTS `question_category` (
  `qCategory` bigint(20) NOT NULL AUTO_INCREMENT,
  `category` varchar(18) DEFAULT NULL,
  PRIMARY KEY (`qCategory`),
  UNIQUE KEY `qCategory` (`qCategory`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;


CREATE TABLE IF NOT EXISTS `question_category_questions` (
  `question_category_qCategory` bigint(20) NOT NULL,
  `questionLibrary_qid` bigint(20) NOT NULL,
  PRIMARY KEY (`question_category_qCategory`,`questionLibrary_qid`),
  KEY `FK2FFE1845A279B31C` (`question_category_qCategory`),
  KEY `FK2FFE1845273B5E2D` (`questionLibrary_qid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


CREATE TABLE IF NOT EXISTS `question_collection` (
  `id_q_colection` bigint(20) NOT NULL AUTO_INCREMENT,
  `creation_date` datetime NOT NULL,
  `des_coleccion` varchar(255) NOT NULL,
  `uid` bigint(20) NOT NULL,
  PRIMARY KEY (`id_q_colection`),
  UNIQUE KEY `id_q_colection` (`id_q_colection`),
  KEY `FKB4097C972B2A6AB4` (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;



CREATE TABLE IF NOT EXISTS `question_dependence_survey` (
  `question_dependence_survey` bigint(20) NOT NULL AUTO_INCREMENT,
  `sid` bigint(20) NOT NULL,
  PRIMARY KEY (`question_dependence_survey`),
  UNIQUE KEY `question_dependence_survey` (`question_dependence_survey`),
  KEY `FKBB424D49793D9E77` (`sid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;



CREATE TABLE IF NOT EXISTS `question_relations` (
  `question_id` bigint(20) NOT NULL,
  `id_q_colection` bigint(20) NOT NULL,
  PRIMARY KEY (`question_id`,`id_q_colection`),
  KEY `FK217954DEB3E0058E` (`id_q_colection`),
  KEY `FK217954DEAA11961F` (`question_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


CREATE TABLE IF NOT EXISTS `social_account` (
  `sec_user_twitter_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `type_account` varchar(255) DEFAULT NULL,
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
  KEY `FK50078B5BEBE472CC` (`secUsers_uid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;



CREATE TABLE IF NOT EXISTS `surveys` (
  `sid` bigint(20) NOT NULL AUTO_INCREMENT,
  `additionalInfo` varchar(255) DEFAULT NULL,
  `closeAfterDate` bit(1) DEFAULT NULL,
  `close_after_quota` bit(1) DEFAULT NULL,
  `close_date` datetime DEFAULT NULL,
  `closed_quota` int(11) DEFAULT NULL,
  `custom_final_message` varchar(255) DEFAULT NULL,
  `custom_message` bit(1) DEFAULT NULL,
  `custom_start_message` varchar(255) DEFAULT NULL,
  `hits` int(11) DEFAULT NULL,
  `ip_protection` varchar(255) DEFAULT NULL,
  `ip_restrictions` bit(1) DEFAULT NULL,
  `multiple_response` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `notifications` bit(1) DEFAULT NULL,
  `numbervotes` int(11) DEFAULT NULL,
  `optional_title` varchar(255) DEFAULT NULL,
  `password_protection` varchar(255) DEFAULT NULL,
  `password_restrictions` bit(1) DEFAULT NULL,
  `showAdditionalInfo` bit(1) DEFAULT NULL,
  `showComments` int(11) DEFAULT NULL,
  `show_progress_bar` bit(1) DEFAULT NULL,
  `showResults` bit(1) DEFAULT NULL,
  `complete` varchar(2) DEFAULT NULL,
  `date_interview` date DEFAULT NULL,
  `end_date` datetime NOT NULL,
  `start_date` datetime NOT NULL,
  `ticket` int(11) NOT NULL,
  `editor` bigint(20) DEFAULT NULL,
  `uid` bigint(20) NOT NULL,
  `id_sid_format` bigint(20) NOT NULL,
  `survey_folder` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`sid`),
  UNIQUE KEY `sid` (`sid`),
  KEY `FK919144592B2A6AB4` (`uid`),
  KEY `FK91914459A3C7A06A` (`survey_folder`),
  KEY `FK91914459B1A6912C` (`id_sid_format`),
  KEY `FK91914459A64FB606` (`editor`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;



CREATE TABLE IF NOT EXISTS `survey_folder` (
  `survey_folderId` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_at` date DEFAULT NULL,
  `folderName` varchar(255) NOT NULL,
  `uid` bigint(20) NOT NULL,
  PRIMARY KEY (`survey_folderId`),
  UNIQUE KEY `survey_folderId` (`survey_folderId`),
  KEY `FK7EF958F32B2A6AB4` (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;



CREATE TABLE IF NOT EXISTS `survey_format` (
  `id_sid_format` bigint(20) NOT NULL AUTO_INCREMENT,
  `date_created` datetime DEFAULT NULL,
  `name` varchar(60) DEFAULT NULL,
  PRIMARY KEY (`id_sid_format`),
  UNIQUE KEY `id_sid_format` (`id_sid_format`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;



CREATE TABLE IF NOT EXISTS `survey_group` (
  `sg_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `date_create` datetime DEFAULT NULL,
  `group_name` varchar(60) DEFAULT NULL,
  PRIMARY KEY (`sg_id`),
  UNIQUE KEY `sg_id` (`sg_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;



CREATE TABLE IF NOT EXISTS `survey_group_format` (
  `sg_id` bigint(20) NOT NULL,
  `id_sid_format` bigint(20) NOT NULL,
  PRIMARY KEY (`id_sid_format`,`sg_id`),
  KEY `FKB4DF867C310E993C` (`sg_id`),
  KEY `FKB4DF867CB1A6912C` (`id_sid_format`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



CREATE TABLE IF NOT EXISTS `survey_group_project` (
  `id_sid_format` bigint(20) NOT NULL,
  `cat_id_project` bigint(20) NOT NULL,
  PRIMARY KEY (`cat_id_project`,`id_sid_format`),
  KEY `FKFD028D3484536452` (`cat_id_project`),
  KEY `FKFD028D34B75F3482` (`id_sid_format`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



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



CREATE TABLE IF NOT EXISTS `survey_result` (
  `rid` bigint(20) NOT NULL AUTO_INCREMENT,
  `resp` varchar(255) NOT NULL,
  `survey_id` bigint(20) NOT NULL,
  PRIMARY KEY (`rid`),
  UNIQUE KEY `rid` (`rid`),
  KEY `FK92EA04A2EB8D35C9` (`survey_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

 -

CREATE TABLE IF NOT EXISTS `survey_section` (
  `ssid` bigint(20) NOT NULL AUTO_INCREMENT,
  `desc_section` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ssid`),
  UNIQUE KEY `ssid` (`ssid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;


CREATE TABLE IF NOT EXISTS `survey_section_questions` (
  `survey_section_ssid` bigint(20) NOT NULL,
  `questionSection_qid` bigint(20) NOT NULL,
  PRIMARY KEY (`survey_section_ssid`,`questionSection_qid`),
  KEY `FK12354ECE11057E56` (`survey_section_ssid`),
  KEY `FK12354ECE6DD59357` (`questionSection_qid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


CREATE TABLE IF NOT EXISTS `tweetPoll` (
  `tweet_poll_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `allow_live_results` bit(1) DEFAULT NULL,
  `allow_repeated_votes` bit(1) DEFAULT NULL,
  `captcha` bit(1) DEFAULT NULL,
  `close_notification` bit(1) DEFAULT NULL,
  `completed` bit(1) NOT NULL,
  `create_date` datetime DEFAULT NULL,
  `favourite` bit(1) DEFAULT NULL,
  `hits` int(11) DEFAULT NULL,
  `limit_votes` int(11) DEFAULT NULL,
  `numberVotes` int(11) DEFAULT NULL,
  `publish` bit(1) DEFAULT NULL,
  `result_notification` bit(1) DEFAULT NULL,
  `resume_live_results` bit(1) DEFAULT NULL,
  `schedule_date_tweet` datetime DEFAULT NULL,
  `schedule` bit(1) DEFAULT NULL,
  `editor` bigint(20) DEFAULT NULL,
  `qid` bigint(20) NOT NULL,
  `uid` bigint(20) NOT NULL,
  `tweetPollFolderId` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`tweet_poll_id`),
  UNIQUE KEY `tweet_poll_id` (`tweet_poll_id`),
  KEY `FKA65B1D02B2A6AB4` (`uid`),
  KEY `FKA65B1D032DD60A8` (`tweetPollFolderId`),
  KEY `FKA65B1D070996757` (`qid`),
  KEY `FKA65B1D0A64FB606` (`editor`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;



CREATE TABLE IF NOT EXISTS `tweetPoll_Folder` (
  `tweetPollFolderId` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_at` date DEFAULT NULL,
  `folderName` varchar(255) NOT NULL,
  `uid` bigint(20) NOT NULL,
  PRIMARY KEY (`tweetPollFolderId`),
  UNIQUE KEY `tweetPollFolderId` (`tweetPollFolderId`),
  KEY `FKA027A9DD2B2A6AB4` (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;


CREATE TABLE IF NOT EXISTS `tweetPoll_hash_tags` (
  `tweetPoll_tweet_poll_id` bigint(20) NOT NULL,
  `hashTags_hash_tag_id` bigint(20) NOT NULL,
  PRIMARY KEY (`tweetPoll_tweet_poll_id`,`hashTags_hash_tag_id`),
  KEY `FKE3434CFBB17CB69F` (`tweetPoll_tweet_poll_id`),
  KEY `FKE3434CFB378AF300` (`hashTags_hash_tag_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


CREATE TABLE IF NOT EXISTS `tweetpoll_result` (
  `tweetpoll_resultId` bigint(20) NOT NULL AUTO_INCREMENT,
  `ip_vote` varchar(100) NOT NULL,
  `tweet_date_response` datetime NOT NULL,
  `tweetpoll_switch_id` bigint(20) NOT NULL,
  PRIMARY KEY (`tweetpoll_resultId`),
  UNIQUE KEY `tweetpoll_resultId` (`tweetpoll_resultId`),
  KEY `FK8749C18C12F681EC` (`tweetpoll_switch_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;



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
  KEY `FKD499A4B6FE548CFF` (`twitterAccount_sec_user_twitter_id`),
  KEY `FKD499A4B6B17CB69F` (`tweetPoll_tweet_poll_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;



CREATE TABLE IF NOT EXISTS `tweetpoll_switch` (
  `tweetpoll_switch_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `tweet_code` varchar(255) NOT NULL,
  `q_answer_id` bigint(20) NOT NULL,
  `tweet_poll_id` bigint(20) NOT NULL,
  PRIMARY KEY (`tweetpoll_switch_id`),
  UNIQUE KEY `tweetpoll_switch_id` (`tweetpoll_switch_id`),
  UNIQUE KEY `tweet_code` (`tweet_code`),
  KEY `FK89F7B0A321905AEE` (`tweet_poll_id`),
  KEY `FK89F7B0A33A73F181` (`q_answer_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;



CREATE TABLE IF NOT EXISTS `userAccount` (
  `uid` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `date_new` datetime DEFAULT NULL,
  `followers` bigint(20) DEFAULT NULL,
  `invite_code` varchar(255) DEFAULT NULL,
  `last_ip_logged` varchar(255) DEFAULT NULL,
  `last_time_logged` datetime DEFAULT NULL,
  `password` varchar(255) NOT NULL,
  `email` varchar(150) NOT NULL,
  `status` bit(1) DEFAULT NULL,
  `twitter` varchar(255) DEFAULT NULL,
  `username` varchar(16) NOT NULL,
  `account_uid` bigint(20) DEFAULT NULL,
  `groupId` bigint(20) DEFAULT NULL,
  `userProfilePicture` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`uid`),
  UNIQUE KEY `uid` (`uid`),
  UNIQUE KEY `email` (`email`),
  UNIQUE KEY `username` (`username`,`email`),
  KEY `FKA7D56BE25ECE45A2` (`account_uid`),
  KEY `FKA7D56BE2B8EB1450` (`groupId`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;


CREATE TABLE IF NOT EXISTS `userAccount_followers` (
  `uid` bigint(20) NOT NULL,
  `uid_follower` bigint(20) NOT NULL,
  PRIMARY KEY (`uid`,`uid_follower`),
  KEY `FK7F1957F8F44558E9` (`uid`),
  KEY `FK7F1957F8E53FBC6` (`uid_follower`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



CREATE TABLE IF NOT EXISTS `userAccount_permission` (
  `sec_id_secondary` bigint(20) NOT NULL,
  `sec_id_permission` bigint(20) NOT NULL,
  PRIMARY KEY (`sec_id_permission`,`sec_id_secondary`),
  KEY `FKBE01CE4C43ADB63D` (`sec_id_permission`),
  KEY `FKBE01CE4C5F77A117` (`sec_id_secondary`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



CREATE TABLE IF NOT EXISTS `userAccount_project` (
  `sec_id_secondary` bigint(20) NOT NULL,
  `cat_id_project` bigint(20) NOT NULL,
  PRIMARY KEY (`cat_id_project`,`sec_id_secondary`),
  KEY `FKFBC45BBC84536452` (`cat_id_project`),
  KEY `FKFBC45BBC5F77A117` (`sec_id_secondary`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
