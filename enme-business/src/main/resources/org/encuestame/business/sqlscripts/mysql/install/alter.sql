
--
-- Constraints for table `access_rate`
--
ALTER TABLE `access_rate`
  ADD CONSTRAINT `FKC2760EDB953C854B` FOREIGN KEY (`tweetPoll_tweet_poll_id`) REFERENCES `tweetPoll` (`tweet_poll_id`),
  ADD CONSTRAINT `FKC2760EDB51153812` FOREIGN KEY (`survey_sid`) REFERENCES `surveys` (`sid`),
  ADD CONSTRAINT `FKC2760EDB63976E9` FOREIGN KEY (`poll_poll_id`) REFERENCES `poll` (`poll_id`),
  ADD CONSTRAINT `FKC2760EDBE4669675` FOREIGN KEY (`user_uid`) REFERENCES `userAccount` (`uid`);

--
-- Constraints for table `application`
--
ALTER TABLE `application`
  ADD CONSTRAINT `FK5CA405505ECE45A2` FOREIGN KEY (`account_uid`) REFERENCES `account` (`uid`);

--
-- Constraints for table `application_connection`
--
ALTER TABLE `application_connection`
  ADD CONSTRAINT `FK73D5D2D4402BE26` FOREIGN KEY (`application_application_id`) REFERENCES `application` (`application_id`),
  ADD CONSTRAINT `FK73D5D2D27E933D7` FOREIGN KEY (`account_uid`) REFERENCES `userAccount` (`uid`);

--
-- Constraints for table `attachment`
--
ALTER TABLE `attachment`
  ADD CONSTRAINT `FK8AF75923225A055` FOREIGN KEY (`project_id`) REFERENCES `project` (`project_id`);

--
-- Constraints for table `client`
--
ALTER TABLE `client`
  ADD CONSTRAINT `FKAF12F3CB225A055` FOREIGN KEY (`project_id`) REFERENCES `project` (`project_id`);

--
-- Constraints for table `comments`
--
ALTER TABLE `comments`
  ADD CONSTRAINT `FKDC17DDF4D9AA8E98` FOREIGN KEY (`tweetPollId`) REFERENCES `tweetPoll` (`tweet_poll_id`),
  ADD CONSTRAINT `FKDC17DDF4793D9E77` FOREIGN KEY (`sid`) REFERENCES `surveys` (`sid`),
  ADD CONSTRAINT `FKDC17DDF4CE12CAE8` FOREIGN KEY (`pollId`) REFERENCES `poll` (`poll_id`),
  ADD CONSTRAINT `FKDC17DDF4F44558E9` FOREIGN KEY (`uid`) REFERENCES `userAccount` (`uid`);

--
-- Constraints for table `dashboard`
--
ALTER TABLE `dashboard`
  ADD CONSTRAINT `FKC18AEA949229BCA5` FOREIGN KEY (`userBoard_uid`) REFERENCES `userAccount` (`uid`);

--
-- Constraints for table `email`
--
ALTER TABLE `email`
  ADD CONSTRAINT `FK5C24B9CED78E617` FOREIGN KEY (`id_list`) REFERENCES `emailList` (`id_list`);

--
-- Constraints for table `emailList`
--
ALTER TABLE `emailList`
  ADD CONSTRAINT `FK7E5F425A2B2A6AB4` FOREIGN KEY (`uid`) REFERENCES `account` (`uid`);

--
-- Constraints for table `emailSubscribe`
--
ALTER TABLE `emailSubscribe`
  ADD CONSTRAINT `FK4B85010EE824035` FOREIGN KEY (`email_id`) REFERENCES `email` (`email_id`),
  ADD CONSTRAINT `FK4B85010EED78E617` FOREIGN KEY (`id_list`) REFERENCES `emailList` (`id_list`);

--
-- Constraints for table `gadget`
--
ALTER TABLE `gadget`
  ADD CONSTRAINT `FKB549144CB975B5F9` FOREIGN KEY (`dashboard_dashboardId`) REFERENCES `dashboard` (`dashboardId`);

--
-- Constraints for table `gadget_properties`
--
ALTER TABLE `gadget_properties`
  ADD CONSTRAINT `FK866B670629091B05` FOREIGN KEY (`gadget_gadgetId`) REFERENCES `gadget` (`gadgetId`),
  ADD CONSTRAINT `FK866B6706369F8B2C` FOREIGN KEY (`userAccount_uid`) REFERENCES `userAccount` (`uid`);

--
-- Constraints for table `geoPoint`
--
ALTER TABLE `geoPoint`
  ADD CONSTRAINT `FK6C73C0BF34EF9A43` FOREIGN KEY (`geoPointFolder_locate_folder_id`) REFERENCES `geoPoint_folder` (`locate_folder_id`),
  ADD CONSTRAINT `FK6C73C0BF5ECE45A2` FOREIGN KEY (`account_uid`) REFERENCES `account` (`uid`),
  ADD CONSTRAINT `FK6C73C0BFBD91661D` FOREIGN KEY (`loc_id_type`) REFERENCES `geoPoint_type` (`loc_id_type`);

--
-- Constraints for table `geoPoint_folder`
--
ALTER TABLE `geoPoint_folder`
  ADD CONSTRAINT `FKF4A1D3EE6E4ED46D` FOREIGN KEY (`subLocationFolder_locate_folder_id`) REFERENCES `geoPoint_folder` (`locate_folder_id`),
  ADD CONSTRAINT `FKF4A1D3EE2B2A6AB4` FOREIGN KEY (`uid`) REFERENCES `account` (`uid`),
  ADD CONSTRAINT `FKF4A1D3EE6EF241E9` FOREIGN KEY (`createdBy_uid`) REFERENCES `userAccount` (`uid`);

--
-- Constraints for table `geoPoint_type`
--
ALTER TABLE `geoPoint_type`
  ADD CONSTRAINT `FK514326BA4075E3FD` FOREIGN KEY (`users_uid`) REFERENCES `account` (`uid`);

--
-- Constraints for table `groups`
--
ALTER TABLE `groups`
  ADD CONSTRAINT `FKB63DD9D45ECE45A2` FOREIGN KEY (`account_uid`) REFERENCES `account` (`uid`);

--
-- Constraints for table `groups_permission`
--
ALTER TABLE `groups_permission`
  ADD CONSTRAINT `FK7F1951A43ADB63D` FOREIGN KEY (`sec_id_permission`) REFERENCES `permission` (`id_permission`),
  ADD CONSTRAINT `FK7F1951A45895AFF` FOREIGN KEY (`sec_id_group`) REFERENCES `groups` (`group_id`);

--
-- Constraints for table `group_permission`
--
ALTER TABLE `group_permission`
  ADD CONSTRAINT `FK362E6F8F43ADB63D` FOREIGN KEY (`sec_id_permission`) REFERENCES `permission` (`id_permission`),
  ADD CONSTRAINT `FK362E6F8F45895AFF` FOREIGN KEY (`sec_id_group`) REFERENCES `groups` (`group_id`);

--
-- Constraints for table `hits`
--
ALTER TABLE `hits`
  ADD CONSTRAINT `FK30DF4046BF7A1C` FOREIGN KEY (`question_qid`) REFERENCES `questions` (`qid`),
  ADD CONSTRAINT `FK30DF40953C854B` FOREIGN KEY (`tweetPoll_tweet_poll_id`) REFERENCES `tweetPoll` (`tweet_poll_id`),
  ADD CONSTRAINT `FK30DF40369F8B2C` FOREIGN KEY (`userAccount_uid`) REFERENCES `userAccount` (`uid`),
  ADD CONSTRAINT `FK30DF4019AA125` FOREIGN KEY (`hashTag_hash_tag_id`) REFERENCES `hash_tags` (`hash_tag_id`),
  ADD CONSTRAINT `FK30DF4051153812` FOREIGN KEY (`survey_sid`) REFERENCES `surveys` (`sid`),
  ADD CONSTRAINT `FK30DF4063976E9` FOREIGN KEY (`poll_poll_id`) REFERENCES `poll` (`poll_id`);

--
-- Constraints for table `notification`
--
ALTER TABLE `notification`
  ADD CONSTRAINT `FK237A88EB2B2A6AB4` FOREIGN KEY (`uid`) REFERENCES `account` (`uid`);

--
-- Constraints for table `poll`
--
ALTER TABLE `poll`
  ADD CONSTRAINT `FK3497BF8E4A448B` FOREIGN KEY (`owner_id`) REFERENCES `account` (`uid`),
  ADD CONSTRAINT `FK3497BF50FE71F5` FOREIGN KEY (`qid`) REFERENCES `questions` (`qid`),
  ADD CONSTRAINT `FK3497BF89452CCA` FOREIGN KEY (`poll_folder`) REFERENCES `poll_folder` (`pollFolderId`),
  ADD CONSTRAINT `FK3497BFA64FB606` FOREIGN KEY (`editor`) REFERENCES `userAccount` (`uid`);

--
-- Constraints for table `poll_folder`
--
ALTER TABLE `poll_folder`
  ADD CONSTRAINT `FKC5911CEE6EF241E9` FOREIGN KEY (`createdBy_uid`) REFERENCES `userAccount` (`uid`),
  ADD CONSTRAINT `FKC5911CEE2B2A6AB4` FOREIGN KEY (`uid`) REFERENCES `account` (`uid`);

--
-- Constraints for table `poll_hashtags`
--
ALTER TABLE `poll_hashtags`
  ADD CONSTRAINT `FK9D199EA7DA98FFE1` FOREIGN KEY (`hastag_id`) REFERENCES `hash_tags` (`hash_tag_id`),
  ADD CONSTRAINT `FK9D199EA7F0ED6769` FOREIGN KEY (`poll_id`) REFERENCES `poll` (`poll_id`);

--
-- Constraints for table `poll_result`
--
ALTER TABLE `poll_result`
  ADD CONSTRAINT `FKD981C89DF0ED6769` FOREIGN KEY (`poll_id`) REFERENCES `poll` (`poll_id`),
  ADD CONSTRAINT `FKD981C89DDDD118B5` FOREIGN KEY (`q_answer_id`) REFERENCES `questions_answers` (`q_answer_id`);

--
-- Constraints for table `project`
--
ALTER TABLE `project`
  ADD CONSTRAINT `FKED904B194075E3FD` FOREIGN KEY (`users_uid`) REFERENCES `account` (`uid`),
  ADD CONSTRAINT `FKED904B19514C1986` FOREIGN KEY (`lead_uid`) REFERENCES `userAccount` (`uid`);

--
-- Constraints for table `project_geoPoint`
--
ALTER TABLE `project_geoPoint`
  ADD CONSTRAINT `FK2599132535313189` FOREIGN KEY (`cat_id_loc`) REFERENCES `geoPoint` (`locate_id`),
  ADD CONSTRAINT `FK2599132584536452` FOREIGN KEY (`cat_id_project`) REFERENCES `project` (`project_id`);

--
-- Constraints for table `project_group`
--
ALTER TABLE `project_group`
  ADD CONSTRAINT `FKC7652DD984536452` FOREIGN KEY (`cat_id_project`) REFERENCES `project` (`project_id`),
  ADD CONSTRAINT `FKC7652DD945895AFF` FOREIGN KEY (`sec_id_group`) REFERENCES `groups` (`group_id`);

--
-- Constraints for table `project_locations`
--
ALTER TABLE `project_locations`
  ADD CONSTRAINT `FK242951B835313189` FOREIGN KEY (`cat_id_loc`) REFERENCES `geoPoint` (`locate_id`),
  ADD CONSTRAINT `FK242951B884536452` FOREIGN KEY (`cat_id_project`) REFERENCES `project` (`project_id`);

--
-- Constraints for table `questions`
--
ALTER TABLE `questions`
  ADD CONSTRAINT `FK95C5414D2B2A6AB4` FOREIGN KEY (`uid`) REFERENCES `account` (`uid`),
  ADD CONSTRAINT `FK95C5414D39E97991` FOREIGN KEY (`section_ssid`) REFERENCES `survey_section` (`ssid`);

--
-- Constraints for table `questions_answers`
--
ALTER TABLE `questions_answers`
  ADD CONSTRAINT `FK539703837E6C7BBC` FOREIGN KEY (`id_question_answer`) REFERENCES `questions` (`qid`);

--
-- Constraints for table `questions_dependencies`
--
ALTER TABLE `questions_dependencies`
  ADD CONSTRAINT `FK92E86ADBDDD118B5` FOREIGN KEY (`q_answer_id`) REFERENCES `questions_answers` (`q_answer_id`);

--
-- Constraints for table `question_category_questions`
--
ALTER TABLE `question_category_questions`
  ADD CONSTRAINT `FK2FFE18457A068CB` FOREIGN KEY (`questionLibrary_qid`) REFERENCES `questions` (`qid`),
  ADD CONSTRAINT `FK2FFE1845B10E79BE` FOREIGN KEY (`question_category_qCategory`) REFERENCES `question_category` (`qCategory`);

--
-- Constraints for table `question_collection`
--
ALTER TABLE `question_collection`
  ADD CONSTRAINT `FKB4097C972B2A6AB4` FOREIGN KEY (`uid`) REFERENCES `account` (`uid`);

--
-- Constraints for table `question_dependence_survey`
--
ALTER TABLE `question_dependence_survey`
  ADD CONSTRAINT `FKBB424D49793D9E77` FOREIGN KEY (`sid`) REFERENCES `surveys` (`sid`);

--
-- Constraints for table `question_relations`
--
ALTER TABLE `question_relations`
  ADD CONSTRAINT `FK217954DE8A76A0BD` FOREIGN KEY (`question_id`) REFERENCES `questions` (`qid`),
  ADD CONSTRAINT `FK217954DE893521DA` FOREIGN KEY (`id_q_colection`) REFERENCES `question_collection` (`id_q_colection`);

--
-- Constraints for table `social_account`
--
ALTER TABLE `social_account`
  ADD CONSTRAINT `FK50078B5BF2F411F2` FOREIGN KEY (`userOwner_uid`) REFERENCES `userAccount` (`uid`),
  ADD CONSTRAINT `FK50078B5B5ECE45A2` FOREIGN KEY (`account_uid`) REFERENCES `account` (`uid`);

--
-- Constraints for table `surveys`
--
ALTER TABLE `surveys`
  ADD CONSTRAINT `FK919144598E4A448B` FOREIGN KEY (`owner_id`) REFERENCES `account` (`uid`),
  ADD CONSTRAINT `FK9191445973FF13B` FOREIGN KEY (`project_project_id`) REFERENCES `project` (`project_id`),
  ADD CONSTRAINT `FK91914459A3C7A06A` FOREIGN KEY (`survey_folder`) REFERENCES `survey_folder` (`survey_folderId`),
  ADD CONSTRAINT `FK91914459A64FB606` FOREIGN KEY (`editor`) REFERENCES `userAccount` (`uid`);

--
-- Constraints for table `survey_folder`
--
ALTER TABLE `survey_folder`
  ADD CONSTRAINT `FK7EF958F36EF241E9` FOREIGN KEY (`createdBy_uid`) REFERENCES `userAccount` (`uid`),
  ADD CONSTRAINT `FK7EF958F32B2A6AB4` FOREIGN KEY (`uid`) REFERENCES `account` (`uid`);

--
-- Constraints for table `survey_group_format`
--
ALTER TABLE `survey_group_format`
  ADD CONSTRAINT `FKB4DF867CB1A6912C` FOREIGN KEY (`id_sid_format`) REFERENCES `survey_format` (`id_sid_format`),
  ADD CONSTRAINT `FKB4DF867C310E993C` FOREIGN KEY (`sg_id`) REFERENCES `survey_group` (`sg_id`);

--
-- Constraints for table `survey_group_project`
--
ALTER TABLE `survey_group_project`
  ADD CONSTRAINT `FKFD028D34B75F3482` FOREIGN KEY (`id_sid_format`) REFERENCES `survey_group` (`sg_id`),
  ADD CONSTRAINT `FKFD028D3484536452` FOREIGN KEY (`cat_id_project`) REFERENCES `project` (`project_id`);

--
-- Constraints for table `survey_pagination`
--
ALTER TABLE `survey_pagination`
  ADD CONSTRAINT `FKBEC9A99F1359B877` FOREIGN KEY (`ssid`) REFERENCES `survey_section` (`ssid`),
  ADD CONSTRAINT `FKBEC9A99F793D9E77` FOREIGN KEY (`sid`) REFERENCES `surveys` (`sid`);

--
-- Constraints for table `survey_result`
--
ALTER TABLE `survey_result`
  ADD CONSTRAINT `FK92EA04A251153812` FOREIGN KEY (`survey_sid`) REFERENCES `surveys` (`sid`),
  ADD CONSTRAINT `FK92EA04A246BF7A1C` FOREIGN KEY (`question_qid`) REFERENCES `questions` (`qid`),
  ADD CONSTRAINT `FK92EA04A2496009B4` FOREIGN KEY (`answer_q_answer_id`) REFERENCES `questions_answers` (`q_answer_id`);

--
-- Constraints for table `survey_section`
--
ALTER TABLE `survey_section`
  ADD CONSTRAINT `FKFE5AD30051153812` FOREIGN KEY (`survey_sid`) REFERENCES `surveys` (`sid`);

--
-- Constraints for table `tweetPoll`
--
ALTER TABLE `tweetPoll`
  ADD CONSTRAINT `FKA65B1D0A64FB606` FOREIGN KEY (`editor`) REFERENCES `userAccount` (`uid`),
  ADD CONSTRAINT `FKA65B1D02B2A6AB4` FOREIGN KEY (`uid`) REFERENCES `account` (`uid`),
  ADD CONSTRAINT `FKA65B1D050FE71F5` FOREIGN KEY (`qid`) REFERENCES `questions` (`qid`),
  ADD CONSTRAINT `FKA65B1D0D9BA7E54` FOREIGN KEY (`tweetPollFolderId`) REFERENCES `tweetPoll_Folder` (`tweetPollFolderId`);

--
-- Constraints for table `tweetPoll_Folder`
--
ALTER TABLE `tweetPoll_Folder`
  ADD CONSTRAINT `FKA027A9DD6EF241E9` FOREIGN KEY (`createdBy_uid`) REFERENCES `userAccount` (`uid`),
  ADD CONSTRAINT `FKA027A9DD2B2A6AB4` FOREIGN KEY (`uid`) REFERENCES `account` (`uid`);

--
-- Constraints for table `tweetpoll_hashtags`
--
ALTER TABLE `tweetpoll_hashtags`
  ADD CONSTRAINT `FKF8C717D6DA98FFE1` FOREIGN KEY (`hastag_id`) REFERENCES `hash_tags` (`hash_tag_id`),
  ADD CONSTRAINT `FKF8C717D6286705D7` FOREIGN KEY (`tweetpoll_id`) REFERENCES `tweetPoll` (`tweet_poll_id`);

--
-- Constraints for table `tweetpoll_result`
--
ALTER TABLE `tweetpoll_result`
  ADD CONSTRAINT `FK8749C18CB9D39F98` FOREIGN KEY (`tweetpoll_switch_id`) REFERENCES `tweetpoll_switch` (`tweetpoll_switch_id`);

--
-- Constraints for table `tweetPoll_save_published_status`
--
ALTER TABLE `tweetPoll_save_published_status`
  ADD CONSTRAINT `FKD499A4B6953C854B` FOREIGN KEY (`tweetPoll_tweet_poll_id`) REFERENCES `tweetPoll` (`tweet_poll_id`),
  ADD CONSTRAINT `FKD499A4B651153812` FOREIGN KEY (`survey_sid`) REFERENCES `surveys` (`sid`),
  ADD CONSTRAINT `FKD499A4B65239D117` FOREIGN KEY (`socialAccount_social_account_id`) REFERENCES `social_account` (`social_account_id`),
  ADD CONSTRAINT `FKD499A4B663976E9` FOREIGN KEY (`poll_poll_id`) REFERENCES `poll` (`poll_id`);

--
-- Constraints for table `tweetpoll_switch`
--
ALTER TABLE `tweetpoll_switch`
  ADD CONSTRAINT `FK89F7B0A3DDD118B5` FOREIGN KEY (`q_answer_id`) REFERENCES `questions_answers` (`q_answer_id`),
  ADD CONSTRAINT `FK89F7B0A3550299A` FOREIGN KEY (`tweet_poll_id`) REFERENCES `tweetPoll` (`tweet_poll_id`);

--
-- Constraints for table `userAccount`
--
ALTER TABLE `userAccount`
  ADD CONSTRAINT `FKA7D56BE25ECE45A2` FOREIGN KEY (`account_uid`) REFERENCES `account` (`uid`),
  ADD CONSTRAINT `FKA7D56BE2B8EB1450` FOREIGN KEY (`groupId`) REFERENCES `groups` (`group_id`);

--
-- Constraints for table `userAccount_followers`
--
ALTER TABLE `userAccount_followers`
  ADD CONSTRAINT `FK7F1957F8E53FBC6` FOREIGN KEY (`uid_follower`) REFERENCES `userAccount` (`uid`),
  ADD CONSTRAINT `FK7F1957F8F44558E9` FOREIGN KEY (`uid`) REFERENCES `userAccount` (`uid`);

--
-- Constraints for table `userAccount_permission`
--
ALTER TABLE `userAccount_permission`
  ADD CONSTRAINT `FKBE01CE4C5F77A117` FOREIGN KEY (`sec_id_secondary`) REFERENCES `userAccount` (`uid`),
  ADD CONSTRAINT `FKBE01CE4C43ADB63D` FOREIGN KEY (`sec_id_permission`) REFERENCES `permission` (`id_permission`);

--
-- Constraints for table `userAccount_project`
--
ALTER TABLE `userAccount_project`
  ADD CONSTRAINT `FKFBC45BBC5F77A117` FOREIGN KEY (`sec_id_secondary`) REFERENCES `userAccount` (`uid`),
  ADD CONSTRAINT `FKFBC45BBC84536452` FOREIGN KEY (`cat_id_project`) REFERENCES `project` (`project_id`);

ALTER TABLE `survey_hashtags`
  ADD CONSTRAINT `FK9D62ED6C793D9E77` FOREIGN KEY (`sid`) REFERENCES `surveys` (`sid`),
  ADD CONSTRAINT `FK9D62ED6CDA98FFE1` FOREIGN KEY (`hastag_id`) REFERENCES `hash_tags` (`hash_tag_id`);


ALTER TABLE `hash_tags_ranking`
  ADD CONSTRAINT `FK71DECDA119AA125` FOREIGN KEY (`hashTag_hash_tag_id`) REFERENCES `hash_tags` (`hash_tag_id`);

--
-- Constraints for table `question_preferences`
--
ALTER TABLE `question_preferences`
  ADD CONSTRAINT `FKD540D01F46BF7A1C` FOREIGN KEY (`question_qid`) REFERENCES `questions` (`qid`);

--
-- Constraints for table `survey_temporal_result`
--
ALTER TABLE `survey_temporal_result`
  ADD CONSTRAINT `FK7867CF551153812` FOREIGN KEY (`survey_sid`) REFERENCES `surveys` (`sid`),
  ADD CONSTRAINT `FK7867CF546BF7A1C` FOREIGN KEY (`question_qid`) REFERENCES `questions` (`qid`),
  ADD CONSTRAINT `FK7867CF5496009B4` FOREIGN KEY (`answer_q_answer_id`) REFERENCES `questions_answers` (`q_answer_id`);



ALTER TABLE `scheduled`
  ADD CONSTRAINT `FKF66BC0AD1366E48E` FOREIGN KEY (`tpoll_tweet_poll_id`) REFERENCES `tweetPoll` (`tweet_poll_id`),
  ADD CONSTRAINT `FKF66BC0AD51153812` FOREIGN KEY (`survey_sid`) REFERENCES `surveys` (`sid`),
  ADD CONSTRAINT `FKF66BC0AD5239D117` FOREIGN KEY (`socialAccount_social_account_id`) REFERENCES `social_account` (`social_account_id`),
  ADD CONSTRAINT `FKF66BC0AD63976E9` FOREIGN KEY (`poll_poll_id`) REFERENCES `poll` (`poll_id`),
  ADD CONSTRAINT `FKF66BC0AD9C14A5E7` FOREIGN KEY (`tpollSavedPublished_status_save_poll_id`) REFERENCES `tweetPoll_save_published_status` (`status_save_poll_id`);


--
-- Constraints for table `helpPage`
--
ALTER TABLE `helpPage`
  ADD CONSTRAINT `FKD0EB1D70F47A8064` FOREIGN KEY (`help_user_id`) REFERENCES `userAccount` (`uid`);