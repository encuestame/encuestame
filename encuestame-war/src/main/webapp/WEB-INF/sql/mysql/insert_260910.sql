-- phpMyAdmin SQL Dump
-- version 3.2.3
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Sep 26, 2010 at 11:32 AM
-- Server version: 5.1.41
-- PHP Version: 5.3.2-1ubuntu4.2

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";

--
-- Database: `encuestame_core`
--

--
-- Dumping data for table `cat_emails`
--


--
-- Dumping data for table `cat_list_emails`
--


--
-- Dumping data for table `cat_location`
--

INSERT INTO `cat_location` (`locate_id`, `accuracy`, `address`, `country_code`, `country_name`, `description`, `lat`, `lng`, `location_status`, `catLocationFolder_locate_folder_id`, `secUsers_uid`, `loc_id_type`) VALUES
(6, NULL, NULL, NULL, NULL, 'Default Item Name', NULL, NULL, NULL, 5, NULL, NULL),
(7, 6, '12a Avenida S.E, Managua, Nicaragua', 'NI', 'Nicaragua', 'Managua Centro', 12.1394, -86.2667, 'ACTIVE', 5, 1, NULL),
(8, 4, 'Los Laureles Norte, Managua, Nicaragua', 'NI', 'Nicaragua', 'Laureles Norte', 12.1306, -86.1946, NULL, 5, 1, NULL),
(9, 9, 'Aeropuerto Internacional Managua, C 1, Managua, Nicaragua', 'NI', 'Nicaragua', 'Aeropuerto', 12.1434, -86.174, 'ACTIVE', 5, 1, NULL),
(10, 6, 'NIC 1, Estelí, Nicaragua', 'NI', 'Nicaragua', 'Esteli Centro', 13.0922, -86.356, 'ACTIVE', 2, 1, NULL),
(11, 4, 'San Isidro, Nicaragua', 'NI', 'Nicaragua', 'La Trinidads', 12.9343, -86.198, 'ACTIVE', 2, 1, NULL),
(12, 4, 'San Sebastián de Yalí­, Nicaragua', 'NI', 'Nicaragua', 'San Sebastian de Yalí', 13.3028, -86.1836, 'ACTIVE', 2, 1, NULL),
(13, 6, 'NIC 12, León, Nicaragua', 'NI', 'Nicaragua', 'Leon', 12.4393, -86.8744, 'ACTIVE', 4, 1, NULL),
(14, 2, 'Leon, Nicaragua', 'NI', 'Nicaragua', 'La Paz Centro', 12.3232, -86.6382, 'ACTIVE', 4, 1, NULL),
(15, 6, 'Hacia Estatua Montoya, Managua, Nicaragua', 'NI', 'Nicaragua', 'Managua', 12.1414, -86.2873, 'ACTIVE', 6, 11, NULL),
(16, 6, '13a Avenida S.E, Managua, Nicaragua', 'NI', 'Nicaragua', 'Default Item Name', 12.1434, -86.2626, 'ACTIVE', 6, 11, NULL),
(17, 6, 'Avenida Mariana, Managua, Nicaragua', 'NI', 'Nicaragua', 'Default Item Name', 12.1333, -86.2921, 'ACTIVE', 6, 11, NULL),
(18, NULL, NULL, NULL, NULL, 'Default Item Name', NULL, NULL, 'ACTIVE', 1, 1, NULL),
(19, 4, 'Managua, Nicaragua', 'NI', 'Nicaragua', 'Morazan', 12.1394, -86.2729, 'ACTIVE', 1, 1, NULL),
(20, 6, 'NIC 2, Managua, Nicaragua', 'NI', 'Nicaragua', 'Mercado Oriental', 12.132, -86.3079, 'ACTIVE', 1, 1, NULL),
(21, 6, 'Museo Huellas de Acahualinca, Managua, Nicaragua', 'NI', 'Nicaragua', 'fdsadsa da s', 12.1608, -86.2901, 'ACTIVE', 5, 1, NULL);

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
-- Dumping data for table `cat_subscribe_emails`
--


--
-- Dumping data for table `client`
--


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

--
-- Dumping data for table `notification`
--

INSERT INTO `notification` (`notification_id`, `description`, `uid`, `created`, `readed`, `additional_description`) VALUES
(7, 'TWEETPOL_CREATED', 1, '2010-09-19 16:20:59', '\0', 'Porquen Nicaragua existe tanta corrupcion'),
(8, 'TWEETPOL_CREATED', 1, '2010-09-19 16:20:59', '\0', 'Porquen Nicaragua existe tanta corrupcion'),
(9, 'TWEETPOLL_PUBLISHED', 1, '2010-09-19 16:21:02', '\0', 'http://twitter.com/testEncuesta/status/24971425167'),
(10, 'TWEETPOL_CREATED', 1, '2010-09-19 21:26:49', '\0', 'Cuantos oscar gano slumdog milionare'),
(11, 'TWEETPOL_CREATED', 1, '2010-09-23 18:34:09', '\0', 'ad sa dsa dsa da sd'),
(13, 'TWEETPOLL_PUBLISHED', 1, '2010-09-23 18:34:11', '\0', 'http://twitter.com/testEncuesta/status/25357571199');

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
(25, '4b47642f226bc296472398b577707415', 'da ssds fsfd faf sdas ddsa', '\0', NULL, NULL, 1),
(26, '2c1bb6330c08d418f79bea9545a6fe9d', ' asd fadsf ad fa f dfda', '\0', NULL, NULL, 1),
(27, '00383764c3cb8ceb71567e5b834c28ab', '1111111111 das dsada dsa dsa', '\0', NULL, NULL, 1),
(28, '701c6a07b626a3bd450da0accf453ad1', ' das dsdfsa fda f', '\0', NULL, NULL, 1),
(29, 'b6ef30b8e3a4cae3fd2687fba6d630c1', 'das d fad sd fds fda fds fds dfs', '\0', NULL, NULL, 1),
(30, 'cf91145c6f813a52921ae88b200f2b9d', 'ds ada sdsa d das da dsa das', '\0', NULL, NULL, 1),
(31, '904769600f58074bc1b0cbaafadc08e4', ' das das dsa', '\0', NULL, NULL, 1),
(32, 'f128f2cb93de27206dc731011d480c4f', 'das da sfds f', '\0', NULL, NULL, 1),
(33, '7bcc90d4d893fba822dec1ded241da2d', 'dsa dsa dsa dsa das dgfds  da', '\0', NULL, NULL, 1),
(34, '2449aa7950fb49fdf9be9528bf06a1ae', 'como ves esta pregunta?', '\0', NULL, NULL, 1),
(35, 'e2fef5d4868f191c6fb9477d5d5e96b3', 'dsa dsa dsa dsa ', '\0', NULL, NULL, 1),
(36, 'd244d27b0f86762d914b4bbadf4be413', 'das dsa dsa dsadsa dsa', '\0', NULL, NULL, 1),
(37, '3f2de394ee5541e85e5db0f0f7eea171', ' das das fdas dsfd f sf dfa f', '\0', NULL, NULL, 1),
(38, 'c64cdbf53f88ecce829ac6a2a1f18d88', 'd as das das d', '\0', NULL, NULL, 1),
(39, '9bad85605d8c5014450cb631235b58e8', ' dsa dsa daas das', '\0', NULL, NULL, 1),
(40, 'ed6350e5240c53fe19b3396dc45a56a0', 'dsa da d asdsadsa dsa ', '\0', NULL, NULL, 1),
(41, '55ded7016e68f4680a496a6772e09fbb', ' das da dsa dasa da sdsa', '\0', NULL, NULL, 1),
(42, '97dac8e450ebf048a7ea39a483ba6d31', ' asd da das das dasds', '\0', NULL, NULL, 1),
(43, '564074b90046d7d8c25e0c19739669c8', 'Como se ven los tag de encuestame?', '\0', NULL, NULL, 1),
(44, 'aa6eade0f765c41c9a2232195cb44861', 'dsa dsa ds dsa', '\0', NULL, NULL, 1),
(45, '7bc01ab1d0bd6bed6840b619f9b74c87', ' dsadsa dsa ', '\0', NULL, NULL, 1),
(46, 'bd83d52caa350a57ee86bce92ac44bb0', ' da d da da dsa', '\0', NULL, NULL, 1),
(47, '5e49773ec448851c8cc8dea624634c57', 'saaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa ', '\0', NULL, NULL, 1),
(48, 'd43b120f02c1852809e680408774f617', ' dsa sa dsa', '\0', NULL, NULL, 1),
(49, '50bc3231c9209481a68cd1bc22c76767', ' d ds a sdsa ', '\0', NULL, NULL, 1),
(50, '68e58ce897eb2b431c43c6c653bf57c1', ' dasdsa dsa da', '\0', NULL, NULL, 1),
(51, 'bce248043e69c95efdcd4ed8d0eeb68c', ' dsa dsa dsa dsa das dsa dsa', '\0', NULL, NULL, 1),
(52, 'd18e73c0a8f9aec9077b47b8338eaba9', 'dsa ds das sa das dsa ', '\0', NULL, NULL, 1),
(53, 'ad33dd03950f999651feca222fca85f0', ' dsa dsa dsa dsa dsad', '\0', NULL, NULL, 1),
(54, '1acb71c18f3ab028af68385d8e527de6', 'juan porque?', '\0', NULL, NULL, 1),
(55, 'f24d4d53e37402a7ec6ee5d4caa970fd', 'question juan carlos nicaragua', '\0', NULL, NULL, 1),
(56, 'cda0c8d7b7deafecc79bd2428d407cc1', '11222111 fds fds fd1 ds dsadas dsada dsa dsa fds fds fsd', '\0', NULL, NULL, 1),
(57, '788cade19481bd3a4e1cc027b81ef975', 'dasd dsa da sda sda da d  da d', '\0', NULL, NULL, 1),
(58, '32e4a36043bb8d6e95720859c03fec3e', '111211111111Pregutate que en Nicaragua1111111111?22222222222222222222222233333223', '\0', NULL, NULL, 1),
(59, '6dbe736c35faf2dd213902953a0c79a3', 'dsa dsa dsa ', '\0', NULL, NULL, 1),
(60, 'e589aa981a259efaf23bfe00c23ec735', 'da d dada das da fdsa fa ddas da dasdas', '\0', NULL, NULL, 1),
(61, '25748812b41341523414b8a128a2cfb4', ' das da sa das das ds da d', '\0', NULL, NULL, 1),
(62, 'bcfa244eb45d33bf9207183085d4f490', 'dasdsa dsa as dsads', '\0', NULL, NULL, 1),
(63, 'dc40b8f9a09524d940cd496abe3c4813', 'd as dasfds fa fda da fda ', '\0', NULL, NULL, 1),
(64, '39dbb0fe42979ca1478e65c78db331f1', 'dsa dsa a dsa sad', '\0', NULL, NULL, 1),
(65, '447a74097dd543d2cd5cb40ce0ee42c0', 'juan carlos picado', '\0', NULL, NULL, 1),
(66, 'cbb702514bfcfba8f891eae3082ea2dc', 'dasd asd sa da dsa dsa', '\0', NULL, NULL, 1),
(67, 'ca45c89ff734a53c6bf4bfc804312657', ' dsads sa dsa ', '\0', NULL, NULL, 1),
(68, 'c1fd132c9625d414884c6cd7528cdbc0', ' dsads sa dsa ', '\0', NULL, NULL, 1),
(69, 'bfd2f31457b4f3fd80fbb0986c6004e0', 'dsadsadsadsadas', '\0', NULL, NULL, 1),
(70, 'b1e9f16d3483340dfdd09fc9043719c7', ' dsads sa dsa ', '\0', NULL, NULL, 1),
(71, '1663b5fc917e601963b0cec94051ab81', ' dsa dsa ds dsa dsa', '\0', NULL, NULL, 1),
(72, 'f94a5e9298de0eadec57bc55bf5671dc', 'Quien ganará', '\0', NULL, NULL, 1),
(73, '07c104d1f37aed4a950e3040dfefe423', 'das dsa dsa dsa dsa dsa dsa dsa', '\0', NULL, NULL, 1),
(74, '8c6888d78efbfab41483d4b3481bee00', ' das dsa dsa ds das das dsa da das ', '\0', NULL, NULL, 1),
(75, 'a007f99c49d4a3e80daf91153e2968ba', 'dsadasdsadsadasds', '\0', NULL, NULL, 1),
(76, '1aba71e5532fd9d76a8c63397c993abd', 'dsa dsa dsa dsa da dsa dsa dsa dsa das', '\0', NULL, NULL, 1),
(77, 'ce6b4711441aa77aea9d9b5b854ad24b', 'Porquen Nicaragua existe tanta corrupcion', '\0', NULL, NULL, 1),
(78, '94433f485dc60e35aa298728ea857811', 'Cuantos oscar gano slumdog milionare', '\0', NULL, NULL, 1),
(79, '92dec1ff3f1af1f7635c8e81c064643e', 'ad sa dsa dsa da sd', '\0', NULL, NULL, 1);

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
(51, ' dsa fas fda fda fda', 3, '497c57975233e5d98345c989c6b75425', 25, 'http://tinyurl.com/2caklmb'),
(52, ' fafd afd af  da fda ', 3, '1dee17cc8feb055e9b42780c336403a3', 26, 'http://tinyurl.com/2vqy3kf'),
(53, ' fda f f f fa fda fd', 3, 'f23740e7abacdec6b7a68891a2bc58f8', 26, 'http://tinyurl.com/33nntgy'),
(54, ' fsd dsa d ad dsa da', 3, '45dbb2fee42cb50806693be2e254cad1', 27, 'http://tinyurl.com/27ty6ba'),
(55, ' dsa das da sdas ', 3, '83183c6f42fc7e7755f8689312181162', 27, 'http://tinyurl.com/263me9a'),
(56, ' das das das ', 3, '947c9e8217d62c04d3976b1dfa32af5c', 28, 'http://tinyurl.com/32yzy63'),
(57, ' das das das ', 3, 'f4d6354b5fd7dea63de8cbf77f96825d', 28, 'http://tinyurl.com/3xsl6fp'),
(58, ' das das da  fsd fds fds fds', 3, '1755335126f6a809c273c20bb73d2df3', 29, 'http://tinyurl.com/29eolp3'),
(59, ' dsa das das das', 3, '350ed89375eb57c7e0197af7278f24f7', 29, 'http://tinyurl.com/2a39svf'),
(60, 'd sa das das das das das', 3, '98d7af592bacc2dfaeb511b69f8ef33b', 30, 'http://tinyurl.com/2eka2oq'),
(61, ' das das da sdas ', 3, '602774cec80f8e4415cd49a22c3c5b19', 30, 'http://tinyurl.com/2dhuvzj'),
(62, ' fds fds fds fds', 3, 'ed7973371ad858a75465ebf18e44f98a', 31, 'http://tinyurl.com/2ev9hh8'),
(63, ' fds fsd fds f dsfsd ', 3, 'cdad50e61c02b6480bd5c2eced38f68e', 31, 'http://tinyurl.com/25tea6x'),
(64, ' dsa d fds sa dsa', 3, 'd28053306b28d83e6e691a9038d68909', 32, 'http://tinyurl.com/2almj2m'),
(65, ' dsa das das das da sdas ', 3, '7f55910805444845371b9e3c2d922e49', 32, 'http://tinyurl.com/28f5vv6'),
(66, 'dsa dsa dsa dsa das', 3, 'bc4b601bcf8751e72447876f91940261', 33, 'http://tinyurl.com/2dbg62x'),
(67, 'ds ada sdsa da sds', 3, '8a6cc724e72bb6a6e9a0f423d3a3ea0a', 33, 'http://tinyurl.com/2535a3m'),
(68, 'yes', 3, '5e274ad4cfd01ec31c20af9476226e97', 34, 'http://tinyurl.com/2d7sauo'),
(69, 'no', 3, 'dc73c729ca17570a8d7ee9cfb4cdde9e', 34, 'http://tinyurl.com/26hqqx5'),
(70, 'd asd as da', 3, '63a601ccbbfaeac8ef868a9058aab597', 35, 'http://tinyurl.com/36cspw8'),
(71, ' dsa dsa dsa', 3, 'd1e6e5431cd2bc14e89aa7893028ffb9', 35, 'http://tinyurl.com/32ms6j2'),
(72, ' fds fds fds', 3, '760989285cedfc1b5db2c78a0cc90441', 36, 'http://tinyurl.com/24pfogk'),
(73, ' fds fds fds f', 3, 'caa90f13a07ad5b19798222201245651', 36, 'http://tinyurl.com/2dxqkev'),
(74, 'fds', 3, 'c7944bf6fd0108d9d394c625f29dce28', 36, 'http://tinyurl.com/2dusmbw'),
(75, ' dsa da', 3, '2a9ec4821f912efc65e4f1d1f400b10d', 37, 'http://tinyurl.com/3akuw38'),
(76, ' dsa dsa das da', 3, '83120762ca1991590c955aebb4ebbe3f', 37, 'http://tinyurl.com/325sk6a'),
(77, 'd sa da a', 3, 'b21f54976f25edf11f1eef08970f83a0', 38, 'http://tinyurl.com/26ld7ur'),
(78, ' das das', 3, 'b0261c70511aa143fe1393b979a2b597', 38, 'http://tinyurl.com/2cecnvc'),
(79, ' das das', 3, 'd6a557d385bbf5d29e130ef1389667ec', 39, 'http://tinyurl.com/33aqhon'),
(80, ' das das', 3, '5e238bc00751ea29a6299cd65aa97713', 39, 'http://tinyurl.com/3xsf4qu'),
(81, ' dsa das dsa das', 3, '0537a7f4452c8e9797fc57a9c6e521fe', 40, 'http://tinyurl.com/26ng4cb'),
(82, 'yes', 3, 'cfcfc3dfc7965f9f51f0fa9c5f5ef065', 41, 'http://tinyurl.com/24cugxa'),
(83, 'no', 3, 'a2b7164b6c2a959d021b04facb9a6e87', 41, 'http://tinyurl.com/267yjrc'),
(84, 'españa', 3, '28951c81e485cf4bb344a3641afc7e39', 42, 'http://tinyurl.com/284hvko'),
(85, 'tuanis', 3, '7abcafdf5ad2b6dabf414142ec965965', 43, 'http://tinyurl.com/26lcjht'),
(86, 'no me gusta', 3, 'd7aa88309e81b6d8218ec51c23cd6680', 43, 'http://tinyurl.com/2a3t5fa'),
(87, ' dsa ', 3, '97c464b5e88252df50ac4fbbf6f43f03', 44, 'http://tinyurl.com/28cxa37'),
(88, ' dsa das', 3, '4f3c7549afe2cacccb862fddc28661e6', 44, 'http://tinyurl.com/345yb8a'),
(89, ' dsa dsa dsa', 3, '4ba4b89cca1c30d332ba5d3978759955', 45, 'http://tinyurl.com/253e4nn'),
(90, ' das dsa ', 3, '646bb93b2d6749ef6017a121ff61e702', 45, 'http://tinyurl.com/26klrak'),
(91, ' dsa das', 3, 'a01f503f0405cd1e5384a97c48dca7c1', 45, 'http://tinyurl.com/24zj3j9'),
(92, ' dsa das ', 3, '8ea066b7cc5432d787b987b041e331d4', 46, 'http://tinyurl.com/2b9ugoe'),
(93, ' dsa das ', 3, '4a9363aaad8345259fd9c432509645d1', 46, 'http://tinyurl.com/2fjdxg9'),
(94, 'aaaaaaaaa das d', 3, 'b471db8f2d73adc0d5dc9d8413d8df6a', 47, 'http://tinyurl.com/2el96j3'),
(95, ' das dsa dsa', 3, '57dececa8a9c92609045c4de0dede0f2', 47, 'http://tinyurl.com/24wwyt6'),
(96, ' dsa das', 3, '957de6410a5dda532bd4875249f24241', 49, 'http://tinyurl.com/29s3vfz'),
(97, ' das dsa das dsa', 3, '3b5c0b3606778ece1a2f2f809c5d2b93', 50, 'http://tinyurl.com/39d958r'),
(98, ' das dsa d', 3, '8bac48a57ae6fe0a3fda57def3bd9c55', 50, 'http://tinyurl.com/37rwr82'),
(99, '', 3, '290cfbf928f91589bc715affc8e6152b', 51, 'http://tinyurl.com/283a7ko'),
(100, 'dsadsa dsa dsa ', 3, '5e1e1c55c21e927ff8538f9e04acd875', 51, 'http://tinyurl.com/29w6n6s'),
(101, ' das da sda sd', 3, '93a02fec5a11f7c798dc2260b519a3e2', 52, 'http://tinyurl.com/398ngrk'),
(102, ' dsa dsa  dsa da', 3, 'e9902ff05b61987f196b171987af380c', 52, 'http://tinyurl.com/2vnba7m'),
(103, ' dsa das das ', 3, '5af631da44df5d2ba0576cd930ed2a1a', 53, 'http://tinyurl.com/24p8dac'),
(104, ' dsa dsa ', 3, 'da8d74510a30cbd9e5fc77842b9324bc', 53, 'http://tinyurl.com/28o3pgs'),
(105, 'dsa da sdas dsa d', 3, '223d1cea9650cc2e51fffb0d6ef47d53', 54, 'http://tinyurl.com/2bllluw'),
(106, ' dsa dsa dsa', 3, 'f655c496a7155ba82c525558f70e5ba3', 54, 'http://tinyurl.com/2brru36'),
(107, 'sdadsa dsa dsa dsa', 3, 'eace9ab528460b711732d8fbc7b3a7b2', 55, 'http://tinyurl.com/2ukw4pp'),
(108, ' dsa', 3, '3a4e8dc9cf51bb165fe392dd6b476200', 55, 'http://tinyurl.com/3afgg59'),
(109, ' dsa dsa ', 3, '42004229c83e0418d58a69121ca3d7ed', 56, 'http://tinyurl.com/24trrop'),
(110, 'a dsa das', 3, '141238989ebbc0556e0feb597c86f323', 56, 'http://tinyurl.com/25dl7fb'),
(111, ' das dsa da sdas', 3, '743475f1d2e33e7d4b995d673e42832c', 57, 'http://tinyurl.com/35bptyx'),
(112, ' dsa das d', 3, '0f443c22faedf0030a90608eb538d8bd', 57, 'http://tinyurl.com/3xe335e'),
(113, 'yes', 3, '5633c62615e64d42e2a086ef04fb09ab', 58, 'http://tinyurl.com/2eutgql'),
(114, 'no', 3, '3e90e5e9bf5c409d0a315b16150ef856', 58, 'http://tinyurl.com/2b7sudf'),
(115, ' dsa dsa ', 3, '174ee97f4a2b06768d82268ddb16544a', 59, 'http://tinyurl.com/37zuztt'),
(116, ' dsa dsa ', 3, '900f462f7bf06f1b4d4f7fb82c70189b', 59, 'http://tinyurl.com/378aebh'),
(117, ' das dsa', 3, '3d08c35bf0f8f6b53e0649f21a7f31a0', 60, 'http://tinyurl.com/28utg9o'),
(118, ' da', 3, '4b4f2d511e24305df0b1a623d67348dc', 60, 'http://tinyurl.com/275ho7o'),
(119, ' dsa das d', 3, 'a6276c457cfc4329149f3e2e973d323e', 61, 'http://tinyurl.com/36x7nql'),
(120, ' das das', 3, '3d985d68aa7679d5ddb681cd8a0a51ef', 61, 'http://tinyurl.com/2u37wa2'),
(121, ' das da', 3, 'a7ed3f0906942cd0c07e9577c8c9433f', 62, 'http://tinyurl.com/36fbrae'),
(122, ' dsa dsa', 3, 'dd794480fd776b55864757e95d60e869', 62, 'http://tinyurl.com/38dxllk'),
(123, ' das das', 3, 'e133d7fff707e2604e4b7e9268f9a8b4', 63, 'http://tinyurl.com/23n4czo'),
(124, ' das das', 3, '808e1f7d6e5f35fdeef3c7a3f620e1b3', 63, 'http://tinyurl.com/23vw5cy'),
(125, 'a das dsa', 3, 'b7489a88e019e369778fc700732aec07', 64, 'http://tinyurl.com/2aop6ns'),
(126, ' das dsa', 3, '147dfe13bb625e9ccf88fe15c2d85588', 64, 'http://tinyurl.com/28pbxkk'),
(127, ' dsa dsa dsa', 3, '7308c45da3291373edd94871b64296b0', 65, 'http://tinyurl.com/27dcg6u'),
(128, ' dsa das das', 3, '3cacddc37b277246858628571cffb245', 65, 'http://tinyurl.com/2eqdmx9'),
(129, ' dsa dsa dsa', 3, 'eaaa54ab47f26986009c0b2a893b2926', 66, 'http://tinyurl.com/24z5jc2'),
(130, ' dsa dsa', 3, '4342008e41134701733946ea05407e8f', 66, 'http://tinyurl.com/2774qtj'),
(131, ' dsa dsa', 3, '0569657949d21b65b053c0974c375dde', 67, 'http://tinyurl.com/29aqtf3'),
(132, ' dsa dsa', 3, '0569657949d21b65b053c0974c375dde', 68, 'http://tinyurl.com/29aqtf3'),
(133, 'dasdsa', 3, 'c0c8c41f20a9a5726d5a8cc8dd1a1381', 69, 'http://tinyurl.com/2fprano'),
(134, 'd', 3, '063680a78a9deedfe28d032095c0caf5', 69, 'http://tinyurl.com/2452wuj'),
(135, ' dsa dsa', 3, '0569657949d21b65b053c0974c375dde', 70, 'http://tinyurl.com/29aqtf3'),
(136, 'a dsa dsa ', 3, '47616983c52c1592c8426d742291be10', 71, 'http://tinyurl.com/2e2v5nh'),
(137, ' dsa ', 3, '4a6db392c2f3418f997a1873fbc65eab', 71, 'http://tinyurl.com/233e4y3'),
(138, 'Real Madrid', 3, 'fc5e4c42db20c333344cb1b174e5c4c3', 72, 'http://tinyurl.com/24f9hvg'),
(139, 'Real Sociedad', 3, 'fb0a64a1dfa062208368723c52590438', 72, 'http://tinyurl.com/2ah3qpb'),
(140, ' dsa ds dsa dsa dsa', 3, '74f53c7a7a80086f888b29c42f3d1388', 73, 'http://tinyurl.com/2b29cqw'),
(141, ' das dsa dsa', 3, '4ffecf2ffc4ae91f052b9c3f0117e0c9', 73, 'http://tinyurl.com/26g2ucq'),
(142, ' das das dsa d', 3, '30c4eefa292d119ad31bd37d92f15c11', 74, 'http://tinyurl.com/23tdvgx'),
(143, ' da das', 3, '98affd4dc1ed5eaa27f7ca66fa4a8e51', 74, 'http://tinyurl.com/25cuuon'),
(144, 'dsadsadsadsadsa', 3, '5f2ff60ca6bb04be7ef36d818c51c68c', 75, 'http://tinyurl.com/2daqgwr'),
(145, ' dsa dsa dsa da', 3, '5863b8c489210686307b2eebd880a617', 76, 'http://tinyurl.com/24zs64w'),
(146, 'dsa', 3, '4049bd2022eec78b3869580306933f01', 76, 'http://tinyurl.com/267m7ms'),
(147, ' dsa ds', 3, '8f629c86b11f48818bd9c422a7e049fc', 77, 'http://tinyurl.com/25b3quh'),
(148, ' dsa d', 3, '70e03825efb1385a852c5d33da0015a8', 77, 'http://tinyurl.com/2f22g8h'),
(149, '1', 3, 'ff096b79d948d0fe5f7d118b565edaaf', 78, 'http://tinyurl.com/2eatrko'),
(150, '2', 3, '451161e2e0fce42ed947c54ea9853a2d', 78, 'http://tinyurl.com/23fqmbf'),
(151, '3', 3, 'faa539aaaace922a085cab6c70e0d940', 78, 'http://tinyurl.com/24z5qhm'),
(152, 'as  dsa dsa d', 3, '123484b158932e88acbbfcc78788e9b1', 79, 'http://tinyurl.com/2ev47dn'),
(153, ' das dsa', 3, '3a310e7329a2792b3dac3d1abeeaad4d', 79, 'http://tinyurl.com/22or54u');

--
-- Dumping data for table `questions_dependencies`
--


--
-- Dumping data for table `questions_pattern`
--

INSERT INTO `questions_pattern` (`pattenr_id`, `class`, `des_qid`, `finallity`, `label_qid`, `level`, `template_patron`, `type_pattern`) VALUES
(59, '', '', 'Example', 'What do you do?', 1, 'radio', 'Yes/No');

--
-- Dumping data for table `question_collection`
--


--
-- Dumping data for table `question_dependence_survey`
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
(18, 'sadsadsadassad2222', 'dasda21312', NULL, 1),
(19, 'dasdsa', 'dsadsa', NULL, 11),
(20, 'dsadsa33 da', 'dsa dsa ', NULL, 1),
(21, 'dsadas', 'dsadsa', NULL, 1);

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
(12, NULL, NULL, NULL, NULL, NULL),
(13, NULL, NULL, NULL, NULL, NULL),
(14, NULL, NULL, NULL, NULL, NULL),
(15, NULL, NULL, NULL, NULL, NULL);

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
(13, 2),
(14, 2),
(15, 2),
(1, 3),
(2, 3),
(3, 3),
(4, 3),
(5, 3),
(8, 3),
(10, 3),
(11, 3),
(12, 3),
(13, 3),
(14, 3),
(15, 3),
(1, 4),
(2, 4),
(3, 4),
(4, 4),
(5, 4),
(8, 4),
(10, 4),
(11, 4),
(12, 4),
(13, 4),
(14, 4),
(15, 4),
(1, 5),
(2, 5),
(3, 5),
(4, 5),
(5, 5),
(8, 5),
(10, 5),
(11, 5),
(12, 5),
(13, 5),
(14, 5),
(15, 5),
(1, 6),
(13, 6),
(14, 6),
(15, 6),
(17, 6),
(18, 6);

--
-- Dumping data for table `sec_user_secondary`
--

INSERT INTO `sec_user_secondary` (`uid`, `name`, `date_new`, `invite_code`, `password`, `email`, `status`, `twitter`, `username`, `secUser_uid`, `last_ip_logged`, `last_time_logged`) VALUES
(1, 'Juan Carlos Picado', '2010-01-20 12:47:40', '', '6xAX8siGWDJXfkJUVxWLqsk0rz8U+aG6Y8yA1IokxuhEIZ8+RugleJtLUYbdGxc+', 'juanpicado19@gmail.com', '', NULL, 'admin', 1, NULL, '2010-09-26 10:07:25'),
(2, '', '2010-06-19 07:56:02', '', 'pDdSocopH0ny7CpmCKJMu19guKthRWyN751c6H2IYnUfjC+tUtAO87pap39B6622', 'juan@jotadeveloper.com', '', NULL, 'jota', 2, NULL, NULL),
(3, '', '2010-06-19 08:19:44', '', 'pKfJHPrLGCUbak2yav0oSvNMYDt3dSDUN8ivYLkCWshhTJtKySHAWY9RXFlVr0L5', 'info@jotadeveloper.comsssssss', '', NULL, 'sadasdasdsa', 3, NULL, NULL),
(4, '', '2010-06-19 08:21:38', '', '3wi3Oy6i9zT2zLTD5ewWqHCG5xpPcwSj/xSpSrLMAgXY3wLVidiV3cKkpbfAP7ar', '2juanpicado19@gmail.com', '', NULL, 'dsadsadkajdlksa', 4, NULL, NULL),
(5, '', '2010-06-19 08:49:33', '', 'Th04R8bHe37/E6ugUpaJZokmV7MIpKAsY4fqgolmhcjosfWTCKbZ3mjeRWcFhRWz', 'jotadeveloper@gmail.com', '', NULL, 'jotadasdsa', 5, NULL, NULL),
(6, '', '2010-06-19 09:15:39', '', 'CnYJswTOBkrF9vU4kY7PvEbA5PFJf1gKdWwGSOIaUFm3ZdD2m84reYp6Lmfl84VM', 'paola@jotadeveloper.com', '', NULL, 'sadasdsakjdsal', 6, NULL, NULL),
(7, '', '2010-06-19 09:17:02', '', 'xqHe0RpeBz/3NA3jnc99Il4/tylQDcWWL+JF3jaDl+aB8bPI1A+3u7BGd23w6NaE', 'paola@encuestame.org', '', NULL, 'paolaaaaaaa', 7, NULL, NULL),
(8, '', '2010-06-19 09:33:34', '', 'scAnsHAW8LgYwVb4AnGGMA4hZsw/ZVMr6MwxsxSh2kPVdL4ArRv16x7aVWiurAps', 'juanpicado19@gmail.comssa', '', NULL, 'josajodasjodas', 8, NULL, NULL),
(10, '', '2010-06-19 09:45:53', '', 'lKTk7hgn57Gj1dPulC4os5hTqpwUMJh1jspj335H6a6mGN77aj85/ojJFbLfWYvI', 'sdadsa', '', NULL, 'dasdasdsad', 10, NULL, NULL),
(11, '', '2010-06-19 09:56:36', '', 'ogae9Ih7UdJMS9+mSL2/62bRCYItdm0iRUSHzSCxB3RdxrXs+QvG7FFphELh/XDZ', 'juanpicado19@gmail.comdasdas', '', NULL, 'dasdsa2131', 11, NULL, NULL),
(12, '', '2010-06-26 08:27:30', '', '5JzT+UZRKdRHKD251b1tcezb/qGXLcmXPEvgEiLnXskYd48QmrLye0tMoU3sJaEw', 'webmaster@encuestame.org', '', NULL, 'juancarlospicado', 12, NULL, '2010-06-26 08:27:34'),
(13, '', '2010-07-30 23:04:14', '', 'KZxzPvQOvVogiitHaAerEgDLdR21ieKFAfToaqd/aXkvjWpf0woLj/c7oaCUxmn9', 'info@jotadeveloper.com', '', NULL, 'jotadeveloper232', 13, NULL, '2010-07-30 23:04:18'),
(14, '', '2010-07-30 23:24:53', '', 'naSxZR7mepyl6/GgUKXgTgO8X6BkgK36RGDYOImd4N1DkWFf76mY3LHMNnMDVReV', 'asadsa@das.com', '', NULL, 'dsadasdasd', 14, NULL, '2010-07-30 23:24:57'),
(15, '', '2010-07-30 23:33:46', '', 'Y3WnSNL+PlqO80NnmCyXlwwiV4prM5h4cwfP5erAhSunYrE9QLji12iE695qRzgM', 'dadas@ldas.com', '', NULL, 'dasdasdas', 15, NULL, NULL),
(17, '', '2010-08-01 17:58:45', NULL, 'kYZb+KGCsnYvSvtPV5d6enX37M2FeWruGhTr+mizoRIT+PJ5aoDwyRoOLqRNRGXo', 'juanpicado2003@yahoo.es', '', NULL, 'shap22', 1, NULL, NULL),
(18, '', '2010-08-01 17:59:11', NULL, 'paeaVMXI8AovgnKQP2zSmVZV8pGoQVSr+hIgSEb8zFj1VJ1ippPz5HV4PKUy42Jx', 'dianmorales@gmail.com', '', NULL, 'dmorales', 1, NULL, NULL);

--
-- Dumping data for table `sec_user_twitter_account`
--

INSERT INTO `sec_user_twitter_account` (`sec_user_twitter_id`, `twitter_account`, `twitter_password`, `secUsers_uid`, `twitter_consumer_key`, `twitter_consumer_secret`, `twitter_pin`, `type_auth`, `twitter_verified`, `twitter_token`, `twitter_secret_token`) VALUES
(1, 'testEncuesta', 'testEncuesta123', 1, 'nFboU4T1Zhv8cqMC4cP0ug', 'GwOPUEJEaCbNBiBzq6J8StDhb7FOmwDcjfX6zMe0', 1393800, 'OAUTH', '', '114054787-1AqKm4jQRRCYSpKzXqyMfC3ZGHcylvTM9ldf7zJ0', 'dSbWWsDrGEv8qKLT2JlPkkvHTTyRL6rh56KcpArVh0'),
(4, 'dasdas', '', 1, NULL, NULL, NULL, 'PASSWORD', '\0', NULL, NULL),
(5, 'testEncuesta2', 'testEncuesta2123', 1, NULL, NULL, NULL, 'PASSWORD', '', NULL, NULL),
(6, 'dsadas', '', 1, NULL, NULL, NULL, 'PASSWORD', '\0', NULL, NULL),
(7, 'dsadas', '', 1, NULL, NULL, NULL, 'PASSWORD', '\0', NULL, NULL),
(8, 'dsadas dsa ', '', 1, NULL, NULL, NULL, 'PASSWORD', '\0', NULL, NULL),
(9, 'dsadas dsa  dsa', '', 1, NULL, NULL, NULL, 'PASSWORD', '\0', NULL, NULL),
(10, 'dsadas dsa  dsa dsa ', '', 1, NULL, NULL, NULL, 'PASSWORD', '\0', NULL, NULL);

--
-- Dumping data for table `surveys`
--


--
-- Dumping data for table `survey_folder`
--


--
-- Dumping data for table `survey_folders`
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
-- Dumping data for table `survey_pagination`
--


--
-- Dumping data for table `survey_result`
--


--
-- Dumping data for table `survey_section`
--


--
-- Dumping data for table `survey_section_questions`
--


--
-- Dumping data for table `tweetPoll`
--

INSERT INTO `tweetPoll` (`tweet_poll_id`, `allow_live_results`, `close_notification`, `completed`, `publication_date_tweet`, `publish`, `result_notification`, `schedule`, `qid`, `uid`, `captcha`, `limit_votes`, `resume_live_results`, `schedule_date_tweet`, `allow_repeated_votes`, `create_date`, `enabled`) VALUES
(1, '', '\0', '\0', NULL, '', '\0', '\0', 30, 1, '', 28767, '\0', NULL, NULL, NULL, '\0'),
(2, '', '\0', '\0', NULL, '', '\0', '\0', 31, 1, '', 6507, '\0', NULL, NULL, NULL, '\0'),
(3, '', '\0', '\0', NULL, '', '\0', '\0', 32, 1, '', 25000, '\0', NULL, NULL, NULL, '\0'),
(4, '', '\0', '\0', NULL, '', '\0', '\0', 33, 1, '', 19521, '\0', NULL, NULL, NULL, '\0'),
(5, '\0', '\0', '\0', NULL, '', '\0', '\0', 34, 1, '\0', 100, '\0', NULL, NULL, NULL, '\0'),
(6, '\0', '\0', '\0', NULL, '', '\0', '\0', 35, 1, '\0', 100, '\0', NULL, NULL, NULL, '\0'),
(7, '', '\0', '\0', NULL, '', '\0', '\0', 36, 1, '', 10274, '\0', NULL, NULL, NULL, '\0'),
(8, '\0', '\0', '\0', NULL, '', '\0', '\0', 37, 1, '\0', 100, '\0', NULL, NULL, NULL, '\0'),
(9, '\0', '\0', '\0', NULL, '', '\0', '\0', 38, 1, '\0', 100, '\0', NULL, NULL, NULL, '\0'),
(10, '\0', '\0', '\0', NULL, '', '\0', '\0', 39, 1, '\0', 100, '\0', NULL, NULL, NULL, '\0'),
(11, '\0', '\0', '\0', NULL, '', '\0', '\0', 40, 1, '\0', 100, '\0', NULL, NULL, NULL, '\0'),
(12, '\0', '\0', '\0', NULL, '', '\0', '\0', 41, 1, '\0', 100, '\0', NULL, NULL, NULL, '\0'),
(13, '\0', '\0', '\0', NULL, '', '\0', '\0', 42, 1, '\0', 100, '\0', NULL, NULL, NULL, '\0'),
(14, '\0', '\0', '\0', NULL, '', '\0', '\0', 43, 1, '\0', 100, '\0', NULL, NULL, NULL, '\0'),
(15, '\0', '\0', '\0', NULL, '', '\0', '\0', 44, 1, '\0', 100, '\0', NULL, NULL, NULL, '\0'),
(16, '\0', '\0', '\0', NULL, '\0', '\0', '\0', 45, 1, '\0', 100, '\0', NULL, NULL, NULL, '\0'),
(17, '\0', '\0', '\0', NULL, '\0', '\0', '\0', 46, 1, '\0', 100, '\0', NULL, NULL, NULL, '\0'),
(18, '', '\0', '\0', NULL, '', '\0', '\0', 47, 1, '', 19173, '\0', NULL, NULL, NULL, '\0'),
(19, '\0', '\0', '\0', NULL, '\0', '\0', '\0', 48, 1, '\0', 100, '\0', NULL, NULL, NULL, '\0'),
(20, '\0', '\0', '\0', NULL, '\0', '\0', '\0', 49, 1, '\0', 100, '\0', NULL, NULL, NULL, '\0'),
(21, '', '\0', '\0', NULL, '', '\0', '', 50, 1, '', 100, '\0', NULL, NULL, NULL, '\0'),
(22, '\0', '\0', '\0', NULL, '', '\0', '\0', 51, 1, '\0', 100, '\0', NULL, NULL, NULL, '\0'),
(23, '\0', '\0', '\0', NULL, '', '\0', '\0', 52, 1, '', 100, '\0', NULL, NULL, NULL, '\0'),
(24, '\0', '\0', '\0', NULL, '', '\0', '\0', 53, 1, '\0', 100, '\0', NULL, NULL, '2010-08-01 18:33:56', '\0'),
(25, '\0', '\0', '\0', NULL, '', '\0', '\0', 54, 1, '\0', 100, '\0', NULL, NULL, '2010-08-01 18:36:18', '\0'),
(26, '\0', '\0', '\0', NULL, '', '\0', '\0', 55, 1, '\0', 100, '\0', NULL, NULL, '2010-08-07 16:01:34', ''),
(27, '\0', '\0', '\0', NULL, '', '\0', '\0', 56, 1, '\0', 100, '\0', NULL, NULL, '2010-08-07 16:13:08', ''),
(28, '\0', '\0', '\0', NULL, '', '\0', '\0', 57, 1, '\0', 100, '\0', NULL, NULL, '2010-08-08 15:21:15', ''),
(29, '\0', '\0', '\0', NULL, '', '\0', '\0', 58, 1, '\0', 100, '\0', NULL, NULL, '2010-08-08 15:22:17', '\0'),
(30, '', '\0', '\0', NULL, '', '\0', '\0', 59, 1, '', 100, '\0', NULL, NULL, '2010-08-20 18:19:22', '\0'),
(31, '\0', '\0', '\0', NULL, '', '\0', '\0', 60, 1, '\0', 100, '\0', NULL, NULL, '2010-09-05 19:29:31', '\0'),
(32, '\0', '\0', '\0', NULL, '', '\0', '\0', 61, 1, '\0', 100, '\0', NULL, NULL, '2010-09-05 21:42:01', '\0'),
(33, '\0', '\0', '\0', NULL, '', '\0', '\0', 62, 1, '\0', 100, '\0', NULL, NULL, '2010-09-05 21:59:58', ''),
(34, '\0', '\0', '\0', NULL, '', '\0', '\0', 63, 1, '\0', 100, '\0', NULL, NULL, '2010-09-05 22:47:31', ''),
(35, '\0', '\0', '\0', NULL, '', '\0', '\0', 64, 1, '\0', 100, '\0', NULL, NULL, '2010-09-05 23:05:56', ''),
(36, '\0', '\0', '\0', NULL, '', '\0', '\0', 65, 1, '\0', 100, '\0', NULL, NULL, '2010-09-05 23:15:31', ''),
(37, '', '\0', '\0', NULL, '', '\0', '', 66, 1, '', 100, '\0', '2010-09-20 12:00:00', NULL, '2010-09-18 10:34:54', ''),
(38, '\0', '\0', '\0', NULL, '', '\0', '\0', 67, 1, '\0', 100, '\0', NULL, NULL, '2010-09-18 16:20:48', ''),
(39, '', '\0', '\0', NULL, '', '\0', '', 68, 1, '', 17143, '\0', '2010-09-26 12:00:00', NULL, '2010-09-18 16:39:41', ''),
(40, '', '\0', '\0', NULL, '', '\0', '', 69, 1, '', 28788, '\0', '2010-09-27 12:00:00', NULL, '2010-09-18 16:45:31', ''),
(41, '', '\0', '\0', NULL, '', '\0', '', 70, 1, '', 17143, '\0', '2010-09-26 12:00:00', NULL, '2010-09-18 17:07:06', ''),
(42, '', '\0', '\0', NULL, '', '\0', '\0', 71, 1, '\0', 100, '\0', NULL, NULL, '2010-09-18 17:07:52', ''),
(43, '\0', '\0', '\0', NULL, '', '\0', '\0', 72, 1, '\0', 100, '\0', NULL, NULL, '2010-09-18 17:42:50', ''),
(44, '\0', '\0', '\0', NULL, '', '\0', '\0', 73, 1, '\0', 100, '\0', NULL, NULL, '2010-09-18 19:33:02', ''),
(45, '\0', '\0', '\0', NULL, '', '\0', '\0', 74, 1, '\0', 100, '\0', NULL, NULL, '2010-09-18 22:09:23', ''),
(46, '\0', '\0', '\0', NULL, '', '\0', '\0', 75, 1, '\0', 100, '\0', NULL, NULL, '2010-09-18 23:41:19', ''),
(47, '\0', '\0', '\0', NULL, '', '\0', '\0', 76, 1, '\0', 100, '\0', NULL, NULL, '2010-09-19 15:52:42', ''),
(48, '\0', '\0', '\0', NULL, '', '\0', '\0', 77, 1, '\0', 100, '\0', NULL, NULL, '2010-09-19 16:20:59', ''),
(49, '\0', '\0', '\0', NULL, '', '\0', '\0', 78, 1, '', 12381, '\0', NULL, NULL, '2010-09-19 21:26:49', ''),
(50, '\0', '\0', '\0', NULL, '', '\0', '\0', 79, 1, '\0', 100, '\0', NULL, NULL, '2010-09-23 18:34:08', '');

--
-- Dumping data for table `tweetPoll_hash_tags`
--

INSERT INTO `tweetPoll_hash_tags` (`tweetPoll_tweet_poll_id`, `hashTags_hash_tag_id`) VALUES
(9, 1),
(9, 2),
(9, 3),
(10, 1),
(10, 2),
(10, 3),
(11, 4),
(11, 5),
(11, 6),
(11, 7),
(11, 8),
(11, 9),
(11, 10),
(12, 8),
(12, 9),
(12, 10),
(12, 11),
(12, 12),
(13, 8),
(13, 9),
(13, 10),
(13, 11),
(13, 12),
(14, 13),
(14, 14),
(14, 15),
(14, 16),
(15, 13),
(15, 17),
(15, 18),
(15, 19),
(15, 20),
(16, 21),
(17, 13),
(17, 20),
(18, 22),
(20, 7),
(20, 23),
(21, 19),
(21, 24),
(21, 25),
(21, 26),
(22, 7),
(22, 25),
(23, 27),
(23, 28),
(24, 29),
(24, 30),
(25, 24),
(26, 5),
(26, 30),
(27, 31),
(27, 32),
(27, 33),
(28, 20),
(28, 25),
(29, 34),
(29, 35),
(29, 36),
(29, 37),
(29, 38),
(30, 7),
(30, 39),
(31, 17),
(31, 20),
(32, 17),
(33, 7),
(33, 20),
(33, 40),
(33, 41),
(33, 42),
(34, 15),
(34, 25),
(34, 43),
(35, 7),
(35, 13),
(35, 24),
(35, 44),
(36, 25),
(36, 41),
(36, 44),
(36, 45),
(37, 7),
(37, 25),
(37, 40),
(40, 7),
(40, 46),
(42, 7),
(43, 47),
(43, 48),
(43, 49),
(43, 50),
(44, 6),
(45, 13),
(45, 20),
(45, 23),
(46, 7),
(46, 13),
(46, 30),
(47, 7),
(47, 21),
(48, 13),
(48, 17),
(49, 51),
(50, 13),
(50, 14),
(50, 19),
(50, 21);

--
-- Dumping data for table `tweetpoll_result`
--

INSERT INTO `tweetpoll_result` (`tweetpoll_resultId`, `ip_vote`, `tweet_date_response`, `tweetpoll_switch_id`) VALUES
(6, '192.168.1.101', '2010-07-23 23:38:37', 9),
(7, '127.0.0.1', '2010-07-25 12:55:53', 11),
(8, '192.168.1.101', '2010-07-25 12:57:37', 11),
(9, '127.0.0.1', '2010-09-13 18:38:34', 10),
(10, '127.0.0.1', '2010-09-13 18:38:34', 10);

--
-- Dumping data for table `tweetPoll_save_published_status`
--

INSERT INTO `tweetPoll_save_published_status` (`status_save_poll_id`, `type`, `status_description`, `publication_date_tweet`, `status`, `tweet_id`, `tweetPoll_tweet_poll_id`, `twitterAccount_sec_user_twitter_id`) VALUES
(1, 'TWITTER', NULL, '2010-07-19 16:16:46', 'SUCCESS', 18947142381, 1, 1),
(2, 'TWITTER', NULL, '2010-07-19 16:16:50', 'SUCCESS', 18947145573, 1, 5),
(3, 'TWITTER', NULL, '2010-07-19 16:40:29', 'SUCCESS', 18948390955, 2, 1),
(4, 'TWITTER', NULL, '2010-07-19 16:40:30', 'SUCCESS', 18948391708, 2, 5),
(5, 'TWITTER', NULL, '2010-07-19 17:33:51', 'SUCCESS', 18951308377, 3, 1),
(6, 'TWITTER', NULL, '2010-07-19 17:33:52', 'SUCCESS', 18951309169, 3, 5),
(7, 'TWITTER', NULL, '2010-07-21 01:45:07', 'SUCCESS', 19059789231, 4, 1),
(8, 'TWITTER', NULL, '2010-07-21 01:45:09', 'SUCCESS', 19059790215, 4, 5),
(9, 'TWITTER', NULL, '2010-07-22 19:16:21', 'SUCCESS', 19299098929, 5, 1),
(10, 'TWITTER', NULL, '2010-07-22 19:16:22', 'SUCCESS', 19299099979, 5, 5),
(11, 'TWITTER', NULL, '2010-07-25 12:55:26', 'SUCCESS', 19514750579, 6, 1),
(12, 'TWITTER', NULL, '2010-07-25 12:55:27', 'SUCCESS', 19514751282, 6, 5),
(13, 'TWITTER', NULL, '2010-07-31 08:09:46', 'SUCCESS', 19993726775, 7, 1),
(14, 'TWITTER', NULL, '2010-07-31 08:09:50', 'SUCCESS', 19993731358, 7, 5),
(15, 'TWITTER', NULL, '2010-07-31 09:14:06', 'SUCCESS', 19997956787, 8, 1),
(16, 'TWITTER', NULL, '2010-07-31 09:14:08', 'SUCCESS', 19997958611, 8, 5),
(17, 'TWITTER', NULL, '2010-07-31 09:14:06', 'SUCCESS', 19997956787, 9, 1),
(18, 'TWITTER', NULL, '2010-07-31 09:14:08', 'SUCCESS', 19997958611, 9, 5),
(19, 'TWITTER', NULL, '2010-07-31 09:20:06', 'SUCCESS', 19998376688, 10, 1),
(20, 'TWITTER', NULL, '2010-07-31 09:20:08', 'SUCCESS', 19998378877, 10, 5),
(21, 'TWITTER', NULL, '2010-07-31 20:43:54', 'SUCCESS', 20036177676, 11, 1),
(22, 'TWITTER', NULL, '2010-07-31 20:43:56', 'SUCCESS', 20036178892, 11, 5),
(23, 'TWITTER', NULL, '2010-07-31 20:53:24', 'SUCCESS', 20036770891, 12, 1),
(24, 'TWITTER', NULL, '2010-07-31 20:59:19', 'SUCCESS', 20037131779, 13, 1),
(25, 'TWITTER', NULL, '2010-08-01 07:58:42', 'SUCCESS', 20063762474, 14, 1),
(26, 'TWITTER', NULL, '2010-08-01 07:58:44', 'SUCCESS', 20063764469, 14, 5),
(27, 'TWITTER', NULL, '2010-08-01 08:54:41', 'SUCCESS', 20067193855, 15, 1),
(28, 'TWITTER', NULL, '2010-08-01 08:54:42', 'SUCCESS', 20067194677, 15, 5),
(29, 'TWITTER', NULL, '2010-08-01 11:21:06', 'SUCCESS', 20076015970, 18, 1),
(30, 'TWITTER', NULL, '2010-08-01 11:21:07', 'SUCCESS', 20076017012, 18, 5),
(31, 'TWITTER', NULL, '2010-08-01 16:04:06', 'SUCCESS', 20090820191, 22, 1),
(32, 'TWITTER', NULL, '2010-08-01 16:04:07', 'SUCCESS', 20090821080, 22, 5),
(33, 'TWITTER', NULL, '2010-08-01 17:54:24', 'SUCCESS', 20097245225, 23, 1),
(34, 'TWITTER', NULL, '2010-08-01 17:54:25', 'SUCCESS', 20097246118, 23, 5),
(35, 'TWITTER', NULL, '2010-08-01 18:33:59', 'SUCCESS', 20099617693, 24, 1),
(36, 'TWITTER', NULL, '2010-08-01 18:34:00', 'SUCCESS', 20099618559, 24, 5),
(37, 'TWITTER', NULL, '2010-08-01 18:36:20', 'SUCCESS', 20099753470, 25, 1),
(38, 'TWITTER', NULL, '2010-08-07 16:01:37', 'SUCCESS', 20580754100, 26, 1),
(39, 'TWITTER', NULL, '2010-08-07 16:01:38', 'SUCCESS', 20580754916, 26, 5),
(40, 'TWITTER', NULL, '2010-08-07 16:13:11', 'SUCCESS', 20581317102, 27, 1),
(41, 'TWITTER', NULL, '2010-08-07 16:13:12', 'SUCCESS', 20581317855, 27, 5),
(42, 'TWITTER', NULL, '2010-08-08 15:21:18', 'SUCCESS', 20655405939, 28, 1),
(43, 'TWITTER', NULL, '2010-08-08 15:21:19', 'SUCCESS', 20655407025, 28, 5),
(44, 'TWITTER', NULL, '2010-08-08 15:22:21', 'SUCCESS', 20655459514, 29, 1),
(45, 'TWITTER', NULL, '2010-08-20 18:19:31', 'SUCCESS', 21706356320, 30, 1),
(46, 'TWITTER', NULL, '2010-08-20 18:19:32', 'SUCCESS', 21706357774, 30, 5),
(47, NULL, 'TwitterException{exceptionCode=[15bb6564-00e304a2], statusCode=401, retryAfter=0, rateLimitStatus=null, version=2.1.3}', NULL, 'FAILED', NULL, 31, 1),
(48, NULL, 'TwitterException{exceptionCode=[15bb6564-00e304a2], statusCode=401, retryAfter=0, rateLimitStatus=null, version=2.1.3}', NULL, 'FAILED', NULL, 32, 1),
(49, 'TWITTER', 'TwitterException{exceptionCode=[15bb6564-00e304a2], statusCode=401, retryAfter=0, rateLimitStatus=null, version=2.1.3}', NULL, 'FAILED', NULL, 33, 1),
(50, 'TWITTER', NULL, '2010-09-05 18:28:23', 'SUCCESS', 23100425854, 34, 1),
(51, 'TWITTER', NULL, '2010-09-05 23:05:58', 'SUCCESS', 23118860709, 35, 1),
(52, 'TWITTER', 'Consumer key and Consumer secret not supplied.', NULL, 'FAILED', NULL, 35, 5),
(53, 'TWITTER', NULL, '2010-09-05 23:15:33', 'SUCCESS', 23119434348, 36, 1),
(54, 'TWITTER', NULL, '2010-09-18 16:20:50', 'SUCCESS', 24883905672, 38, 1),
(55, 'TWITTER', NULL, '2010-09-18 17:07:55', 'SUCCESS', 24886604190, 42, 1),
(56, 'TWITTER', NULL, '2010-09-18 17:42:54', 'SUCCESS', 24888739010, 43, 1),
(57, 'TWITTER', NULL, '2010-09-18 19:33:04', 'SUCCESS', 24895653243, 44, 1),
(58, 'TWITTER', NULL, '2010-09-18 22:09:25', 'SUCCESS', 24906214055, 45, 1),
(59, 'TWITTER', NULL, '2010-09-18 23:41:21', 'SUCCESS', 24911768971, 46, 1),
(60, 'TWITTER', NULL, '2010-09-19 15:52:48', 'SUCCESS', 24969624485, 47, 1),
(61, 'TWITTER', NULL, '2010-09-19 16:21:01', 'SUCCESS', 24971425167, 48, 1),
(62, 'TWITTER', NULL, '2010-09-19 21:26:54', 'SUCCESS', 24993299171, 49, 1),
(63, 'TWITTER', NULL, '2010-09-23 18:34:11', 'SUCCESS', 25357571199, 50, 1);

--
-- Dumping data for table `tweetpoll_switch`
--

INSERT INTO `tweetpoll_switch` (`tweetpoll_switch_id`, `tweet_code`, `q_answer_id`, `tweet_poll_id`) VALUES
(1, '98d7af592bacc2dfaeb511b69f8ef33b', 60, 1),
(2, '602774cec80f8e4415cd49a22c3c5b19', 61, 1),
(3, 'ed7973371ad858a75465ebf18e44f98a', 62, 2),
(4, 'cdad50e61c02b6480bd5c2eced38f68e', 63, 2),
(5, 'd28053306b28d83e6e691a9038d68909', 64, 3),
(6, '7f55910805444845371b9e3c2d922e49', 65, 3),
(7, 'bc4b601bcf8751e72447876f91940261', 66, 4),
(8, '8a6cc724e72bb6a6e9a0f423d3a3ea0a', 67, 4),
(9, '5e274ad4cfd01ec31c20af9476226e97', 68, 5),
(10, 'dc73c729ca17570a8d7ee9cfb4cdde9e', 69, 5),
(11, '63a601ccbbfaeac8ef868a9058aab597', 70, 6),
(12, 'd1e6e5431cd2bc14e89aa7893028ffb9', 71, 6),
(13, '760989285cedfc1b5db2c78a0cc90441', 72, 7),
(14, 'caa90f13a07ad5b19798222201245651', 73, 7),
(15, 'c7944bf6fd0108d9d394c625f29dce28', 74, 7),
(16, '2a9ec4821f912efc65e4f1d1f400b10d', 75, 8),
(17, '83120762ca1991590c955aebb4ebbe3f', 76, 8),
(18, 'b21f54976f25edf11f1eef08970f83a0', 77, 9),
(19, 'b0261c70511aa143fe1393b979a2b597', 78, 9),
(20, 'd6a557d385bbf5d29e130ef1389667ec', 79, 10),
(21, '5e238bc00751ea29a6299cd65aa97713', 80, 10),
(22, '0537a7f4452c8e9797fc57a9c6e521fe', 81, 11),
(23, 'cfcfc3dfc7965f9f51f0fa9c5f5ef065', 82, 12),
(24, 'a2b7164b6c2a959d021b04facb9a6e87', 83, 12),
(25, '28951c81e485cf4bb344a3641afc7e39', 84, 13),
(26, '7abcafdf5ad2b6dabf414142ec965965', 85, 14),
(27, 'd7aa88309e81b6d8218ec51c23cd6680', 86, 14),
(28, '97c464b5e88252df50ac4fbbf6f43f03', 87, 15),
(29, '4f3c7549afe2cacccb862fddc28661e6', 88, 15),
(30, '4ba4b89cca1c30d332ba5d3978759955', 89, 16),
(31, '646bb93b2d6749ef6017a121ff61e702', 90, 16),
(32, 'a01f503f0405cd1e5384a97c48dca7c1', 91, 16),
(33, '8ea066b7cc5432d787b987b041e331d4', 92, 17),
(34, '4a9363aaad8345259fd9c432509645d1', 93, 17),
(35, 'b471db8f2d73adc0d5dc9d8413d8df6a', 94, 18),
(36, '57dececa8a9c92609045c4de0dede0f2', 95, 18),
(37, '957de6410a5dda532bd4875249f24241', 96, 20),
(38, '3b5c0b3606778ece1a2f2f809c5d2b93', 97, 21),
(39, '8bac48a57ae6fe0a3fda57def3bd9c55', 98, 21),
(40, '290cfbf928f91589bc715affc8e6152b', 99, 22),
(41, '5e1e1c55c21e927ff8538f9e04acd875', 100, 22),
(42, '93a02fec5a11f7c798dc2260b519a3e2', 101, 23),
(43, 'e9902ff05b61987f196b171987af380c', 102, 23),
(44, '5af631da44df5d2ba0576cd930ed2a1a', 103, 24),
(45, 'da8d74510a30cbd9e5fc77842b9324bc', 104, 24),
(46, '223d1cea9650cc2e51fffb0d6ef47d53', 105, 25),
(47, 'f655c496a7155ba82c525558f70e5ba3', 106, 25),
(48, 'eace9ab528460b711732d8fbc7b3a7b2', 107, 26),
(49, '3a4e8dc9cf51bb165fe392dd6b476200', 108, 26),
(50, '42004229c83e0418d58a69121ca3d7ed', 109, 27),
(51, '141238989ebbc0556e0feb597c86f323', 110, 27),
(52, '743475f1d2e33e7d4b995d673e42832c', 111, 28),
(53, '0f443c22faedf0030a90608eb538d8bd', 112, 28),
(54, '5633c62615e64d42e2a086ef04fb09ab', 113, 29),
(55, '3e90e5e9bf5c409d0a315b16150ef856', 114, 29),
(56, '174ee97f4a2b06768d82268ddb16544a', 115, 30),
(57, '900f462f7bf06f1b4d4f7fb82c70189b', 116, 30),
(58, '3d08c35bf0f8f6b53e0649f21a7f31a0', 117, 31),
(59, '4b4f2d511e24305df0b1a623d67348dc', 118, 31),
(60, 'a6276c457cfc4329149f3e2e973d323e', 119, 32),
(61, '3d985d68aa7679d5ddb681cd8a0a51ef', 120, 32),
(62, 'a7ed3f0906942cd0c07e9577c8c9433f', 121, 33),
(63, 'dd794480fd776b55864757e95d60e869', 122, 33),
(64, 'e133d7fff707e2604e4b7e9268f9a8b4', 123, 34),
(65, '808e1f7d6e5f35fdeef3c7a3f620e1b3', 124, 34),
(66, 'b7489a88e019e369778fc700732aec07', 125, 35),
(67, '147dfe13bb625e9ccf88fe15c2d85588', 126, 35),
(68, '7308c45da3291373edd94871b64296b0', 127, 36),
(69, '3cacddc37b277246858628571cffb245', 128, 36),
(70, 'eaaa54ab47f26986009c0b2a893b2926', 129, 37),
(71, '4342008e41134701733946ea05407e8f', 130, 37),
(72, '0569657949d21b65b053c0974c375dde', 131, 38),
(74, 'c0c8c41f20a9a5726d5a8cc8dd1a1381', 133, 40),
(75, '063680a78a9deedfe28d032095c0caf5', 134, 40),
(77, '47616983c52c1592c8426d742291be10', 136, 42),
(78, '4a6db392c2f3418f997a1873fbc65eab', 137, 42),
(79, 'fc5e4c42db20c333344cb1b174e5c4c3', 138, 43),
(80, 'fb0a64a1dfa062208368723c52590438', 139, 43),
(81, '74f53c7a7a80086f888b29c42f3d1388', 140, 44),
(82, '4ffecf2ffc4ae91f052b9c3f0117e0c9', 141, 44),
(83, '30c4eefa292d119ad31bd37d92f15c11', 142, 45),
(84, '98affd4dc1ed5eaa27f7ca66fa4a8e51', 143, 45),
(85, '5f2ff60ca6bb04be7ef36d818c51c68c', 144, 46),
(86, '5863b8c489210686307b2eebd880a617', 145, 47),
(87, '4049bd2022eec78b3869580306933f01', 146, 47),
(88, '8f629c86b11f48818bd9c422a7e049fc', 147, 48),
(89, '70e03825efb1385a852c5d33da0015a8', 148, 48),
(90, 'ff096b79d948d0fe5f7d118b565edaaf', 149, 49),
(91, '451161e2e0fce42ed947c54ea9853a2d', 150, 49),
(92, 'faa539aaaace922a085cab6c70e0d940', 151, 49),
(93, '123484b158932e88acbbfcc78788e9b1', 152, 50),
(94, '3a310e7329a2792b3dac3d1abeeaad4d', 153, 50);
