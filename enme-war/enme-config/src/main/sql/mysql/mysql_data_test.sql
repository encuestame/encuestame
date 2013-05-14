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

INSERT INTO `sec_user_twitter_account` (`sec_user_twitter_id`, `twitter_account`, `twitter_password`, `secUsers_uid`, `twitter_consumer_key`, `twitter_consumer_secret`, `twitter_pin`, `type_auth`, `twitter_verified`) VALUES
(1, 'testEncuesta', 'testEncuesta123', 1, 'nFboU4T1Zhv8cqMC4cP0ug', 'GwOPUEJEaCbNBiBzq6J8StDhb7FOmwDcjfX6zMe0', 4129201, 'PASSWORD', ''),
(4, 'dasdas', '', 1, NULL, NULL, NULL, 'PASSWORD', '\0'),
(5, 'testEncuesta2', 'testEncuesta2123', 1, NULL, NULL, NULL, 'PASSWORD', '');

INSERT INTO `sec_permission` (`id_permission`, `permission`, `description`) VALUES
(2, 'ENCUESTAME_ADMIN', 'ENCUESTAME_ADMIN'),
(3, 'ENCUESTAME_OWNER', 'ENCUESTAME_OWNER'),
(4, 'ENCUESTAME_PUBLISHER', 'ENCUESTAME_PUBLISHER'),
(5, 'ENCUESTAME_EDITOR', 'ENCUESTAME_EDITOR'),
(6, 'ENCUESTAME_USER', 'ENCUESTAME_USER');

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

INSERT INTO `sec_groups` (`group_id`, `des_info`, `name`, `id_state`, `secUsers_uid`) VALUES
(1, 'Demo', 'Demo Group', 1, NULL),
(2, 'Favourites', 'Favourites', 1, NULL),
(18, 'sadsadsadassad', 'dasda2131', NULL, 1),
(19, 'dasdsa', 'dsadsa', NULL, 11),
(20, 'dsadsa', 'dsa', NULL, 1);


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

INSERT INTO `questions_pattern` (`pattenr_id`, `class`, `des_qid`, `finallity`, `label_qid`, `level`, `template_patron`, `type_pattern`) VALUES
(59, '', '', 'Example', 'What do you do?', 1, 'radio', 'Yes/No');

INSERT INTO `cat_state` (`id_state`, `desc_state`, `image`) VALUES
(1, 'Enabled', NULL),
(2, 'Disabled', NULL);