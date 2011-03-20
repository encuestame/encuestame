

ALTER TABLE `application`
  ADD CONSTRAINT `FK5CA405505ECE45A2` FOREIGN KEY (`account_uid`) REFERENCES `account` (`uid`);


ALTER TABLE `application_connection`
  ADD CONSTRAINT `FK73D5D2D27E933D7` FOREIGN KEY (`account_uid`) REFERENCES `userAccount` (`uid`),
  ADD CONSTRAINT `FK73D5D2D4402BE26` FOREIGN KEY (`application_application_id`) REFERENCES `application` (`application_id`);


ALTER TABLE `client`
  ADD CONSTRAINT `FKAF12F3CB225A055` FOREIGN KEY (`project_id`) REFERENCES `project` (`project_id`);


ALTER TABLE `email`
  ADD CONSTRAINT `FK5C24B9CED78E617` FOREIGN KEY (`id_list`) REFERENCES `emailList` (`id_list`);


ALTER TABLE `emailList`
  ADD CONSTRAINT `FK7E5F425A2B2A6AB4` FOREIGN KEY (`uid`) REFERENCES `account` (`uid`);


ALTER TABLE `emailSubscribe`
  ADD CONSTRAINT `FK4B85010EE824035` FOREIGN KEY (`email_id`) REFERENCES `email` (`email_id`),
  ADD CONSTRAINT `FK4B85010EED78E617` FOREIGN KEY (`id_list`) REFERENCES `emailList` (`id_list`);

ALTER TABLE `geoPoint`
  ADD CONSTRAINT `FK6C73C0BF34EF9A43` FOREIGN KEY (`geoPointFolder_locate_folder_id`) REFERENCES `geoPoint_folder` (`locate_folder_id`),
  ADD CONSTRAINT `FK6C73C0BF5ADC6017` FOREIGN KEY (`catLocationFolder_locate_folder_id`) REFERENCES `geoPoint_folder` (`locate_folder_id`),
  ADD CONSTRAINT `FK6C73C0BF5ECE45A2` FOREIGN KEY (`account_uid`) REFERENCES `account` (`uid`),
  ADD CONSTRAINT `FK6C73C0BFBD91661D` FOREIGN KEY (`loc_id_type`) REFERENCES `geoPoint_type` (`loc_id_type`);


ALTER TABLE `geoPoint_folder`
  ADD CONSTRAINT `FKF4A1D3EE5ECE45A2` FOREIGN KEY (`account_uid`) REFERENCES `account` (`uid`),
  ADD CONSTRAINT `FKF4A1D3EE6E4ED46D` FOREIGN KEY (`subLocationFolder_locate_folder_id`) REFERENCES `geoPoint_folder` (`locate_folder_id`);


ALTER TABLE `geoPoint_type`
  ADD CONSTRAINT `FK514326BA4075E3FD` FOREIGN KEY (`users_uid`) REFERENCES `account` (`uid`);

ALTER TABLE `groups`
  ADD CONSTRAINT `FKB63DD9D45ECE45A2` FOREIGN KEY (`account_uid`) REFERENCES `account` (`uid`);


ALTER TABLE `groups_permission`
  ADD CONSTRAINT `FK7F1951A43ADB63D` FOREIGN KEY (`sec_id_permission`) REFERENCES `permission` (`id_permission`),
  ADD CONSTRAINT `FK7F1951A45895AFF` FOREIGN KEY (`sec_id_group`) REFERENCES `groups` (`group_id`);


ALTER TABLE `group_permission`
  ADD CONSTRAINT `FK362E6F8F43ADB63D` FOREIGN KEY (`sec_id_permission`) REFERENCES `permission` (`id_permission`),
  ADD CONSTRAINT `FK362E6F8F45895AFF` FOREIGN KEY (`sec_id_group`) REFERENCES `groups` (`group_id`);


ALTER TABLE `notification`
  ADD CONSTRAINT `FK237A88EB2B2A6AB4` FOREIGN KEY (`uid`) REFERENCES `account` (`uid`);


ALTER TABLE `oauth_account_connection`
  ADD CONSTRAINT `FK222E06D86926A720` FOREIGN KEY (`accountProvider_social_provider_id`) REFERENCES `oauth_account_social_provider` (`social_provider_id`),
  ADD CONSTRAINT `FK222E06D87AD3EEE2` FOREIGN KEY (`userAccout_uid`) REFERENCES `userAccount` (`uid`);


ALTER TABLE `poll`
  ADD CONSTRAINT `FK3497BF2B2A6AB4` FOREIGN KEY (`uid`) REFERENCES `account` (`uid`),
  ADD CONSTRAINT `FK3497BF70996757` FOREIGN KEY (`qid`) REFERENCES `questions` (`qid`),
  ADD CONSTRAINT `FK3497BF89452CCA` FOREIGN KEY (`poll_folder`) REFERENCES `poll_folder` (`pollFolderId`),
  ADD CONSTRAINT `FK3497BFA64FB606` FOREIGN KEY (`editor`) REFERENCES `userAccount` (`uid`);


ALTER TABLE `poll_folder`
  ADD CONSTRAINT `FKC5911CEE2B2A6AB4` FOREIGN KEY (`uid`) REFERENCES `account` (`uid`);


ALTER TABLE `poll_result`
  ADD CONSTRAINT `FKD981C89D3A73F181` FOREIGN KEY (`q_answer_id`) REFERENCES `questions_answers` (`q_answer_id`),
  ADD CONSTRAINT `FKD981C89DF0ED6769` FOREIGN KEY (`poll_id`) REFERENCES `poll` (`poll_id`);


ALTER TABLE `project`
  ADD CONSTRAINT `FKED904B194075E3FD` FOREIGN KEY (`users_uid`) REFERENCES `account` (`uid`),
  ADD CONSTRAINT `FKED904B19514C1986` FOREIGN KEY (`lead_uid`) REFERENCES `userAccount` (`uid`);

ALTER TABLE `project_geoPoint`
  ADD CONSTRAINT `FK2599132535313189` FOREIGN KEY (`cat_id_loc`) REFERENCES `geoPoint` (`locate_id`),
  ADD CONSTRAINT `FK2599132584536452` FOREIGN KEY (`cat_id_project`) REFERENCES `project` (`project_id`);


ALTER TABLE `project_group`
  ADD CONSTRAINT `FKC7652DD945895AFF` FOREIGN KEY (`sec_id_group`) REFERENCES `groups` (`group_id`),
  ADD CONSTRAINT `FKC7652DD984536452` FOREIGN KEY (`cat_id_project`) REFERENCES `project` (`project_id`);


ALTER TABLE `project_locations`
  ADD CONSTRAINT `FK242951B835313189` FOREIGN KEY (`cat_id_loc`) REFERENCES `geoPoint` (`locate_id`),
  ADD CONSTRAINT `FK242951B884536452` FOREIGN KEY (`cat_id_project`) REFERENCES `project` (`project_id`);


ALTER TABLE `questions`
  ADD CONSTRAINT `FK95C5414D2B2A6AB4` FOREIGN KEY (`uid`) REFERENCES `account` (`uid`),
  ADD CONSTRAINT `FK95C5414DBBCB7E36` FOREIGN KEY (`id_question_pattern`) REFERENCES `questions_pattern` (`pattenr_id`);


ALTER TABLE `questions_answers`
  ADD CONSTRAINT `FK539703839E07711E` FOREIGN KEY (`id_question_answer`) REFERENCES `questions` (`qid`);


ALTER TABLE `questions_dependencies`
  ADD CONSTRAINT `FK92E86ADB3A73F181` FOREIGN KEY (`q_answer_id`) REFERENCES `questions_answers` (`q_answer_id`);


ALTER TABLE `question_category_questions`
  ADD CONSTRAINT `FK2FFE1845273B5E2D` FOREIGN KEY (`questionLibrary_qid`) REFERENCES `questions` (`qid`),
  ADD CONSTRAINT `FK2FFE1845A279B31C` FOREIGN KEY (`question_category_qCategory`) REFERENCES `question_category` (`qCategory`);


ALTER TABLE `question_collection`
  ADD CONSTRAINT `FKB4097C972B2A6AB4` FOREIGN KEY (`uid`) REFERENCES `account` (`uid`);


ALTER TABLE `question_dependence_survey`
  ADD CONSTRAINT `FKBB424D49793D9E77` FOREIGN KEY (`sid`) REFERENCES `surveys` (`sid`);


ALTER TABLE `question_relations`
  ADD CONSTRAINT `FK217954DEAA11961F` FOREIGN KEY (`question_id`) REFERENCES `questions` (`qid`),
  ADD CONSTRAINT `FK217954DEB3E0058E` FOREIGN KEY (`id_q_colection`) REFERENCES `question_collection` (`id_q_colection`);


ALTER TABLE `social_account`
  ADD CONSTRAINT `FK50078B5BEBE472CC` FOREIGN KEY (`secUsers_uid`) REFERENCES `account` (`uid`);


ALTER TABLE `surveys`
  ADD CONSTRAINT `FK919144592B2A6AB4` FOREIGN KEY (`uid`) REFERENCES `account` (`uid`),
  ADD CONSTRAINT `FK91914459A3C7A06A` FOREIGN KEY (`survey_folder`) REFERENCES `survey_folder` (`survey_folderId`),
  ADD CONSTRAINT `FK91914459A64FB606` FOREIGN KEY (`editor`) REFERENCES `userAccount` (`uid`),
  ADD CONSTRAINT `FK91914459B1A6912C` FOREIGN KEY (`id_sid_format`) REFERENCES `survey_format` (`id_sid_format`);


ALTER TABLE `survey_folder`
  ADD CONSTRAINT `FK7EF958F32B2A6AB4` FOREIGN KEY (`uid`) REFERENCES `account` (`uid`);


ALTER TABLE `survey_group_format`
  ADD CONSTRAINT `FKB4DF867C310E993C` FOREIGN KEY (`sg_id`) REFERENCES `survey_group` (`sg_id`),
  ADD CONSTRAINT `FKB4DF867CB1A6912C` FOREIGN KEY (`id_sid_format`) REFERENCES `survey_format` (`id_sid_format`);


ALTER TABLE `survey_group_project`
  ADD CONSTRAINT `FKFD028D3484536452` FOREIGN KEY (`cat_id_project`) REFERENCES `project` (`project_id`),
  ADD CONSTRAINT `FKFD028D34B75F3482` FOREIGN KEY (`id_sid_format`) REFERENCES `survey_group` (`sg_id`);


ALTER TABLE `survey_pagination`
  ADD CONSTRAINT `FKBEC9A99F1359B877` FOREIGN KEY (`ssid`) REFERENCES `survey_section` (`ssid`),
  ADD CONSTRAINT `FKBEC9A99F793D9E77` FOREIGN KEY (`sid`) REFERENCES `surveys` (`sid`);


ALTER TABLE `survey_result`
  ADD CONSTRAINT `FK92EA04A2EB8D35C9` FOREIGN KEY (`survey_id`) REFERENCES `surveys` (`sid`);


ALTER TABLE `survey_section_questions`
  ADD CONSTRAINT `FK12354ECE11057E56` FOREIGN KEY (`survey_section_ssid`) REFERENCES `survey_section` (`ssid`),
  ADD CONSTRAINT `FK12354ECE6DD59357` FOREIGN KEY (`questionSection_qid`) REFERENCES `questions` (`qid`);

ALTER TABLE `tweetPoll`
  ADD CONSTRAINT `FKA65B1D02B2A6AB4` FOREIGN KEY (`uid`) REFERENCES `account` (`uid`),
  ADD CONSTRAINT `FKA65B1D032DD60A8` FOREIGN KEY (`tweetPollFolderId`) REFERENCES `tweetPoll_Folder` (`tweetPollFolderId`),
  ADD CONSTRAINT `FKA65B1D070996757` FOREIGN KEY (`qid`) REFERENCES `questions` (`qid`),
  ADD CONSTRAINT `FKA65B1D0A64FB606` FOREIGN KEY (`editor`) REFERENCES `userAccount` (`uid`);


ALTER TABLE `tweetPoll_Folder`
  ADD CONSTRAINT `FKA027A9DD2B2A6AB4` FOREIGN KEY (`uid`) REFERENCES `account` (`uid`);


ALTER TABLE `tweetPoll_hash_tags`
  ADD CONSTRAINT `FKE3434CFB378AF300` FOREIGN KEY (`hashTags_hash_tag_id`) REFERENCES `hash_tags` (`hash_tag_id`),
  ADD CONSTRAINT `FKE3434CFBB17CB69F` FOREIGN KEY (`tweetPoll_tweet_poll_id`) REFERENCES `tweetPoll` (`tweet_poll_id`);


ALTER TABLE `tweetpoll_result`
  ADD CONSTRAINT `FK8749C18C12F681EC` FOREIGN KEY (`tweetpoll_switch_id`) REFERENCES `tweetpoll_switch` (`tweetpoll_switch_id`);


ALTER TABLE `tweetPoll_save_published_status`
  ADD CONSTRAINT `FKD499A4B6B17CB69F` FOREIGN KEY (`tweetPoll_tweet_poll_id`) REFERENCES `tweetPoll` (`tweet_poll_id`),
  ADD CONSTRAINT `FKD499A4B6FE548CFF` FOREIGN KEY (`twitterAccount_sec_user_twitter_id`) REFERENCES `social_account` (`sec_user_twitter_id`);


ALTER TABLE `tweetpoll_switch`
  ADD CONSTRAINT `FK89F7B0A321905AEE` FOREIGN KEY (`tweet_poll_id`) REFERENCES `tweetPoll` (`tweet_poll_id`),
  ADD CONSTRAINT `FK89F7B0A33A73F181` FOREIGN KEY (`q_answer_id`) REFERENCES `questions_answers` (`q_answer_id`);


ALTER TABLE `userAccount`
  ADD CONSTRAINT `FKA7D56BE25ECE45A2` FOREIGN KEY (`account_uid`) REFERENCES `account` (`uid`),
  ADD CONSTRAINT `FKA7D56BE2B8EB1450` FOREIGN KEY (`groupId`) REFERENCES `groups` (`group_id`);


ALTER TABLE `userAccount_followers`
  ADD CONSTRAINT `FK7F1957F8E53FBC6` FOREIGN KEY (`uid_follower`) REFERENCES `userAccount` (`uid`),
  ADD CONSTRAINT `FK7F1957F8F44558E9` FOREIGN KEY (`uid`) REFERENCES `userAccount` (`uid`);


ALTER TABLE `userAccount_permission`
  ADD CONSTRAINT `FKBE01CE4C43ADB63D` FOREIGN KEY (`sec_id_permission`) REFERENCES `permission` (`id_permission`),
  ADD CONSTRAINT `FKBE01CE4C5F77A117` FOREIGN KEY (`sec_id_secondary`) REFERENCES `userAccount` (`uid`);

ALTER TABLE `userAccount_project`
  ADD CONSTRAINT `FKFBC45BBC5F77A117` FOREIGN KEY (`sec_id_secondary`) REFERENCES `userAccount` (`uid`),
  ADD CONSTRAINT `FKFBC45BBC84536452` FOREIGN KEY (`cat_id_project`) REFERENCES `project` (`project_id`);
