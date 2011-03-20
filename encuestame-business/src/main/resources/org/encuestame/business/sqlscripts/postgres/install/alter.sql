ALTER TABLE ONLY account
    ADD CONSTRAINT account_pkey PRIMARY KEY (uid);


ALTER TABLE ONLY application_connection
    ADD CONSTRAINT application_connection_pkey PRIMARY KEY (connection_id);


ALTER TABLE ONLY application
    ADD CONSTRAINT application_pkey PRIMARY KEY (application_id);

ALTER TABLE ONLY client
    ADD CONSTRAINT client_pkey PRIMARY KEY (client_id);


ALTER TABLE ONLY email
    ADD CONSTRAINT email_email_key UNIQUE (email);

ALTER TABLE ONLY email
    ADD CONSTRAINT email_pkey PRIMARY KEY (email_id);

ALTER TABLE ONLY emaillist
    ADD CONSTRAINT emaillist_pkey PRIMARY KEY (id_list);

ALTER TABLE ONLY emailsubscribe
    ADD CONSTRAINT emailsubscribe_pkey PRIMARY KEY (id_subscribe);

ALTER TABLE ONLY geopoint_folder
    ADD CONSTRAINT geopoint_folder_pkey PRIMARY KEY (locate_folder_id);

ALTER TABLE ONLY geopoint
    ADD CONSTRAINT geopoint_pkey PRIMARY KEY (locate_id);

ALTER TABLE ONLY geopoint_type
    ADD CONSTRAINT geopoint_type_pkey PRIMARY KEY (loc_id_type);

ALTER TABLE ONLY group_permission
    ADD CONSTRAINT group_permission_pkey PRIMARY KEY (sec_id_permission, sec_id_group);

ALTER TABLE ONLY groups_permission
    ADD CONSTRAINT groups_permission_pkey PRIMARY KEY (sec_id_group, sec_id_permission);

ALTER TABLE ONLY groups
    ADD CONSTRAINT groups_pkey PRIMARY KEY (group_id);

ALTER TABLE ONLY hash_tags
    ADD CONSTRAINT hash_tags_pkey PRIMARY KEY (hash_tag_id);

ALTER TABLE ONLY notification
    ADD CONSTRAINT notification_pkey PRIMARY KEY (notification_id);

ALTER TABLE ONLY oauth_account_connection
    ADD CONSTRAINT oauth_account_connection_pkey PRIMARY KEY (account_connection_id);

ALTER TABLE ONLY oauth_account_social_provider
    ADD CONSTRAINT oauth_account_social_provider_name_key UNIQUE (name);

ALTER TABLE ONLY oauth_account_social_provider
    ADD CONSTRAINT oauth_account_social_provider_pkey PRIMARY KEY (social_provider_id);

ALTER TABLE ONLY permission
    ADD CONSTRAINT permission_pkey PRIMARY KEY (id_permission);

ALTER TABLE ONLY poll_folder
    ADD CONSTRAINT poll_folder_pkey PRIMARY KEY (pollfolderid);

ALTER TABLE ONLY poll
    ADD CONSTRAINT poll_pkey PRIMARY KEY (poll_id);

ALTER TABLE ONLY poll
    ADD CONSTRAINT poll_poll_hash_key UNIQUE (poll_hash);

ALTER TABLE ONLY poll_result
    ADD CONSTRAINT poll_result_pkey PRIMARY KEY (poll_resultid);

ALTER TABLE ONLY project_geopoint
    ADD CONSTRAINT project_geopoint_pkey PRIMARY KEY (cat_id_project, cat_id_loc);

ALTER TABLE ONLY project_group
    ADD CONSTRAINT project_group_pkey PRIMARY KEY (cat_id_project, sec_id_group);

ALTER TABLE ONLY project_locations
    ADD CONSTRAINT project_locations_pkey PRIMARY KEY (cat_id_loc, cat_id_project);

ALTER TABLE ONLY project
    ADD CONSTRAINT project_pkey PRIMARY KEY (project_id);

ALTER TABLE ONLY question_category
    ADD CONSTRAINT question_category_pkey PRIMARY KEY (qcategory);

ALTER TABLE ONLY question_category_questions
    ADD CONSTRAINT question_category_questions_pkey PRIMARY KEY (question_category_qcategory, questionlibrary_qid);

ALTER TABLE ONLY question_collection
    ADD CONSTRAINT question_collection_pkey PRIMARY KEY (id_q_colection);

ALTER TABLE ONLY question_dependence_survey
    ADD CONSTRAINT question_dependence_survey_pkey PRIMARY KEY (question_dependence_survey);

ALTER TABLE ONLY question_relations
    ADD CONSTRAINT question_relations_pkey PRIMARY KEY (question_id, id_q_colection);

ALTER TABLE ONLY questions_answers
    ADD CONSTRAINT questions_answers_pkey PRIMARY KEY (q_answer_id);

ALTER TABLE ONLY questions_dependencies
    ADD CONSTRAINT questions_dependencies_descriptiondependence_key UNIQUE (descriptiondependence);

ALTER TABLE ONLY questions_dependencies
    ADD CONSTRAINT questions_dependencies_pkey PRIMARY KEY (question_dependenceid);

ALTER TABLE ONLY questions_dependencies
    ADD CONSTRAINT questions_dependencies_questionid_from_key UNIQUE (questionid_from);--

ALTER TABLE ONLY questions_dependencies
    ADD CONSTRAINT questions_dependencies_questionid_to_key UNIQUE (questionid_to);

ALTER TABLE ONLY questions_pattern
    ADD CONSTRAINT questions_pattern_pkey PRIMARY KEY (pattenr_id);

ALTER TABLE ONLY questions
    ADD CONSTRAINT questions_pkey PRIMARY KEY (qid);

ALTER TABLE ONLY social_account
    ADD CONSTRAINT social_account_pkey PRIMARY KEY (sec_user_twitter_id);

ALTER TABLE ONLY social_account
    ADD CONSTRAINT social_account_social_account_id_key UNIQUE (social_account_id);

ALTER TABLE ONLY survey_folder
    ADD CONSTRAINT survey_folder_pkey PRIMARY KEY (survey_folderid);

ALTER TABLE ONLY survey_format
    ADD CONSTRAINT survey_format_pkey PRIMARY KEY (id_sid_format);

ALTER TABLE ONLY survey_group_format
    ADD CONSTRAINT survey_group_format_pkey PRIMARY KEY (id_sid_format, sg_id);

ALTER TABLE ONLY survey_group
    ADD CONSTRAINT survey_group_pkey PRIMARY KEY (sg_id);

ALTER TABLE ONLY survey_group_project
    ADD CONSTRAINT survey_group_project_pkey PRIMARY KEY (cat_id_project, id_sid_format);

ALTER TABLE ONLY survey_pagination
    ADD CONSTRAINT survey_pagination_pkey PRIMARY KEY (pagination_id);

ALTER TABLE ONLY survey_result
    ADD CONSTRAINT survey_result_pkey PRIMARY KEY (rid);

ALTER TABLE ONLY survey_section
    ADD CONSTRAINT survey_section_pkey PRIMARY KEY (ssid);

ALTER TABLE ONLY survey_section_questions
    ADD CONSTRAINT survey_section_questions_pkey PRIMARY KEY (survey_section_ssid, questionsection_qid);

ALTER TABLE ONLY surveys
    ADD CONSTRAINT surveys_pkey PRIMARY KEY (sid);

ALTER TABLE ONLY tweetpoll_folder
    ADD CONSTRAINT tweetpoll_folder_pkey PRIMARY KEY (tweetpollfolderid);


--
-- TOC entry 2059 (class 2606 OID 3683698)
-- Dependencies: 1667 1667 1667
-- Name: tweetpoll_hash_tags_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace:
--

ALTER TABLE ONLY tweetpoll_hash_tags
    ADD CONSTRAINT tweetpoll_hash_tags_pkey PRIMARY KEY (tweetpoll_tweet_poll_id, hashtags_hash_tag_id);


--
-- TOC entry 2055 (class 2606 OID 3683688)
-- Dependencies: 1665 1665
-- Name: tweetpoll_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace:
--

ALTER TABLE ONLY tweetpoll
    ADD CONSTRAINT tweetpoll_pkey PRIMARY KEY (tweet_poll_id);


--
-- TOC entry 2063 (class 2606 OID 3683711)
-- Dependencies: 1669 1669
-- Name: tweetpoll_result_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace:
--

ALTER TABLE ONLY tweetpoll_result
    ADD CONSTRAINT tweetpoll_result_pkey PRIMARY KEY (tweetpoll_resultid);


--
-- TOC entry 2061 (class 2606 OID 3683706)
-- Dependencies: 1668 1668
-- Name: tweetpoll_save_published_status_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace:
--

ALTER TABLE ONLY tweetpoll_save_published_status
    ADD CONSTRAINT tweetpoll_save_published_status_pkey PRIMARY KEY (status_save_poll_id);


--
-- TOC entry 2065 (class 2606 OID 3683716)
-- Dependencies: 1670 1670
-- Name: tweetpoll_switch_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace:
--

ALTER TABLE ONLY tweetpoll_switch
    ADD CONSTRAINT tweetpoll_switch_pkey PRIMARY KEY (tweetpoll_switch_id);


--
-- TOC entry 2067 (class 2606 OID 3683718)
-- Dependencies: 1670 1670
-- Name: tweetpoll_switch_tweet_code_key; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace:
--

ALTER TABLE ONLY tweetpoll_switch
    ADD CONSTRAINT tweetpoll_switch_tweet_code_key UNIQUE (tweet_code);


--
-- TOC entry 2070 (class 2606 OID 3683728)
-- Dependencies: 1671 1671
-- Name: useraccount_email_key; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace:
--

ALTER TABLE ONLY useraccount
    ADD CONSTRAINT useraccount_email_key UNIQUE (email);


--
-- TOC entry 2079 (class 2606 OID 3683737)
-- Dependencies: 1672 1672 1672
-- Name: useraccount_followers_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace:
--

ALTER TABLE ONLY useraccount_followers
    ADD CONSTRAINT useraccount_followers_pkey PRIMARY KEY (uid, uid_follower);


--
-- TOC entry 2081 (class 2606 OID 3683742)
-- Dependencies: 1673 1673 1673
-- Name: useraccount_permission_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace:
--

ALTER TABLE ONLY useraccount_permission
    ADD CONSTRAINT useraccount_permission_pkey PRIMARY KEY (sec_id_permission, sec_id_secondary);


--
-- TOC entry 2072 (class 2606 OID 3683726)
-- Dependencies: 1671 1671
-- Name: useraccount_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace:
--

ALTER TABLE ONLY useraccount
    ADD CONSTRAINT useraccount_pkey PRIMARY KEY (uid);


--
-- TOC entry 2083 (class 2606 OID 3683747)
-- Dependencies: 1674 1674 1674
-- Name: useraccount_project_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace:
--

ALTER TABLE ONLY useraccount_project
    ADD CONSTRAINT useraccount_project_pkey PRIMARY KEY (cat_id_project, sec_id_secondary);


--
-- TOC entry 2074 (class 2606 OID 3683730)
-- Dependencies: 1671 1671
-- Name: useraccount_username_key; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace:
--

ALTER TABLE ONLY useraccount
    ADD CONSTRAINT useraccount_username_key UNIQUE (username);


--
-- TOC entry 2076 (class 2606 OID 3683732)
-- Dependencies: 1671 1671 1671
-- Name: useraccount_username_key1; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace:
--

ALTER TABLE ONLY useraccount
    ADD CONSTRAINT useraccount_username_key1 UNIQUE (username, email);


--
-- TOC entry 2143 (class 2606 OID 3684028)
-- Dependencies: 1663 1662 2048
-- Name: fk12354ece11057e56; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY survey_section_questions
    ADD CONSTRAINT fk12354ece11057e56 FOREIGN KEY (survey_section_ssid) REFERENCES survey_section(ssid);


--
-- TOC entry 2144 (class 2606 OID 3684033)
-- Dependencies: 1663 1651 2020
-- Name: fk12354ece6dd59357; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY survey_section_questions
    ADD CONSTRAINT fk12354ece6dd59357 FOREIGN KEY (questionsection_qid) REFERENCES questions(qid);


--
-- TOC entry 2130 (class 2606 OID 3683958)
-- Dependencies: 1650 1651 2020
-- Name: fk217954deaa11961f; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY question_relations
    ADD CONSTRAINT fk217954deaa11961f FOREIGN KEY (question_id) REFERENCES questions(qid);


--
-- TOC entry 2129 (class 2606 OID 3683953)
-- Dependencies: 1650 1648 2014
-- Name: fk217954deb3e0058e; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY question_relations
    ADD CONSTRAINT fk217954deb3e0058e FOREIGN KEY (id_q_colection) REFERENCES question_collection(id_q_colection);


--
-- TOC entry 2108 (class 2606 OID 3683848)
-- Dependencies: 1636 1637 1990
-- Name: fk222e06d86926a720; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY oauth_account_connection
    ADD CONSTRAINT fk222e06d86926a720 FOREIGN KEY (accountprovider_social_provider_id) REFERENCES oauth_account_social_provider(social_provider_id);


--
-- TOC entry 2109 (class 2606 OID 3683853)
-- Dependencies: 1671 2071 1636
-- Name: fk222e06d87ad3eee2; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY oauth_account_connection
    ADD CONSTRAINT fk222e06d87ad3eee2 FOREIGN KEY (useraccout_uid) REFERENCES useraccount(uid);


--
-- TOC entry 2107 (class 2606 OID 3683843)
-- Dependencies: 1635 1621 1954
-- Name: fk237a88eb2b2a6ab4; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY notification
    ADD CONSTRAINT fk237a88eb2b2a6ab4 FOREIGN KEY (uid) REFERENCES account(uid);


--
-- TOC entry 2124 (class 2606 OID 3683928)
-- Dependencies: 1645 1628 1970
-- Name: fk242951b835313189; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY project_locations
    ADD CONSTRAINT fk242951b835313189 FOREIGN KEY (cat_id_loc) REFERENCES geopoint(locate_id);


--
-- TOC entry 2123 (class 2606 OID 3683923)
-- Dependencies: 1645 1642 2002
-- Name: fk242951b884536452; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY project_locations
    ADD CONSTRAINT fk242951b884536452 FOREIGN KEY (cat_id_project) REFERENCES project(project_id);


--
-- TOC entry 2120 (class 2606 OID 3683908)
-- Dependencies: 1643 1628 1970
-- Name: fk2599132535313189; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY project_geopoint
    ADD CONSTRAINT fk2599132535313189 FOREIGN KEY (cat_id_loc) REFERENCES geopoint(locate_id);


--
-- TOC entry 2119 (class 2606 OID 3683903)
-- Dependencies: 2002 1642 1643
-- Name: fk2599132584536452; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY project_geopoint
    ADD CONSTRAINT fk2599132584536452 FOREIGN KEY (cat_id_project) REFERENCES project(project_id);


--
-- TOC entry 2126 (class 2606 OID 3683938)
-- Dependencies: 1647 1651 2020
-- Name: fk2ffe1845273b5e2d; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY question_category_questions
    ADD CONSTRAINT fk2ffe1845273b5e2d FOREIGN KEY (questionlibrary_qid) REFERENCES questions(qid);


--
-- TOC entry 2125 (class 2606 OID 3683933)
-- Dependencies: 1647 1646 2010
-- Name: fk2ffe1845a279b31c; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY question_category_questions
    ADD CONSTRAINT fk2ffe1845a279b31c FOREIGN KEY (question_category_qcategory) REFERENCES question_category(qcategory);


--
-- TOC entry 2110 (class 2606 OID 3683858)
-- Dependencies: 1621 1954 1639
-- Name: fk3497bf2b2a6ab4; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY poll
    ADD CONSTRAINT fk3497bf2b2a6ab4 FOREIGN KEY (uid) REFERENCES account(uid);


--
-- TOC entry 2112 (class 2606 OID 3683868)
-- Dependencies: 1651 2020 1639
-- Name: fk3497bf70996757; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY poll
    ADD CONSTRAINT fk3497bf70996757 FOREIGN KEY (qid) REFERENCES questions(qid);


--
-- TOC entry 2111 (class 2606 OID 3683863)
-- Dependencies: 1998 1639 1640
-- Name: fk3497bf89452cca; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY poll
    ADD CONSTRAINT fk3497bf89452cca FOREIGN KEY (poll_folder) REFERENCES poll_folder(pollfolderid);


--
-- TOC entry 2113 (class 2606 OID 3683873)
-- Dependencies: 1639 1671 2071
-- Name: fk3497bfa64fb606; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY poll
    ADD CONSTRAINT fk3497bfa64fb606 FOREIGN KEY (editor) REFERENCES useraccount(uid);


--
-- TOC entry 2103 (class 2606 OID 3683823)
-- Dependencies: 1631 1638 1992
-- Name: fk362e6f8f43adb63d; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY group_permission
    ADD CONSTRAINT fk362e6f8f43adb63d FOREIGN KEY (sec_id_permission) REFERENCES permission(id_permission);


--
-- TOC entry 2102 (class 2606 OID 3683818)
-- Dependencies: 1632 1978 1631
-- Name: fk362e6f8f45895aff; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY group_permission
    ADD CONSTRAINT fk362e6f8f45895aff FOREIGN KEY (sec_id_group) REFERENCES groups(group_id);


--
-- TOC entry 2095 (class 2606 OID 3683783)
-- Dependencies: 1964 1625 1627
-- Name: fk4b85010ee824035; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY emailsubscribe
    ADD CONSTRAINT fk4b85010ee824035 FOREIGN KEY (email_id) REFERENCES email(email_id);


--
-- TOC entry 2094 (class 2606 OID 3683778)
-- Dependencies: 1626 1627 1966
-- Name: fk4b85010eed78e617; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY emailsubscribe
    ADD CONSTRAINT fk4b85010eed78e617 FOREIGN KEY (id_list) REFERENCES emaillist(id_list);


--
-- TOC entry 2168 (class 2606 OID 3685068)
-- Dependencies: 1621 1676 1954
-- Name: fk50078b5bebe472cc; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY social_account
    ADD CONSTRAINT fk50078b5bebe472cc FOREIGN KEY (secusers_uid) REFERENCES account(uid);


--
-- TOC entry 2101 (class 2606 OID 3683813)
-- Dependencies: 1621 1954 1630
-- Name: fk514326ba4075e3fd; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY geopoint_type
    ADD CONSTRAINT fk514326ba4075e3fd FOREIGN KEY (users_uid) REFERENCES account(uid);


--
-- TOC entry 2133 (class 2606 OID 3683973)
-- Dependencies: 1652 1651 2020
-- Name: fk539703839e07711e; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY questions_answers
    ADD CONSTRAINT fk539703839e07711e FOREIGN KEY (id_question_answer) REFERENCES questions(qid);


--
-- TOC entry 2092 (class 2606 OID 3683768)
-- Dependencies: 1626 1625 1966
-- Name: fk5c24b9ced78e617; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY email
    ADD CONSTRAINT fk5c24b9ced78e617 FOREIGN KEY (id_list) REFERENCES emaillist(id_list);


--
-- TOC entry 2088 (class 2606 OID 3683748)
-- Dependencies: 1621 1622 1954
-- Name: fk5ca405505ece45a2; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY application
    ADD CONSTRAINT fk5ca405505ece45a2 FOREIGN KEY (account_uid) REFERENCES account(uid);


--
-- TOC entry 2098 (class 2606 OID 3683798)
-- Dependencies: 1629 1972 1628
-- Name: fk6c73c0bf34ef9a43; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY geopoint
    ADD CONSTRAINT fk6c73c0bf34ef9a43 FOREIGN KEY (geopointfolder_locate_folder_id) REFERENCES geopoint_folder(locate_folder_id);


--
-- TOC entry 2097 (class 2606 OID 3683793)
-- Dependencies: 1954 1621 1628
-- Name: fk6c73c0bf5ece45a2; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY geopoint
    ADD CONSTRAINT fk6c73c0bf5ece45a2 FOREIGN KEY (account_uid) REFERENCES account(uid);


--
-- TOC entry 2096 (class 2606 OID 3683788)
-- Dependencies: 1628 1974 1630
-- Name: fk6c73c0bfbd91661d; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY geopoint
    ADD CONSTRAINT fk6c73c0bfbd91661d FOREIGN KEY (loc_id_type) REFERENCES geopoint_type(loc_id_type);


--
-- TOC entry 2089 (class 2606 OID 3683753)
-- Dependencies: 2071 1623 1671
-- Name: fk73d5d2d27e933d7; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY application_connection
    ADD CONSTRAINT fk73d5d2d27e933d7 FOREIGN KEY (account_uid) REFERENCES useraccount(uid);


--
-- TOC entry 2090 (class 2606 OID 3683758)
-- Dependencies: 1623 1622 1956
-- Name: fk73d5d2d4402be26; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY application_connection
    ADD CONSTRAINT fk73d5d2d4402be26 FOREIGN KEY (application_application_id) REFERENCES application(application_id);


--
-- TOC entry 2093 (class 2606 OID 3683773)
-- Dependencies: 1621 1626 1954
-- Name: fk7e5f425a2b2a6ab4; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY emaillist
    ADD CONSTRAINT fk7e5f425a2b2a6ab4 FOREIGN KEY (uid) REFERENCES account(uid);


--
-- TOC entry 2135 (class 2606 OID 3683988)
-- Dependencies: 1655 1621 1954
-- Name: fk7ef958f32b2a6ab4; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY survey_folder
    ADD CONSTRAINT fk7ef958f32b2a6ab4 FOREIGN KEY (uid) REFERENCES account(uid);


--
-- TOC entry 2106 (class 2606 OID 3683838)
-- Dependencies: 1633 1638 1992
-- Name: fk7f1951a43adb63d; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY groups_permission
    ADD CONSTRAINT fk7f1951a43adb63d FOREIGN KEY (sec_id_permission) REFERENCES permission(id_permission);


--
-- TOC entry 2105 (class 2606 OID 3683833)
-- Dependencies: 1632 1633 1978
-- Name: fk7f1951a45895aff; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY groups_permission
    ADD CONSTRAINT fk7f1951a45895aff FOREIGN KEY (sec_id_group) REFERENCES groups(group_id);


--
-- TOC entry 2163 (class 2606 OID 3684133)
-- Dependencies: 2071 1671 1672
-- Name: fk7f1957f8e53fbc6; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY useraccount_followers
    ADD CONSTRAINT fk7f1957f8e53fbc6 FOREIGN KEY (uid_follower) REFERENCES useraccount(uid);


--
-- TOC entry 2162 (class 2606 OID 3684128)
-- Dependencies: 1672 2071 1671
-- Name: fk7f1957f8f44558e9; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY useraccount_followers
    ADD CONSTRAINT fk7f1957f8f44558e9 FOREIGN KEY (uid) REFERENCES useraccount(uid);


--
-- TOC entry 2157 (class 2606 OID 3684103)
-- Dependencies: 2064 1669 1670
-- Name: fk8749c18c12f681ec; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY tweetpoll_result
    ADD CONSTRAINT fk8749c18c12f681ec FOREIGN KEY (tweetpoll_switch_id) REFERENCES tweetpoll_switch(tweetpoll_switch_id);


--
-- TOC entry 2158 (class 2606 OID 3684108)
-- Dependencies: 2054 1670 1665
-- Name: fk89f7b0a321905aee; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY tweetpoll_switch
    ADD CONSTRAINT fk89f7b0a321905aee FOREIGN KEY (tweet_poll_id) REFERENCES tweetpoll(tweet_poll_id);


--
-- TOC entry 2159 (class 2606 OID 3684113)
-- Dependencies: 1670 2022 1652
-- Name: fk89f7b0a33a73f181; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY tweetpoll_switch
    ADD CONSTRAINT fk89f7b0a33a73f181 FOREIGN KEY (q_answer_id) REFERENCES questions_answers(q_answer_id);


--
-- TOC entry 2145 (class 2606 OID 3684038)
-- Dependencies: 1664 1621 1954
-- Name: fk919144592b2a6ab4; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY surveys
    ADD CONSTRAINT fk919144592b2a6ab4 FOREIGN KEY (uid) REFERENCES account(uid);


--
-- TOC entry 2146 (class 2606 OID 3684043)
-- Dependencies: 1664 1655 2034
-- Name: fk91914459a3c7a06a; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY surveys
    ADD CONSTRAINT fk91914459a3c7a06a FOREIGN KEY (survey_folder) REFERENCES survey_folder(survey_folderid);


--
-- TOC entry 2148 (class 2606 OID 3684053)
-- Dependencies: 1671 1664 2071
-- Name: fk91914459a64fb606; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY surveys
    ADD CONSTRAINT fk91914459a64fb606 FOREIGN KEY (editor) REFERENCES useraccount(uid);


--
-- TOC entry 2147 (class 2606 OID 3684048)
-- Dependencies: 1664 1656 2036
-- Name: fk91914459b1a6912c; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY surveys
    ADD CONSTRAINT fk91914459b1a6912c FOREIGN KEY (id_sid_format) REFERENCES survey_format(id_sid_format);


--
-- TOC entry 2134 (class 2606 OID 3683978)
-- Dependencies: 1653 1652 2022
-- Name: fk92e86adb3a73f181; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY questions_dependencies
    ADD CONSTRAINT fk92e86adb3a73f181 FOREIGN KEY (q_answer_id) REFERENCES questions_answers(q_answer_id);


--
-- TOC entry 2142 (class 2606 OID 3684023)
-- Dependencies: 1661 1664 2052
-- Name: fk92ea04a2eb8d35c9; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY survey_result
    ADD CONSTRAINT fk92ea04a2eb8d35c9 FOREIGN KEY (survey_id) REFERENCES surveys(sid);


--
-- TOC entry 2131 (class 2606 OID 3683963)
-- Dependencies: 1651 1621 1954
-- Name: fk95c5414d2b2a6ab4; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY questions
    ADD CONSTRAINT fk95c5414d2b2a6ab4 FOREIGN KEY (uid) REFERENCES account(uid);


--
-- TOC entry 2132 (class 2606 OID 3683968)
-- Dependencies: 1651 1654 2032
-- Name: fk95c5414dbbcb7e36; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY questions
    ADD CONSTRAINT fk95c5414dbbcb7e36 FOREIGN KEY (id_question_pattern) REFERENCES questions_pattern(pattenr_id);


--
-- TOC entry 2153 (class 2606 OID 3684078)
-- Dependencies: 1666 1621 1954
-- Name: fka027a9dd2b2a6ab4; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY tweetpoll_folder
    ADD CONSTRAINT fka027a9dd2b2a6ab4 FOREIGN KEY (uid) REFERENCES account(uid);


--
-- TOC entry 2149 (class 2606 OID 3684058)
-- Dependencies: 1665 1621 1954
-- Name: fka65b1d02b2a6ab4; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY tweetpoll
    ADD CONSTRAINT fka65b1d02b2a6ab4 FOREIGN KEY (uid) REFERENCES account(uid);


--
-- TOC entry 2150 (class 2606 OID 3684063)
-- Dependencies: 1665 1666 2056
-- Name: fka65b1d032dd60a8; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY tweetpoll
    ADD CONSTRAINT fka65b1d032dd60a8 FOREIGN KEY (tweetpollfolderid) REFERENCES tweetpoll_folder(tweetpollfolderid);

ALTER TABLE ONLY tweetpoll
    ADD CONSTRAINT fka65b1d070996757 FOREIGN KEY (qid) REFERENCES questions(qid);-

ALTER TABLE ONLY tweetpoll
    ADD CONSTRAINT fka65b1d0a64fb606 FOREIGN KEY (editor) REFERENCES useraccount(uid);

ALTER TABLE ONLY useraccount
    ADD CONSTRAINT fka7d56be25ece45a2 FOREIGN KEY (account_uid) REFERENCES account(uid);

ALTER TABLE ONLY useraccount
    ADD CONSTRAINT fka7d56be2b8eb1450 FOREIGN KEY (groupid) REFERENCES groups(group_id);

ALTER TABLE ONLY client
    ADD CONSTRAINT fkaf12f3cb225a055 FOREIGN KEY (project_id) REFERENCES project(project_id);

ALTER TABLE ONLY question_collection
    ADD CONSTRAINT fkb4097c972b2a6ab4 FOREIGN KEY (uid) REFERENCES account(uid);

ALTER TABLE ONLY survey_group_format
    ADD CONSTRAINT fkb4df867c310e993c FOREIGN KEY (sg_id) REFERENCES survey_group(sg_id);

ALTER TABLE ONLY survey_group_format
    ADD CONSTRAINT fkb4df867cb1a6912c FOREIGN KEY (id_sid_format) REFERENCES survey_format(id_sid_format);

ALTER TABLE ONLY groups
    ADD CONSTRAINT fkb63dd9d45ece45a2 FOREIGN KEY (account_uid) REFERENCES account(uid);

ALTER TABLE ONLY question_dependence_survey
    ADD CONSTRAINT fkbb424d49793d9e77 FOREIGN KEY (sid) REFERENCES surveys(sid);

ALTER TABLE ONLY useraccount_permission
    ADD CONSTRAINT fkbe01ce4c43adb63d FOREIGN KEY (sec_id_permission) REFERENCES permission(id_permission);

ALTER TABLE ONLY useraccount_permission
    ADD CONSTRAINT fkbe01ce4c5f77a117 FOREIGN KEY (sec_id_secondary) REFERENCES useraccount(uid);

ALTER TABLE ONLY survey_pagination
    ADD CONSTRAINT fkbec9a99f1359b877 FOREIGN KEY (ssid) REFERENCES survey_section(ssid);

ALTER TABLE ONLY survey_pagination
    ADD CONSTRAINT fkbec9a99f793d9e77 FOREIGN KEY (sid) REFERENCES surveys(sid);

ALTER TABLE ONLY poll_folder
    ADD CONSTRAINT fkc5911cee2b2a6ab4 FOREIGN KEY (uid) REFERENCES account(uid);


ALTER TABLE ONLY project_group
    ADD CONSTRAINT fkc7652dd945895aff FOREIGN KEY (sec_id_group) REFERENCES groups(group_id);

ALTER TABLE ONLY project_group
    ADD CONSTRAINT fkc7652dd984536452 FOREIGN KEY (cat_id_project) REFERENCES project(project_id);

ALTER TABLE ONLY tweetpoll_save_published_status
    ADD CONSTRAINT fkd499a4b6b17cb69f FOREIGN KEY (tweetpoll_tweet_poll_id) REFERENCES tweetpoll(tweet_poll_id);

ALTER TABLE ONLY poll_result
    ADD CONSTRAINT fkd981c89d3a73f181 FOREIGN KEY (q_answer_id) REFERENCES questions_answers(q_answer_id);

ALTER TABLE ONLY poll_result
    ADD CONSTRAINT fkd981c89df0ed6769 FOREIGN KEY (poll_id) REFERENCES poll(poll_id);

ALTER TABLE ONLY tweetpoll_hash_tags
    ADD CONSTRAINT fke3434cfb378af300 FOREIGN KEY (hashtags_hash_tag_id) REFERENCES hash_tags(hash_tag_id);

ALTER TABLE ONLY tweetpoll_hash_tags
    ADD CONSTRAINT fke3434cfbb17cb69f FOREIGN KEY (tweetpoll_tweet_poll_id) REFERENCES tweetpoll(tweet_poll_id);

ALTER TABLE ONLY project
    ADD CONSTRAINT fked904b194075e3fd FOREIGN KEY (users_uid) REFERENCES account(uid);

ALTER TABLE ONLY project
    ADD CONSTRAINT fked904b19514c1986 FOREIGN KEY (lead_uid) REFERENCES useraccount(uid);

ALTER TABLE ONLY geopoint_folder
    ADD CONSTRAINT fkf4a1d3ee5ece45a2 FOREIGN KEY (account_uid) REFERENCES account(uid);

ALTER TABLE ONLY geopoint_folder
    ADD CONSTRAINT fkf4a1d3ee6e4ed46d FOREIGN KEY (sublocationfolder_locate_folder_id) REFERENCES geopoint_folder(locate_folder_id);

ALTER TABLE ONLY useraccount_project
    ADD CONSTRAINT fkfbc45bbc5f77a117 FOREIGN KEY (sec_id_secondary) REFERENCES useraccount(uid);

ALTER TABLE ONLY useraccount_project
    ADD CONSTRAINT fkfbc45bbc84536452 FOREIGN KEY (cat_id_project) REFERENCES project(project_id);

ALTER TABLE ONLY survey_group_project
    ADD CONSTRAINT fkfd028d3484536452 FOREIGN KEY (cat_id_project) REFERENCES project(project_id);

ALTER TABLE ONLY survey_group_project
    ADD CONSTRAINT fkfd028d34b75f3482 FOREIGN KEY (id_sid_format) REFERENCES survey_group(sg_id);
