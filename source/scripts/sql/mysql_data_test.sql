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


INSERT INTO `cat_state` (`id_state`, `desc_state`, `image`) VALUES
(1, 'Enabled', NULL),
(2, 'Disabled', NULL);


INSERT INTO `sec_groups` (`group_id`, `des_info`, `name`, `id_state`) VALUES
(1, 'Demo', 'Demo Group', 1),
(2, 'Favourites', 'Favourites', 1);

INSERT INTO `sec_user_permission` (`sec_id_secondary`, `sec_id_permission`) VALUES
(1, 1),
(1, 2);


INSERT INTO `sec_permission` (`id_permission`, `permission`, `description`) VALUES
(1, 'ENCUESTAME_USER', 'ENCUESTAME_USER'),
(2, 'ENCUESTAME_ADMIN', 'ENCUESTAME_ADMIN');


INSERT INTO `sec_user` (`uid`) VALUES
(1);

-- demo pass encrypt: 12345
INSERT INTO `sec_user_secondary` (`uid`, `name`, `date_new`, `invite_code`, `owner`, `password`, `publisher`, `email`, `status`, `twitter`, `username`, `secUser_uid`) VALUES
(1, 'admin', '2010-01-20 12:47:40', NULL, NULL, '6xAX8siGWDJXfkJUVxWLqsk0rz8U+aG6Y8yA1IokxuhEIZ8+RugleJtLUYbdGxc+', NULL, 'admin@encuestame.org', '', NULL, 'admin', 1)

