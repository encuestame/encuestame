/*
 ************************************************************************************
 * Copyright (C) 2001-2010 encuestame: system online surveys Copyright (C) 2010
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


INSERT INTO `cat_state` (`id_state`, `desc_state`, `image`) VALUES
(1, 'Enabled', NULL),
(2, 'Disabled', NULL);


INSERT INTO `sec_groups` (`group_id`, `des_info`, `name`, `id_state`) VALUES
(1, 'Demo', 'Demo Group', 1),
(2, 'Favourites', 'Favourites', 1);


INSERT INTO `sec_permission` (`id_permission`, `permission`, `description`) VALUES
(1, 'ENCUESTAME_USER', 'ENCUESTAME_USER'),
(2, 'ENCUESTAME_ADMIN', 'ENCUESTAME_ADMIN'),
(3, 'ENCUESTAME_ADMIN', 'ENCUESTAME_OWNER'),
(4, 'ENCUESTAME_PUBLISHER', 'ENCUESTAME_PUBLISHER'),
(5, 'ENCUESTAME_EDITOR', 'ENCUESTAME_EDITOR');

INSERT INTO `sec_user` (`uid`, `twitter_consumer_key`, `twitter_consumer_secret`, `twitter_account`, `twitter_password`, `twitter_pin`) VALUES
(1, 'nFboU4T1Zhv8cqMC4cP0ug', 'GwOPUEJEaCbNBiBzq6J8StDhb7FOmwDcjfX6zMe0', 'testEncuesta', 'testEncuesta123', 4189783);

INSERT INTO `sec_user_secondary` (`uid`, `name`, `date_new`, `invite_code`,  `password`, `email`, `status`, `twitter`, `username`, `secUser_uid`) VALUES
(1, 'admin', '2010-01-20 12:47:40', NULL, '6xAX8siGWDJXfkJUVxWLqsk0rz8U+aG6Y8yA1IokxuhEIZ8+RugleJtLUYbdGxc+', 'admin@encuestame.org', '', NULL, 'admin', 1);


INSERT INTO `sec_user_permission` (`sec_id_secondary`, `sec_id_permission`) VALUES
(1, 1),
(1, 2),
(1, 3),
(1, 4);

-- Location Data

INSERT INTO `cat_location_folder` (`locate_folder_id`, `type`, `name`, `secUsers_uid`) VALUES
(1, 'GROUPING', 'Managua', 1),
(2, 'GROUPING', 'Esteli', NULL),
(3, 'GROUPING', 'Matagalpa', 1),
(4, 'GROUPING', 'Leon', 1);

INSERT INTO `cat_location` (`locate_id`, `description`, `lat`, `lng`, `location_status`, `catLocationFolder_locate_folder_id`, `secUsers_uid`, `loc_id_type`) VALUES
(1, 'Managua', 12.1333, -86.25, 'ACTIVE', 1, NULL, NULL),
(2, 'Tipitapa', 12.1333, -86.25, 'ACTIVE', 1, NULL, NULL),
(3, 'Ciudad Sandino', 12.1333, -86.25, 'ACTIVE', 1, NULL, NULL),
(4, 'Batahola', 12.1333, -86.25, 'ACTIVE', 1, NULL, NULL),
(5, 'Lindavista', 12.1333, -86.25, 'ACTIVE', 1, NULL, NULL);

-- Question Pattern
INSERT INTO `encuestame_core`.`questions_pattern` (`pattenr_id`, `class`, `des_qid`, `finallity`, `label_qid`, `level`, `template_patron`, `type_pattern`)
VALUES (NULL, '', '', 'Example', 'What do you do?', '1', 'radio', 'Yes/No');

-- Questions
INSERT INTO `encuestame_core`.`questions` (`qid`, `qid_key`, `question`, `shared_question`, `id_state`, `id_question_pattern`, `uid`)
VALUES (NULL, '12345', 'What do you do at home?', NULL, '1', '1', '1');

-- Poll
INSERT INTO `encuestame_core`.`poll` (`poll_id`, `created_at`, `completed`, `poll_hash`, `uid`, `qid`)
VALUES (NULL, '2010-05-17 22:23:57', '', '123ABC', '1', '1');
