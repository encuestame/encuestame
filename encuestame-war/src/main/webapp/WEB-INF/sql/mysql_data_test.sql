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

INSERT INTO `cat_location` (`locate_id`, `accuracy`, `address`, `country_code`, `country_name`, `description`, `lat`, `lng`, `location_status`, `catLocationFolder_locate_folder_id`, `secUsers_uid`, `loc_id_type`) VALUES
(6, NULL, NULL, NULL, NULL, 'Default Item Name', NULL, NULL, NULL, 5, NULL, NULL),
(7, 6, '12a Avenida S.E, Managua, Nicaragua', 'NI', 'Nicaragua', 'Managua Centro', 12.1394, -86.2667, 'ACTIVE', 5, 1, NULL),
(8, 4, 'Los Laureles Norte, Managua, Nicaragua', 'NI', 'Nicaragua', 'Laureles Norte', 12.1306, -86.1946, NULL, 5, 1, NULL),
(9, 9, 'Aeropuerto Internacional Managua, C 1, Managua, Nicaragua', 'NI', 'Nicaragua', 'Aeropuerto', 12.1434, -86.174, 'ACTIVE', 5, 1, NULL),
(10, 2, 'Esteli, Nicaragua', 'NI', 'Nicaragua', 'Esteli Centro', 13.0842, -86.3381, 'ACTIVE', 2, 1, NULL),
(11, 2, 'Matagalpa, Nicaragua', 'NI', 'Nicaragua', 'La Trinidads', 12.9296, -86.1438, 'ACTIVE', 2, 1, NULL),
(12, 4, 'San Sebastián de Yalí­, Nicaragua', 'NI', 'Nicaragua', 'San Sebastian de Yalí', 13.3028, -86.1836, 'ACTIVE', 2, 1, NULL),
(13, 6, 'NIC 12, León, Nicaragua', 'NI', 'Nicaragua', 'Leon', 12.4393, -86.8744, 'ACTIVE', 4, 1, NULL),
(14, 2, 'Leon, Nicaragua', 'NI', 'Nicaragua', 'La Paz Centro', 12.3232, -86.6382, 'ACTIVE', 4, 1, NULL),
(15, 6, 'Hacia Estatua Montoya, Managua, Nicaragua', 'NI', 'Nicaragua', 'Managua', 12.1414, -86.2873, 'ACTIVE', 6, 11, NULL),
(16, 6, '13a Avenida S.E, Managua, Nicaragua', 'NI', 'Nicaragua', 'Default Item Name', 12.1434, -86.2626, 'ACTIVE', 6, 11, NULL),
(17, 6, 'Avenida Mariana, Managua, Nicaragua', 'NI', 'Nicaragua', 'Default Item Name', 12.1333, -86.2921, 'ACTIVE', 6, 11, NULL),
(18, NULL, NULL, NULL, NULL, 'Default Item Name', NULL, NULL, 'ACTIVE', 1, 1, NULL),
(19, 6, '24a Avenida S.O, Managua, Nicaragua', 'NI', 'Nicaragua', 'Morazan', 12.1521, -86.2949, 'ACTIVE', 1, 1, NULL),
(20, 6, 'Radial Santo Domingo, Managua, Nicaragua', 'NI', 'Nicaragua', 'Mercado Oriental', 12.1434, -86.2598, 'ACTIVE', 1, 1, NULL);

--
-- Dumping data for table `cat_location_folder`
--

INSERT INTO `cat_location_folder` (`locate_folder_id`, `type`, `name`, `secUsers_uid`, `subLocationFolder_locate_folder_id`) VALUES
(1, 'GROUPING', 'Managua', 1, NULL),
(2, 'GROUPING', 'Esteli', 1, NULL),
(3, 'GROUPING', 'Matagalpa', 1, NULL),
(4, 'GROUPING', 'Leon', 1, NULL),
(5, 'GROUPING', 'Las Brisass', 1, 1),
(6, 'GROUPING', 'dsadsadsa', 11, NULL);

--
-- Dumping data for table `cat_location_type`
--


--
-- Dumping data for table `cat_state`
--

INSERT INTO `cat_state` (`id_state`, `desc_state`, `image`) VALUES
(1, 'Enabled', NULL),
(2, 'Disabled', NULL);

--
-- Dumping data for table `client`
--


--
-- Dumping data for table `poll`
--


--
-- Dumping data for table `poll_result`
--


--
-- Dumping data for table `project`
--


--
-- Dumping data for table `project_locations`
--


--
-- Dumping data for table `questions`
--

INSERT INTO `questions` (`qid`, `qid_key`, `question`, `shared_question`, `id_state`, `id_question_pattern`, `uid`) VALUES
(1, '171ca49a90afe44bad3f5d05158e4675', 'd l;asd lkdl kdlsa dl;sak ldkasl; kdla;s dlask ldkasl kdlask ldaskl das', '\0', NULL, NULL, 1),
(2, '29d3ed842cb53f7272f69b710627c76d', 'Final del Mundia? ', '\0', NULL, NULL, 1),
(3, '0852c7aa4aa8fa63c9f18c517acc05f2', 'dadadadada', '\0', NULL, NULL, 1),
(4, 'ebf4f7cb73a2624b2787fe75fe3bb25c', 'Probando pregunta', '\0', NULL, NULL, 1),
(5, 'b4ebf90ba8ad3a0c438ece97fc3270a0', 'dsa d da da da', '\0', NULL, NULL, 1),
(6, 'c1df56dd22b34e43a970c7b192a0212f', 'Quien gana?????', '\0', NULL, NULL, 1),
(7, 'a73bcf03f9df5e5dad72a85148455b91', 'Como la ves?', '\0', NULL, NULL, 1),
(8, 'c95ed96699392bc42943749825cfd1ba', 'xsa dsa dsa das dsa d', '\0', NULL, NULL, 1),
(9, 'f3ce3829de7f472f44fb2d1033c33f18', 'Que nota', '\0', NULL, NULL, 1),
(10, '29dc4d00844e48faf82282822291f8d4', 'dsadas das', '\0', NULL, NULL, 1),
(11, 'b8ca2c63a2d1255edb2ad239cf0fb52e', 'dsad asdjkas jdkla sjdlka s', '\0', NULL, NULL, 1),
(12, '32c4e166959ecdb7489d844e111180f8', ' das das das', '\0', NULL, NULL, 1),
(13, 'bc98e449ee44ef53b30b6c3b6c5bcac4', 'Quien ganará Holanda o España?', '\0', NULL, NULL, 1),
(14, '795be6d730bbb21026f8c48eeaff2136', 'Quien ganará Holanda o España?', '\0', NULL, NULL, 1),
(15, 'd905c819758902ddc699b5e85a15a14e', 'd as das das dsa', '\0', NULL, NULL, 1),
(16, '291d91160cdb61e4e659097c3727d986', 'dsasdasdad das d Holanda España das probando Mundial', '\0', NULL, NULL, 1),
(17, '4070dc38d204faaf216b669ccd77983b', 'd as das das das dsa', '\0', NULL, NULL, 1),
(18, 'ab46a06380c5aeef61b5ba7a5b365ead', 'asddsa da das da sdas das dsa das', '\0', NULL, NULL, 1),
(19, '017b37f37e1d577631a0093ffe35fc8c', 'das', '\0', NULL, NULL, 1),
(20, '507cde536c607ec0270590d4efa26a20', 'Holanda', '\0', NULL, NULL, 1),
(21, '1c783cb8e73b04a1945ea855161a9e4b', 'dsasdasdad das d Holanda España das probando Mundial', '\0', NULL, NULL, 1),
(22, '60e3141b918eb96aa10d8c70b6a2b5f5', ' grb gbrrbgbgt bgt bry btrnbrh brbrybbrgbr brgb   g', '\0', NULL, NULL, 1),
(23, '23a1f487c54cc579659acdeeae370e99', ' fda sdfa adfff f', '\0', NULL, NULL, 1),
(24, '3116698b4cc6b7b6a7075f9fb648002f', ' fda fda fdafdfa', '\0', NULL, NULL, 1),
(25, '4b47642f226bc296472398b577707415', 'da ssds fsfd faf sdas ddsa', '\0', NULL, NULL, 1);

--
-- Dumping data for table `questions_answers`
--

INSERT INTO `questions_answers` (`q_answer_id`, `answer`, `answerType`, `answer_hash`, `id_question_answer`, `answer_url`) VALUES
(1, 'fdsdsadasdas', 3, '222e7706a22da526656eb36f6e10273c', 1, NULL),
(2, 'dsa das dsa da', 3, 'e7931ecdc351bae2ead5cfa5fe210a72', 1, NULL),
(3, 'das dsa das dsa', 3, '4e76b32bd2f5d335f61ba41ea88c0475', 2, NULL),
(4, ' das dsa dsa das ds adas', 3, 'f8ce729926f2aa6cef6431e3eac8c76f', 2, NULL),
(5, 'sIp', 3, 'c583493444e8e87473638864825b1c45', 7, 'http://tinyurl.com/23h4nha'),
(6, 'Nop', 3, 'c760000d37a5a395b4ada0474bcd7077', 7, 'http://tinyurl.com/2btx9wp'),
(7, ' das ', 3, '462385e40bdcc3b4c0cdecc44d3f6ccd', 8, 'http://tinyurl.com/28p7wcm'),
(8, ' das das', 3, '8e4c4609a51271fac9e286dabc887907', 8, 'http://tinyurl.com/28waddu'),
(9, 'xsasa', 3, 'ddfc1b747953d07cfe489f1d12154ff0', 9, 'http://tinyurl.com/2bu6999'),
(10, 'dsada', 3, '2fd9500b39aab63f3ec66db1fe7d92ee', 9, 'http://tinyurl.com/26svptj'),
(11, 'dsadas', 3, '0055e7a09fcb7cf31002121f88745d74', 10, 'http://tinyurl.com/3xd9c2l'),
(12, 'dsada', 3, '354f53280b570fc1e090dab6eaee3923', 10, 'http://tinyurl.com/37lxfjr'),
(13, 'da skdaslda kl;da', 3, 'f51200027e44e7b01758d0aabfcbe0c1', 11, 'http://tinyurl.com/2v74jxt'),
(14, 'k;lk l;k;l kl; k;l', 3, '90b389c010ca5d8f29988d7c65cb5574', 11, 'http://tinyurl.com/39kqfpq'),
(15, 'das das das', 3, '2b3ba16ea528eff837dd0540834adf84', 12, 'http://tinyurl.com/2eegafr'),
(16, ' das d dsa', 3, '570e773ae24f2ee7fdfa666f74abe98b', 12, 'http://tinyurl.com/25hv5dp'),
(17, 'd as das das', 3, '091ccb4324794ac113a7b54d2cc508a6', 12, 'http://tinyurl.com/2d45jcb'),
(18, 'das das das', 3, '2b3ba16ea528eff837dd0540834adf84', 13, 'http://tinyurl.com/2eegafr'),
(19, ' das d dsa', 3, '570e773ae24f2ee7fdfa666f74abe98b', 13, 'http://tinyurl.com/25hv5dp'),
(20, 'd as das das', 3, '091ccb4324794ac113a7b54d2cc508a6', 13, 'http://tinyurl.com/2d45jcb'),
(21, 'Holanda', 3, 'e2f322613b400274d8882d8987d500c9', 13, 'http://tinyurl.com/2g4drkg'),
(22, 'España', 3, 'abe422d8d43791df69b867ce0a5977e1', 13, 'http://tinyurl.com/2dd7ltd'),
(23, 'das das das', 3, '2b3ba16ea528eff837dd0540834adf84', 14, 'http://tinyurl.com/2eegafr'),
(24, ' das d dsa', 3, '570e773ae24f2ee7fdfa666f74abe98b', 14, 'http://tinyurl.com/25hv5dp'),
(25, 'd as das das', 3, '091ccb4324794ac113a7b54d2cc508a6', 14, 'http://tinyurl.com/2d45jcb'),
(26, 'Holanda', 3, 'e2f322613b400274d8882d8987d500c9', 14, 'http://tinyurl.com/2g4drkg'),
(27, 'España', 3, 'abe422d8d43791df69b867ce0a5977e1', 14, 'http://tinyurl.com/2dd7ltd'),
(28, ' das das da', 3, '687f7474449bb58f5a86f725db075879', 15, 'http://tinyurl.com/24c6q88'),
(29, ' da dsa', 3, '8ce2247f1b16f336199e9e92924a31e7', 15, 'http://tinyurl.com/2bekrg8'),
(30, 'das das', 3, '705d13e68fc42185fb5b1d5fd9ff3333', 16, 'http://tinyurl.com/22or6qb'),
(31, 'das das', 3, 'c71c1f811d13bd77fa87ddabaabb0f79', 16, 'http://tinyurl.com/27tjdn5'),
(32, ' das das dsa da', 3, '0258b5790567ad3bbf69491b8cd78531', 16, 'http://tinyurl.com/2ewxev5'),
(33, 'd asdas das  dsa dsa dsa', 3, 'e48a635b51a974812141f959cebfe2a3', 17, 'http://tinyurl.com/27ft526'),
(34, ' dsa das da sdsa', 3, 'f09cf7bf74d057380c7a6c506932625b', 17, 'http://tinyurl.com/24bea4l'),
(35, ' das da sdas', 3, '6fe8c83c77756d36aef1909ce391e7d8', 17, 'http://tinyurl.com/26huu94'),
(36, ' das das das', 3, '022a7dcc6dbc3cb24d64fa186ea42962', 18, 'http://tinyurl.com/2395mk5'),
(37, ' das das da', 3, 'b3b610bd9cd3e8435f2ff837893e6118', 18, 'http://tinyurl.com/2fz49hy'),
(38, 'dscds', 3, 'd02319b62dbd54739337768664673be0', 19, 'http://tinyurl.com/33ats2n'),
(39, 'fsdfsdc', 3, '282d287bf3af3fdb4eeb73dd21eb7483', 19, 'http://tinyurl.com/39fr7w5'),
(40, 'yes', 3, '8bf2344a04e091d96695c10843253ade', 20, 'http://tinyurl.com/2ugm3g9'),
(41, 'no', 3, '7f334c15cc00f2d9a9eebd82f00c402f', 20, 'http://tinyurl.com/34mks8d'),
(42, ' das dsa fda fda fda fda fa fda', 3, 'd994919467892483127cb1e6d0f562e8', 21, 'http://tinyurl.com/399tmqx'),
(43, ' dsa fds afd fdafad fda', 3, '218065eda77c3bf84db96e749d2a5f30', 21, 'http://tinyurl.com/348m6da'),
(44, 'g tgtr gegte gte ', 3, '41ecf9d98f37a13a971b0367656b337f', 22, 'http://tinyurl.com/34zqwc3'),
(45, ' gt gtr gt re', 3, '84b3308db8330f638e49ade1052cc2a9', 22, 'http://tinyurl.com/347p8c7'),
(46, ' ads fads fda fa fda fda', 3, '7826e7cb3f15a4be4999f284af679f08', 23, 'http://tinyurl.com/2fodwln'),
(47, ' das dsa da', 3, '512fb9df31285a1c8c54281fb7606b79', 23, 'http://tinyurl.com/24wshtz'),
(48, ' ds DSA dsA ', 3, 'c88c3ffe6da943d9fe3f71dc51525f33', 24, 'http://tinyurl.com/38gfrn6'),
(49, ' DSA dA DS ADAd ', 3, '2de027eda4c4f05f51bb530054d9a37c', 24, 'http://tinyurl.com/38b5pex'),
(50, ' dsa das fads fa fda ', 3, '9040e623899f928d1024800179ebf930', 25, 'http://tinyurl.com/27en7zr'),
(51, ' dsa fas fda fda fda', 3, '497c57975233e5d98345c989c6b75425', 25, 'http://tinyurl.com/2caklmb');

--
-- Dumping data for table `questions_pattern`
--

INSERT INTO `questions_pattern` (`pattenr_id`, `class`, `des_qid`, `finallity`, `label_qid`, `level`, `template_patron`, `type_pattern`) VALUES
(59, '', '', 'Example', 'What do you do?', 1, 'radio', 'Yes/No');

--
-- Dumping data for table `question_collection`
--


--
-- Dumping data for table `question_relations`
--


--
-- Dumping data for table `sec_groups`
--

INSERT INTO `sec_groups` (`group_id`, `des_info`, `name`, `id_state`, `secUsers_uid`) VALUES
(1, 'Demo', 'Demo Group', 1, NULL),
(2, 'Favourites', 'Favourites', 1, NULL),
(18, 'sadsadsadassad', 'dasda2131', NULL, 1),
(19, 'dasdsa', 'dsadsa', NULL, 11),
(20, 'dsadsa', 'dsa', NULL, 1);

--
-- Dumping data for table `sec_group_permission`
--


--
-- Dumping data for table `sec_permission`
--

INSERT INTO `sec_permission` (`id_permission`, `permission`, `description`) VALUES
(2, 'ENCUESTAME_ADMIN', 'ENCUESTAME_ADMIN'),
(3, 'ENCUESTAME_OWNER', 'ENCUESTAME_OWNER'),
(4, 'ENCUESTAME_PUBLISHER', 'ENCUESTAME_PUBLISHER'),
(5, 'ENCUESTAME_EDITOR', 'ENCUESTAME_EDITOR'),
(6, 'ENCUESTAME_USER', 'ENCUESTAME_USER');

--
-- Dumping data for table `sec_project_group`
--


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

--
-- Dumping data for table `sec_user_group`
--


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
(1, 6);

--
-- Dumping data for table `sec_user_project`
--


--
-- Dumping data for table `sec_user_secondary`
--

INSERT INTO `sec_user_secondary` (`uid`, `name`, `date_new`, `invite_code`, `password`, `email`, `status`, `twitter`, `username`, `secUser_uid`, `last_ip_logged`, `last_time_logged`) VALUES
(1, 'Juan Carlos Picado', '2010-01-20 12:47:40', '', '6xAX8siGWDJXfkJUVxWLqsk0rz8U+aG6Y8yA1IokxuhEIZ8+RugleJtLUYbdGxc+', 'juanpicado19@gmail.com', '', NULL, 'admin', 1, NULL, '2010-07-19 10:55:27'),
(2, '', '2010-06-19 07:56:02', '', 'pDdSocopH0ny7CpmCKJMu19guKthRWyN751c6H2IYnUfjC+tUtAO87pap39B6622', 'juan@jotadeveloper.com', '', NULL, 'jota', 2, NULL, NULL),
(3, '', '2010-06-19 08:19:44', '', 'pKfJHPrLGCUbak2yav0oSvNMYDt3dSDUN8ivYLkCWshhTJtKySHAWY9RXFlVr0L5', 'info@jotadeveloper.comsssssss', '', NULL, 'sadasdasdsa', 3, NULL, NULL),
(4, '', '2010-06-19 08:21:38', '', '3wi3Oy6i9zT2zLTD5ewWqHCG5xpPcwSj/xSpSrLMAgXY3wLVidiV3cKkpbfAP7ar', '2juanpicado19@gmail.com', '', NULL, 'dsadsadkajdlksa', 4, NULL, NULL),
(5, '', '2010-06-19 08:49:33', '', 'Th04R8bHe37/E6ugUpaJZokmV7MIpKAsY4fqgolmhcjosfWTCKbZ3mjeRWcFhRWz', 'jotadeveloper@gmail.com', '', NULL, 'jotadasdsa', 5, NULL, NULL),
(6, '', '2010-06-19 09:15:39', '', 'CnYJswTOBkrF9vU4kY7PvEbA5PFJf1gKdWwGSOIaUFm3ZdD2m84reYp6Lmfl84VM', 'paola@jotadeveloper.com', '', NULL, 'sadasdsakjdsal', 6, NULL, NULL),
(7, '', '2010-06-19 09:17:02', '', 'xqHe0RpeBz/3NA3jnc99Il4/tylQDcWWL+JF3jaDl+aB8bPI1A+3u7BGd23w6NaE', 'paola@encuestame.org', '', NULL, 'paolaaaaaaa', 7, NULL, NULL),
(8, '', '2010-06-19 09:33:34', '', 'scAnsHAW8LgYwVb4AnGGMA4hZsw/ZVMr6MwxsxSh2kPVdL4ArRv16x7aVWiurAps', 'juanpicado19@gmail.comssa', '', NULL, 'josajodasjodas', 8, NULL, NULL),
(10, '', '2010-06-19 09:45:53', '', 'lKTk7hgn57Gj1dPulC4os5hTqpwUMJh1jspj335H6a6mGN77aj85/ojJFbLfWYvI', 'sdadsa', '', NULL, 'dasdasdsad', 10, NULL, NULL),
(11, '', '2010-06-19 09:56:36', '', 'ogae9Ih7UdJMS9+mSL2/62bRCYItdm0iRUSHzSCxB3RdxrXs+QvG7FFphELh/XDZ', 'juanpicado19@gmail.comdasdas', '', NULL, 'dasdsa2131', 11, NULL, NULL),
(12, '', '2010-06-26 08:27:30', '', '5JzT+UZRKdRHKD251b1tcezb/qGXLcmXPEvgEiLnXskYd48QmrLye0tMoU3sJaEw', 'webmaster@encuestame.org', '', NULL, 'juancarlospicado', 12, NULL, '2010-06-26 08:27:34');

--
-- Dumping data for table `sec_user_twitter_account`
--

INSERT INTO `sec_user_twitter_account` (`sec_user_twitter_id`, `twitter_account`, `twitter_password`, `secUsers_uid`, `twitter_consumer_key`, `twitter_consumer_secret`, `twitter_pin`, `type_auth`, `twitter_verified`) VALUES
(1, 'testEncuesta', 'testEncuesta123', 1, 'nFboU4T1Zhv8cqMC4cP0ug', 'GwOPUEJEaCbNBiBzq6J8StDhb7FOmwDcjfX6zMe0', 4129201, 'PASSWORD', ''),
(4, 'dasdas', '', 1, NULL, NULL, NULL, 'PASSWORD', '\0'),
(5, 'testEncuesta2', 'testEncuesta2123', 1, NULL, NULL, NULL, 'PASSWORD', '');

--
-- Dumping data for table `surveys`
--


--
-- Dumping data for table `survey_format`
--


--
-- Dumping data for table `survey_group`
--


--
-- Dumping data for table `survey_group_format`
--


--
-- Dumping data for table `survey_group_project`
--


--
-- Dumping data for table `survey_result`
--


--
-- Dumping data for table `survey_section`
--


--
-- Dumping data for table `tweetPoll`
--

INSERT INTO `tweetPoll` (`tweet_poll_id`, `allow_live_results`, `close_notification`, `completed`, `publication_date_tweet`, `publish`, `result_notification`, `schedule_date_tweet`, `schedule`, `qid`, `uid`, `captcha`, `limit_votes`, `resume_live_results`) VALUES
(1, '\0', '\0', '\0', NULL, '\0', '\0', '2010-06-20 21:45:17', '\0', 1, 1, NULL, NULL, NULL),
(2, '\0', '\0', '\0', NULL, '\0', '\0', '2010-06-20 21:48:29', '\0', 2, 1, NULL, NULL, NULL),
(3, '\0', '\0', '\0', NULL, '\0', '\0', '2010-06-26 12:24:15', '\0', 3, 1, NULL, NULL, NULL),
(4, '\0', '\0', '\0', NULL, '', '\0', '2010-06-26 12:36:28', '\0', 4, 1, NULL, NULL, NULL),
(5, '\0', '\0', '\0', '2010-06-26 12:56:07', '', '\0', '2010-06-26 12:56:06', '\0', 5, 1, NULL, NULL, NULL),
(6, '\0', '\0', '\0', '2010-06-26 13:10:41', '', '\0', '2010-06-26 13:10:40', '\0', 6, 1, NULL, NULL, NULL),
(7, '\0', '\0', '\0', '2010-06-26 13:16:11', '', '\0', '2010-06-26 13:16:09', '\0', 7, 1, NULL, NULL, NULL),
(8, '\0', '\0', '\0', '2010-06-26 17:35:01', '', '\0', '2010-06-26 17:34:58', '\0', 8, 1, NULL, NULL, NULL),
(9, '\0', '\0', '\0', '2010-06-27 11:13:11', '', '\0', '2010-06-27 11:13:08', '\0', 9, 1, NULL, NULL, NULL),
(10, '\0', '\0', '\0', '2010-07-04 19:27:12', '', '\0', '2010-07-04 19:27:10', '\0', 10, 1, NULL, NULL, NULL),
(11, '', '\0', '\0', '2010-07-10 00:29:34', '', '\0', '2010-07-10 00:29:31', '\0', 11, 1, NULL, NULL, NULL),
(12, '', '\0', '\0', NULL, '\0', '\0', '2010-07-11 05:10:08', '\0', 12, 1, NULL, NULL, NULL),
(13, '', '\0', '\0', NULL, '\0', '\0', '2010-07-11 05:10:43', '\0', 13, 1, NULL, NULL, NULL),
(14, '', '\0', '\0', NULL, '\0', '\0', '2010-07-11 05:11:19', '\0', 14, 1, NULL, NULL, NULL),
(15, '', '\0', '\0', NULL, '\0', '\0', '2010-07-11 05:12:09', '\0', 15, 1, NULL, NULL, NULL),
(16, '', '\0', '\0', NULL, '\0', '\0', '2010-07-11 05:14:26', '\0', 16, 1, NULL, NULL, NULL),
(17, '', '\0', '\0', '2010-07-11 02:40:37', '', '\0', '2010-07-11 05:15:03', '\0', 17, 1, NULL, NULL, NULL),
(18, '', '\0', '\0', NULL, '\0', '\0', '2010-07-11 10:55:21', '\0', 18, 1, NULL, NULL, NULL),
(19, '', '\0', '\0', '2010-07-17 12:14:36', '', '\0', '2010-07-17 12:14:34', '\0', 19, 1, NULL, NULL, NULL),
(20, '', '\0', '\0', '2010-07-17 12:51:04', '', '\0', '2010-07-17 12:51:01', '\0', 20, 1, NULL, NULL, NULL),
(21, '', '\0', '\0', NULL, '', '\0', '2010-07-17 23:14:43', '\0', 21, 1, NULL, NULL, NULL),
(22, '', '\0', '\0', '2010-07-18 13:36:03', '', '\0', '2010-07-18 13:35:54', '\0', 22, 1, NULL, NULL, NULL),
(23, '', '\0', '\0', NULL, '', '\0', '2010-07-19 02:44:04', '', 23, 1, '', 5000, '\0'),
(24, '', '\0', '\0', NULL, '', '\0', '2010-07-19 02:46:18', '', 24, 1, '', 5000, '\0'),
(25, '', '\0', '\0', NULL, '', '\0', '2010-07-19 10:42:05', '', 25, 1, '', 5000, '\0');

--
-- Dumping data for table `tweetpoll_result`
--

INSERT INTO `tweetpoll_result` (`tweetpoll_resultId`, `ip_vote`, `tweet_date_response`, `tweetpoll_switch_id`) VALUES
(1, '0:0:0:0:0:0:0:1', '2010-07-04 09:59:50', 10),
(2, '127.0.0.1', '2010-07-19 02:48:31', 40),
(3, '0:0:0:0:0:0:0:1', '2010-07-19 10:42:29', 42);

--
-- Dumping data for table `tweetPoll_save_published_status`
--

INSERT INTO `tweetPoll_save_published_status` (`status_save_poll_id`, `type`, `tweet_id`, `tweetPoll_tweet_poll_id`, `twitterAccount_sec_user_twitter_id`, `status_description`, `publication_date_tweet`, `status`) VALUES
(1, NULL, NULL, 23, 1, 'twitter4j.TwitterException: 401:Authentication credentials were missing or incorrect.\n{"errors":[{"code":32,"message":"Could not authenticate you"}]}\n', NULL, 'FAILED'),
(2, NULL, 18901994631, 24, 1, NULL, '2010-07-19 02:46:20', 'SUCCESS'),
(3, 'TWITTER', 18928975215, 25, 1, NULL, '2010-07-19 10:42:07', 'SUCCESS'),
(4, 'TWITTER', 18928976340, 25, 5, NULL, '2010-07-19 10:42:08', 'SUCCESS');

--
-- Dumping data for table `tweetpoll_switch`
--

INSERT INTO `tweetpoll_switch` (`tweetpoll_switch_id`, `tweet_code`, `q_answer_id`, `tweet_poll_id`) VALUES
(1, '222e7706a22da526656eb36f6e10273c', 1, 1),
(2, 'e7931ecdc351bae2ead5cfa5fe210a72', 2, 1),
(3, '4e76b32bd2f5d335f61ba41ea88c0475', 3, 2),
(4, 'f8ce729926f2aa6cef6431e3eac8c76f', 4, 2),
(5, 'c583493444e8e87473638864825b1c45', 5, 7),
(6, 'c760000d37a5a395b4ada0474bcd7077', 6, 7),
(7, '462385e40bdcc3b4c0cdecc44d3f6ccd', 7, 8),
(8, '8e4c4609a51271fac9e286dabc887907', 8, 8),
(9, 'ddfc1b747953d07cfe489f1d12154ff0', 9, 9),
(10, '2fd9500b39aab63f3ec66db1fe7d92ee', 10, 9),
(11, '0055e7a09fcb7cf31002121f88745d74', 11, 10),
(12, '354f53280b570fc1e090dab6eaee3923', 12, 10),
(13, 'f51200027e44e7b01758d0aabfcbe0c1', 13, 11),
(14, '90b389c010ca5d8f29988d7c65cb5574', 14, 11),
(15, '2b3ba16ea528eff837dd0540834adf84', 15, 12),
(16, '570e773ae24f2ee7fdfa666f74abe98b', 16, 12),
(17, '091ccb4324794ac113a7b54d2cc508a6', 17, 12),
(20, '687f7474449bb58f5a86f725db075879', 28, 15),
(21, '8ce2247f1b16f336199e9e92924a31e7', 29, 15),
(22, '705d13e68fc42185fb5b1d5fd9ff3333', 30, 16),
(23, 'c71c1f811d13bd77fa87ddabaabb0f79', 31, 16),
(24, '0258b5790567ad3bbf69491b8cd78531', 32, 16),
(25, 'e48a635b51a974812141f959cebfe2a3', 33, 17),
(26, 'f09cf7bf74d057380c7a6c506932625b', 34, 17),
(27, '6fe8c83c77756d36aef1909ce391e7d8', 35, 17),
(28, '022a7dcc6dbc3cb24d64fa186ea42962', 36, 18),
(29, 'b3b610bd9cd3e8435f2ff837893e6118', 37, 18),
(30, 'd02319b62dbd54739337768664673be0', 38, 19),
(31, '282d287bf3af3fdb4eeb73dd21eb7483', 39, 19),
(32, '8bf2344a04e091d96695c10843253ade', 40, 20),
(33, '7f334c15cc00f2d9a9eebd82f00c402f', 41, 20),
(34, 'd994919467892483127cb1e6d0f562e8', 42, 21),
(35, '218065eda77c3bf84db96e749d2a5f30', 43, 21),
(36, '41ecf9d98f37a13a971b0367656b337f', 44, 22),
(37, '84b3308db8330f638e49ade1052cc2a9', 45, 22),
(38, '7826e7cb3f15a4be4999f284af679f08', 46, 23),
(39, '512fb9df31285a1c8c54281fb7606b79', 47, 23),
(40, 'c88c3ffe6da943d9fe3f71dc51525f33', 48, 24),
(41, '2de027eda4c4f05f51bb530054d9a37c', 49, 24),
(42, '9040e623899f928d1024800179ebf930', 50, 25),
(43, '497c57975233e5d98345c989c6b75425', 51, 25);
