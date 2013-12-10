
ALTER TABLE ONLY access_rate
    ADD CONSTRAINT access_rate_pkey PRIMARY KEY (rateid);


--
-- TOC entry 1974 (class 2606 OID 99433)
-- Dependencies: 1634 1634
-- Name: account_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY account
    ADD CONSTRAINT account_pkey PRIMARY KEY (uid);


--
-- TOC entry 1978 (class 2606 OID 99449)
-- Dependencies: 1636 1636
-- Name: application_connection_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY application_connection
    ADD CONSTRAINT application_connection_pkey PRIMARY KEY (connection_id);


--
-- TOC entry 1976 (class 2606 OID 99441)
-- Dependencies: 1635 1635
-- Name: application_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY application
    ADD CONSTRAINT application_pkey PRIMARY KEY (application_id);


--
-- TOC entry 1980 (class 2606 OID 99454)
-- Dependencies: 1637 1637
-- Name: attachment_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY attachment
    ADD CONSTRAINT attachment_pkey PRIMARY KEY (attachment_id);


--
-- TOC entry 1982 (class 2606 OID 99462)
-- Dependencies: 1638 1638
-- Name: client_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY client
    ADD CONSTRAINT client_pkey PRIMARY KEY (client_id);


--
-- TOC entry 1984 (class 2606 OID 99470)
-- Dependencies: 1639 1639
-- Name: comments_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY comments
    ADD CONSTRAINT comments_pkey PRIMARY KEY (commentid);


--
-- TOC entry 1986 (class 2606 OID 99478)
-- Dependencies: 1640 1640
-- Name: dashboard_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY dashboard
    ADD CONSTRAINT dashboard_pkey PRIMARY KEY (dashboardid);


--
-- TOC entry 1988 (class 2606 OID 99488)
-- Dependencies: 1641 1641
-- Name: email_email_key; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY email
    ADD CONSTRAINT email_email_key UNIQUE (email);


--
-- TOC entry 1990 (class 2606 OID 99486)
-- Dependencies: 1641 1641
-- Name: email_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY email
    ADD CONSTRAINT email_pkey PRIMARY KEY (email_id);


--
-- TOC entry 1992 (class 2606 OID 99496)
-- Dependencies: 1642 1642
-- Name: emaillist_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY emaillist
    ADD CONSTRAINT emaillist_pkey PRIMARY KEY (id_list);


--
-- TOC entry 1994 (class 2606 OID 99501)
-- Dependencies: 1643 1643
-- Name: emailsubscribe_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY emailsubscribe
    ADD CONSTRAINT emailsubscribe_pkey PRIMARY KEY (id_subscribe);


--
-- TOC entry 1996 (class 2606 OID 99509)
-- Dependencies: 1644 1644
-- Name: gadget_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY gadget
    ADD CONSTRAINT gadget_pkey PRIMARY KEY (gadgetid);


--
-- TOC entry 1998 (class 2606 OID 99517)
-- Dependencies: 1645 1645
-- Name: gadget_properties_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY gadget_properties
    ADD CONSTRAINT gadget_properties_pkey PRIMARY KEY (propertyid);


--
-- TOC entry 2002 (class 2606 OID 99533)
-- Dependencies: 1647 1647
-- Name: geopoint_folder_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY geopoint_folder
    ADD CONSTRAINT geopoint_folder_pkey PRIMARY KEY (locate_folder_id);


--
-- TOC entry 2000 (class 2606 OID 99525)
-- Dependencies: 1646 1646
-- Name: geopoint_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY geopoint
    ADD CONSTRAINT geopoint_pkey PRIMARY KEY (locate_id);


--
-- TOC entry 2004 (class 2606 OID 99538)
-- Dependencies: 1648 1648
-- Name: geopoint_type_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY geopoint_type
    ADD CONSTRAINT geopoint_type_pkey PRIMARY KEY (loc_id_type);


--
-- TOC entry 2006 (class 2606 OID 99543)
-- Dependencies: 1649 1649 1649
-- Name: group_permission_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY group_permission
    ADD CONSTRAINT group_permission_pkey PRIMARY KEY (sec_id_permission, sec_id_group);


--
-- TOC entry 2010 (class 2606 OID 99556)
-- Dependencies: 1651 1651 1651
-- Name: groups_permission_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY groups_permission
    ADD CONSTRAINT groups_permission_pkey PRIMARY KEY (sec_id_group, sec_id_permission);


--
-- TOC entry 2008 (class 2606 OID 99551)
-- Dependencies: 1650 1650
-- Name: groups_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY groups
    ADD CONSTRAINT groups_pkey PRIMARY KEY (group_id);


--
-- TOC entry 2012 (class 2606 OID 99561)
-- Dependencies: 1652 1652
-- Name: hash_tags_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY hash_tags
    ADD CONSTRAINT hash_tags_pkey PRIMARY KEY (hash_tag_id);


--
-- TOC entry 2014 (class 2606 OID 99566)
-- Dependencies: 1653 1653
-- Name: hits_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY hits
    ADD CONSTRAINT hits_pkey PRIMARY KEY (hit_id);


--
-- TOC entry 2016 (class 2606 OID 99574)
-- Dependencies: 1654 1654
-- Name: notification_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY notification
    ADD CONSTRAINT notification_pkey PRIMARY KEY (notification_id);


--
-- TOC entry 2018 (class 2606 OID 99582)
-- Dependencies: 1655 1655
-- Name: permission_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY permission
    ADD CONSTRAINT permission_pkey PRIMARY KEY (id_permission);


--
-- TOC entry 2024 (class 2606 OID 99597)
-- Dependencies: 1657 1657
-- Name: poll_folder_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY poll_folder
    ADD CONSTRAINT poll_folder_pkey PRIMARY KEY (pollfolderid);


--
-- TOC entry 2026 (class 2606 OID 99602)
-- Dependencies: 1658 1658 1658
-- Name: poll_hashtags_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY poll_hashtags
    ADD CONSTRAINT poll_hashtags_pkey PRIMARY KEY (poll_id, hastag_id);


--
-- TOC entry 2020 (class 2606 OID 99590)
-- Dependencies: 1656 1656
-- Name: poll_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY poll
    ADD CONSTRAINT poll_pkey PRIMARY KEY (poll_id);


--
-- TOC entry 2022 (class 2606 OID 99592)
-- Dependencies: 1656 1656
-- Name: poll_poll_hash_key; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY poll
    ADD CONSTRAINT poll_poll_hash_key UNIQUE (poll_hash);


--
-- TOC entry 2028 (class 2606 OID 99607)
-- Dependencies: 1659 1659
-- Name: poll_result_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY poll_result
    ADD CONSTRAINT poll_result_pkey PRIMARY KEY (poll_resultid);


--
-- TOC entry 2032 (class 2606 OID 99620)
-- Dependencies: 1661 1661 1661
-- Name: project_geopoint_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY project_geopoint
    ADD CONSTRAINT project_geopoint_pkey PRIMARY KEY (cat_id_project, cat_id_loc);


--
-- TOC entry 2034 (class 2606 OID 99625)
-- Dependencies: 1662 1662 1662
-- Name: project_group_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY project_group
    ADD CONSTRAINT project_group_pkey PRIMARY KEY (cat_id_project, sec_id_group);


--
-- TOC entry 2036 (class 2606 OID 99630)
-- Dependencies: 1663 1663 1663
-- Name: project_locations_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY project_locations
    ADD CONSTRAINT project_locations_pkey PRIMARY KEY (cat_id_loc, cat_id_project);


--
-- TOC entry 2030 (class 2606 OID 99615)
-- Dependencies: 1660 1660
-- Name: project_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY project
    ADD CONSTRAINT project_pkey PRIMARY KEY (project_id);


--
-- TOC entry 2038 (class 2606 OID 99635)
-- Dependencies: 1664 1664
-- Name: question_category_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY question_category
    ADD CONSTRAINT question_category_pkey PRIMARY KEY (qcategory);


--
-- TOC entry 2040 (class 2606 OID 99640)
-- Dependencies: 1665 1665 1665
-- Name: question_category_questions_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY question_category_questions
    ADD CONSTRAINT question_category_questions_pkey PRIMARY KEY (question_category_qcategory, questionlibrary_qid);


--
-- TOC entry 2042 (class 2606 OID 99645)
-- Dependencies: 1666 1666
-- Name: question_collection_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY question_collection
    ADD CONSTRAINT question_collection_pkey PRIMARY KEY (id_q_colection);


--
-- TOC entry 2044 (class 2606 OID 99650)
-- Dependencies: 1667 1667
-- Name: question_dependence_survey_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY question_dependence_survey
    ADD CONSTRAINT question_dependence_survey_pkey PRIMARY KEY (question_dependence_survey);


--
-- TOC entry 2046 (class 2606 OID 99655)
-- Dependencies: 1668 1668 1668
-- Name: question_relations_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY question_relations
    ADD CONSTRAINT question_relations_pkey PRIMARY KEY (question_id, id_q_colection);


--
-- TOC entry 2050 (class 2606 OID 99671)
-- Dependencies: 1670 1670
-- Name: questions_answers_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY questions_answers
    ADD CONSTRAINT questions_answers_pkey PRIMARY KEY (q_answer_id);


--
-- TOC entry 2052 (class 2606 OID 99678)
-- Dependencies: 1671 1671
-- Name: questions_dependencies_descriptiondependence_key; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY questions_dependencies
    ADD CONSTRAINT questions_dependencies_descriptiondependence_key UNIQUE (descriptiondependence);


--
-- TOC entry 2054 (class 2606 OID 99676)
-- Dependencies: 1671 1671
-- Name: questions_dependencies_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY questions_dependencies
    ADD CONSTRAINT questions_dependencies_pkey PRIMARY KEY (question_dependenceid);


--
-- TOC entry 2056 (class 2606 OID 99680)
-- Dependencies: 1671 1671
-- Name: questions_dependencies_questionid_from_key; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY questions_dependencies
    ADD CONSTRAINT questions_dependencies_questionid_from_key UNIQUE (questionid_from);


--
-- TOC entry 2058 (class 2606 OID 99682)
-- Dependencies: 1671 1671
-- Name: questions_dependencies_questionid_to_key; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY questions_dependencies
    ADD CONSTRAINT questions_dependencies_questionid_to_key UNIQUE (questionid_to);


--
-- TOC entry 2060 (class 2606 OID 99690)
-- Dependencies: 1672 1672
-- Name: questions_pattern_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY questions_pattern
    ADD CONSTRAINT questions_pattern_pkey PRIMARY KEY (pattenr_id);


--
-- TOC entry 2048 (class 2606 OID 99663)
-- Dependencies: 1669 1669
-- Name: questions_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY questions
    ADD CONSTRAINT questions_pkey PRIMARY KEY (qid);


--
-- TOC entry 2062 (class 2606 OID 99698)
-- Dependencies: 1673 1673
-- Name: social_account_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY social_account
    ADD CONSTRAINT social_account_pkey PRIMARY KEY (social_account_id);


--
-- TOC entry 2064 (class 2606 OID 99702)
-- Dependencies: 1673 1673
-- Name: social_account_social_account_name_key; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY social_account
    ADD CONSTRAINT social_account_social_account_name_key UNIQUE (social_account_name);

--
-- TOC entry 2068 (class 2606 OID 99707)
-- Dependencies: 1674 1674
-- Name: survey_folder_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY survey_folder
    ADD CONSTRAINT survey_folder_pkey PRIMARY KEY (survey_folderid);


--
-- TOC entry 2070 (class 2606 OID 99712)
-- Dependencies: 1675 1675
-- Name: survey_format_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY survey_format
    ADD CONSTRAINT survey_format_pkey PRIMARY KEY (id_sid_format);


--
-- TOC entry 2074 (class 2606 OID 99722)
-- Dependencies: 1677 1677 1677
-- Name: survey_group_format_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY survey_group_format
    ADD CONSTRAINT survey_group_format_pkey PRIMARY KEY (id_sid_format, sg_id);


--
-- TOC entry 2072 (class 2606 OID 99717)
-- Dependencies: 1676 1676
-- Name: survey_group_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY survey_group
    ADD CONSTRAINT survey_group_pkey PRIMARY KEY (sg_id);


--
-- TOC entry 2076 (class 2606 OID 99727)
-- Dependencies: 1678 1678 1678
-- Name: survey_group_project_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY survey_group_project
    ADD CONSTRAINT survey_group_project_pkey PRIMARY KEY (cat_id_project, id_sid_format);


--
-- TOC entry 2078 (class 2606 OID 99732)
-- Dependencies: 1679 1679
-- Name: survey_pagination_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY survey_pagination
    ADD CONSTRAINT survey_pagination_pkey PRIMARY KEY (pagination_id);


--
-- TOC entry 2080 (class 2606 OID 99737)
-- Dependencies: 1680 1680
-- Name: survey_result_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY survey_result
    ADD CONSTRAINT survey_result_pkey PRIMARY KEY (rid);


--
-- TOC entry 2082 (class 2606 OID 99742)
-- Dependencies: 1681 1681
-- Name: survey_section_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY survey_section
    ADD CONSTRAINT survey_section_pkey PRIMARY KEY (ssid);


--
-- TOC entry 2084 (class 2606 OID 99750)
-- Dependencies: 1682 1682
-- Name: surveys_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY surveys
    ADD CONSTRAINT surveys_pkey PRIMARY KEY (sid);


--
-- TOC entry 2088 (class 2606 OID 99760)
-- Dependencies: 1684 1684
-- Name: tweetpoll_folder_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY tweetpoll_folder
    ADD CONSTRAINT tweetpoll_folder_pkey PRIMARY KEY (tweetpollfolderid);


--
-- TOC entry 2092 (class 2606 OID 99773)
-- Dependencies: 1686 1686 1686
-- Name: tweetpoll_hashtags_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY tweetpoll_hashtags
    ADD CONSTRAINT tweetpoll_hashtags_pkey PRIMARY KEY (hastag_id, tweetpoll_id);


--
-- TOC entry 2086 (class 2606 OID 99755)
-- Dependencies: 1683 1683
-- Name: tweetpoll_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY tweetpoll
    ADD CONSTRAINT tweetpoll_pkey PRIMARY KEY (tweet_poll_id);


--
-- TOC entry 2094 (class 2606 OID 99778)
-- Dependencies: 1687 1687
-- Name: tweetpoll_result_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY tweetpoll_result
    ADD CONSTRAINT tweetpoll_result_pkey PRIMARY KEY (tweetpoll_resultid);


--
-- TOC entry 2090 (class 2606 OID 99768)
-- Dependencies: 1685 1685
-- Name: tweetpoll_save_published_status_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY tweetpoll_save_published_status
    ADD CONSTRAINT tweetpoll_save_published_status_pkey PRIMARY KEY (status_save_poll_id);


--
-- TOC entry 2096 (class 2606 OID 99786)
-- Dependencies: 1688 1688
-- Name: tweetpoll_switch_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY tweetpoll_switch
    ADD CONSTRAINT tweetpoll_switch_pkey PRIMARY KEY (tweetpoll_switch_id);


--
-- TOC entry 2098 (class 2606 OID 99788)
-- Dependencies: 1688 1688
-- Name: tweetpoll_switch_tweet_code_key; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY tweetpoll_switch
    ADD CONSTRAINT tweetpoll_switch_tweet_code_key UNIQUE (tweet_code);


--
-- TOC entry 2101 (class 2606 OID 99798)
-- Dependencies: 1689 1689
-- Name: useraccount_email_key; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY useraccount
    ADD CONSTRAINT useraccount_email_key UNIQUE (email);


--
-- TOC entry 2110 (class 2606 OID 99808)
-- Dependencies: 1690 1690 1690
-- Name: useraccount_followers_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY useraccount_followers
    ADD CONSTRAINT useraccount_followers_pkey PRIMARY KEY (uid, uid_follower);


--
-- TOC entry 2112 (class 2606 OID 99813)
-- Dependencies: 1691 1691 1691
-- Name: useraccount_permission_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY useraccount_permission
    ADD CONSTRAINT useraccount_permission_pkey PRIMARY KEY (sec_id_permission, sec_id_secondary);


--
-- TOC entry 2103 (class 2606 OID 99796)
-- Dependencies: 1689 1689
-- Name: useraccount_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY useraccount
    ADD CONSTRAINT useraccount_pkey PRIMARY KEY (uid);


--
-- TOC entry 2114 (class 2606 OID 99818)
-- Dependencies: 1692 1692 1692
-- Name: useraccount_project_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY useraccount_project
    ADD CONSTRAINT useraccount_project_pkey PRIMARY KEY (cat_id_project, sec_id_secondary);


--
-- TOC entry 2105 (class 2606 OID 99800)
-- Dependencies: 1689 1689
-- Name: useraccount_username_key; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY useraccount
    ADD CONSTRAINT useraccount_username_key UNIQUE (username);


--
-- TOC entry 2107 (class 2606 OID 99802)
-- Dependencies: 1689 1689 1689
-- Name: useraccount_username_key1; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY useraccount
    ADD CONSTRAINT useraccount_username_key1 UNIQUE (username, email);



--
-- TOC entry 2177 (class 2606 OID 100129)
-- Dependencies: 2041 1668 1666
-- Name: fk217954de893521da; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY question_relations
    ADD CONSTRAINT fk217954de893521da FOREIGN KEY (id_q_colection) REFERENCES question_collection(id_q_colection);


--
-- TOC entry 2178 (class 2606 OID 100134)
-- Dependencies: 1668 2047 1669
-- Name: fk217954de8a76a0bd; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY question_relations
    ADD CONSTRAINT fk217954de8a76a0bd FOREIGN KEY (question_id) REFERENCES questions(qid);


--
-- TOC entry 2153 (class 2606 OID 100009)
-- Dependencies: 1973 1654 1634
-- Name: fk237a88eb2b2a6ab4; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY notification
    ADD CONSTRAINT fk237a88eb2b2a6ab4 FOREIGN KEY (uid) REFERENCES account(uid);


--
-- TOC entry 2172 (class 2606 OID 100104)
-- Dependencies: 1663 1646 1999
-- Name: fk242951b835313189; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY project_locations
    ADD CONSTRAINT fk242951b835313189 FOREIGN KEY (cat_id_loc) REFERENCES geopoint(locate_id);


--
-- TOC entry 2171 (class 2606 OID 100099)
-- Dependencies: 1660 2029 1663
-- Name: fk242951b884536452; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY project_locations
    ADD CONSTRAINT fk242951b884536452 FOREIGN KEY (cat_id_project) REFERENCES project(project_id);


--
-- TOC entry 2168 (class 2606 OID 100084)
-- Dependencies: 1661 1646 1999
-- Name: fk2599132535313189; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY project_geopoint
    ADD CONSTRAINT fk2599132535313189 FOREIGN KEY (cat_id_loc) REFERENCES geopoint(locate_id);


--
-- TOC entry 2167 (class 2606 OID 100079)
-- Dependencies: 1661 1660 2029
-- Name: fk2599132584536452; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY project_geopoint
    ADD CONSTRAINT fk2599132584536452 FOREIGN KEY (cat_id_project) REFERENCES project(project_id);


--
-- TOC entry 2174 (class 2606 OID 100114)
-- Dependencies: 2047 1665 1669
-- Name: fk2ffe18457a068cb; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY question_category_questions
    ADD CONSTRAINT fk2ffe18457a068cb FOREIGN KEY (questionlibrary_qid) REFERENCES questions(qid);


--
-- TOC entry 2173 (class 2606 OID 100109)
-- Dependencies: 2037 1665 1664
-- Name: fk2ffe1845b10e79be; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY question_category_questions
    ADD CONSTRAINT fk2ffe1845b10e79be FOREIGN KEY (question_category_qcategory) REFERENCES question_category(qcategory);


--
-- TOC entry 2149 (class 2606 OID 99989)
-- Dependencies: 1653 2011 1652
-- Name: fk30df4019aa125; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY hits
    ADD CONSTRAINT fk30df4019aa125 FOREIGN KEY (hashtag_hash_tag_id) REFERENCES hash_tags(hash_tag_id);


--
-- TOC entry 2151 (class 2606 OID 99999)
-- Dependencies: 1682 1653 2083
-- Name: fk30df4051153812; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY hits
    ADD CONSTRAINT fk30df4051153812 FOREIGN KEY (survey_sid) REFERENCES surveys(sid);


--
-- TOC entry 2150 (class 2606 OID 99994)
-- Dependencies: 1653 2019 1656
-- Name: fk30df4063976e9; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY hits
    ADD CONSTRAINT fk30df4063976e9 FOREIGN KEY (poll_poll_id) REFERENCES poll(poll_id);


--
-- TOC entry 2152 (class 2606 OID 100004)
-- Dependencies: 1653 2085 1683
-- Name: fk30df40953c854b; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY hits
    ADD CONSTRAINT fk30df40953c854b FOREIGN KEY (tweetpoll_tweet_poll_id) REFERENCES tweetpoll(tweet_poll_id);


ALTER TABLE ONLY hits
    ADD CONSTRAINT FK30DF40369F8B2C FOREIGN KEY (userAccount_uid) REFERENCES userAccount(uid);
--
-- TOC entry 2155 (class 2606 OID 100019)
-- Dependencies: 1669 1656 2047
-- Name: fk3497bf50fe71f5; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY poll
    ADD CONSTRAINT fk3497bf50fe71f5 FOREIGN KEY (qid) REFERENCES questions(qid);


--
-- TOC entry 2154 (class 2606 OID 100014)
-- Dependencies: 1657 2023 1656
-- Name: fk3497bf89452cca; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY poll
    ADD CONSTRAINT fk3497bf89452cca FOREIGN KEY (poll_folder) REFERENCES poll_folder(pollfolderid);


--
-- TOC entry 2157 (class 2606 OID 100029)
-- Dependencies: 1656 1973 1634
-- Name: fk3497bf8e4a448b; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY poll
    ADD CONSTRAINT fk3497bf8e4a448b FOREIGN KEY (owner_id) REFERENCES account(uid);


--
-- TOC entry 2156 (class 2606 OID 100024)
-- Dependencies: 1689 2102 1656
-- Name: fk3497bfa64fb606; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY poll
    ADD CONSTRAINT fk3497bfa64fb606 FOREIGN KEY (editor) REFERENCES useraccount(uid);


--
-- TOC entry 2145 (class 2606 OID 99969)
-- Dependencies: 1649 1655 2017
-- Name: fk362e6f8f43adb63d; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY group_permission
    ADD CONSTRAINT fk362e6f8f43adb63d FOREIGN KEY (sec_id_permission) REFERENCES permission(id_permission);


--
-- TOC entry 2144 (class 2606 OID 99964)
-- Dependencies: 2007 1649 1650
-- Name: fk362e6f8f45895aff; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY group_permission
    ADD CONSTRAINT fk362e6f8f45895aff FOREIGN KEY (sec_id_group) REFERENCES groups(group_id);


--
-- TOC entry 2133 (class 2606 OID 99909)
-- Dependencies: 1643 1641 1989
-- Name: fk4b85010ee824035; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY emailsubscribe
    ADD CONSTRAINT fk4b85010ee824035 FOREIGN KEY (email_id) REFERENCES email(email_id);


--
-- TOC entry 2132 (class 2606 OID 99904)
-- Dependencies: 1643 1642 1991
-- Name: fk4b85010eed78e617; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY emailsubscribe
    ADD CONSTRAINT fk4b85010eed78e617 FOREIGN KEY (id_list) REFERENCES emaillist(id_list);


--
-- TOC entry 2184 (class 2606 OID 100164)
-- Dependencies: 1973 1673 1634
-- Name: fk50078b5b5ece45a2; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY social_account
    ADD CONSTRAINT fk50078b5b5ece45a2 FOREIGN KEY (account_uid) REFERENCES account(uid);


--
-- TOC entry 2185 (class 2606 OID 100169)
-- Dependencies: 1689 2102 1673
-- Name: fk50078b5bf2f411f2; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY social_account
    ADD CONSTRAINT fk50078b5bf2f411f2 FOREIGN KEY (userowner_uid) REFERENCES useraccount(uid);


--
-- TOC entry 2143 (class 2606 OID 99959)
-- Dependencies: 1634 1648 1973
-- Name: fk514326ba4075e3fd; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY geopoint_type
    ADD CONSTRAINT fk514326ba4075e3fd FOREIGN KEY (users_uid) REFERENCES account(uid);


--
-- TOC entry 2182 (class 2606 OID 100154)
-- Dependencies: 2047 1669 1670
-- Name: fk539703837e6c7bbc; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY questions_answers
    ADD CONSTRAINT fk539703837e6c7bbc FOREIGN KEY (id_question_answer) REFERENCES questions(qid);


--
-- TOC entry 2130 (class 2606 OID 99894)
-- Dependencies: 1642 1641 1991
-- Name: fk5c24b9ced78e617; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY email
    ADD CONSTRAINT fk5c24b9ced78e617 FOREIGN KEY (id_list) REFERENCES emaillist(id_list);


--
-- TOC entry 2120 (class 2606 OID 99844)
-- Dependencies: 1635 1634 1973
-- Name: fk5ca405505ece45a2; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY application
    ADD CONSTRAINT fk5ca405505ece45a2 FOREIGN KEY (account_uid) REFERENCES account(uid);


--
-- TOC entry 2139 (class 2606 OID 99939)
-- Dependencies: 1647 1646 2001
-- Name: fk6c73c0bf34ef9a43; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY geopoint
    ADD CONSTRAINT fk6c73c0bf34ef9a43 FOREIGN KEY (geopointfolder_locate_folder_id) REFERENCES geopoint_folder(locate_folder_id);


--
-- TOC entry 2138 (class 2606 OID 99934)
-- Dependencies: 1646 1634 1973
-- Name: fk6c73c0bf5ece45a2; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY geopoint
    ADD CONSTRAINT fk6c73c0bf5ece45a2 FOREIGN KEY (account_uid) REFERENCES account(uid);


--
-- TOC entry 2137 (class 2606 OID 99929)
-- Dependencies: 1646 2003 1648
-- Name: fk6c73c0bfbd91661d; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY geopoint
    ADD CONSTRAINT fk6c73c0bfbd91661d FOREIGN KEY (loc_id_type) REFERENCES geopoint_type(loc_id_type);


--
-- TOC entry 2121 (class 2606 OID 99849)
-- Dependencies: 2102 1689 1636
-- Name: fk73d5d2d27e933d7; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY application_connection
    ADD CONSTRAINT fk73d5d2d27e933d7 FOREIGN KEY (account_uid) REFERENCES useraccount(uid);


--
-- TOC entry 2122 (class 2606 OID 99854)
-- Dependencies: 1975 1636 1635
-- Name: fk73d5d2d4402be26; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY application_connection
    ADD CONSTRAINT fk73d5d2d4402be26 FOREIGN KEY (application_application_id) REFERENCES application(application_id);


--
-- TOC entry 2131 (class 2606 OID 99899)
-- Dependencies: 1634 1642 1973
-- Name: fk7e5f425a2b2a6ab4; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY emaillist
    ADD CONSTRAINT fk7e5f425a2b2a6ab4 FOREIGN KEY (uid) REFERENCES account(uid);


--
-- TOC entry 2186 (class 2606 OID 100174)
-- Dependencies: 1634 1674 1973
-- Name: fk7ef958f32b2a6ab4; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY survey_folder
    ADD CONSTRAINT fk7ef958f32b2a6ab4 FOREIGN KEY (uid) REFERENCES account(uid);


--
-- TOC entry 2187 (class 2606 OID 100179)
-- Dependencies: 1674 2102 1689
-- Name: fk7ef958f36ef241e9; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY survey_folder
    ADD CONSTRAINT fk7ef958f36ef241e9 FOREIGN KEY (createdby_uid) REFERENCES useraccount(uid);


--
-- TOC entry 2148 (class 2606 OID 99984)
-- Dependencies: 2017 1655 1651
-- Name: fk7f1951a43adb63d; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY groups_permission
    ADD CONSTRAINT fk7f1951a43adb63d FOREIGN KEY (sec_id_permission) REFERENCES permission(id_permission);


--
-- TOC entry 2147 (class 2606 OID 99979)
-- Dependencies: 2007 1650 1651
-- Name: fk7f1951a45895aff; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY groups_permission
    ADD CONSTRAINT fk7f1951a45895aff FOREIGN KEY (sec_id_group) REFERENCES groups(group_id);


--
-- TOC entry 2220 (class 2606 OID 100346)
-- Dependencies: 2102 1689 1690
-- Name: fk7f1957f8e53fbc6; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY useraccount_followers
    ADD CONSTRAINT fk7f1957f8e53fbc6 FOREIGN KEY (uid_follower) REFERENCES useraccount(uid);


--
-- TOC entry 2219 (class 2606 OID 100341)
-- Dependencies: 1690 2102 1689
-- Name: fk7f1957f8f44558e9; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY useraccount_followers
    ADD CONSTRAINT fk7f1957f8f44558e9 FOREIGN KEY (uid) REFERENCES useraccount(uid);


--
-- TOC entry 2136 (class 2606 OID 99924)
-- Dependencies: 1995 1645 1644
-- Name: fk866b670629091b05; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY gadget_properties
    ADD CONSTRAINT fk866b670629091b05 FOREIGN KEY (gadget_gadgetid) REFERENCES gadget(gadgetid);


--
-- TOC entry 2135 (class 2606 OID 99919)
-- Dependencies: 1645 1689 2102
-- Name: fk866b6706369f8b2c; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY gadget_properties
    ADD CONSTRAINT fk866b6706369f8b2c FOREIGN KEY (useraccount_uid) REFERENCES useraccount(uid);


--
-- TOC entry 2214 (class 2606 OID 100314)
-- Dependencies: 1688 2095 1687
-- Name: fk8749c18cb9d39f98; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY tweetpoll_result
    ADD CONSTRAINT fk8749c18cb9d39f98 FOREIGN KEY (tweetpoll_switch_id) REFERENCES tweetpoll_switch(tweetpoll_switch_id);


--
-- TOC entry 2215 (class 2606 OID 100319)
-- Dependencies: 1683 1688 2085
-- Name: fk89f7b0a3550299a; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY tweetpoll_switch
    ADD CONSTRAINT fk89f7b0a3550299a FOREIGN KEY (tweet_poll_id) REFERENCES tweetpoll(tweet_poll_id);


--
-- TOC entry 2216 (class 2606 OID 100324)
-- Dependencies: 1688 2049 1670
-- Name: fk89f7b0a3ddd118b5; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY tweetpoll_switch
    ADD CONSTRAINT fk89f7b0a3ddd118b5 FOREIGN KEY (q_answer_id) REFERENCES questions_answers(q_answer_id);


--
-- TOC entry 2123 (class 2606 OID 99859)
-- Dependencies: 2029 1637 1660
-- Name: fk8af75923225a055; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY attachment
    ADD CONSTRAINT fk8af75923225a055 FOREIGN KEY (project_id) REFERENCES project(project_id);


--
-- TOC entry 2199 (class 2606 OID 100239)
-- Dependencies: 1660 1682 2029
-- Name: fk9191445973ff13b; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY surveys
    ADD CONSTRAINT fk9191445973ff13b FOREIGN KEY (project_project_id) REFERENCES project(project_id);


--
-- TOC entry 2201 (class 2606 OID 100249)
-- Dependencies: 1682 1634 1973
-- Name: fk919144598e4a448b; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY surveys
    ADD CONSTRAINT fk919144598e4a448b FOREIGN KEY (owner_id) REFERENCES account(uid);


--
-- TOC entry 2198 (class 2606 OID 100234)
-- Dependencies: 2067 1682 1674
-- Name: fk91914459a3c7a06a; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY surveys
    ADD CONSTRAINT fk91914459a3c7a06a FOREIGN KEY (survey_folder) REFERENCES survey_folder(survey_folderid);


--
-- TOC entry 2200 (class 2606 OID 100244)
-- Dependencies: 1689 2102 1682
-- Name: fk91914459a64fb606; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY surveys
    ADD CONSTRAINT fk91914459a64fb606 FOREIGN KEY (editor) REFERENCES useraccount(uid);


--
-- TOC entry 2183 (class 2606 OID 100159)
-- Dependencies: 2049 1671 1670
-- Name: fk92e86adbddd118b5; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY questions_dependencies
    ADD CONSTRAINT fk92e86adbddd118b5 FOREIGN KEY (q_answer_id) REFERENCES questions_answers(q_answer_id);


--
-- TOC entry 2194 (class 2606 OID 100214)
-- Dependencies: 2047 1680 1669
-- Name: fk92ea04a246bf7a1c; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY survey_result
    ADD CONSTRAINT fk92ea04a246bf7a1c FOREIGN KEY (question_qid) REFERENCES questions(qid);


--
-- TOC entry 2195 (class 2606 OID 100219)
-- Dependencies: 2049 1670 1680
-- Name: fk92ea04a2496009b4; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY survey_result
    ADD CONSTRAINT fk92ea04a2496009b4 FOREIGN KEY (answer_q_answer_id) REFERENCES questions_answers(q_answer_id);


--
-- TOC entry 2196 (class 2606 OID 100224)
-- Dependencies: 2083 1682 1680
-- Name: fk92ea04a251153812; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY survey_result
    ADD CONSTRAINT fk92ea04a251153812 FOREIGN KEY (survey_sid) REFERENCES surveys(sid);


--
-- TOC entry 2179 (class 2606 OID 100139)
-- Dependencies: 1634 1669 1973
-- Name: fk95c5414d2b2a6ab4; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY questions
    ADD CONSTRAINT fk95c5414d2b2a6ab4 FOREIGN KEY (uid) REFERENCES account(uid);


--
-- TOC entry 2180 (class 2606 OID 100144)
-- Dependencies: 1681 2081 1669
-- Name: fk95c5414d39e97991; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY questions
    ADD CONSTRAINT fk95c5414d39e97991 FOREIGN KEY (section_ssid) REFERENCES survey_section(ssid);

--
-- TOC entry 2160 (class 2606 OID 100044)
-- Dependencies: 1652 2011 1658
-- Name: fk9d199ea7da98ffe1; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY poll_hashtags
    ADD CONSTRAINT fk9d199ea7da98ffe1 FOREIGN KEY (hastag_id) REFERENCES hash_tags(hash_tag_id);


--
-- TOC entry 2161 (class 2606 OID 100049)
-- Dependencies: 1656 1658 2019
-- Name: fk9d199ea7f0ed6769; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY poll_hashtags
    ADD CONSTRAINT fk9d199ea7f0ed6769 FOREIGN KEY (poll_id) REFERENCES poll(poll_id);


--
-- TOC entry 2206 (class 2606 OID 100274)
-- Dependencies: 1634 1684 1973
-- Name: fka027a9dd2b2a6ab4; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY tweetpoll_folder
    ADD CONSTRAINT fka027a9dd2b2a6ab4 FOREIGN KEY (uid) REFERENCES account(uid);


--
-- TOC entry 2207 (class 2606 OID 100279)
-- Dependencies: 1684 1689 2102
-- Name: fka027a9dd6ef241e9; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY tweetpoll_folder
    ADD CONSTRAINT fka027a9dd6ef241e9 FOREIGN KEY (createdby_uid) REFERENCES useraccount(uid);


--
-- TOC entry 2202 (class 2606 OID 100254)
-- Dependencies: 1683 1973 1634
-- Name: fka65b1d02b2a6ab4; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY tweetpoll
    ADD CONSTRAINT fka65b1d02b2a6ab4 FOREIGN KEY (uid) REFERENCES account(uid);


--
-- TOC entry 2204 (class 2606 OID 100264)
-- Dependencies: 2047 1669 1683
-- Name: fka65b1d050fe71f5; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY tweetpoll
    ADD CONSTRAINT fka65b1d050fe71f5 FOREIGN KEY (qid) REFERENCES questions(qid);


--
-- TOC entry 2205 (class 2606 OID 100269)
-- Dependencies: 1683 2102 1689
-- Name: fka65b1d0a64fb606; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY tweetpoll
    ADD CONSTRAINT fka65b1d0a64fb606 FOREIGN KEY (editor) REFERENCES useraccount(uid);


--
-- TOC entry 2203 (class 2606 OID 100259)
-- Dependencies: 1683 2087 1684
-- Name: fka65b1d0d9ba7e54; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY tweetpoll
    ADD CONSTRAINT fka65b1d0d9ba7e54 FOREIGN KEY (tweetpollfolderid) REFERENCES tweetpoll_folder(tweetpollfolderid);


--
-- TOC entry 2217 (class 2606 OID 100329)
-- Dependencies: 1689 1973 1634
-- Name: fka7d56be25ece45a2; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY useraccount
    ADD CONSTRAINT fka7d56be25ece45a2 FOREIGN KEY (account_uid) REFERENCES account(uid);


--
-- TOC entry 2218 (class 2606 OID 100334)
-- Dependencies: 1689 1650 2007
-- Name: fka7d56be2b8eb1450; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY useraccount
    ADD CONSTRAINT fka7d56be2b8eb1450 FOREIGN KEY (groupid) REFERENCES groups(group_id);


--
-- TOC entry 2124 (class 2606 OID 99864)
-- Dependencies: 1638 1660 2029
-- Name: fkaf12f3cb225a055; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY client
    ADD CONSTRAINT fkaf12f3cb225a055 FOREIGN KEY (project_id) REFERENCES project(project_id);


--
-- TOC entry 2175 (class 2606 OID 100119)
-- Dependencies: 1634 1666 1973
-- Name: fkb4097c972b2a6ab4; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY question_collection
    ADD CONSTRAINT fkb4097c972b2a6ab4 FOREIGN KEY (uid) REFERENCES account(uid);


--
-- TOC entry 2188 (class 2606 OID 100184)
-- Dependencies: 1676 2071 1677
-- Name: fkb4df867c310e993c; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY survey_group_format
    ADD CONSTRAINT fkb4df867c310e993c FOREIGN KEY (sg_id) REFERENCES survey_group(sg_id);


--
-- TOC entry 2189 (class 2606 OID 100189)
-- Dependencies: 2069 1675 1677
-- Name: fkb4df867cb1a6912c; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY survey_group_format
    ADD CONSTRAINT fkb4df867cb1a6912c FOREIGN KEY (id_sid_format) REFERENCES survey_format(id_sid_format);


--
-- TOC entry 2134 (class 2606 OID 99914)
-- Dependencies: 1644 1640 1985
-- Name: fkb549144cb975b5f9; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY gadget
    ADD CONSTRAINT fkb549144cb975b5f9 FOREIGN KEY (dashboard_dashboardid) REFERENCES dashboard(dashboardid);


--
-- TOC entry 2146 (class 2606 OID 99974)
-- Dependencies: 1973 1650 1634
-- Name: fkb63dd9d45ece45a2; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY groups
    ADD CONSTRAINT fkb63dd9d45ece45a2 FOREIGN KEY (account_uid) REFERENCES account(uid);


--
-- TOC entry 2176 (class 2606 OID 100124)
-- Dependencies: 2083 1682 1667
-- Name: fkbb424d49793d9e77; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY question_dependence_survey
    ADD CONSTRAINT fkbb424d49793d9e77 FOREIGN KEY (sid) REFERENCES surveys(sid);


--
-- TOC entry 2221 (class 2606 OID 100351)
-- Dependencies: 1691 2017 1655
-- Name: fkbe01ce4c43adb63d; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY useraccount_permission
    ADD CONSTRAINT fkbe01ce4c43adb63d FOREIGN KEY (sec_id_permission) REFERENCES permission(id_permission);


--
-- TOC entry 2222 (class 2606 OID 100356)
-- Dependencies: 1691 1689 2102
-- Name: fkbe01ce4c5f77a117; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY useraccount_permission
    ADD CONSTRAINT fkbe01ce4c5f77a117 FOREIGN KEY (sec_id_secondary) REFERENCES useraccount(uid);


--
-- TOC entry 2193 (class 2606 OID 100209)
-- Dependencies: 1679 1681 2081
-- Name: fkbec9a99f1359b877; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY survey_pagination
    ADD CONSTRAINT fkbec9a99f1359b877 FOREIGN KEY (ssid) REFERENCES survey_section(ssid);


--
-- TOC entry 2192 (class 2606 OID 100204)
-- Dependencies: 1682 2083 1679
-- Name: fkbec9a99f793d9e77; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY survey_pagination
    ADD CONSTRAINT fkbec9a99f793d9e77 FOREIGN KEY (sid) REFERENCES surveys(sid);


--
-- TOC entry 2129 (class 2606 OID 99889)
-- Dependencies: 2102 1640 1689
-- Name: fkc18aea949229bca5; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY dashboard
    ADD CONSTRAINT fkc18aea949229bca5 FOREIGN KEY (userboard_uid) REFERENCES useraccount(uid);


--
-- TOC entry 2118 (class 2606 OID 99834)
-- Dependencies: 2083 1633 1682
-- Name: fkc2760edb51153812; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY access_rate
    ADD CONSTRAINT fkc2760edb51153812 FOREIGN KEY (survey_sid) REFERENCES surveys(sid);


--
-- TOC entry 2115 (class 2606 OID 99819)
-- Dependencies: 1983 1639 1633
-- Name: fkc2760edb546d76c9; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY access_rate
    ADD CONSTRAINT fkc2760edb546d76c9 FOREIGN KEY (comments_commentid) REFERENCES comments(commentid);


--
-- TOC entry 2116 (class 2606 OID 99824)
-- Dependencies: 1656 2019 1633
-- Name: fkc2760edb63976e9; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY access_rate
    ADD CONSTRAINT fkc2760edb63976e9 FOREIGN KEY (poll_poll_id) REFERENCES poll(poll_id);


--
-- TOC entry 2119 (class 2606 OID 99839)
-- Dependencies: 2085 1683 1633
-- Name: fkc2760edb953c854b; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY access_rate
    ADD CONSTRAINT fkc2760edb953c854b FOREIGN KEY (tweetpoll_tweet_poll_id) REFERENCES tweetpoll(tweet_poll_id);


--
-- TOC entry 2117 (class 2606 OID 99829)
-- Dependencies: 1689 2102 1633
-- Name: fkc2760edbe4669675; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY access_rate
    ADD CONSTRAINT fkc2760edbe4669675 FOREIGN KEY (user_uid) REFERENCES useraccount(uid);


--
-- TOC entry 2158 (class 2606 OID 100034)
-- Dependencies: 1634 1657 1973
-- Name: fkc5911cee2b2a6ab4; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY poll_folder
    ADD CONSTRAINT fkc5911cee2b2a6ab4 FOREIGN KEY (uid) REFERENCES account(uid);


--
-- TOC entry 2159 (class 2606 OID 100039)
-- Dependencies: 2102 1689 1657
-- Name: fkc5911cee6ef241e9; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY poll_folder
    ADD CONSTRAINT fkc5911cee6ef241e9 FOREIGN KEY (createdby_uid) REFERENCES useraccount(uid);


--
-- TOC entry 2169 (class 2606 OID 100089)
-- Dependencies: 2007 1650 1662
-- Name: fkc7652dd945895aff; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY project_group
    ADD CONSTRAINT fkc7652dd945895aff FOREIGN KEY (sec_id_group) REFERENCES groups(group_id);


--
-- TOC entry 2170 (class 2606 OID 100094)
-- Dependencies: 2029 1660 1662
-- Name: fkc7652dd984536452; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY project_group
    ADD CONSTRAINT fkc7652dd984536452 FOREIGN KEY (cat_id_project) REFERENCES project(project_id);


--
-- TOC entry 2210 (class 2606 OID 100294)
-- Dependencies: 1685 1682 2083
-- Name: fkd499a4b651153812; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY tweetpoll_save_published_status
    ADD CONSTRAINT fkd499a4b651153812 FOREIGN KEY (survey_sid) REFERENCES surveys(sid);


--
-- TOC entry 2208 (class 2606 OID 100284)
-- Dependencies: 1685 2061 1673
-- Name: fkd499a4b65239d117; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY tweetpoll_save_published_status
    ADD CONSTRAINT fkd499a4b65239d117 FOREIGN KEY (socialaccount_social_account_id) REFERENCES social_account(social_account_id);


--
-- TOC entry 2209 (class 2606 OID 100289)
-- Dependencies: 1656 2019 1685
-- Name: fkd499a4b663976e9; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY tweetpoll_save_published_status
    ADD CONSTRAINT fkd499a4b663976e9 FOREIGN KEY (poll_poll_id) REFERENCES poll(poll_id);


--
-- TOC entry 2211 (class 2606 OID 100299)
-- Dependencies: 1685 2085 1683
-- Name: fkd499a4b6953c854b; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY tweetpoll_save_published_status
    ADD CONSTRAINT fkd499a4b6953c854b FOREIGN KEY (tweetpoll_tweet_poll_id) REFERENCES tweetpoll(tweet_poll_id);


--
-- TOC entry 2163 (class 2606 OID 100059)
-- Dependencies: 1670 2049 1659
-- Name: fkd981c89dddd118b5; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY poll_result
    ADD CONSTRAINT fkd981c89dddd118b5 FOREIGN KEY (q_answer_id) REFERENCES questions_answers(q_answer_id);


--
-- TOC entry 2164 (class 2606 OID 100064)
-- Dependencies: 2019 1656 1659
-- Name: fkd981c89df0ed6769; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY poll_result
    ADD CONSTRAINT fkd981c89df0ed6769 FOREIGN KEY (poll_id) REFERENCES poll(poll_id);


--
-- TOC entry 2126 (class 2606 OID 99874)
-- Dependencies: 2083 1682 1639
-- Name: fkdc17ddf4793d9e77; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY comments
    ADD CONSTRAINT fkdc17ddf4793d9e77 FOREIGN KEY (sid) REFERENCES surveys(sid);


--
-- TOC entry 2127 (class 2606 OID 99879)
-- Dependencies: 2019 1639 1656
-- Name: fkdc17ddf4ce12cae8; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY comments
    ADD CONSTRAINT fkdc17ddf4ce12cae8 FOREIGN KEY (pollid) REFERENCES poll(poll_id);


--
-- TOC entry 2128 (class 2606 OID 99884)
-- Dependencies: 2085 1639 1683
-- Name: fkdc17ddf4d9aa8e98; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY comments
    ADD CONSTRAINT fkdc17ddf4d9aa8e98 FOREIGN KEY (tweetpollid) REFERENCES tweetpoll(tweet_poll_id);


--
-- TOC entry 2125 (class 2606 OID 99869)
-- Dependencies: 1689 1639 2102
-- Name: fkdc17ddf4f44558e9; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY comments
    ADD CONSTRAINT fkdc17ddf4f44558e9 FOREIGN KEY (uid) REFERENCES useraccount(uid);


--
-- TOC entry 2166 (class 2606 OID 100074)
-- Dependencies: 1634 1660 1973
-- Name: fked904b194075e3fd; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY project
    ADD CONSTRAINT fked904b194075e3fd FOREIGN KEY (users_uid) REFERENCES account(uid);


--
-- TOC entry 2165 (class 2606 OID 100069)
-- Dependencies: 1689 1660 2102
-- Name: fked904b19514c1986; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY project
    ADD CONSTRAINT fked904b19514c1986 FOREIGN KEY (lead_uid) REFERENCES useraccount(uid);


--
-- TOC entry 2140 (class 2606 OID 99944)
-- Dependencies: 1634 1973 1647
-- Name: fkf4a1d3ee2b2a6ab4; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY geopoint_folder
    ADD CONSTRAINT fkf4a1d3ee2b2a6ab4 FOREIGN KEY (uid) REFERENCES account(uid);


--
-- TOC entry 2142 (class 2606 OID 99954)
-- Dependencies: 2001 1647 1647
-- Name: fkf4a1d3ee6e4ed46d; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY geopoint_folder
    ADD CONSTRAINT fkf4a1d3ee6e4ed46d FOREIGN KEY (sublocationfolder_locate_folder_id) REFERENCES geopoint_folder(locate_folder_id);


--
-- TOC entry 2141 (class 2606 OID 99949)
-- Dependencies: 2102 1647 1689
-- Name: fkf4a1d3ee6ef241e9; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY geopoint_folder
    ADD CONSTRAINT fkf4a1d3ee6ef241e9 FOREIGN KEY (createdby_uid) REFERENCES useraccount(uid);


--
-- TOC entry 2212 (class 2606 OID 100304)
-- Dependencies: 1686 2085 1683
-- Name: fkf8c717d6286705d7; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY tweetpoll_hashtags
    ADD CONSTRAINT fkf8c717d6286705d7 FOREIGN KEY (tweetpoll_id) REFERENCES tweetpoll(tweet_poll_id);


--
-- TOC entry 2213 (class 2606 OID 100309)
-- Dependencies: 2011 1686 1652
-- Name: fkf8c717d6da98ffe1; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY tweetpoll_hashtags
    ADD CONSTRAINT fkf8c717d6da98ffe1 FOREIGN KEY (hastag_id) REFERENCES hash_tags(hash_tag_id);


--
-- TOC entry 2224 (class 2606 OID 100366)
-- Dependencies: 1689 2102 1692
-- Name: fkfbc45bbc5f77a117; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY useraccount_project
    ADD CONSTRAINT fkfbc45bbc5f77a117 FOREIGN KEY (sec_id_secondary) REFERENCES useraccount(uid);


--
-- TOC entry 2223 (class 2606 OID 100361)
-- Dependencies: 2029 1692 1660
-- Name: fkfbc45bbc84536452; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY useraccount_project
    ADD CONSTRAINT fkfbc45bbc84536452 FOREIGN KEY (cat_id_project) REFERENCES project(project_id);


--
-- TOC entry 2190 (class 2606 OID 100194)
-- Dependencies: 2029 1678 1660
-- Name: fkfd028d3484536452; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY survey_group_project
    ADD CONSTRAINT fkfd028d3484536452 FOREIGN KEY (cat_id_project) REFERENCES project(project_id);


--
-- TOC entry 2191 (class 2606 OID 100199)
-- Dependencies: 1676 2071 1678
-- Name: fkfd028d34b75f3482; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY survey_group_project
    ADD CONSTRAINT fkfd028d34b75f3482 FOREIGN KEY (id_sid_format) REFERENCES survey_group(sg_id);


--
-- TOC entry 2197 (class 2606 OID 100229)
-- Dependencies: 2083 1682 1681
-- Name: fkfe5ad30051153812; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY survey_section
    ADD CONSTRAINT fkfe5ad30051153812 FOREIGN KEY (survey_sid) REFERENCES surveys(sid);


ALTER TABLE ONLY survey_temporal_result ADD CONSTRAINT FK7867CF546BF7A1C FOREIGN KEY (question_qid) REFERENCES questions;
ALTER TABLE ONLY survey_temporal_result ADD CONSTRAINT FK7867CF5496009B4 FOREIGN KEY (answer_q_answer_id) REFERENCES questions_answers;
ALTER TABLE ONLY survey_temporal_result ADD CONSTRAINT FK7867CF551153812 FOREIGN KEY (survey_sid) REFERENCES surveys;
ALTER TABLE ONLY question_preferences ADD CONSTRAINT FKD540D01F46BF7A1C FOREIGN KEY (question_qid) REFERENCES questions;


ALTER TABLE ONLY hash_tags_ranking
  ADD CONSTRAINT FK71DECDA119AA125 FOREIGN KEY (hashTag_hash_tag_id) REFERENCES hash_tags (hash_tag_id);

 
ALTER TABLE ONLY scheduled ADD CONSTRAINT FKF66BC0AD1366E48E FOREIGN KEY (tpoll_tweet_poll_id) REFERENCES tweetpoll(tweet_poll_id);
ALTER TABLE ONLY scheduled ADD CONSTRAINT FKF66BC0AD51153812 FOREIGN KEY (survey_sid) REFERENCES surveys(sid);
ALTER TABLE ONLY scheduled ADD CONSTRAINT FKF66BC0AD5239D117 FOREIGN KEY (socialAccount_social_account_id) REFERENCES social_account(social_account_id);
ALTER TABLE ONLY scheduled ADD CONSTRAINT FKF66BC0AD63976E9 FOREIGN KEY (poll_poll_id) REFERENCES poll(poll_id);
ALTER TABLE ONLY scheduled ADD CONSTRAINT FKF66BC0AD9C14A5E7 FOREIGN KEY (tpollSavedPublished_status_save_poll_id) REFERENCES tweetPoll_save_published_status(status_save_poll_id);
  

-- Completed on 2011-11-30 21:40:42 CET

--
-- PostgreSQL database dump complete
--