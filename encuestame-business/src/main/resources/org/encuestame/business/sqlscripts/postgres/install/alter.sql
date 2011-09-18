
ALTER TABLE public.useraccount_project OWNER TO postgres;

--
-- TOC entry 1969 (class 2606 OID 50483)
-- Dependencies: 1631 1631
-- Name: account_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace:
--

ALTER TABLE ONLY account
    ADD CONSTRAINT account_pkey PRIMARY KEY (uid);


--
-- TOC entry 1973 (class 2606 OID 50499)
-- Dependencies: 1633 1633
-- Name: application_connection_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace:
--

ALTER TABLE ONLY application_connection
    ADD CONSTRAINT application_connection_pkey PRIMARY KEY (connection_id);


--
-- TOC entry 1971 (class 2606 OID 50491)
-- Dependencies: 1632 1632
-- Name: application_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace:
--

ALTER TABLE ONLY application
    ADD CONSTRAINT application_pkey PRIMARY KEY (application_id);


--
-- TOC entry 1975 (class 2606 OID 50504)
-- Dependencies: 1634 1634
-- Name: attachment_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace:
--

ALTER TABLE ONLY attachment
    ADD CONSTRAINT attachment_pkey PRIMARY KEY (attachment_id);


--
-- TOC entry 1977 (class 2606 OID 50512)
-- Dependencies: 1635 1635
-- Name: client_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace:
--

ALTER TABLE ONLY client
    ADD CONSTRAINT client_pkey PRIMARY KEY (client_id);


--
-- TOC entry 1979 (class 2606 OID 50520)
-- Dependencies: 1636 1636
-- Name: comments_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace:
--

ALTER TABLE ONLY comments
    ADD CONSTRAINT comments_pkey PRIMARY KEY (commentid);


--
-- TOC entry 1981 (class 2606 OID 50528)
-- Dependencies: 1637 1637
-- Name: dashboard_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace:
--

ALTER TABLE ONLY dashboard
    ADD CONSTRAINT dashboard_pkey PRIMARY KEY (dashboardid);


--
-- TOC entry 1983 (class 2606 OID 50538)
-- Dependencies: 1638 1638
-- Name: email_email_key; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace:
--

ALTER TABLE ONLY email
    ADD CONSTRAINT email_email_key UNIQUE (email);


--
-- TOC entry 1985 (class 2606 OID 50536)
-- Dependencies: 1638 1638
-- Name: email_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace:
--

ALTER TABLE ONLY email
    ADD CONSTRAINT email_pkey PRIMARY KEY (email_id);


--
-- TOC entry 1987 (class 2606 OID 50546)
-- Dependencies: 1639 1639
-- Name: emaillist_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace:
--

ALTER TABLE ONLY emaillist
    ADD CONSTRAINT emaillist_pkey PRIMARY KEY (id_list);


--
-- TOC entry 1989 (class 2606 OID 50551)
-- Dependencies: 1640 1640
-- Name: emailsubscribe_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace:
--

ALTER TABLE ONLY emailsubscribe
    ADD CONSTRAINT emailsubscribe_pkey PRIMARY KEY (id_subscribe);


--
-- TOC entry 1991 (class 2606 OID 50559)
-- Dependencies: 1641 1641
-- Name: gadget_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace:
--

ALTER TABLE ONLY gadget
    ADD CONSTRAINT gadget_pkey PRIMARY KEY (gadgetid);


--
-- TOC entry 1993 (class 2606 OID 50567)
-- Dependencies: 1642 1642
-- Name: gadget_properties_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace:
--

ALTER TABLE ONLY gadget_properties
    ADD CONSTRAINT gadget_properties_pkey PRIMARY KEY (propertyid);


--
-- TOC entry 1997 (class 2606 OID 50583)
-- Dependencies: 1644 1644
-- Name: geopoint_folder_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace:
--

ALTER TABLE ONLY geopoint_folder
    ADD CONSTRAINT geopoint_folder_pkey PRIMARY KEY (locate_folder_id);


--
-- TOC entry 1995 (class 2606 OID 50575)
-- Dependencies: 1643 1643
-- Name: geopoint_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace:
--

ALTER TABLE ONLY geopoint
    ADD CONSTRAINT geopoint_pkey PRIMARY KEY (locate_id);


--
-- TOC entry 1999 (class 2606 OID 50588)
-- Dependencies: 1645 1645
-- Name: geopoint_type_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace:
--

ALTER TABLE ONLY geopoint_type
    ADD CONSTRAINT geopoint_type_pkey PRIMARY KEY (loc_id_type);


--
-- TOC entry 2001 (class 2606 OID 50593)
-- Dependencies: 1646 1646 1646
-- Name: group_permission_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace:
--

ALTER TABLE ONLY group_permission
    ADD CONSTRAINT group_permission_pkey PRIMARY KEY (sec_id_permission, sec_id_group);


--
-- TOC entry 2005 (class 2606 OID 50606)
-- Dependencies: 1648 1648 1648
-- Name: groups_permission_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace:
--

ALTER TABLE ONLY groups_permission
    ADD CONSTRAINT groups_permission_pkey PRIMARY KEY (sec_id_group, sec_id_permission);


--
-- TOC entry 2003 (class 2606 OID 50601)
-- Dependencies: 1647 1647
-- Name: groups_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace:
--

ALTER TABLE ONLY groups
    ADD CONSTRAINT groups_pkey PRIMARY KEY (group_id);


--
-- TOC entry 2009 (class 2606 OID 50616)
-- Dependencies: 1650 1650
-- Name: hash_tags_hits_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace:
--

ALTER TABLE ONLY hash_tags_hits
    ADD CONSTRAINT hash_tags_hits_pkey PRIMARY KEY (hashtag_hits_id);


--
-- TOC entry 2007 (class 2606 OID 50611)
-- Dependencies: 1649 1649
-- Name: hash_tags_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace:
--

ALTER TABLE ONLY hash_tags
    ADD CONSTRAINT hash_tags_pkey PRIMARY KEY (hash_tag_id);


--
-- TOC entry 2011 (class 2606 OID 50624)
-- Dependencies: 1651 1651
-- Name: notification_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace:
--

ALTER TABLE ONLY notification
    ADD CONSTRAINT notification_pkey PRIMARY KEY (notification_id);


--
-- TOC entry 2013 (class 2606 OID 50632)
-- Dependencies: 1652 1652
-- Name: permission_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace:
--

ALTER TABLE ONLY permission
    ADD CONSTRAINT permission_pkey PRIMARY KEY (id_permission);


--
-- TOC entry 2019 (class 2606 OID 50647)
-- Dependencies: 1654 1654
-- Name: poll_folder_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace:
--

ALTER TABLE ONLY poll_folder
    ADD CONSTRAINT poll_folder_pkey PRIMARY KEY (pollfolderid);


--
-- TOC entry 2015 (class 2606 OID 50640)
-- Dependencies: 1653 1653
-- Name: poll_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace:
--

ALTER TABLE ONLY poll
    ADD CONSTRAINT poll_pkey PRIMARY KEY (poll_id);


--
-- TOC entry 2017 (class 2606 OID 50642)
-- Dependencies: 1653 1653
-- Name: poll_poll_hash_key; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace:
--

ALTER TABLE ONLY poll
    ADD CONSTRAINT poll_poll_hash_key UNIQUE (poll_hash);


--
-- TOC entry 2021 (class 2606 OID 50652)
-- Dependencies: 1655 1655
-- Name: poll_result_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace:
--

ALTER TABLE ONLY poll_result
    ADD CONSTRAINT poll_result_pkey PRIMARY KEY (poll_resultid);


--
-- TOC entry 2025 (class 2606 OID 50665)
-- Dependencies: 1657 1657 1657
-- Name: project_geopoint_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace:
--

ALTER TABLE ONLY project_geopoint
    ADD CONSTRAINT project_geopoint_pkey PRIMARY KEY (cat_id_project, cat_id_loc);


--
-- TOC entry 2027 (class 2606 OID 50670)
-- Dependencies: 1658 1658 1658
-- Name: project_group_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace:
--

ALTER TABLE ONLY project_group
    ADD CONSTRAINT project_group_pkey PRIMARY KEY (cat_id_project, sec_id_group);


--
-- TOC entry 2029 (class 2606 OID 50675)
-- Dependencies: 1659 1659 1659
-- Name: project_locations_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace:
--

ALTER TABLE ONLY project_locations
    ADD CONSTRAINT project_locations_pkey PRIMARY KEY (cat_id_loc, cat_id_project);


--
-- TOC entry 2023 (class 2606 OID 50660)
-- Dependencies: 1656 1656
-- Name: project_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace:
--

ALTER TABLE ONLY project
    ADD CONSTRAINT project_pkey PRIMARY KEY (project_id);


--
-- TOC entry 2031 (class 2606 OID 50680)
-- Dependencies: 1660 1660
-- Name: question_category_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace:
--

ALTER TABLE ONLY question_category
    ADD CONSTRAINT question_category_pkey PRIMARY KEY (qcategory);


--
-- TOC entry 2033 (class 2606 OID 50685)
-- Dependencies: 1661 1661 1661
-- Name: question_category_questions_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace:
--

ALTER TABLE ONLY question_category_questions
    ADD CONSTRAINT question_category_questions_pkey PRIMARY KEY (question_category_qcategory, questionlibrary_qid);


--
-- TOC entry 2035 (class 2606 OID 50690)
-- Dependencies: 1662 1662
-- Name: question_collection_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace:
--

ALTER TABLE ONLY question_collection
    ADD CONSTRAINT question_collection_pkey PRIMARY KEY (id_q_colection);


--
-- TOC entry 2037 (class 2606 OID 50695)
-- Dependencies: 1663 1663
-- Name: question_dependence_survey_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace:
--

ALTER TABLE ONLY question_dependence_survey
    ADD CONSTRAINT question_dependence_survey_pkey PRIMARY KEY (question_dependence_survey);


--
-- TOC entry 2039 (class 2606 OID 50700)
-- Dependencies: 1664 1664 1664
-- Name: question_relations_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace:
--

ALTER TABLE ONLY question_relations
    ADD CONSTRAINT question_relations_pkey PRIMARY KEY (question_id, id_q_colection);


--
-- TOC entry 2043 (class 2606 OID 50716)
-- Dependencies: 1666 1666
-- Name: questions_answers_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace:
--

ALTER TABLE ONLY questions_answers
    ADD CONSTRAINT questions_answers_pkey PRIMARY KEY (q_answer_id);


--
-- TOC entry 2045 (class 2606 OID 50723)
-- Dependencies: 1667 1667
-- Name: questions_dependencies_descriptiondependence_key; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace:
--

ALTER TABLE ONLY questions_dependencies
    ADD CONSTRAINT questions_dependencies_descriptiondependence_key UNIQUE (descriptiondependence);


--
-- TOC entry 2047 (class 2606 OID 50721)
-- Dependencies: 1667 1667
-- Name: questions_dependencies_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace:
--

ALTER TABLE ONLY questions_dependencies
    ADD CONSTRAINT questions_dependencies_pkey PRIMARY KEY (question_dependenceid);


--
-- TOC entry 2049 (class 2606 OID 50725)
-- Dependencies: 1667 1667
-- Name: questions_dependencies_questionid_from_key; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace:
--

ALTER TABLE ONLY questions_dependencies
    ADD CONSTRAINT questions_dependencies_questionid_from_key UNIQUE (questionid_from);


--
-- TOC entry 2051 (class 2606 OID 50727)
-- Dependencies: 1667 1667
-- Name: questions_dependencies_questionid_to_key; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace:
--

ALTER TABLE ONLY questions_dependencies
    ADD CONSTRAINT questions_dependencies_questionid_to_key UNIQUE (questionid_to);


--
-- TOC entry 2053 (class 2606 OID 50735)
-- Dependencies: 1668 1668
-- Name: questions_pattern_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace:
--

ALTER TABLE ONLY questions_pattern
    ADD CONSTRAINT questions_pattern_pkey PRIMARY KEY (pattenr_id);


--
-- TOC entry 2041 (class 2606 OID 50708)
-- Dependencies: 1665 1665
-- Name: questions_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace:
--

ALTER TABLE ONLY questions
    ADD CONSTRAINT questions_pkey PRIMARY KEY (qid);


--
-- TOC entry 2055 (class 2606 OID 50743)
-- Dependencies: 1669 1669
-- Name: social_account_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace:
--

ALTER TABLE ONLY social_account
    ADD CONSTRAINT social_account_pkey PRIMARY KEY (social_account_id);


--
-- TOC entry 2057 (class 2606 OID 50747)
-- Dependencies: 1669 1669
-- Name: social_account_social_account_name_key; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace:
--

ALTER TABLE ONLY social_account
    ADD CONSTRAINT social_account_social_account_name_key UNIQUE (social_account_name);


--
-- TOC entry 2059 (class 2606 OID 50745)
-- Dependencies: 1669 1669
-- Name: social_account_social_profile_id_key; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace:
--

ALTER TABLE ONLY social_account
    ADD CONSTRAINT social_account_social_profile_id_key UNIQUE (social_profile_id);


--
-- TOC entry 2061 (class 2606 OID 50752)
-- Dependencies: 1670 1670
-- Name: survey_folder_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace:
--

ALTER TABLE ONLY survey_folder
    ADD CONSTRAINT survey_folder_pkey PRIMARY KEY (survey_folderid);


--
-- TOC entry 2063 (class 2606 OID 50757)
-- Dependencies: 1671 1671
-- Name: survey_format_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace:
--

ALTER TABLE ONLY survey_format
    ADD CONSTRAINT survey_format_pkey PRIMARY KEY (id_sid_format);


--
-- TOC entry 2067 (class 2606 OID 50767)
-- Dependencies: 1673 1673 1673
-- Name: survey_group_format_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace:
--

ALTER TABLE ONLY survey_group_format
    ADD CONSTRAINT survey_group_format_pkey PRIMARY KEY (id_sid_format, sg_id);


--
-- TOC entry 2065 (class 2606 OID 50762)
-- Dependencies: 1672 1672
-- Name: survey_group_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace:
--

ALTER TABLE ONLY survey_group
    ADD CONSTRAINT survey_group_pkey PRIMARY KEY (sg_id);


--
-- TOC entry 2069 (class 2606 OID 50772)
-- Dependencies: 1674 1674 1674
-- Name: survey_group_project_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace:
--

ALTER TABLE ONLY survey_group_project
    ADD CONSTRAINT survey_group_project_pkey PRIMARY KEY (cat_id_project, id_sid_format);


--
-- TOC entry 2071 (class 2606 OID 50777)
-- Dependencies: 1675 1675
-- Name: survey_pagination_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace:
--

ALTER TABLE ONLY survey_pagination
    ADD CONSTRAINT survey_pagination_pkey PRIMARY KEY (pagination_id);


--
-- TOC entry 2073 (class 2606 OID 50782)
-- Dependencies: 1676 1676
-- Name: survey_result_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace:
--

ALTER TABLE ONLY survey_result
    ADD CONSTRAINT survey_result_pkey PRIMARY KEY (rid);


--
-- TOC entry 2075 (class 2606 OID 50787)
-- Dependencies: 1677 1677
-- Name: survey_section_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace:
--

ALTER TABLE ONLY survey_section
    ADD CONSTRAINT survey_section_pkey PRIMARY KEY (ssid);


--
-- TOC entry 2077 (class 2606 OID 50792)
-- Dependencies: 1678 1678 1678
-- Name: survey_section_questions_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace:
--

ALTER TABLE ONLY survey_section_questions
    ADD CONSTRAINT survey_section_questions_pkey PRIMARY KEY (survey_section_ssid, questionsection_qid);


--
-- TOC entry 2079 (class 2606 OID 50800)
-- Dependencies: 1679 1679
-- Name: surveys_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace:
--

ALTER TABLE ONLY surveys
    ADD CONSTRAINT surveys_pkey PRIMARY KEY (sid);


--
-- TOC entry 2083 (class 2606 OID 50810)
-- Dependencies: 1681 1681
-- Name: tweetpoll_folder_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace:
--

ALTER TABLE ONLY tweetpoll_folder
    ADD CONSTRAINT tweetpoll_folder_pkey PRIMARY KEY (tweetpollfolderid);


--
-- TOC entry 2087 (class 2606 OID 50823)
-- Dependencies: 1683 1683 1683
-- Name: tweetpoll_hashtags_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace:
--

ALTER TABLE ONLY tweetpoll_hashtags
    ADD CONSTRAINT tweetpoll_hashtags_pkey PRIMARY KEY (hastag_id, tweetpoll_id);


--
-- TOC entry 2081 (class 2606 OID 50805)
-- Dependencies: 1680 1680
-- Name: tweetpoll_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace:
--

ALTER TABLE ONLY tweetpoll
    ADD CONSTRAINT tweetpoll_pkey PRIMARY KEY (tweet_poll_id);


--
-- TOC entry 2089 (class 2606 OID 50828)
-- Dependencies: 1684 1684
-- Name: tweetpoll_result_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace:
--

ALTER TABLE ONLY tweetpoll_result
    ADD CONSTRAINT tweetpoll_result_pkey PRIMARY KEY (tweetpoll_resultid);


--
-- TOC entry 2085 (class 2606 OID 50818)
-- Dependencies: 1682 1682
-- Name: tweetpoll_save_published_status_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace:
--

ALTER TABLE ONLY tweetpoll_save_published_status
    ADD CONSTRAINT tweetpoll_save_published_status_pkey PRIMARY KEY (status_save_poll_id);


--
-- TOC entry 2091 (class 2606 OID 50836)
-- Dependencies: 1685 1685
-- Name: tweetpoll_switch_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace:
--

ALTER TABLE ONLY tweetpoll_switch
    ADD CONSTRAINT tweetpoll_switch_pkey PRIMARY KEY (tweetpoll_switch_id);


--
-- TOC entry 2093 (class 2606 OID 50838)
-- Dependencies: 1685 1685
-- Name: tweetpoll_switch_tweet_code_key; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace:
--

ALTER TABLE ONLY tweetpoll_switch
    ADD CONSTRAINT tweetpoll_switch_tweet_code_key UNIQUE (tweet_code);


--
-- TOC entry 2096 (class 2606 OID 50848)
-- Dependencies: 1686 1686
-- Name: useraccount_email_key; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace:
--

ALTER TABLE ONLY useraccount
    ADD CONSTRAINT useraccount_email_key UNIQUE (email);


--
-- TOC entry 2105 (class 2606 OID 50857)
-- Dependencies: 1687 1687 1687
-- Name: useraccount_followers_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace:
--

ALTER TABLE ONLY useraccount_followers
    ADD CONSTRAINT useraccount_followers_pkey PRIMARY KEY (uid, uid_follower);


--
-- TOC entry 2107 (class 2606 OID 50862)
-- Dependencies: 1688 1688 1688
-- Name: useraccount_permission_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace:
--

ALTER TABLE ONLY useraccount_permission
    ADD CONSTRAINT useraccount_permission_pkey PRIMARY KEY (sec_id_permission, sec_id_secondary);


--
-- TOC entry 2098 (class 2606 OID 50846)
-- Dependencies: 1686 1686
-- Name: useraccount_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace:
--

ALTER TABLE ONLY useraccount
    ADD CONSTRAINT useraccount_pkey PRIMARY KEY (uid);


--
-- TOC entry 2109 (class 2606 OID 50867)
-- Dependencies: 1689 1689 1689
-- Name: useraccount_project_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace:
--

ALTER TABLE ONLY useraccount_project
    ADD CONSTRAINT useraccount_project_pkey PRIMARY KEY (cat_id_project, sec_id_secondary);


--
-- TOC entry 2100 (class 2606 OID 50850)
-- Dependencies: 1686 1686
-- Name: useraccount_username_key; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace:
--

ALTER TABLE ONLY useraccount
    ADD CONSTRAINT useraccount_username_key UNIQUE (username);


--
-- TOC entry 2102 (class 2606 OID 50852)
-- Dependencies: 1686 1686 1686
-- Name: useraccount_username_key1; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace:
--

ALTER TABLE ONLY useraccount
    ADD CONSTRAINT useraccount_username_key1 UNIQUE (username, email);
--
-- TOC entry 2179 (class 2606 OID 51214)
-- Dependencies: 1678 1677 2074
-- Name: fk12354ece11057e56; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY survey_section_questions
    ADD CONSTRAINT fk12354ece11057e56 FOREIGN KEY (survey_section_ssid) REFERENCES survey_section(ssid);


--
-- TOC entry 2180 (class 2606 OID 51219)
-- Dependencies: 1678 1665 2040
-- Name: fk12354ece4e3a9df5; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY survey_section_questions
    ADD CONSTRAINT fk12354ece4e3a9df5 FOREIGN KEY (questionsection_qid) REFERENCES questions(qid);


--
-- TOC entry 2162 (class 2606 OID 51129)
-- Dependencies: 2034 1664 1662
-- Name: fk217954de893521da; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY question_relations
    ADD CONSTRAINT fk217954de893521da FOREIGN KEY (id_q_colection) REFERENCES question_collection(id_q_colection);


--
-- TOC entry 2163 (class 2606 OID 51134)
-- Dependencies: 1664 2040 1665
-- Name: fk217954de8a76a0bd; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY question_relations
    ADD CONSTRAINT fk217954de8a76a0bd FOREIGN KEY (question_id) REFERENCES questions(qid);


--
-- TOC entry 2141 (class 2606 OID 51024)
-- Dependencies: 1968 1651 1631
-- Name: fk237a88eb2b2a6ab4; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY notification
    ADD CONSTRAINT fk237a88eb2b2a6ab4 FOREIGN KEY (uid) REFERENCES account(uid);


--
-- TOC entry 2157 (class 2606 OID 51104)
-- Dependencies: 1643 1994 1659
-- Name: fk242951b835313189; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY project_locations
    ADD CONSTRAINT fk242951b835313189 FOREIGN KEY (cat_id_loc) REFERENCES geopoint(locate_id);


--
-- TOC entry 2156 (class 2606 OID 51099)
-- Dependencies: 1659 2022 1656
-- Name: fk242951b884536452; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY project_locations
    ADD CONSTRAINT fk242951b884536452 FOREIGN KEY (cat_id_project) REFERENCES project(project_id);


--
-- TOC entry 2153 (class 2606 OID 51084)
-- Dependencies: 1657 1994 1643
-- Name: fk2599132535313189; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY project_geopoint
    ADD CONSTRAINT fk2599132535313189 FOREIGN KEY (cat_id_loc) REFERENCES geopoint(locate_id);


--
-- TOC entry 2152 (class 2606 OID 51079)
-- Dependencies: 2022 1656 1657
-- Name: fk2599132584536452; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY project_geopoint
    ADD CONSTRAINT fk2599132584536452 FOREIGN KEY (cat_id_project) REFERENCES project(project_id);


--
-- TOC entry 2159 (class 2606 OID 51114)
-- Dependencies: 2040 1665 1661
-- Name: fk2ffe18457a068cb; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY question_category_questions
    ADD CONSTRAINT fk2ffe18457a068cb FOREIGN KEY (questionlibrary_qid) REFERENCES questions(qid);


--
-- TOC entry 2158 (class 2606 OID 51109)
-- Dependencies: 2030 1661 1660
-- Name: fk2ffe1845b10e79be; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY question_category_questions
    ADD CONSTRAINT fk2ffe1845b10e79be FOREIGN KEY (question_category_qcategory) REFERENCES question_category(qcategory);


--
-- TOC entry 2144 (class 2606 OID 51039)
-- Dependencies: 2040 1653 1665
-- Name: fk3497bf50fe71f5; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY poll
    ADD CONSTRAINT fk3497bf50fe71f5 FOREIGN KEY (qid) REFERENCES questions(qid);


--
-- TOC entry 2143 (class 2606 OID 51034)
-- Dependencies: 1654 1653 2018
-- Name: fk3497bf89452cca; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY poll
    ADD CONSTRAINT fk3497bf89452cca FOREIGN KEY (poll_folder) REFERENCES poll_folder(pollfolderid);


--
-- TOC entry 2145 (class 2606 OID 51044)
-- Dependencies: 2097 1686 1653
-- Name: fk3497bfa64fb606; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY poll
    ADD CONSTRAINT fk3497bfa64fb606 FOREIGN KEY (editor) REFERENCES useraccount(uid);


--
-- TOC entry 2142 (class 2606 OID 51029)
-- Dependencies: 2097 1653 1686
-- Name: fk3497bff44558e9; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY poll
    ADD CONSTRAINT fk3497bff44558e9 FOREIGN KEY (uid) REFERENCES useraccount(uid);


--
-- TOC entry 2135 (class 2606 OID 50994)
-- Dependencies: 1652 2012 1646
-- Name: fk362e6f8f43adb63d; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY group_permission
    ADD CONSTRAINT fk362e6f8f43adb63d FOREIGN KEY (sec_id_permission) REFERENCES permission(id_permission);


--
-- TOC entry 2134 (class 2606 OID 50989)
-- Dependencies: 1647 1646 2002
-- Name: fk362e6f8f45895aff; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY group_permission
    ADD CONSTRAINT fk362e6f8f45895aff FOREIGN KEY (sec_id_group) REFERENCES groups(group_id);


--
-- TOC entry 2123 (class 2606 OID 50934)
-- Dependencies: 1640 1984 1638
-- Name: fk4b85010ee824035; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY emailsubscribe
    ADD CONSTRAINT fk4b85010ee824035 FOREIGN KEY (email_id) REFERENCES email(email_id);


--
-- TOC entry 2122 (class 2606 OID 50929)
-- Dependencies: 1640 1986 1639
-- Name: fk4b85010eed78e617; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY emailsubscribe
    ADD CONSTRAINT fk4b85010eed78e617 FOREIGN KEY (id_list) REFERENCES emaillist(id_list);


--
-- TOC entry 2168 (class 2606 OID 51159)
-- Dependencies: 1669 1631 1968
-- Name: fk50078b5b5ece45a2; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY social_account
    ADD CONSTRAINT fk50078b5b5ece45a2 FOREIGN KEY (account_uid) REFERENCES account(uid);


--
-- TOC entry 2169 (class 2606 OID 51164)
-- Dependencies: 1669 1686 2097
-- Name: fk50078b5bf2f411f2; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY social_account
    ADD CONSTRAINT fk50078b5bf2f411f2 FOREIGN KEY (userowner_uid) REFERENCES useraccount(uid);


--
-- TOC entry 2133 (class 2606 OID 50984)
-- Dependencies: 1645 1968 1631
-- Name: fk514326ba4075e3fd; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY geopoint_type
    ADD CONSTRAINT fk514326ba4075e3fd FOREIGN KEY (users_uid) REFERENCES account(uid);


--
-- TOC entry 2166 (class 2606 OID 51149)
-- Dependencies: 1666 1665 2040
-- Name: fk539703837e6c7bbc; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY questions_answers
    ADD CONSTRAINT fk539703837e6c7bbc FOREIGN KEY (id_question_answer) REFERENCES questions(qid);


--
-- TOC entry 2140 (class 2606 OID 51019)
-- Dependencies: 2006 1650 1649
-- Name: fk58554db519aa125; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY hash_tags_hits
    ADD CONSTRAINT fk58554db519aa125 FOREIGN KEY (hashtag_hash_tag_id) REFERENCES hash_tags(hash_tag_id);


--
-- TOC entry 2139 (class 2606 OID 51014)
-- Dependencies: 1686 2097 1650
-- Name: fk58554db538a08f1; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY hash_tags_hits
    ADD CONSTRAINT fk58554db538a08f1 FOREIGN KEY (hits_user_account) REFERENCES useraccount(uid);


--
-- TOC entry 2120 (class 2606 OID 50919)
-- Dependencies: 1638 1639 1986
-- Name: fk5c24b9ced78e617; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY email
    ADD CONSTRAINT fk5c24b9ced78e617 FOREIGN KEY (id_list) REFERENCES emaillist(id_list);


--
-- TOC entry 2110 (class 2606 OID 50868)
-- Dependencies: 1632 1631 1968
-- Name: fk5ca405505ece45a2; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY application
    ADD CONSTRAINT fk5ca405505ece45a2 FOREIGN KEY (account_uid) REFERENCES account(uid);


--
-- TOC entry 2129 (class 2606 OID 50964)
-- Dependencies: 1643 1644 1996
-- Name: fk6c73c0bf34ef9a43; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY geopoint
    ADD CONSTRAINT fk6c73c0bf34ef9a43 FOREIGN KEY (geopointfolder_locate_folder_id) REFERENCES geopoint_folder(locate_folder_id);


--
-- TOC entry 2128 (class 2606 OID 50959)
-- Dependencies: 1968 1643 1631
-- Name: fk6c73c0bf5ece45a2; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY geopoint
    ADD CONSTRAINT fk6c73c0bf5ece45a2 FOREIGN KEY (account_uid) REFERENCES account(uid);


--
-- TOC entry 2127 (class 2606 OID 50954)
-- Dependencies: 1998 1645 1643
-- Name: fk6c73c0bfbd91661d; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY geopoint
    ADD CONSTRAINT fk6c73c0bfbd91661d FOREIGN KEY (loc_id_type) REFERENCES geopoint_type(loc_id_type);


--
-- TOC entry 2111 (class 2606 OID 50874)
-- Dependencies: 1633 1686 2097
-- Name: fk73d5d2d27e933d7; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY application_connection
    ADD CONSTRAINT fk73d5d2d27e933d7 FOREIGN KEY (account_uid) REFERENCES useraccount(uid);


--
-- TOC entry 2112 (class 2606 OID 50879)
-- Dependencies: 1633 1632 1970
-- Name: fk73d5d2d4402be26; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY application_connection
    ADD CONSTRAINT fk73d5d2d4402be26 FOREIGN KEY (application_application_id) REFERENCES application(application_id);


--
-- TOC entry 2121 (class 2606 OID 50924)
-- Dependencies: 1639 1968 1631
-- Name: fk7e5f425a2b2a6ab4; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY emaillist
    ADD CONSTRAINT fk7e5f425a2b2a6ab4 FOREIGN KEY (uid) REFERENCES account(uid);


--
-- TOC entry 2170 (class 2606 OID 51169)
-- Dependencies: 1968 1631 1670
-- Name: fk7ef958f32b2a6ab4; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY survey_folder
    ADD CONSTRAINT fk7ef958f32b2a6ab4 FOREIGN KEY (uid) REFERENCES account(uid);


--
-- TOC entry 2171 (class 2606 OID 51174)
-- Dependencies: 1670 1686 2097
-- Name: fk7ef958f36ef241e9; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY survey_folder
    ADD CONSTRAINT fk7ef958f36ef241e9 FOREIGN KEY (createdby_uid) REFERENCES useraccount(uid);


--
-- TOC entry 2138 (class 2606 OID 51009)
-- Dependencies: 1648 2012 1652
-- Name: fk7f1951a43adb63d; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY groups_permission
    ADD CONSTRAINT fk7f1951a43adb63d FOREIGN KEY (sec_id_permission) REFERENCES permission(id_permission);


--
-- TOC entry 2137 (class 2606 OID 51004)
-- Dependencies: 1648 2002 1647
-- Name: fk7f1951a45895aff; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY groups_permission
    ADD CONSTRAINT fk7f1951a45895aff FOREIGN KEY (sec_id_group) REFERENCES groups(group_id);


--
-- TOC entry 2201 (class 2606 OID 51326)
-- Dependencies: 1687 2097 1686
-- Name: fk7f1957f8e53fbc6; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY useraccount_followers
    ADD CONSTRAINT fk7f1957f8e53fbc6 FOREIGN KEY (uid_follower) REFERENCES useraccount(uid);


--
-- TOC entry 2200 (class 2606 OID 51321)
-- Dependencies: 1687 1686 2097
-- Name: fk7f1957f8f44558e9; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY useraccount_followers
    ADD CONSTRAINT fk7f1957f8f44558e9 FOREIGN KEY (uid) REFERENCES useraccount(uid);


--
-- TOC entry 2126 (class 2606 OID 50949)
-- Dependencies: 1642 1990 1641
-- Name: fk866b670629091b05; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY gadget_properties
    ADD CONSTRAINT fk866b670629091b05 FOREIGN KEY (gadget_gadgetid) REFERENCES gadget(gadgetid);


--
-- TOC entry 2125 (class 2606 OID 50944)
-- Dependencies: 1686 1642 2097
-- Name: fk866b6706369f8b2c; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY gadget_properties
    ADD CONSTRAINT fk866b6706369f8b2c FOREIGN KEY (useraccount_uid) REFERENCES useraccount(uid);


--
-- TOC entry 2195 (class 2606 OID 51294)
-- Dependencies: 1684 1685 2090
-- Name: fk8749c18cb9d39f98; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY tweetpoll_result
    ADD CONSTRAINT fk8749c18cb9d39f98 FOREIGN KEY (tweetpoll_switch_id) REFERENCES tweetpoll_switch(tweetpoll_switch_id);


--
-- TOC entry 2196 (class 2606 OID 51299)
-- Dependencies: 1685 2080 1680
-- Name: fk89f7b0a3550299a; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY tweetpoll_switch
    ADD CONSTRAINT fk89f7b0a3550299a FOREIGN KEY (tweet_poll_id) REFERENCES tweetpoll(tweet_poll_id);


--
-- TOC entry 2197 (class 2606 OID 51304)
-- Dependencies: 1666 1685 2042
-- Name: fk89f7b0a3ddd118b5; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY tweetpoll_switch
    ADD CONSTRAINT fk89f7b0a3ddd118b5 FOREIGN KEY (q_answer_id) REFERENCES questions_answers(q_answer_id);


--
-- TOC entry 2113 (class 2606 OID 50884)
-- Dependencies: 2022 1656 1634
-- Name: fk8af75923225a055; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY attachment
    ADD CONSTRAINT fk8af75923225a055 FOREIGN KEY (project_id) REFERENCES project(project_id);


--
-- TOC entry 2181 (class 2606 OID 51224)
-- Dependencies: 1679 1631 1968
-- Name: fk919144592b2a6ab4; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY surveys
    ADD CONSTRAINT fk919144592b2a6ab4 FOREIGN KEY (uid) REFERENCES account(uid);


--
-- TOC entry 2182 (class 2606 OID 51229)
-- Dependencies: 1679 1670 2060
-- Name: fk91914459a3c7a06a; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY surveys
    ADD CONSTRAINT fk91914459a3c7a06a FOREIGN KEY (survey_folder) REFERENCES survey_folder(survey_folderid);


--
-- TOC entry 2184 (class 2606 OID 51239)
-- Dependencies: 1679 2097 1686
-- Name: fk91914459a64fb606; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY surveys
    ADD CONSTRAINT fk91914459a64fb606 FOREIGN KEY (editor) REFERENCES useraccount(uid);


--
-- TOC entry 2183 (class 2606 OID 51234)
-- Dependencies: 1679 2062 1671
-- Name: fk91914459b1a6912c; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY surveys
    ADD CONSTRAINT fk91914459b1a6912c FOREIGN KEY (id_sid_format) REFERENCES survey_format(id_sid_format);


--
-- TOC entry 2167 (class 2606 OID 51154)
-- Dependencies: 1667 1666 2042
-- Name: fk92e86adbddd118b5; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY questions_dependencies
    ADD CONSTRAINT fk92e86adbddd118b5 FOREIGN KEY (q_answer_id) REFERENCES questions_answers(q_answer_id);


--
-- TOC entry 2178 (class 2606 OID 51209)
-- Dependencies: 1676 1679 2078
-- Name: fk92ea04a2eb8d35c9; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY survey_result
    ADD CONSTRAINT fk92ea04a2eb8d35c9 FOREIGN KEY (survey_id) REFERENCES surveys(sid);


--
-- TOC entry 2164 (class 2606 OID 51139)
-- Dependencies: 1665 1631 1968
-- Name: fk95c5414d2b2a6ab4; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY questions
    ADD CONSTRAINT fk95c5414d2b2a6ab4 FOREIGN KEY (uid) REFERENCES account(uid);


--
-- TOC entry 2165 (class 2606 OID 51144)
-- Dependencies: 1665 1668 2052
-- Name: fk95c5414d84133d82; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY questions
    ADD CONSTRAINT fk95c5414d84133d82 FOREIGN KEY (id_question_pattern) REFERENCES questions_pattern(pattenr_id);


--
-- TOC entry 2189 (class 2606 OID 51264)
-- Dependencies: 1681 1631 1968
-- Name: fka027a9dd2b2a6ab4; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY tweetpoll_folder
    ADD CONSTRAINT fka027a9dd2b2a6ab4 FOREIGN KEY (uid) REFERENCES account(uid);


--
-- TOC entry 2190 (class 2606 OID 51269)
-- Dependencies: 1681 1686 2097
-- Name: fka027a9dd6ef241e9; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY tweetpoll_folder
    ADD CONSTRAINT fka027a9dd6ef241e9 FOREIGN KEY (createdby_uid) REFERENCES useraccount(uid);


--
-- TOC entry 2185 (class 2606 OID 51244)
-- Dependencies: 1680 1631 1968
-- Name: fka65b1d02b2a6ab4; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY tweetpoll
    ADD CONSTRAINT fka65b1d02b2a6ab4 FOREIGN KEY (uid) REFERENCES account(uid);


--
-- TOC entry 2187 (class 2606 OID 51254)
-- Dependencies: 1680 1665 2040
-- Name: fka65b1d050fe71f5; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY tweetpoll
    ADD CONSTRAINT fka65b1d050fe71f5 FOREIGN KEY (qid) REFERENCES questions(qid);


--
-- TOC entry 2188 (class 2606 OID 51259)
-- Dependencies: 1680 1686 2097
-- Name: fka65b1d0a64fb606; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY tweetpoll
    ADD CONSTRAINT fka65b1d0a64fb606 FOREIGN KEY (editor) REFERENCES useraccount(uid);


--
-- TOC entry 2186 (class 2606 OID 51249)
-- Dependencies: 1680 1681 2082
-- Name: fka65b1d0d9ba7e54; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY tweetpoll
    ADD CONSTRAINT fka65b1d0d9ba7e54 FOREIGN KEY (tweetpollfolderid) REFERENCES tweetpoll_folder(tweetpollfolderid);


--
-- TOC entry 2198 (class 2606 OID 51311)
-- Dependencies: 1686 1631 1968
-- Name: fka7d56be25ece45a2; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY useraccount
    ADD CONSTRAINT fka7d56be25ece45a2 FOREIGN KEY (account_uid) REFERENCES account(uid);


--
-- TOC entry 2199 (class 2606 OID 51316)
-- Dependencies: 1686 2002 1647
-- Name: fka7d56be2b8eb1450; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY useraccount
    ADD CONSTRAINT fka7d56be2b8eb1450 FOREIGN KEY (groupid) REFERENCES groups(group_id);


--
-- TOC entry 2114 (class 2606 OID 50889)
-- Dependencies: 1656 2022 1635
-- Name: fkaf12f3cb225a055; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY client
    ADD CONSTRAINT fkaf12f3cb225a055 FOREIGN KEY (project_id) REFERENCES project(project_id);


--
-- TOC entry 2160 (class 2606 OID 51119)
-- Dependencies: 1968 1662 1631
-- Name: fkb4097c972b2a6ab4; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY question_collection
    ADD CONSTRAINT fkb4097c972b2a6ab4 FOREIGN KEY (uid) REFERENCES account(uid);


--
-- TOC entry 2172 (class 2606 OID 51179)
-- Dependencies: 2064 1673 1672
-- Name: fkb4df867c310e993c; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY survey_group_format
    ADD CONSTRAINT fkb4df867c310e993c FOREIGN KEY (sg_id) REFERENCES survey_group(sg_id);


--
-- TOC entry 2173 (class 2606 OID 51184)
-- Dependencies: 1673 1671 2062
-- Name: fkb4df867cb1a6912c; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY survey_group_format
    ADD CONSTRAINT fkb4df867cb1a6912c FOREIGN KEY (id_sid_format) REFERENCES survey_format(id_sid_format);


--
-- TOC entry 2124 (class 2606 OID 50939)
-- Dependencies: 1641 1980 1637
-- Name: fkb549144cb975b5f9; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY gadget
    ADD CONSTRAINT fkb549144cb975b5f9 FOREIGN KEY (dashboard_dashboardid) REFERENCES dashboard(dashboardid);


--
-- TOC entry 2136 (class 2606 OID 50999)
-- Dependencies: 1647 1631 1968
-- Name: fkb63dd9d45ece45a2; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY groups
    ADD CONSTRAINT fkb63dd9d45ece45a2 FOREIGN KEY (account_uid) REFERENCES account(uid);


--
-- TOC entry 2161 (class 2606 OID 51124)
-- Dependencies: 2078 1663 1679
-- Name: fkbb424d49793d9e77; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY question_dependence_survey
    ADD CONSTRAINT fkbb424d49793d9e77 FOREIGN KEY (sid) REFERENCES surveys(sid);


--
-- TOC entry 2202 (class 2606 OID 51331)
-- Dependencies: 1652 1688 2012
-- Name: fkbe01ce4c43adb63d; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY useraccount_permission
    ADD CONSTRAINT fkbe01ce4c43adb63d FOREIGN KEY (sec_id_permission) REFERENCES permission(id_permission);


--
-- TOC entry 2203 (class 2606 OID 51336)
-- Dependencies: 2097 1688 1686
-- Name: fkbe01ce4c5f77a117; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY useraccount_permission
    ADD CONSTRAINT fkbe01ce4c5f77a117 FOREIGN KEY (sec_id_secondary) REFERENCES useraccount(uid);


--
-- TOC entry 2177 (class 2606 OID 51204)
-- Dependencies: 1675 1677 2074
-- Name: fkbec9a99f1359b877; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY survey_pagination
    ADD CONSTRAINT fkbec9a99f1359b877 FOREIGN KEY (ssid) REFERENCES survey_section(ssid);


--
-- TOC entry 2176 (class 2606 OID 51199)
-- Dependencies: 1675 1679 2078
-- Name: fkbec9a99f793d9e77; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY survey_pagination
    ADD CONSTRAINT fkbec9a99f793d9e77 FOREIGN KEY (sid) REFERENCES surveys(sid);


--
-- TOC entry 2119 (class 2606 OID 50914)
-- Dependencies: 1637 1686 2097
-- Name: fkc18aea949229bca5; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY dashboard
    ADD CONSTRAINT fkc18aea949229bca5 FOREIGN KEY (userboard_uid) REFERENCES useraccount(uid);


--
-- TOC entry 2146 (class 2606 OID 51049)
-- Dependencies: 1631 1968 1654
-- Name: fkc5911cee2b2a6ab4; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY poll_folder
    ADD CONSTRAINT fkc5911cee2b2a6ab4 FOREIGN KEY (uid) REFERENCES account(uid);


--
-- TOC entry 2147 (class 2606 OID 51054)
-- Dependencies: 1686 1654 2097
-- Name: fkc5911cee6ef241e9; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY poll_folder
    ADD CONSTRAINT fkc5911cee6ef241e9 FOREIGN KEY (createdby_uid) REFERENCES useraccount(uid);


--
-- TOC entry 2154 (class 2606 OID 51089)
-- Dependencies: 1647 2002 1658
-- Name: fkc7652dd945895aff; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY project_group
    ADD CONSTRAINT fkc7652dd945895aff FOREIGN KEY (sec_id_group) REFERENCES groups(group_id);


--
-- TOC entry 2155 (class 2606 OID 51094)
-- Dependencies: 2022 1656 1658
-- Name: fkc7652dd984536452; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY project_group
    ADD CONSTRAINT fkc7652dd984536452 FOREIGN KEY (cat_id_project) REFERENCES project(project_id);


--
-- TOC entry 2191 (class 2606 OID 51274)
-- Dependencies: 1682 1669 2054
-- Name: fkd499a4b65239d117; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY tweetpoll_save_published_status
    ADD CONSTRAINT fkd499a4b65239d117 FOREIGN KEY (socialaccount_social_account_id) REFERENCES social_account(social_account_id);


--
-- TOC entry 2192 (class 2606 OID 51279)
-- Dependencies: 1682 1680 2080
-- Name: fkd499a4b6953c854b; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY tweetpoll_save_published_status
    ADD CONSTRAINT fkd499a4b6953c854b FOREIGN KEY (tweetpoll_tweet_poll_id) REFERENCES tweetpoll(tweet_poll_id);


--
-- TOC entry 2148 (class 2606 OID 51059)
-- Dependencies: 1666 1655 2042
-- Name: fkd981c89dddd118b5; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY poll_result
    ADD CONSTRAINT fkd981c89dddd118b5 FOREIGN KEY (q_answer_id) REFERENCES questions_answers(q_answer_id);


--
-- TOC entry 2149 (class 2606 OID 51064)
-- Dependencies: 2014 1655 1653
-- Name: fkd981c89df0ed6769; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY poll_result
    ADD CONSTRAINT fkd981c89df0ed6769 FOREIGN KEY (poll_id) REFERENCES poll(poll_id);


--
-- TOC entry 2116 (class 2606 OID 50899)
-- Dependencies: 1636 1679 2078
-- Name: fkdc17ddf4793d9e77; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY comments
    ADD CONSTRAINT fkdc17ddf4793d9e77 FOREIGN KEY (sid) REFERENCES surveys(sid);


--
-- TOC entry 2117 (class 2606 OID 50904)
-- Dependencies: 1636 1653 2014
-- Name: fkdc17ddf4ce12cae8; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY comments
    ADD CONSTRAINT fkdc17ddf4ce12cae8 FOREIGN KEY (pollid) REFERENCES poll(poll_id);


--
-- TOC entry 2118 (class 2606 OID 50909)
-- Dependencies: 1636 1680 2080
-- Name: fkdc17ddf4d9aa8e98; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY comments
    ADD CONSTRAINT fkdc17ddf4d9aa8e98 FOREIGN KEY (tweetpollid) REFERENCES tweetpoll(tweet_poll_id);


--
-- TOC entry 2115 (class 2606 OID 50894)
-- Dependencies: 1636 1686 2097
-- Name: fkdc17ddf4f44558e9; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY comments
    ADD CONSTRAINT fkdc17ddf4f44558e9 FOREIGN KEY (uid) REFERENCES useraccount(uid);


--
-- TOC entry 2151 (class 2606 OID 51074)
-- Dependencies: 1656 1968 1631
-- Name: fked904b194075e3fd; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY project
    ADD CONSTRAINT fked904b194075e3fd FOREIGN KEY (users_uid) REFERENCES account(uid);


--
-- TOC entry 2150 (class 2606 OID 51069)
-- Dependencies: 1656 1686 2097
-- Name: fked904b19514c1986; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY project
    ADD CONSTRAINT fked904b19514c1986 FOREIGN KEY (lead_uid) REFERENCES useraccount(uid);


--
-- TOC entry 2130 (class 2606 OID 50969)
-- Dependencies: 1631 1968 1644
-- Name: fkf4a1d3ee2b2a6ab4; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY geopoint_folder
    ADD CONSTRAINT fkf4a1d3ee2b2a6ab4 FOREIGN KEY (uid) REFERENCES account(uid);


--
-- TOC entry 2132 (class 2606 OID 50979)
-- Dependencies: 1996 1644 1644
-- Name: fkf4a1d3ee6e4ed46d; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY geopoint_folder
    ADD CONSTRAINT fkf4a1d3ee6e4ed46d FOREIGN KEY (sublocationfolder_locate_folder_id) REFERENCES geopoint_folder(locate_folder_id);


--
-- TOC entry 2131 (class 2606 OID 50974)
-- Dependencies: 1686 2097 1644
-- Name: fkf4a1d3ee6ef241e9; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY geopoint_folder
    ADD CONSTRAINT fkf4a1d3ee6ef241e9 FOREIGN KEY (createdby_uid) REFERENCES useraccount(uid);


--
-- TOC entry 2193 (class 2606 OID 51284)
-- Dependencies: 1683 1680 2080
-- Name: fkf8c717d6286705d7; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY tweetpoll_hashtags
    ADD CONSTRAINT fkf8c717d6286705d7 FOREIGN KEY (tweetpoll_id) REFERENCES tweetpoll(tweet_poll_id);


--
-- TOC entry 2194 (class 2606 OID 51289)
-- Dependencies: 1683 1649 2006
-- Name: fkf8c717d6da98ffe1; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY tweetpoll_hashtags
    ADD CONSTRAINT fkf8c717d6da98ffe1 FOREIGN KEY (hastag_id) REFERENCES hash_tags(hash_tag_id);


--
-- TOC entry 2205 (class 2606 OID 51346)
-- Dependencies: 2097 1689 1686
-- Name: fkfbc45bbc5f77a117; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY useraccount_project
    ADD CONSTRAINT fkfbc45bbc5f77a117 FOREIGN KEY (sec_id_secondary) REFERENCES useraccount(uid);


--
-- TOC entry 2204 (class 2606 OID 51341)
-- Dependencies: 2022 1689 1656
-- Name: fkfbc45bbc84536452; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY useraccount_project
    ADD CONSTRAINT fkfbc45bbc84536452 FOREIGN KEY (cat_id_project) REFERENCES project(project_id);


--
-- TOC entry 2174 (class 2606 OID 51189)
-- Dependencies: 1674 1656 2022
-- Name: fkfd028d3484536452; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY survey_group_project
    ADD CONSTRAINT fkfd028d3484536452 FOREIGN KEY (cat_id_project) REFERENCES project(project_id);


--
-- TOC entry 2175 (class 2606 OID 51194)
-- Dependencies: 1674 1672 2064
-- Name: fkfd028d34b75f3482; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY survey_group_project
    ADD CONSTRAINT fkfd028d34b75f3482 FOREIGN KEY (id_sid_format) REFERENCES survey_group(sg_id);


--
-- TOC entry 2210 (class 0 OID 0)
-- Dependencies: 3
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


-- Completed on 2011-09-10 13:33:11 CEST

--
-- PostgreSQL database dump complete
--
