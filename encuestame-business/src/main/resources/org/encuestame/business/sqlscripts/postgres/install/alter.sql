--
-- TOC entry 1972 (class 2606 OID 83975)
-- Dependencies: 1633 1633
-- Name: access_rate_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY access_rate
    ADD CONSTRAINT access_rate_pkey PRIMARY KEY (rateid);


--
-- TOC entry 1974 (class 2606 OID 83980)
-- Dependencies: 1634 1634
-- Name: account_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY account
    ADD CONSTRAINT account_pkey PRIMARY KEY (uid);


--
-- TOC entry 1978 (class 2606 OID 83996)
-- Dependencies: 1636 1636
-- Name: application_connection_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY application_connection
    ADD CONSTRAINT application_connection_pkey PRIMARY KEY (connection_id);


--
-- TOC entry 1976 (class 2606 OID 83988)
-- Dependencies: 1635 1635
-- Name: application_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY application
    ADD CONSTRAINT application_pkey PRIMARY KEY (application_id);


--
-- TOC entry 1980 (class 2606 OID 84001)
-- Dependencies: 1637 1637
-- Name: attachment_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY attachment
    ADD CONSTRAINT attachment_pkey PRIMARY KEY (attachment_id);


--
-- TOC entry 1982 (class 2606 OID 84009)
-- Dependencies: 1638 1638
-- Name: client_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY client
    ADD CONSTRAINT client_pkey PRIMARY KEY (client_id);


--
-- TOC entry 1984 (class 2606 OID 84017)
-- Dependencies: 1639 1639
-- Name: comments_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY comments
    ADD CONSTRAINT comments_pkey PRIMARY KEY (commentid);


--
-- TOC entry 1986 (class 2606 OID 84025)
-- Dependencies: 1640 1640
-- Name: dashboard_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY dashboard
    ADD CONSTRAINT dashboard_pkey PRIMARY KEY (dashboardid);


--
-- TOC entry 1988 (class 2606 OID 84035)
-- Dependencies: 1641 1641
-- Name: email_email_key; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY email
    ADD CONSTRAINT email_email_key UNIQUE (email);


--
-- TOC entry 1990 (class 2606 OID 84033)
-- Dependencies: 1641 1641
-- Name: email_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY email
    ADD CONSTRAINT email_pkey PRIMARY KEY (email_id);


--
-- TOC entry 1992 (class 2606 OID 84043)
-- Dependencies: 1642 1642
-- Name: emaillist_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY emaillist
    ADD CONSTRAINT emaillist_pkey PRIMARY KEY (id_list);


--
-- TOC entry 1994 (class 2606 OID 84048)
-- Dependencies: 1643 1643
-- Name: emailsubscribe_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY emailsubscribe
    ADD CONSTRAINT emailsubscribe_pkey PRIMARY KEY (id_subscribe);


--
-- TOC entry 1996 (class 2606 OID 84056)
-- Dependencies: 1644 1644
-- Name: gadget_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY gadget
    ADD CONSTRAINT gadget_pkey PRIMARY KEY (gadgetid);


--
-- TOC entry 1998 (class 2606 OID 84064)
-- Dependencies: 1645 1645
-- Name: gadget_properties_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY gadget_properties
    ADD CONSTRAINT gadget_properties_pkey PRIMARY KEY (propertyid);


--
-- TOC entry 2002 (class 2606 OID 84080)
-- Dependencies: 1647 1647
-- Name: geopoint_folder_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY geopoint_folder
    ADD CONSTRAINT geopoint_folder_pkey PRIMARY KEY (locate_folder_id);


--
-- TOC entry 2000 (class 2606 OID 84072)
-- Dependencies: 1646 1646
-- Name: geopoint_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY geopoint
    ADD CONSTRAINT geopoint_pkey PRIMARY KEY (locate_id);


--
-- TOC entry 2004 (class 2606 OID 84085)
-- Dependencies: 1648 1648
-- Name: geopoint_type_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY geopoint_type
    ADD CONSTRAINT geopoint_type_pkey PRIMARY KEY (loc_id_type);


--
-- TOC entry 2006 (class 2606 OID 84090)
-- Dependencies: 1649 1649 1649
-- Name: group_permission_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY group_permission
    ADD CONSTRAINT group_permission_pkey PRIMARY KEY (sec_id_permission, sec_id_group);


--
-- TOC entry 2010 (class 2606 OID 84103)
-- Dependencies: 1651 1651 1651
-- Name: groups_permission_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY groups_permission
    ADD CONSTRAINT groups_permission_pkey PRIMARY KEY (sec_id_group, sec_id_permission);


--
-- TOC entry 2008 (class 2606 OID 84098)
-- Dependencies: 1650 1650
-- Name: groups_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY groups
    ADD CONSTRAINT groups_pkey PRIMARY KEY (group_id);


--
-- TOC entry 2012 (class 2606 OID 84108)
-- Dependencies: 1652 1652
-- Name: hash_tags_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY hash_tags
    ADD CONSTRAINT hash_tags_pkey PRIMARY KEY (hash_tag_id);


--
-- TOC entry 2014 (class 2606 OID 84113)
-- Dependencies: 1653 1653
-- Name: hits_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY hits
    ADD CONSTRAINT hits_pkey PRIMARY KEY (hit_id);


--
-- TOC entry 2016 (class 2606 OID 84121)
-- Dependencies: 1654 1654
-- Name: notification_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY notification
    ADD CONSTRAINT notification_pkey PRIMARY KEY (notification_id);


--
-- TOC entry 2018 (class 2606 OID 84129)
-- Dependencies: 1655 1655
-- Name: permission_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY permission
    ADD CONSTRAINT permission_pkey PRIMARY KEY (id_permission);


--
-- TOC entry 2024 (class 2606 OID 84144)
-- Dependencies: 1657 1657
-- Name: poll_folder_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY poll_folder
    ADD CONSTRAINT poll_folder_pkey PRIMARY KEY (pollfolderid);


--
-- TOC entry 2020 (class 2606 OID 84137)
-- Dependencies: 1656 1656
-- Name: poll_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY poll
    ADD CONSTRAINT poll_pkey PRIMARY KEY (poll_id);


--
-- TOC entry 2022 (class 2606 OID 84139)
-- Dependencies: 1656 1656
-- Name: poll_poll_hash_key; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY poll
    ADD CONSTRAINT poll_poll_hash_key UNIQUE (poll_hash);


--
-- TOC entry 2026 (class 2606 OID 84149)
-- Dependencies: 1658 1658
-- Name: poll_result_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY poll_result
    ADD CONSTRAINT poll_result_pkey PRIMARY KEY (poll_resultid);


--
-- TOC entry 2030 (class 2606 OID 84162)
-- Dependencies: 1660 1660 1660
-- Name: project_geopoint_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY project_geopoint
    ADD CONSTRAINT project_geopoint_pkey PRIMARY KEY (cat_id_project, cat_id_loc);


--
-- TOC entry 2032 (class 2606 OID 84167)
-- Dependencies: 1661 1661 1661
-- Name: project_group_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY project_group
    ADD CONSTRAINT project_group_pkey PRIMARY KEY (cat_id_project, sec_id_group);


--
-- TOC entry 2034 (class 2606 OID 84172)
-- Dependencies: 1662 1662 1662
-- Name: project_locations_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY project_locations
    ADD CONSTRAINT project_locations_pkey PRIMARY KEY (cat_id_loc, cat_id_project);


--
-- TOC entry 2028 (class 2606 OID 84157)
-- Dependencies: 1659 1659
-- Name: project_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY project
    ADD CONSTRAINT project_pkey PRIMARY KEY (project_id);


--
-- TOC entry 2036 (class 2606 OID 84177)
-- Dependencies: 1663 1663
-- Name: question_category_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY question_category
    ADD CONSTRAINT question_category_pkey PRIMARY KEY (qcategory);


--
-- TOC entry 2038 (class 2606 OID 84182)
-- Dependencies: 1664 1664 1664
-- Name: question_category_questions_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY question_category_questions
    ADD CONSTRAINT question_category_questions_pkey PRIMARY KEY (question_category_qcategory, questionlibrary_qid);


--
-- TOC entry 2040 (class 2606 OID 84187)
-- Dependencies: 1665 1665
-- Name: question_collection_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY question_collection
    ADD CONSTRAINT question_collection_pkey PRIMARY KEY (id_q_colection);


--
-- TOC entry 2042 (class 2606 OID 84192)
-- Dependencies: 1666 1666
-- Name: question_dependence_survey_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY question_dependence_survey
    ADD CONSTRAINT question_dependence_survey_pkey PRIMARY KEY (question_dependence_survey);


--
-- TOC entry 2044 (class 2606 OID 84197)
-- Dependencies: 1667 1667 1667
-- Name: question_relations_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY question_relations
    ADD CONSTRAINT question_relations_pkey PRIMARY KEY (question_id, id_q_colection);


--
-- TOC entry 2048 (class 2606 OID 84213)
-- Dependencies: 1669 1669
-- Name: questions_answers_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY questions_answers
    ADD CONSTRAINT questions_answers_pkey PRIMARY KEY (q_answer_id);


--
-- TOC entry 2050 (class 2606 OID 84220)
-- Dependencies: 1670 1670
-- Name: questions_dependencies_descriptiondependence_key; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY questions_dependencies
    ADD CONSTRAINT questions_dependencies_descriptiondependence_key UNIQUE (descriptiondependence);


--
-- TOC entry 2052 (class 2606 OID 84218)
-- Dependencies: 1670 1670
-- Name: questions_dependencies_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY questions_dependencies
    ADD CONSTRAINT questions_dependencies_pkey PRIMARY KEY (question_dependenceid);


--
-- TOC entry 2054 (class 2606 OID 84222)
-- Dependencies: 1670 1670
-- Name: questions_dependencies_questionid_from_key; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY questions_dependencies
    ADD CONSTRAINT questions_dependencies_questionid_from_key UNIQUE (questionid_from);


--
-- TOC entry 2056 (class 2606 OID 84224)
-- Dependencies: 1670 1670
-- Name: questions_dependencies_questionid_to_key; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY questions_dependencies
    ADD CONSTRAINT questions_dependencies_questionid_to_key UNIQUE (questionid_to);


--
-- TOC entry 2058 (class 2606 OID 84232)
-- Dependencies: 1671 1671
-- Name: questions_pattern_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY questions_pattern
    ADD CONSTRAINT questions_pattern_pkey PRIMARY KEY (pattenr_id);


--
-- TOC entry 2046 (class 2606 OID 84205)
-- Dependencies: 1668 1668
-- Name: questions_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY questions
    ADD CONSTRAINT questions_pkey PRIMARY KEY (qid);


--
-- TOC entry 2060 (class 2606 OID 84240)
-- Dependencies: 1672 1672
-- Name: social_account_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY social_account
    ADD CONSTRAINT social_account_pkey PRIMARY KEY (social_account_id);


--
-- TOC entry 2062 (class 2606 OID 84244)
-- Dependencies: 1672 1672
-- Name: social_account_social_account_name_key; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY social_account
    ADD CONSTRAINT social_account_social_account_name_key UNIQUE (social_account_name);


--
-- TOC entry 2064 (class 2606 OID 84242)
-- Dependencies: 1672 1672
-- Name: social_account_social_profile_id_key; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY social_account
    ADD CONSTRAINT social_account_social_profile_id_key UNIQUE (social_profile_id);


--
-- TOC entry 2066 (class 2606 OID 84249)
-- Dependencies: 1673 1673
-- Name: survey_folder_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY survey_folder
    ADD CONSTRAINT survey_folder_pkey PRIMARY KEY (survey_folderid);


--
-- TOC entry 2068 (class 2606 OID 84254)
-- Dependencies: 1674 1674
-- Name: survey_format_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY survey_format
    ADD CONSTRAINT survey_format_pkey PRIMARY KEY (id_sid_format);


--
-- TOC entry 2072 (class 2606 OID 84264)
-- Dependencies: 1676 1676 1676
-- Name: survey_group_format_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY survey_group_format
    ADD CONSTRAINT survey_group_format_pkey PRIMARY KEY (id_sid_format, sg_id);


--
-- TOC entry 2070 (class 2606 OID 84259)
-- Dependencies: 1675 1675
-- Name: survey_group_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY survey_group
    ADD CONSTRAINT survey_group_pkey PRIMARY KEY (sg_id);


--
-- TOC entry 2074 (class 2606 OID 84269)
-- Dependencies: 1677 1677 1677
-- Name: survey_group_project_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY survey_group_project
    ADD CONSTRAINT survey_group_project_pkey PRIMARY KEY (cat_id_project, id_sid_format);


--
-- TOC entry 2076 (class 2606 OID 84274)
-- Dependencies: 1678 1678
-- Name: survey_pagination_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY survey_pagination
    ADD CONSTRAINT survey_pagination_pkey PRIMARY KEY (pagination_id);


--
-- TOC entry 2078 (class 2606 OID 84279)
-- Dependencies: 1679 1679
-- Name: survey_result_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY survey_result
    ADD CONSTRAINT survey_result_pkey PRIMARY KEY (rid);


--
-- TOC entry 2080 (class 2606 OID 84284)
-- Dependencies: 1680 1680
-- Name: survey_section_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY survey_section
    ADD CONSTRAINT survey_section_pkey PRIMARY KEY (ssid);


--
-- TOC entry 2082 (class 2606 OID 84289)
-- Dependencies: 1681 1681 1681
-- Name: survey_section_questions_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY survey_section_questions
    ADD CONSTRAINT survey_section_questions_pkey PRIMARY KEY (survey_section_ssid, questionsection_qid);


--
-- TOC entry 2084 (class 2606 OID 84297)
-- Dependencies: 1682 1682
-- Name: surveys_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY surveys
    ADD CONSTRAINT surveys_pkey PRIMARY KEY (sid);


--
-- TOC entry 2088 (class 2606 OID 84307)
-- Dependencies: 1684 1684
-- Name: tweetpoll_folder_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY tweetpoll_folder
    ADD CONSTRAINT tweetpoll_folder_pkey PRIMARY KEY (tweetpollfolderid);


--
-- TOC entry 2092 (class 2606 OID 84320)
-- Dependencies: 1686 1686 1686
-- Name: tweetpoll_hashtags_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY tweetpoll_hashtags
    ADD CONSTRAINT tweetpoll_hashtags_pkey PRIMARY KEY (hastag_id, tweetpoll_id);


--
-- TOC entry 2086 (class 2606 OID 84302)
-- Dependencies: 1683 1683
-- Name: tweetpoll_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY tweetpoll
    ADD CONSTRAINT tweetpoll_pkey PRIMARY KEY (tweet_poll_id);


--
-- TOC entry 2094 (class 2606 OID 84325)
-- Dependencies: 1687 1687
-- Name: tweetpoll_result_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY tweetpoll_result
    ADD CONSTRAINT tweetpoll_result_pkey PRIMARY KEY (tweetpoll_resultid);


--
-- TOC entry 2090 (class 2606 OID 84315)
-- Dependencies: 1685 1685
-- Name: tweetpoll_save_published_status_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY tweetpoll_save_published_status
    ADD CONSTRAINT tweetpoll_save_published_status_pkey PRIMARY KEY (status_save_poll_id);


--
-- TOC entry 2096 (class 2606 OID 84333)
-- Dependencies: 1688 1688
-- Name: tweetpoll_switch_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY tweetpoll_switch
    ADD CONSTRAINT tweetpoll_switch_pkey PRIMARY KEY (tweetpoll_switch_id);


--
-- TOC entry 2098 (class 2606 OID 84335)
-- Dependencies: 1688 1688
-- Name: tweetpoll_switch_tweet_code_key; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY tweetpoll_switch
    ADD CONSTRAINT tweetpoll_switch_tweet_code_key UNIQUE (tweet_code);


--
-- TOC entry 2101 (class 2606 OID 84345)
-- Dependencies: 1689 1689
-- Name: useraccount_email_key; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY useraccount
    ADD CONSTRAINT useraccount_email_key UNIQUE (email);


--
-- TOC entry 2110 (class 2606 OID 84354)
-- Dependencies: 1690 1690 1690
-- Name: useraccount_followers_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY useraccount_followers
    ADD CONSTRAINT useraccount_followers_pkey PRIMARY KEY (uid, uid_follower);


--
-- TOC entry 2112 (class 2606 OID 84359)
-- Dependencies: 1691 1691 1691
-- Name: useraccount_permission_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY useraccount_permission
    ADD CONSTRAINT useraccount_permission_pkey PRIMARY KEY (sec_id_permission, sec_id_secondary);


--
-- TOC entry 2103 (class 2606 OID 84343)
-- Dependencies: 1689 1689
-- Name: useraccount_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY useraccount
    ADD CONSTRAINT useraccount_pkey PRIMARY KEY (uid);


--
-- TOC entry 2114 (class 2606 OID 84364)
-- Dependencies: 1692 1692 1692
-- Name: useraccount_project_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY useraccount_project
    ADD CONSTRAINT useraccount_project_pkey PRIMARY KEY (cat_id_project, sec_id_secondary);


--
-- TOC entry 2105 (class 2606 OID 84347)
-- Dependencies: 1689 1689
-- Name: useraccount_username_key; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY useraccount
    ADD CONSTRAINT useraccount_username_key UNIQUE (username);


--
-- TOC entry 2107 (class 2606 OID 84349)
-- Dependencies: 1689 1689 1689
-- Name: useraccount_username_key1; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY useraccount
    ADD CONSTRAINT useraccount_username_key1 UNIQUE (username, email);


    --
-- TOC entry 2190 (class 2606 OID 84740)
-- Dependencies: 1681 1680 2079
-- Name: fk12354ece11057e56; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY survey_section_questions
    ADD CONSTRAINT fk12354ece11057e56 FOREIGN KEY (survey_section_ssid) REFERENCES survey_section(ssid);


--
-- TOC entry 2191 (class 2606 OID 84745)
-- Dependencies: 2045 1668 1681
-- Name: fk12354ece4e3a9df5; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY survey_section_questions
    ADD CONSTRAINT fk12354ece4e3a9df5 FOREIGN KEY (questionsection_qid) REFERENCES questions(qid);


--
-- TOC entry 2173 (class 2606 OID 84655)
-- Dependencies: 1665 2039 1667
-- Name: fk217954de893521da; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY question_relations
    ADD CONSTRAINT fk217954de893521da FOREIGN KEY (id_q_colection) REFERENCES question_collection(id_q_colection);


--
-- TOC entry 2174 (class 2606 OID 84660)
-- Dependencies: 2045 1668 1667
-- Name: fk217954de8a76a0bd; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY question_relations
    ADD CONSTRAINT fk217954de8a76a0bd FOREIGN KEY (question_id) REFERENCES questions(qid);


--
-- TOC entry 2152 (class 2606 OID 84550)
-- Dependencies: 1973 1634 1654
-- Name: fk237a88eb2b2a6ab4; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY notification
    ADD CONSTRAINT fk237a88eb2b2a6ab4 FOREIGN KEY (uid) REFERENCES account(uid);


--
-- TOC entry 2168 (class 2606 OID 84630)
-- Dependencies: 1662 1646 1999
-- Name: fk242951b835313189; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY project_locations
    ADD CONSTRAINT fk242951b835313189 FOREIGN KEY (cat_id_loc) REFERENCES geopoint(locate_id);


--
-- TOC entry 2167 (class 2606 OID 84625)
-- Dependencies: 1662 1659 2027
-- Name: fk242951b884536452; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY project_locations
    ADD CONSTRAINT fk242951b884536452 FOREIGN KEY (cat_id_project) REFERENCES project(project_id);


--
-- TOC entry 2164 (class 2606 OID 84610)
-- Dependencies: 1646 1660 1999
-- Name: fk2599132535313189; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY project_geopoint
    ADD CONSTRAINT fk2599132535313189 FOREIGN KEY (cat_id_loc) REFERENCES geopoint(locate_id);


--
-- TOC entry 2163 (class 2606 OID 84605)
-- Dependencies: 1659 1660 2027
-- Name: fk2599132584536452; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY project_geopoint
    ADD CONSTRAINT fk2599132584536452 FOREIGN KEY (cat_id_project) REFERENCES project(project_id);


--
-- TOC entry 2170 (class 2606 OID 84640)
-- Dependencies: 1664 1668 2045
-- Name: fk2ffe18457a068cb; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY question_category_questions
    ADD CONSTRAINT fk2ffe18457a068cb FOREIGN KEY (questionlibrary_qid) REFERENCES questions(qid);


--
-- TOC entry 2169 (class 2606 OID 84635)
-- Dependencies: 1664 1663 2035
-- Name: fk2ffe1845b10e79be; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY question_category_questions
    ADD CONSTRAINT fk2ffe1845b10e79be FOREIGN KEY (question_category_qcategory) REFERENCES question_category(qcategory);


--
-- TOC entry 2148 (class 2606 OID 84530)
-- Dependencies: 2011 1652 1653
-- Name: fk30df4019aa125; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY hits
    ADD CONSTRAINT fk30df4019aa125 FOREIGN KEY (hashtag_hash_tag_id) REFERENCES hash_tags(hash_tag_id);


--
-- TOC entry 2150 (class 2606 OID 84540)
-- Dependencies: 1653 2083 1682
-- Name: fk30df4051153812; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY hits
    ADD CONSTRAINT fk30df4051153812 FOREIGN KEY (survey_sid) REFERENCES surveys(sid);


--
-- TOC entry 2149 (class 2606 OID 84535)
-- Dependencies: 2019 1656 1653
-- Name: fk30df4063976e9; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY hits
    ADD CONSTRAINT fk30df4063976e9 FOREIGN KEY (poll_poll_id) REFERENCES poll(poll_id);


--
-- TOC entry 2151 (class 2606 OID 84545)
-- Dependencies: 2085 1683 1653
-- Name: fk30df40953c854b; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY hits
    ADD CONSTRAINT fk30df40953c854b FOREIGN KEY (tweetpoll_tweet_poll_id) REFERENCES tweetpoll(tweet_poll_id);


--
-- TOC entry 2155 (class 2606 OID 84565)
-- Dependencies: 2045 1668 1656
-- Name: fk3497bf50fe71f5; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY poll
    ADD CONSTRAINT fk3497bf50fe71f5 FOREIGN KEY (qid) REFERENCES questions(qid);


--
-- TOC entry 2154 (class 2606 OID 84560)
-- Dependencies: 1656 2023 1657
-- Name: fk3497bf89452cca; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY poll
    ADD CONSTRAINT fk3497bf89452cca FOREIGN KEY (poll_folder) REFERENCES poll_folder(pollfolderid);


--
-- TOC entry 2156 (class 2606 OID 84570)
-- Dependencies: 1689 2102 1656
-- Name: fk3497bfa64fb606; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY poll
    ADD CONSTRAINT fk3497bfa64fb606 FOREIGN KEY (editor) REFERENCES useraccount(uid);


--
-- TOC entry 2153 (class 2606 OID 84555)
-- Dependencies: 1689 1656 2102
-- Name: fk3497bff44558e9; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY poll
    ADD CONSTRAINT fk3497bff44558e9 FOREIGN KEY (uid) REFERENCES useraccount(uid);


--
-- TOC entry 2144 (class 2606 OID 84510)
-- Dependencies: 2017 1649 1655
-- Name: fk362e6f8f43adb63d; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY group_permission
    ADD CONSTRAINT fk362e6f8f43adb63d FOREIGN KEY (sec_id_permission) REFERENCES permission(id_permission);


--
-- TOC entry 2143 (class 2606 OID 84505)
-- Dependencies: 2007 1650 1649
-- Name: fk362e6f8f45895aff; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY group_permission
    ADD CONSTRAINT fk362e6f8f45895aff FOREIGN KEY (sec_id_group) REFERENCES groups(group_id);


--
-- TOC entry 2132 (class 2606 OID 84450)
-- Dependencies: 1643 1989 1641
-- Name: fk4b85010ee824035; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY emailsubscribe
    ADD CONSTRAINT fk4b85010ee824035 FOREIGN KEY (email_id) REFERENCES email(email_id);


--
-- TOC entry 2131 (class 2606 OID 84445)
-- Dependencies: 1991 1643 1642
-- Name: fk4b85010eed78e617; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY emailsubscribe
    ADD CONSTRAINT fk4b85010eed78e617 FOREIGN KEY (id_list) REFERENCES emaillist(id_list);


--
-- TOC entry 2179 (class 2606 OID 84685)
-- Dependencies: 1672 1973 1634
-- Name: fk50078b5b5ece45a2; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY social_account
    ADD CONSTRAINT fk50078b5b5ece45a2 FOREIGN KEY (account_uid) REFERENCES account(uid);


--
-- TOC entry 2180 (class 2606 OID 84690)
-- Dependencies: 1672 2102 1689
-- Name: fk50078b5bf2f411f2; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY social_account
    ADD CONSTRAINT fk50078b5bf2f411f2 FOREIGN KEY (userowner_uid) REFERENCES useraccount(uid);


--
-- TOC entry 2142 (class 2606 OID 84500)
-- Dependencies: 1973 1634 1648
-- Name: fk514326ba4075e3fd; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY geopoint_type
    ADD CONSTRAINT fk514326ba4075e3fd FOREIGN KEY (users_uid) REFERENCES account(uid);


--
-- TOC entry 2177 (class 2606 OID 84675)
-- Dependencies: 2045 1669 1668
-- Name: fk539703837e6c7bbc; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY questions_answers
    ADD CONSTRAINT fk539703837e6c7bbc FOREIGN KEY (id_question_answer) REFERENCES questions(qid);


--
-- TOC entry 2129 (class 2606 OID 84435)
-- Dependencies: 1991 1642 1641
-- Name: fk5c24b9ced78e617; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY email
    ADD CONSTRAINT fk5c24b9ced78e617 FOREIGN KEY (id_list) REFERENCES emaillist(id_list);


--
-- TOC entry 2119 (class 2606 OID 84385)
-- Dependencies: 1635 1634 1973
-- Name: fk5ca405505ece45a2; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY application
    ADD CONSTRAINT fk5ca405505ece45a2 FOREIGN KEY (account_uid) REFERENCES account(uid);


--
-- TOC entry 2138 (class 2606 OID 84480)
-- Dependencies: 1646 1647 2001
-- Name: fk6c73c0bf34ef9a43; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY geopoint
    ADD CONSTRAINT fk6c73c0bf34ef9a43 FOREIGN KEY (geopointfolder_locate_folder_id) REFERENCES geopoint_folder(locate_folder_id);


--
-- TOC entry 2137 (class 2606 OID 84475)
-- Dependencies: 1634 1646 1973
-- Name: fk6c73c0bf5ece45a2; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY geopoint
    ADD CONSTRAINT fk6c73c0bf5ece45a2 FOREIGN KEY (account_uid) REFERENCES account(uid);


--
-- TOC entry 2136 (class 2606 OID 84470)
-- Dependencies: 2003 1646 1648
-- Name: fk6c73c0bfbd91661d; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY geopoint
    ADD CONSTRAINT fk6c73c0bfbd91661d FOREIGN KEY (loc_id_type) REFERENCES geopoint_type(loc_id_type);


--
-- TOC entry 2120 (class 2606 OID 84390)
-- Dependencies: 1636 1689 2102
-- Name: fk73d5d2d27e933d7; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY application_connection
    ADD CONSTRAINT fk73d5d2d27e933d7 FOREIGN KEY (account_uid) REFERENCES useraccount(uid);


--
-- TOC entry 2121 (class 2606 OID 84395)
-- Dependencies: 1635 1636 1975
-- Name: fk73d5d2d4402be26; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY application_connection
    ADD CONSTRAINT fk73d5d2d4402be26 FOREIGN KEY (application_application_id) REFERENCES application(application_id);


--
-- TOC entry 2130 (class 2606 OID 84440)
-- Dependencies: 1973 1634 1642
-- Name: fk7e5f425a2b2a6ab4; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY emaillist
    ADD CONSTRAINT fk7e5f425a2b2a6ab4 FOREIGN KEY (uid) REFERENCES account(uid);


--
-- TOC entry 2181 (class 2606 OID 84695)
-- Dependencies: 1673 1973 1634
-- Name: fk7ef958f32b2a6ab4; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY survey_folder
    ADD CONSTRAINT fk7ef958f32b2a6ab4 FOREIGN KEY (uid) REFERENCES account(uid);


--
-- TOC entry 2182 (class 2606 OID 84700)
-- Dependencies: 1673 1689 2102
-- Name: fk7ef958f36ef241e9; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY survey_folder
    ADD CONSTRAINT fk7ef958f36ef241e9 FOREIGN KEY (createdby_uid) REFERENCES useraccount(uid);


--
-- TOC entry 2147 (class 2606 OID 84525)
-- Dependencies: 2017 1655 1651
-- Name: fk7f1951a43adb63d; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY groups_permission
    ADD CONSTRAINT fk7f1951a43adb63d FOREIGN KEY (sec_id_permission) REFERENCES permission(id_permission);


--
-- TOC entry 2146 (class 2606 OID 84520)
-- Dependencies: 2007 1650 1651
-- Name: fk7f1951a45895aff; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY groups_permission
    ADD CONSTRAINT fk7f1951a45895aff FOREIGN KEY (sec_id_group) REFERENCES groups(group_id);


--
-- TOC entry 2212 (class 2606 OID 84852)
-- Dependencies: 1689 2102 1690
-- Name: fk7f1957f8e53fbc6; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY useraccount_followers
    ADD CONSTRAINT fk7f1957f8e53fbc6 FOREIGN KEY (uid_follower) REFERENCES useraccount(uid);


--
-- TOC entry 2211 (class 2606 OID 84847)
-- Dependencies: 2102 1689 1690
-- Name: fk7f1957f8f44558e9; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY useraccount_followers
    ADD CONSTRAINT fk7f1957f8f44558e9 FOREIGN KEY (uid) REFERENCES useraccount(uid);


--
-- TOC entry 2135 (class 2606 OID 84465)
-- Dependencies: 1995 1644 1645
-- Name: fk866b670629091b05; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY gadget_properties
    ADD CONSTRAINT fk866b670629091b05 FOREIGN KEY (gadget_gadgetid) REFERENCES gadget(gadgetid);


--
-- TOC entry 2134 (class 2606 OID 84460)
-- Dependencies: 1645 2102 1689
-- Name: fk866b6706369f8b2c; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY gadget_properties
    ADD CONSTRAINT fk866b6706369f8b2c FOREIGN KEY (useraccount_uid) REFERENCES useraccount(uid);


--
-- TOC entry 2206 (class 2606 OID 84820)
-- Dependencies: 1688 1687 2095
-- Name: fk8749c18cb9d39f98; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY tweetpoll_result
    ADD CONSTRAINT fk8749c18cb9d39f98 FOREIGN KEY (tweetpoll_switch_id) REFERENCES tweetpoll_switch(tweetpoll_switch_id);


--
-- TOC entry 2207 (class 2606 OID 84825)
-- Dependencies: 1683 1688 2085
-- Name: fk89f7b0a3550299a; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY tweetpoll_switch
    ADD CONSTRAINT fk89f7b0a3550299a FOREIGN KEY (tweet_poll_id) REFERENCES tweetpoll(tweet_poll_id);


--
-- TOC entry 2208 (class 2606 OID 84830)
-- Dependencies: 1669 1688 2047
-- Name: fk89f7b0a3ddd118b5; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY tweetpoll_switch
    ADD CONSTRAINT fk89f7b0a3ddd118b5 FOREIGN KEY (q_answer_id) REFERENCES questions_answers(q_answer_id);


--
-- TOC entry 2122 (class 2606 OID 84400)
-- Dependencies: 2027 1637 1659
-- Name: fk8af75923225a055; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY attachment
    ADD CONSTRAINT fk8af75923225a055 FOREIGN KEY (project_id) REFERENCES project(project_id);


--
-- TOC entry 2192 (class 2606 OID 84750)
-- Dependencies: 1973 1634 1682
-- Name: fk919144592b2a6ab4; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY surveys
    ADD CONSTRAINT fk919144592b2a6ab4 FOREIGN KEY (uid) REFERENCES account(uid);


--
-- TOC entry 2193 (class 2606 OID 84755)
-- Dependencies: 1682 2065 1673
-- Name: fk91914459a3c7a06a; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY surveys
    ADD CONSTRAINT fk91914459a3c7a06a FOREIGN KEY (survey_folder) REFERENCES survey_folder(survey_folderid);


--
-- TOC entry 2195 (class 2606 OID 84765)
-- Dependencies: 2102 1682 1689
-- Name: fk91914459a64fb606; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY surveys
    ADD CONSTRAINT fk91914459a64fb606 FOREIGN KEY (editor) REFERENCES useraccount(uid);


--
-- TOC entry 2194 (class 2606 OID 84760)
-- Dependencies: 1674 1682 2067
-- Name: fk91914459b1a6912c; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY surveys
    ADD CONSTRAINT fk91914459b1a6912c FOREIGN KEY (id_sid_format) REFERENCES survey_format(id_sid_format);


--
-- TOC entry 2178 (class 2606 OID 84680)
-- Dependencies: 1669 2047 1670
-- Name: fk92e86adbddd118b5; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY questions_dependencies
    ADD CONSTRAINT fk92e86adbddd118b5 FOREIGN KEY (q_answer_id) REFERENCES questions_answers(q_answer_id);


--
-- TOC entry 2189 (class 2606 OID 84735)
-- Dependencies: 2083 1682 1679
-- Name: fk92ea04a2eb8d35c9; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY survey_result
    ADD CONSTRAINT fk92ea04a2eb8d35c9 FOREIGN KEY (survey_id) REFERENCES surveys(sid);


--
-- TOC entry 2175 (class 2606 OID 84665)
-- Dependencies: 1668 1973 1634
-- Name: fk95c5414d2b2a6ab4; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY questions
    ADD CONSTRAINT fk95c5414d2b2a6ab4 FOREIGN KEY (uid) REFERENCES account(uid);


--
-- TOC entry 2176 (class 2606 OID 84670)
-- Dependencies: 1668 1671 2057
-- Name: fk95c5414d84133d82; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY questions
    ADD CONSTRAINT fk95c5414d84133d82 FOREIGN KEY (id_question_pattern) REFERENCES questions_pattern(pattenr_id);


--
-- TOC entry 2200 (class 2606 OID 84790)
-- Dependencies: 1684 1634 1973
-- Name: fka027a9dd2b2a6ab4; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY tweetpoll_folder
    ADD CONSTRAINT fka027a9dd2b2a6ab4 FOREIGN KEY (uid) REFERENCES account(uid);


--
-- TOC entry 2201 (class 2606 OID 84795)
-- Dependencies: 2102 1684 1689
-- Name: fka027a9dd6ef241e9; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY tweetpoll_folder
    ADD CONSTRAINT fka027a9dd6ef241e9 FOREIGN KEY (createdby_uid) REFERENCES useraccount(uid);


--
-- TOC entry 2196 (class 2606 OID 84770)
-- Dependencies: 1973 1683 1634
-- Name: fka65b1d02b2a6ab4; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY tweetpoll
    ADD CONSTRAINT fka65b1d02b2a6ab4 FOREIGN KEY (uid) REFERENCES account(uid);


--
-- TOC entry 2198 (class 2606 OID 84780)
-- Dependencies: 2045 1683 1668
-- Name: fka65b1d050fe71f5; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY tweetpoll
    ADD CONSTRAINT fka65b1d050fe71f5 FOREIGN KEY (qid) REFERENCES questions(qid);


--
-- TOC entry 2199 (class 2606 OID 84785)
-- Dependencies: 1683 1689 2102
-- Name: fka65b1d0a64fb606; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY tweetpoll
    ADD CONSTRAINT fka65b1d0a64fb606 FOREIGN KEY (editor) REFERENCES useraccount(uid);


--
-- TOC entry 2197 (class 2606 OID 84775)
-- Dependencies: 2087 1683 1684
-- Name: fka65b1d0d9ba7e54; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY tweetpoll
    ADD CONSTRAINT fka65b1d0d9ba7e54 FOREIGN KEY (tweetpollfolderid) REFERENCES tweetpoll_folder(tweetpollfolderid);


--
-- TOC entry 2209 (class 2606 OID 84835)
-- Dependencies: 1634 1689 1973
-- Name: fka7d56be25ece45a2; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY useraccount
    ADD CONSTRAINT fka7d56be25ece45a2 FOREIGN KEY (account_uid) REFERENCES account(uid);


--
-- TOC entry 2210 (class 2606 OID 84840)
-- Dependencies: 1650 1689 2007
-- Name: fka7d56be2b8eb1450; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY useraccount
    ADD CONSTRAINT fka7d56be2b8eb1450 FOREIGN KEY (groupid) REFERENCES groups(group_id);


--
-- TOC entry 2123 (class 2606 OID 84405)
-- Dependencies: 1638 2027 1659
-- Name: fkaf12f3cb225a055; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY client
    ADD CONSTRAINT fkaf12f3cb225a055 FOREIGN KEY (project_id) REFERENCES project(project_id);


--
-- TOC entry 2171 (class 2606 OID 84645)
-- Dependencies: 1634 1973 1665
-- Name: fkb4097c972b2a6ab4; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY question_collection
    ADD CONSTRAINT fkb4097c972b2a6ab4 FOREIGN KEY (uid) REFERENCES account(uid);


--
-- TOC entry 2183 (class 2606 OID 84705)
-- Dependencies: 1676 2069 1675
-- Name: fkb4df867c310e993c; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY survey_group_format
    ADD CONSTRAINT fkb4df867c310e993c FOREIGN KEY (sg_id) REFERENCES survey_group(sg_id);


--
-- TOC entry 2184 (class 2606 OID 84710)
-- Dependencies: 1674 1676 2067
-- Name: fkb4df867cb1a6912c; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY survey_group_format
    ADD CONSTRAINT fkb4df867cb1a6912c FOREIGN KEY (id_sid_format) REFERENCES survey_format(id_sid_format);


--
-- TOC entry 2133 (class 2606 OID 84455)
-- Dependencies: 1985 1644 1640
-- Name: fkb549144cb975b5f9; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY gadget
    ADD CONSTRAINT fkb549144cb975b5f9 FOREIGN KEY (dashboard_dashboardid) REFERENCES dashboard(dashboardid);


--
-- TOC entry 2145 (class 2606 OID 84515)
-- Dependencies: 1973 1650 1634
-- Name: fkb63dd9d45ece45a2; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY groups
    ADD CONSTRAINT fkb63dd9d45ece45a2 FOREIGN KEY (account_uid) REFERENCES account(uid);


--
-- TOC entry 2172 (class 2606 OID 84650)
-- Dependencies: 1666 2083 1682
-- Name: fkbb424d49793d9e77; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY question_dependence_survey
    ADD CONSTRAINT fkbb424d49793d9e77 FOREIGN KEY (sid) REFERENCES surveys(sid);


--
-- TOC entry 2213 (class 2606 OID 84857)
-- Dependencies: 1655 1691 2017
-- Name: fkbe01ce4c43adb63d; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY useraccount_permission
    ADD CONSTRAINT fkbe01ce4c43adb63d FOREIGN KEY (sec_id_permission) REFERENCES permission(id_permission);


--
-- TOC entry 2214 (class 2606 OID 84862)
-- Dependencies: 1691 1689 2102
-- Name: fkbe01ce4c5f77a117; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY useraccount_permission
    ADD CONSTRAINT fkbe01ce4c5f77a117 FOREIGN KEY (sec_id_secondary) REFERENCES useraccount(uid);


--
-- TOC entry 2188 (class 2606 OID 84730)
-- Dependencies: 1680 1678 2079
-- Name: fkbec9a99f1359b877; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY survey_pagination
    ADD CONSTRAINT fkbec9a99f1359b877 FOREIGN KEY (ssid) REFERENCES survey_section(ssid);


--
-- TOC entry 2187 (class 2606 OID 84725)
-- Dependencies: 1682 1678 2083
-- Name: fkbec9a99f793d9e77; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY survey_pagination
    ADD CONSTRAINT fkbec9a99f793d9e77 FOREIGN KEY (sid) REFERENCES surveys(sid);


--
-- TOC entry 2128 (class 2606 OID 84430)
-- Dependencies: 1640 2102 1689
-- Name: fkc18aea949229bca5; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY dashboard
    ADD CONSTRAINT fkc18aea949229bca5 FOREIGN KEY (userboard_uid) REFERENCES useraccount(uid);


--
-- TOC entry 2117 (class 2606 OID 84375)
-- Dependencies: 1633 1682 2083
-- Name: fkc2760edb51153812; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY access_rate
    ADD CONSTRAINT fkc2760edb51153812 FOREIGN KEY (survey_sid) REFERENCES surveys(sid);


--
-- TOC entry 2115 (class 2606 OID 84365)
-- Dependencies: 2019 1633 1656
-- Name: fkc2760edb63976e9; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY access_rate
    ADD CONSTRAINT fkc2760edb63976e9 FOREIGN KEY (poll_poll_id) REFERENCES poll(poll_id);


--
-- TOC entry 2118 (class 2606 OID 84380)
-- Dependencies: 1633 1683 2085
-- Name: fkc2760edb953c854b; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY access_rate
    ADD CONSTRAINT fkc2760edb953c854b FOREIGN KEY (tweetpoll_tweet_poll_id) REFERENCES tweetpoll(tweet_poll_id);


--
-- TOC entry 2116 (class 2606 OID 84370)
-- Dependencies: 1689 1633 2102
-- Name: fkc2760edbe4669675; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY access_rate
    ADD CONSTRAINT fkc2760edbe4669675 FOREIGN KEY (user_uid) REFERENCES useraccount(uid);


--
-- TOC entry 2157 (class 2606 OID 84575)
-- Dependencies: 1634 1657 1973
-- Name: fkc5911cee2b2a6ab4; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY poll_folder
    ADD CONSTRAINT fkc5911cee2b2a6ab4 FOREIGN KEY (uid) REFERENCES account(uid);


--
-- TOC entry 2158 (class 2606 OID 84580)
-- Dependencies: 1657 1689 2102
-- Name: fkc5911cee6ef241e9; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY poll_folder
    ADD CONSTRAINT fkc5911cee6ef241e9 FOREIGN KEY (createdby_uid) REFERENCES useraccount(uid);


--
-- TOC entry 2165 (class 2606 OID 84615)
-- Dependencies: 1650 2007 1661
-- Name: fkc7652dd945895aff; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY project_group
    ADD CONSTRAINT fkc7652dd945895aff FOREIGN KEY (sec_id_group) REFERENCES groups(group_id);


--
-- TOC entry 2166 (class 2606 OID 84620)
-- Dependencies: 1661 1659 2027
-- Name: fkc7652dd984536452; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY project_group
    ADD CONSTRAINT fkc7652dd984536452 FOREIGN KEY (cat_id_project) REFERENCES project(project_id);


--
-- TOC entry 2202 (class 2606 OID 84800)
-- Dependencies: 1672 1685 2059
-- Name: fkd499a4b65239d117; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY tweetpoll_save_published_status
    ADD CONSTRAINT fkd499a4b65239d117 FOREIGN KEY (socialaccount_social_account_id) REFERENCES social_account(social_account_id);


--
-- TOC entry 2203 (class 2606 OID 84805)
-- Dependencies: 1683 1685 2085
-- Name: fkd499a4b6953c854b; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY tweetpoll_save_published_status
    ADD CONSTRAINT fkd499a4b6953c854b FOREIGN KEY (tweetpoll_tweet_poll_id) REFERENCES tweetpoll(tweet_poll_id);


--
-- TOC entry 2159 (class 2606 OID 84585)
-- Dependencies: 1658 2047 1669
-- Name: fkd981c89dddd118b5; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY poll_result
    ADD CONSTRAINT fkd981c89dddd118b5 FOREIGN KEY (q_answer_id) REFERENCES questions_answers(q_answer_id);


--
-- TOC entry 2160 (class 2606 OID 84590)
-- Dependencies: 1656 2019 1658
-- Name: fkd981c89df0ed6769; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY poll_result
    ADD CONSTRAINT fkd981c89df0ed6769 FOREIGN KEY (poll_id) REFERENCES poll(poll_id);


--
-- TOC entry 2125 (class 2606 OID 84415)
-- Dependencies: 1639 1682 2083
-- Name: fkdc17ddf4793d9e77; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY comments
    ADD CONSTRAINT fkdc17ddf4793d9e77 FOREIGN KEY (sid) REFERENCES surveys(sid);


--
-- TOC entry 2126 (class 2606 OID 84420)
-- Dependencies: 2019 1639 1656
-- Name: fkdc17ddf4ce12cae8; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY comments
    ADD CONSTRAINT fkdc17ddf4ce12cae8 FOREIGN KEY (pollid) REFERENCES poll(poll_id);


--
-- TOC entry 2127 (class 2606 OID 84425)
-- Dependencies: 1639 2085 1683
-- Name: fkdc17ddf4d9aa8e98; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY comments
    ADD CONSTRAINT fkdc17ddf4d9aa8e98 FOREIGN KEY (tweetpollid) REFERENCES tweetpoll(tweet_poll_id);


--
-- TOC entry 2124 (class 2606 OID 84410)
-- Dependencies: 1689 1639 2102
-- Name: fkdc17ddf4f44558e9; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY comments
    ADD CONSTRAINT fkdc17ddf4f44558e9 FOREIGN KEY (uid) REFERENCES useraccount(uid);


--
-- TOC entry 2162 (class 2606 OID 84600)
-- Dependencies: 1634 1973 1659
-- Name: fked904b194075e3fd; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY project
    ADD CONSTRAINT fked904b194075e3fd FOREIGN KEY (users_uid) REFERENCES account(uid);


--
-- TOC entry 2161 (class 2606 OID 84595)
-- Dependencies: 2102 1689 1659
-- Name: fked904b19514c1986; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY project
    ADD CONSTRAINT fked904b19514c1986 FOREIGN KEY (lead_uid) REFERENCES useraccount(uid);


--
-- TOC entry 2139 (class 2606 OID 84485)
-- Dependencies: 1634 1973 1647
-- Name: fkf4a1d3ee2b2a6ab4; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY geopoint_folder
    ADD CONSTRAINT fkf4a1d3ee2b2a6ab4 FOREIGN KEY (uid) REFERENCES account(uid);


--
-- TOC entry 2141 (class 2606 OID 84495)
-- Dependencies: 1647 2001 1647
-- Name: fkf4a1d3ee6e4ed46d; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY geopoint_folder
    ADD CONSTRAINT fkf4a1d3ee6e4ed46d FOREIGN KEY (sublocationfolder_locate_folder_id) REFERENCES geopoint_folder(locate_folder_id);


--
-- TOC entry 2140 (class 2606 OID 84490)
-- Dependencies: 2102 1689 1647
-- Name: fkf4a1d3ee6ef241e9; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY geopoint_folder
    ADD CONSTRAINT fkf4a1d3ee6ef241e9 FOREIGN KEY (createdby_uid) REFERENCES useraccount(uid);


--
-- TOC entry 2204 (class 2606 OID 84810)
-- Dependencies: 1683 1686 2085
-- Name: fkf8c717d6286705d7; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY tweetpoll_hashtags
    ADD CONSTRAINT fkf8c717d6286705d7 FOREIGN KEY (tweetpoll_id) REFERENCES tweetpoll(tweet_poll_id);


--
-- TOC entry 2205 (class 2606 OID 84815)
-- Dependencies: 1652 1686 2011
-- Name: fkf8c717d6da98ffe1; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY tweetpoll_hashtags
    ADD CONSTRAINT fkf8c717d6da98ffe1 FOREIGN KEY (hastag_id) REFERENCES hash_tags(hash_tag_id);


--
-- TOC entry 2216 (class 2606 OID 84872)
-- Dependencies: 2102 1692 1689
-- Name: fkfbc45bbc5f77a117; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY useraccount_project
    ADD CONSTRAINT fkfbc45bbc5f77a117 FOREIGN KEY (sec_id_secondary) REFERENCES useraccount(uid);


--
-- TOC entry 2215 (class 2606 OID 84867)
-- Dependencies: 1692 2027 1659
-- Name: fkfbc45bbc84536452; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY useraccount_project
    ADD CONSTRAINT fkfbc45bbc84536452 FOREIGN KEY (cat_id_project) REFERENCES project(project_id);


--
-- TOC entry 2185 (class 2606 OID 84715)
-- Dependencies: 1677 1659 2027
-- Name: fkfd028d3484536452; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY survey_group_project
    ADD CONSTRAINT fkfd028d3484536452 FOREIGN KEY (cat_id_project) REFERENCES project(project_id);


--
-- TOC entry 2186 (class 2606 OID 84720)
-- Dependencies: 2069 1677 1675
-- Name: fkfd028d34b75f3482; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY survey_group_project
    ADD CONSTRAINT fkfd028d34b75f3482 FOREIGN KEY (id_sid_format) REFERENCES survey_group(sg_id);