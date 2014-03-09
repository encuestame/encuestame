--
-- TOC entry 2372 (class 2606 OID 16390)
-- Name: access_rate_pkey; Type: CONSTRAINT; Schema: public; Owner: jpicado; Tablespace: 
--

ALTER TABLE ONLY access_rate
    ADD CONSTRAINT access_rate_pkey PRIMARY KEY (rateid);


--
-- TOC entry 2374 (class 2606 OID 16395)
-- Name: account_pkey; Type: CONSTRAINT; Schema: public; Owner: jpicado; Tablespace: 
--

ALTER TABLE ONLY account
    ADD CONSTRAINT account_pkey PRIMARY KEY (uid);


--
-- TOC entry 2378 (class 2606 OID 16411)
-- Name: application_connection_pkey; Type: CONSTRAINT; Schema: public; Owner: jpicado; Tablespace: 
--

ALTER TABLE ONLY application_connection
    ADD CONSTRAINT application_connection_pkey PRIMARY KEY (connection_id);


--
-- TOC entry 2376 (class 2606 OID 16403)
-- Name: application_pkey; Type: CONSTRAINT; Schema: public; Owner: jpicado; Tablespace: 
--

ALTER TABLE ONLY application
    ADD CONSTRAINT application_pkey PRIMARY KEY (application_id);


--
-- TOC entry 2380 (class 2606 OID 16416)
-- Name: attachment_pkey; Type: CONSTRAINT; Schema: public; Owner: jpicado; Tablespace: 
--

ALTER TABLE ONLY attachment
    ADD CONSTRAINT attachment_pkey PRIMARY KEY (attachment_id);


--
-- TOC entry 2382 (class 2606 OID 16424)
-- Name: client_pkey; Type: CONSTRAINT; Schema: public; Owner: jpicado; Tablespace: 
--

ALTER TABLE ONLY client
    ADD CONSTRAINT client_pkey PRIMARY KEY (client_id);


--
-- TOC entry 2384 (class 2606 OID 16432)
-- Name: comments_pkey; Type: CONSTRAINT; Schema: public; Owner: jpicado; Tablespace: 
--

ALTER TABLE ONLY comments
    ADD CONSTRAINT comments_pkey PRIMARY KEY (commentid);


--
-- TOC entry 2386 (class 2606 OID 16440)
-- Name: dashboard_pkey; Type: CONSTRAINT; Schema: public; Owner: jpicado; Tablespace: 
--

ALTER TABLE ONLY dashboard
    ADD CONSTRAINT dashboard_pkey PRIMARY KEY (dashboardid);


--
-- TOC entry 2388 (class 2606 OID 16450)
-- Name: email_email_key; Type: CONSTRAINT; Schema: public; Owner: jpicado; Tablespace: 
--

ALTER TABLE ONLY email
    ADD CONSTRAINT email_email_key UNIQUE (email);


--
-- TOC entry 2390 (class 2606 OID 16448)
-- Name: email_pkey; Type: CONSTRAINT; Schema: public; Owner: jpicado; Tablespace: 
--

ALTER TABLE ONLY email
    ADD CONSTRAINT email_pkey PRIMARY KEY (email_id);


--
-- TOC entry 2392 (class 2606 OID 16458)
-- Name: emaillist_pkey; Type: CONSTRAINT; Schema: public; Owner: jpicado; Tablespace: 
--

ALTER TABLE ONLY emaillist
    ADD CONSTRAINT emaillist_pkey PRIMARY KEY (id_list);


--
-- TOC entry 2394 (class 2606 OID 16463)
-- Name: emailsubscribe_pkey; Type: CONSTRAINT; Schema: public; Owner: jpicado; Tablespace: 
--

ALTER TABLE ONLY emailsubscribe
    ADD CONSTRAINT emailsubscribe_pkey PRIMARY KEY (id_subscribe);


--
-- TOC entry 2396 (class 2606 OID 16471)
-- Name: gadget_pkey; Type: CONSTRAINT; Schema: public; Owner: jpicado; Tablespace: 
--

ALTER TABLE ONLY gadget
    ADD CONSTRAINT gadget_pkey PRIMARY KEY (gadgetid);


--
-- TOC entry 2398 (class 2606 OID 16479)
-- Name: gadget_properties_pkey; Type: CONSTRAINT; Schema: public; Owner: jpicado; Tablespace: 
--

ALTER TABLE ONLY gadget_properties
    ADD CONSTRAINT gadget_properties_pkey PRIMARY KEY (propertyid);


--
-- TOC entry 2402 (class 2606 OID 16495)
-- Name: geopoint_folder_pkey; Type: CONSTRAINT; Schema: public; Owner: jpicado; Tablespace: 
--

ALTER TABLE ONLY geopoint_folder
    ADD CONSTRAINT geopoint_folder_pkey PRIMARY KEY (locate_folder_id);


--
-- TOC entry 2400 (class 2606 OID 16487)
-- Name: geopoint_pkey; Type: CONSTRAINT; Schema: public; Owner: jpicado; Tablespace: 
--

ALTER TABLE ONLY geopoint
    ADD CONSTRAINT geopoint_pkey PRIMARY KEY (locate_id);


--
-- TOC entry 2404 (class 2606 OID 16500)
-- Name: geopoint_type_pkey; Type: CONSTRAINT; Schema: public; Owner: jpicado; Tablespace: 
--

ALTER TABLE ONLY geopoint_type
    ADD CONSTRAINT geopoint_type_pkey PRIMARY KEY (loc_id_type);


--
-- TOC entry 2406 (class 2606 OID 16505)
-- Name: group_permission_pkey; Type: CONSTRAINT; Schema: public; Owner: jpicado; Tablespace: 
--

ALTER TABLE ONLY group_permission
    ADD CONSTRAINT group_permission_pkey PRIMARY KEY (sec_id_permission, sec_id_group);


--
-- TOC entry 2410 (class 2606 OID 16518)
-- Name: groups_permission_pkey; Type: CONSTRAINT; Schema: public; Owner: jpicado; Tablespace: 
--

ALTER TABLE ONLY groups_permission
    ADD CONSTRAINT groups_permission_pkey PRIMARY KEY (sec_id_group, sec_id_permission);


--
-- TOC entry 2408 (class 2606 OID 16513)
-- Name: groups_pkey; Type: CONSTRAINT; Schema: public; Owner: jpicado; Tablespace: 
--

ALTER TABLE ONLY groups
    ADD CONSTRAINT groups_pkey PRIMARY KEY (group_id);


--
-- TOC entry 2412 (class 2606 OID 16523)
-- Name: hash_tags_pkey; Type: CONSTRAINT; Schema: public; Owner: jpicado; Tablespace: 
--

ALTER TABLE ONLY hash_tags
    ADD CONSTRAINT hash_tags_pkey PRIMARY KEY (hash_tag_id);


--
-- TOC entry 2414 (class 2606 OID 16528)
-- Name: hash_tags_ranking_pkey; Type: CONSTRAINT; Schema: public; Owner: jpicado; Tablespace: 
--

ALTER TABLE ONLY hash_tags_ranking
    ADD CONSTRAINT hash_tags_ranking_pkey PRIMARY KEY (rank_id);


--
-- TOC entry 2416 (class 2606 OID 16533)
-- Name: hits_pkey; Type: CONSTRAINT; Schema: public; Owner: jpicado; Tablespace: 
--

ALTER TABLE ONLY hits
    ADD CONSTRAINT hits_pkey PRIMARY KEY (hit_id);


--
-- TOC entry 2418 (class 2606 OID 16541)
-- Name: notification_pkey; Type: CONSTRAINT; Schema: public; Owner: jpicado; Tablespace: 
--

ALTER TABLE ONLY notification
    ADD CONSTRAINT notification_pkey PRIMARY KEY (notification_id);


--
-- TOC entry 2420 (class 2606 OID 16549)
-- Name: permission_pkey; Type: CONSTRAINT; Schema: public; Owner: jpicado; Tablespace: 
--

ALTER TABLE ONLY permission
    ADD CONSTRAINT permission_pkey PRIMARY KEY (id_permission);


--
-- TOC entry 2426 (class 2606 OID 16564)
-- Name: poll_folder_pkey; Type: CONSTRAINT; Schema: public; Owner: jpicado; Tablespace: 
--

ALTER TABLE ONLY poll_folder
    ADD CONSTRAINT poll_folder_pkey PRIMARY KEY (pollfolderid);


--
-- TOC entry 2428 (class 2606 OID 16569)
-- Name: poll_hashtags_pkey; Type: CONSTRAINT; Schema: public; Owner: jpicado; Tablespace: 
--

ALTER TABLE ONLY poll_hashtags
    ADD CONSTRAINT poll_hashtags_pkey PRIMARY KEY (poll_id, hastag_id);


--
-- TOC entry 2422 (class 2606 OID 16557)
-- Name: poll_pkey; Type: CONSTRAINT; Schema: public; Owner: jpicado; Tablespace: 
--

ALTER TABLE ONLY poll
    ADD CONSTRAINT poll_pkey PRIMARY KEY (poll_id);


--
-- TOC entry 2424 (class 2606 OID 16559)
-- Name: poll_poll_hash_key; Type: CONSTRAINT; Schema: public; Owner: jpicado; Tablespace: 
--

ALTER TABLE ONLY poll
    ADD CONSTRAINT poll_poll_hash_key UNIQUE (poll_hash);


--
-- TOC entry 2430 (class 2606 OID 16574)
-- Name: poll_result_pkey; Type: CONSTRAINT; Schema: public; Owner: jpicado; Tablespace: 
--

ALTER TABLE ONLY poll_result
    ADD CONSTRAINT poll_result_pkey PRIMARY KEY (poll_resultid);


--
-- TOC entry 2434 (class 2606 OID 16587)
-- Name: project_geopoint_pkey; Type: CONSTRAINT; Schema: public; Owner: jpicado; Tablespace: 
--

ALTER TABLE ONLY project_geopoint
    ADD CONSTRAINT project_geopoint_pkey PRIMARY KEY (cat_id_project, cat_id_loc);


--
-- TOC entry 2436 (class 2606 OID 16592)
-- Name: project_group_pkey; Type: CONSTRAINT; Schema: public; Owner: jpicado; Tablespace: 
--

ALTER TABLE ONLY project_group
    ADD CONSTRAINT project_group_pkey PRIMARY KEY (cat_id_project, sec_id_group);


--
-- TOC entry 2438 (class 2606 OID 16597)
-- Name: project_locations_pkey; Type: CONSTRAINT; Schema: public; Owner: jpicado; Tablespace: 
--

ALTER TABLE ONLY project_locations
    ADD CONSTRAINT project_locations_pkey PRIMARY KEY (cat_id_loc, cat_id_project);


--
-- TOC entry 2432 (class 2606 OID 16582)
-- Name: project_pkey; Type: CONSTRAINT; Schema: public; Owner: jpicado; Tablespace: 
--

ALTER TABLE ONLY project
    ADD CONSTRAINT project_pkey PRIMARY KEY (project_id);


--
-- TOC entry 2440 (class 2606 OID 16602)
-- Name: question_category_pkey; Type: CONSTRAINT; Schema: public; Owner: jpicado; Tablespace: 
--

ALTER TABLE ONLY question_category
    ADD CONSTRAINT question_category_pkey PRIMARY KEY (qcategory);


--
-- TOC entry 2442 (class 2606 OID 16607)
-- Name: question_category_questions_pkey; Type: CONSTRAINT; Schema: public; Owner: jpicado; Tablespace: 
--

ALTER TABLE ONLY question_category_questions
    ADD CONSTRAINT question_category_questions_pkey PRIMARY KEY (question_category_qcategory, questionlibrary_qid);


--
-- TOC entry 2444 (class 2606 OID 16612)
-- Name: question_collection_pkey; Type: CONSTRAINT; Schema: public; Owner: jpicado; Tablespace: 
--

ALTER TABLE ONLY question_collection
    ADD CONSTRAINT question_collection_pkey PRIMARY KEY (id_q_colection);


--
-- TOC entry 2446 (class 2606 OID 16617)
-- Name: question_dependence_survey_pkey; Type: CONSTRAINT; Schema: public; Owner: jpicado; Tablespace: 
--

ALTER TABLE ONLY question_dependence_survey
    ADD CONSTRAINT question_dependence_survey_pkey PRIMARY KEY (question_dependence_survey);


--
-- TOC entry 2448 (class 2606 OID 16625)
-- Name: question_preferences_pkey; Type: CONSTRAINT; Schema: public; Owner: jpicado; Tablespace: 
--

ALTER TABLE ONLY question_preferences
    ADD CONSTRAINT question_preferences_pkey PRIMARY KEY (preferenceid);


--
-- TOC entry 2450 (class 2606 OID 16630)
-- Name: question_relations_pkey; Type: CONSTRAINT; Schema: public; Owner: jpicado; Tablespace: 
--

ALTER TABLE ONLY question_relations
    ADD CONSTRAINT question_relations_pkey PRIMARY KEY (question_id, id_q_colection);


--
-- TOC entry 2454 (class 2606 OID 16646)
-- Name: questions_answers_pkey; Type: CONSTRAINT; Schema: public; Owner: jpicado; Tablespace: 
--

ALTER TABLE ONLY questions_answers
    ADD CONSTRAINT questions_answers_pkey PRIMARY KEY (q_answer_id);


--
-- TOC entry 2456 (class 2606 OID 16653)
-- Name: questions_dependencies_descriptiondependence_key; Type: CONSTRAINT; Schema: public; Owner: jpicado; Tablespace: 
--

ALTER TABLE ONLY questions_dependencies
    ADD CONSTRAINT questions_dependencies_descriptiondependence_key UNIQUE (descriptiondependence);


--
-- TOC entry 2458 (class 2606 OID 16651)
-- Name: questions_dependencies_pkey; Type: CONSTRAINT; Schema: public; Owner: jpicado; Tablespace: 
--

ALTER TABLE ONLY questions_dependencies
    ADD CONSTRAINT questions_dependencies_pkey PRIMARY KEY (question_dependenceid);


--
-- TOC entry 2460 (class 2606 OID 16655)
-- Name: questions_dependencies_questionid_from_key; Type: CONSTRAINT; Schema: public; Owner: jpicado; Tablespace: 
--

ALTER TABLE ONLY questions_dependencies
    ADD CONSTRAINT questions_dependencies_questionid_from_key UNIQUE (questionid_from);


--
-- TOC entry 2462 (class 2606 OID 16657)
-- Name: questions_dependencies_questionid_to_key; Type: CONSTRAINT; Schema: public; Owner: jpicado; Tablespace: 
--

ALTER TABLE ONLY questions_dependencies
    ADD CONSTRAINT questions_dependencies_questionid_to_key UNIQUE (questionid_to);


--
-- TOC entry 2452 (class 2606 OID 16638)
-- Name: questions_pkey; Type: CONSTRAINT; Schema: public; Owner: jpicado; Tablespace: 
--

ALTER TABLE ONLY questions
    ADD CONSTRAINT questions_pkey PRIMARY KEY (qid);


--
-- TOC entry 2464 (class 2606 OID 16662)
-- Name: scheduled_pkey; Type: CONSTRAINT; Schema: public; Owner: jpicado; Tablespace: 
--

ALTER TABLE ONLY scheduled
    ADD CONSTRAINT scheduled_pkey PRIMARY KEY (publish_scheduled_id);


--
-- TOC entry 2466 (class 2606 OID 16670)
-- Name: social_account_pkey; Type: CONSTRAINT; Schema: public; Owner: jpicado; Tablespace: 
--

ALTER TABLE ONLY social_account
    ADD CONSTRAINT social_account_pkey PRIMARY KEY (social_account_id);


--
-- TOC entry 2468 (class 2606 OID 16672)
-- Name: social_account_social_profile_id_key; Type: CONSTRAINT; Schema: public; Owner: jpicado; Tablespace: 
--

ALTER TABLE ONLY social_account
    ADD CONSTRAINT social_account_social_profile_id_key UNIQUE (social_profile_id);


--
-- TOC entry 2470 (class 2606 OID 16677)
-- Name: survey_folder_pkey; Type: CONSTRAINT; Schema: public; Owner: jpicado; Tablespace: 
--

ALTER TABLE ONLY survey_folder
    ADD CONSTRAINT survey_folder_pkey PRIMARY KEY (survey_folderid);


--
-- TOC entry 2472 (class 2606 OID 16682)
-- Name: survey_format_pkey; Type: CONSTRAINT; Schema: public; Owner: jpicado; Tablespace: 
--

ALTER TABLE ONLY survey_format
    ADD CONSTRAINT survey_format_pkey PRIMARY KEY (id_sid_format);


--
-- TOC entry 2476 (class 2606 OID 16692)
-- Name: survey_group_format_pkey; Type: CONSTRAINT; Schema: public; Owner: jpicado; Tablespace: 
--

ALTER TABLE ONLY survey_group_format
    ADD CONSTRAINT survey_group_format_pkey PRIMARY KEY (id_sid_format, sg_id);


--
-- TOC entry 2474 (class 2606 OID 16687)
-- Name: survey_group_pkey; Type: CONSTRAINT; Schema: public; Owner: jpicado; Tablespace: 
--

ALTER TABLE ONLY survey_group
    ADD CONSTRAINT survey_group_pkey PRIMARY KEY (sg_id);


--
-- TOC entry 2478 (class 2606 OID 16697)
-- Name: survey_group_project_pkey; Type: CONSTRAINT; Schema: public; Owner: jpicado; Tablespace: 
--

ALTER TABLE ONLY survey_group_project
    ADD CONSTRAINT survey_group_project_pkey PRIMARY KEY (cat_id_project, id_sid_format);


--
-- TOC entry 2480 (class 2606 OID 16702)
-- Name: survey_hashtags_pkey; Type: CONSTRAINT; Schema: public; Owner: jpicado; Tablespace: 
--

ALTER TABLE ONLY survey_hashtags
    ADD CONSTRAINT survey_hashtags_pkey PRIMARY KEY (sid, hastag_id);


--
-- TOC entry 2482 (class 2606 OID 16707)
-- Name: survey_pagination_pkey; Type: CONSTRAINT; Schema: public; Owner: jpicado; Tablespace: 
--

ALTER TABLE ONLY survey_pagination
    ADD CONSTRAINT survey_pagination_pkey PRIMARY KEY (pagination_id);


--
-- TOC entry 2484 (class 2606 OID 16712)
-- Name: survey_result_pkey; Type: CONSTRAINT; Schema: public; Owner: jpicado; Tablespace: 
--

ALTER TABLE ONLY survey_result
    ADD CONSTRAINT survey_result_pkey PRIMARY KEY (rid);


--
-- TOC entry 2486 (class 2606 OID 16720)
-- Name: survey_section_pkey; Type: CONSTRAINT; Schema: public; Owner: jpicado; Tablespace: 
--

ALTER TABLE ONLY survey_section
    ADD CONSTRAINT survey_section_pkey PRIMARY KEY (ssid);


--
-- TOC entry 2488 (class 2606 OID 16730)
-- Name: survey_temporal_result_hash_key; Type: CONSTRAINT; Schema: public; Owner: jpicado; Tablespace: 
--

ALTER TABLE ONLY survey_temporal_result
    ADD CONSTRAINT survey_temporal_result_hash_key UNIQUE (hash);


--
-- TOC entry 2490 (class 2606 OID 16728)
-- Name: survey_temporal_result_pkey; Type: CONSTRAINT; Schema: public; Owner: jpicado; Tablespace: 
--

ALTER TABLE ONLY survey_temporal_result
    ADD CONSTRAINT survey_temporal_result_pkey PRIMARY KEY (idtempresult);


--
-- TOC entry 2492 (class 2606 OID 16738)
-- Name: surveys_pkey; Type: CONSTRAINT; Schema: public; Owner: jpicado; Tablespace: 
--

ALTER TABLE ONLY surveys
    ADD CONSTRAINT surveys_pkey PRIMARY KEY (sid);


--
-- TOC entry 2496 (class 2606 OID 16748)
-- Name: tweetpoll_folder_pkey; Type: CONSTRAINT; Schema: public; Owner: jpicado; Tablespace: 
--

ALTER TABLE ONLY tweetpoll_folder
    ADD CONSTRAINT tweetpoll_folder_pkey PRIMARY KEY (tweetpollfolderid);


--
-- TOC entry 2500 (class 2606 OID 16761)
-- Name: tweetpoll_hashtags_pkey; Type: CONSTRAINT; Schema: public; Owner: jpicado; Tablespace: 
--

ALTER TABLE ONLY tweetpoll_hashtags
    ADD CONSTRAINT tweetpoll_hashtags_pkey PRIMARY KEY (hastag_id, tweetpoll_id);


--
-- TOC entry 2494 (class 2606 OID 16743)
-- Name: tweetpoll_pkey; Type: CONSTRAINT; Schema: public; Owner: jpicado; Tablespace: 
--

ALTER TABLE ONLY tweetpoll
    ADD CONSTRAINT tweetpoll_pkey PRIMARY KEY (tweet_poll_id);


--
-- TOC entry 2502 (class 2606 OID 16766)
-- Name: tweetpoll_rate_pkey; Type: CONSTRAINT; Schema: public; Owner: jpicado; Tablespace: 
--

ALTER TABLE ONLY tweetpoll_rate
    ADD CONSTRAINT tweetpoll_rate_pkey PRIMARY KEY (tweetpollrateid);


--
-- TOC entry 2504 (class 2606 OID 16771)
-- Name: tweetpoll_result_pkey; Type: CONSTRAINT; Schema: public; Owner: jpicado; Tablespace: 
--

ALTER TABLE ONLY tweetpoll_result
    ADD CONSTRAINT tweetpoll_result_pkey PRIMARY KEY (tweetpoll_resultid);


--
-- TOC entry 2498 (class 2606 OID 16756)
-- Name: tweetpoll_save_published_status_pkey; Type: CONSTRAINT; Schema: public; Owner: jpicado; Tablespace: 
--

ALTER TABLE ONLY tweetpoll_save_published_status
    ADD CONSTRAINT tweetpoll_save_published_status_pkey PRIMARY KEY (status_save_poll_id);


--
-- TOC entry 2506 (class 2606 OID 16779)
-- Name: tweetpoll_switch_pkey; Type: CONSTRAINT; Schema: public; Owner: jpicado; Tablespace: 
--

ALTER TABLE ONLY tweetpoll_switch
    ADD CONSTRAINT tweetpoll_switch_pkey PRIMARY KEY (tweetpoll_switch_id);


--
-- TOC entry 2508 (class 2606 OID 16781)
-- Name: tweetpoll_switch_tweet_code_key; Type: CONSTRAINT; Schema: public; Owner: jpicado; Tablespace: 
--

ALTER TABLE ONLY tweetpoll_switch
    ADD CONSTRAINT tweetpoll_switch_tweet_code_key UNIQUE (tweet_code);


--
-- TOC entry 2511 (class 2606 OID 16791)
-- Name: useraccount_email_key; Type: CONSTRAINT; Schema: public; Owner: jpicado; Tablespace: 
--

ALTER TABLE ONLY useraccount
    ADD CONSTRAINT useraccount_email_key UNIQUE (email);


--
-- TOC entry 2520 (class 2606 OID 16800)
-- Name: useraccount_followers_pkey; Type: CONSTRAINT; Schema: public; Owner: jpicado; Tablespace: 
--

ALTER TABLE ONLY useraccount_followers
    ADD CONSTRAINT useraccount_followers_pkey PRIMARY KEY (uid, uid_follower);


--
-- TOC entry 2522 (class 2606 OID 16805)
-- Name: useraccount_permission_pkey; Type: CONSTRAINT; Schema: public; Owner: jpicado; Tablespace: 
--

ALTER TABLE ONLY useraccount_permission
    ADD CONSTRAINT useraccount_permission_pkey PRIMARY KEY (sec_id_permission, sec_id_secondary);


--
-- TOC entry 2513 (class 2606 OID 16789)
-- Name: useraccount_pkey; Type: CONSTRAINT; Schema: public; Owner: jpicado; Tablespace: 
--

ALTER TABLE ONLY useraccount
    ADD CONSTRAINT useraccount_pkey PRIMARY KEY (uid);


--
-- TOC entry 2524 (class 2606 OID 16810)
-- Name: useraccount_project_pkey; Type: CONSTRAINT; Schema: public; Owner: jpicado; Tablespace: 
--

ALTER TABLE ONLY useraccount_project
    ADD CONSTRAINT useraccount_project_pkey PRIMARY KEY (cat_id_project, sec_id_secondary);


--
-- TOC entry 2515 (class 2606 OID 16795)
-- Name: useraccount_username_email_key; Type: CONSTRAINT; Schema: public; Owner: jpicado; Tablespace: 
--

ALTER TABLE ONLY useraccount
    ADD CONSTRAINT useraccount_username_email_key UNIQUE (username, email);


--
-- TOC entry 2517 (class 2606 OID 16793)
-- Name: useraccount_username_key; Type: CONSTRAINT; Schema: public; Owner: jpicado; Tablespace: 
--

ALTER TABLE ONLY useraccount
    ADD CONSTRAINT useraccount_username_key UNIQUE (username);


--
-- TOC entry 2589 (class 2606 OID 17131)
-- Name: fk217954de893521da; Type: FK CONSTRAINT; Schema: public; Owner: jpicado
--

ALTER TABLE ONLY question_relations
    ADD CONSTRAINT fk217954de893521da FOREIGN KEY (id_q_colection) REFERENCES question_collection(id_q_colection);


--
-- TOC entry 2590 (class 2606 OID 17136)
-- Name: fk217954de8a76a0bd; Type: FK CONSTRAINT; Schema: public; Owner: jpicado
--

ALTER TABLE ONLY question_relations
    ADD CONSTRAINT fk217954de8a76a0bd FOREIGN KEY (question_id) REFERENCES questions(qid);


--
-- TOC entry 2565 (class 2606 OID 17011)
-- Name: fk237a88eb2b2a6ab4; Type: FK CONSTRAINT; Schema: public; Owner: jpicado
--

ALTER TABLE ONLY notification
    ADD CONSTRAINT fk237a88eb2b2a6ab4 FOREIGN KEY (uid) REFERENCES account(uid);


--
-- TOC entry 2583 (class 2606 OID 17101)
-- Name: fk242951b835313189; Type: FK CONSTRAINT; Schema: public; Owner: jpicado
--

ALTER TABLE ONLY project_locations
    ADD CONSTRAINT fk242951b835313189 FOREIGN KEY (cat_id_loc) REFERENCES geopoint(locate_id);


--
-- TOC entry 2582 (class 2606 OID 17096)
-- Name: fk242951b884536452; Type: FK CONSTRAINT; Schema: public; Owner: jpicado
--

ALTER TABLE ONLY project_locations
    ADD CONSTRAINT fk242951b884536452 FOREIGN KEY (cat_id_project) REFERENCES project(project_id);


--
-- TOC entry 2579 (class 2606 OID 17081)
-- Name: fk2599132535313189; Type: FK CONSTRAINT; Schema: public; Owner: jpicado
--

ALTER TABLE ONLY project_geopoint
    ADD CONSTRAINT fk2599132535313189 FOREIGN KEY (cat_id_loc) REFERENCES geopoint(locate_id);


--
-- TOC entry 2578 (class 2606 OID 17076)
-- Name: fk2599132584536452; Type: FK CONSTRAINT; Schema: public; Owner: jpicado
--

ALTER TABLE ONLY project_geopoint
    ADD CONSTRAINT fk2599132584536452 FOREIGN KEY (cat_id_project) REFERENCES project(project_id);


--
-- TOC entry 2585 (class 2606 OID 17111)
-- Name: fk2ffe18457a068cb; Type: FK CONSTRAINT; Schema: public; Owner: jpicado
--

ALTER TABLE ONLY question_category_questions
    ADD CONSTRAINT fk2ffe18457a068cb FOREIGN KEY (questionlibrary_qid) REFERENCES questions(qid);


--
-- TOC entry 2584 (class 2606 OID 17106)
-- Name: fk2ffe1845b10e79be; Type: FK CONSTRAINT; Schema: public; Owner: jpicado
--

ALTER TABLE ONLY question_category_questions
    ADD CONSTRAINT fk2ffe1845b10e79be FOREIGN KEY (question_category_qcategory) REFERENCES question_category(qcategory);


--
-- TOC entry 2561 (class 2606 OID 16991)
-- Name: fk30df4019aa125; Type: FK CONSTRAINT; Schema: public; Owner: jpicado
--

ALTER TABLE ONLY hits
    ADD CONSTRAINT fk30df4019aa125 FOREIGN KEY (hashtag_hash_tag_id) REFERENCES hash_tags(hash_tag_id);


--
-- TOC entry 2560 (class 2606 OID 16986)
-- Name: fk30df40369f8b2c; Type: FK CONSTRAINT; Schema: public; Owner: jpicado
--

ALTER TABLE ONLY hits
    ADD CONSTRAINT fk30df40369f8b2c FOREIGN KEY (useraccount_uid) REFERENCES useraccount(uid);


--
-- TOC entry 2563 (class 2606 OID 17001)
-- Name: fk30df4051153812; Type: FK CONSTRAINT; Schema: public; Owner: jpicado
--

ALTER TABLE ONLY hits
    ADD CONSTRAINT fk30df4051153812 FOREIGN KEY (survey_sid) REFERENCES surveys(sid);


--
-- TOC entry 2562 (class 2606 OID 16996)
-- Name: fk30df4063976e9; Type: FK CONSTRAINT; Schema: public; Owner: jpicado
--

ALTER TABLE ONLY hits
    ADD CONSTRAINT fk30df4063976e9 FOREIGN KEY (poll_poll_id) REFERENCES poll(poll_id);


--
-- TOC entry 2564 (class 2606 OID 17006)
-- Name: fk30df40953c854b; Type: FK CONSTRAINT; Schema: public; Owner: jpicado
--

ALTER TABLE ONLY hits
    ADD CONSTRAINT fk30df40953c854b FOREIGN KEY (tweetpoll_tweet_poll_id) REFERENCES tweetpoll(tweet_poll_id);


--
-- TOC entry 2567 (class 2606 OID 17021)
-- Name: fk3497bf50fe71f5; Type: FK CONSTRAINT; Schema: public; Owner: jpicado
--

ALTER TABLE ONLY poll
    ADD CONSTRAINT fk3497bf50fe71f5 FOREIGN KEY (qid) REFERENCES questions(qid);


--
-- TOC entry 2566 (class 2606 OID 17016)
-- Name: fk3497bf89452cca; Type: FK CONSTRAINT; Schema: public; Owner: jpicado
--

ALTER TABLE ONLY poll
    ADD CONSTRAINT fk3497bf89452cca FOREIGN KEY (poll_folder) REFERENCES poll_folder(pollfolderid);


--
-- TOC entry 2569 (class 2606 OID 17031)
-- Name: fk3497bf8e4a448b; Type: FK CONSTRAINT; Schema: public; Owner: jpicado
--

ALTER TABLE ONLY poll
    ADD CONSTRAINT fk3497bf8e4a448b FOREIGN KEY (owner_id) REFERENCES account(uid);


--
-- TOC entry 2568 (class 2606 OID 17026)
-- Name: fk3497bfa64fb606; Type: FK CONSTRAINT; Schema: public; Owner: jpicado
--

ALTER TABLE ONLY poll
    ADD CONSTRAINT fk3497bfa64fb606 FOREIGN KEY (editor) REFERENCES useraccount(uid);


--
-- TOC entry 2555 (class 2606 OID 16961)
-- Name: fk362e6f8f43adb63d; Type: FK CONSTRAINT; Schema: public; Owner: jpicado
--

ALTER TABLE ONLY group_permission
    ADD CONSTRAINT fk362e6f8f43adb63d FOREIGN KEY (sec_id_permission) REFERENCES permission(id_permission);


--
-- TOC entry 2554 (class 2606 OID 16956)
-- Name: fk362e6f8f45895aff; Type: FK CONSTRAINT; Schema: public; Owner: jpicado
--

ALTER TABLE ONLY group_permission
    ADD CONSTRAINT fk362e6f8f45895aff FOREIGN KEY (sec_id_group) REFERENCES groups(group_id);


--
-- TOC entry 2543 (class 2606 OID 16901)
-- Name: fk4b85010ee824035; Type: FK CONSTRAINT; Schema: public; Owner: jpicado
--

ALTER TABLE ONLY emailsubscribe
    ADD CONSTRAINT fk4b85010ee824035 FOREIGN KEY (email_id) REFERENCES email(email_id);


--
-- TOC entry 2542 (class 2606 OID 16896)
-- Name: fk4b85010eed78e617; Type: FK CONSTRAINT; Schema: public; Owner: jpicado
--

ALTER TABLE ONLY emailsubscribe
    ADD CONSTRAINT fk4b85010eed78e617 FOREIGN KEY (id_list) REFERENCES emaillist(id_list);


--
-- TOC entry 2600 (class 2606 OID 17186)
-- Name: fk50078b5b5ece45a2; Type: FK CONSTRAINT; Schema: public; Owner: jpicado
--

ALTER TABLE ONLY social_account
    ADD CONSTRAINT fk50078b5b5ece45a2 FOREIGN KEY (account_uid) REFERENCES account(uid);


--
-- TOC entry 2601 (class 2606 OID 17191)
-- Name: fk50078b5bf2f411f2; Type: FK CONSTRAINT; Schema: public; Owner: jpicado
--

ALTER TABLE ONLY social_account
    ADD CONSTRAINT fk50078b5bf2f411f2 FOREIGN KEY (userowner_uid) REFERENCES useraccount(uid);


--
-- TOC entry 2553 (class 2606 OID 16951)
-- Name: fk514326ba4075e3fd; Type: FK CONSTRAINT; Schema: public; Owner: jpicado
--

ALTER TABLE ONLY geopoint_type
    ADD CONSTRAINT fk514326ba4075e3fd FOREIGN KEY (users_uid) REFERENCES account(uid);


--
-- TOC entry 2593 (class 2606 OID 17151)
-- Name: fk539703837e6c7bbc; Type: FK CONSTRAINT; Schema: public; Owner: jpicado
--

ALTER TABLE ONLY questions_answers
    ADD CONSTRAINT fk539703837e6c7bbc FOREIGN KEY (id_question_answer) REFERENCES questions(qid);


--
-- TOC entry 2540 (class 2606 OID 16886)
-- Name: fk5c24b9ced78e617; Type: FK CONSTRAINT; Schema: public; Owner: jpicado
--

ALTER TABLE ONLY email
    ADD CONSTRAINT fk5c24b9ced78e617 FOREIGN KEY (id_list) REFERENCES emaillist(id_list);


--
-- TOC entry 2530 (class 2606 OID 16836)
-- Name: fk5ca405505ece45a2; Type: FK CONSTRAINT; Schema: public; Owner: jpicado
--

ALTER TABLE ONLY application
    ADD CONSTRAINT fk5ca405505ece45a2 FOREIGN KEY (account_uid) REFERENCES account(uid);


--
-- TOC entry 2549 (class 2606 OID 16931)
-- Name: fk6c73c0bf34ef9a43; Type: FK CONSTRAINT; Schema: public; Owner: jpicado
--

ALTER TABLE ONLY geopoint
    ADD CONSTRAINT fk6c73c0bf34ef9a43 FOREIGN KEY (geopointfolder_locate_folder_id) REFERENCES geopoint_folder(locate_folder_id);


--
-- TOC entry 2548 (class 2606 OID 16926)
-- Name: fk6c73c0bf5ece45a2; Type: FK CONSTRAINT; Schema: public; Owner: jpicado
--

ALTER TABLE ONLY geopoint
    ADD CONSTRAINT fk6c73c0bf5ece45a2 FOREIGN KEY (account_uid) REFERENCES account(uid);


--
-- TOC entry 2547 (class 2606 OID 16921)
-- Name: fk6c73c0bfbd91661d; Type: FK CONSTRAINT; Schema: public; Owner: jpicado
--

ALTER TABLE ONLY geopoint
    ADD CONSTRAINT fk6c73c0bfbd91661d FOREIGN KEY (loc_id_type) REFERENCES geopoint_type(loc_id_type);


--
-- TOC entry 2559 (class 2606 OID 16981)
-- Name: fk71decda119aa125; Type: FK CONSTRAINT; Schema: public; Owner: jpicado
--

ALTER TABLE ONLY hash_tags_ranking
    ADD CONSTRAINT fk71decda119aa125 FOREIGN KEY (hashtag_hash_tag_id) REFERENCES hash_tags(hash_tag_id);


--
-- TOC entry 2531 (class 2606 OID 16841)
-- Name: fk73d5d2d27e933d7; Type: FK CONSTRAINT; Schema: public; Owner: jpicado
--

ALTER TABLE ONLY application_connection
    ADD CONSTRAINT fk73d5d2d27e933d7 FOREIGN KEY (account_uid) REFERENCES useraccount(uid);


--
-- TOC entry 2532 (class 2606 OID 16846)
-- Name: fk73d5d2d4402be26; Type: FK CONSTRAINT; Schema: public; Owner: jpicado
--

ALTER TABLE ONLY application_connection
    ADD CONSTRAINT fk73d5d2d4402be26 FOREIGN KEY (application_application_id) REFERENCES application(application_id);


--
-- TOC entry 2616 (class 2606 OID 17266)
-- Name: fk7867cf546bf7a1c; Type: FK CONSTRAINT; Schema: public; Owner: jpicado
--

ALTER TABLE ONLY survey_temporal_result
    ADD CONSTRAINT fk7867cf546bf7a1c FOREIGN KEY (question_qid) REFERENCES questions(qid);


--
-- TOC entry 2617 (class 2606 OID 17271)
-- Name: fk7867cf5496009b4; Type: FK CONSTRAINT; Schema: public; Owner: jpicado
--

ALTER TABLE ONLY survey_temporal_result
    ADD CONSTRAINT fk7867cf5496009b4 FOREIGN KEY (answer_q_answer_id) REFERENCES questions_answers(q_answer_id);


--
-- TOC entry 2618 (class 2606 OID 17276)
-- Name: fk7867cf551153812; Type: FK CONSTRAINT; Schema: public; Owner: jpicado
--

ALTER TABLE ONLY survey_temporal_result
    ADD CONSTRAINT fk7867cf551153812 FOREIGN KEY (survey_sid) REFERENCES surveys(sid);


--
-- TOC entry 2541 (class 2606 OID 16891)
-- Name: fk7e5f425a2b2a6ab4; Type: FK CONSTRAINT; Schema: public; Owner: jpicado
--

ALTER TABLE ONLY emaillist
    ADD CONSTRAINT fk7e5f425a2b2a6ab4 FOREIGN KEY (uid) REFERENCES account(uid);


--
-- TOC entry 2602 (class 2606 OID 17196)
-- Name: fk7ef958f32b2a6ab4; Type: FK CONSTRAINT; Schema: public; Owner: jpicado
--

ALTER TABLE ONLY survey_folder
    ADD CONSTRAINT fk7ef958f32b2a6ab4 FOREIGN KEY (uid) REFERENCES account(uid);


--
-- TOC entry 2603 (class 2606 OID 17201)
-- Name: fk7ef958f36ef241e9; Type: FK CONSTRAINT; Schema: public; Owner: jpicado
--

ALTER TABLE ONLY survey_folder
    ADD CONSTRAINT fk7ef958f36ef241e9 FOREIGN KEY (createdby_uid) REFERENCES useraccount(uid);


--
-- TOC entry 2558 (class 2606 OID 16976)
-- Name: fk7f1951a43adb63d; Type: FK CONSTRAINT; Schema: public; Owner: jpicado
--

ALTER TABLE ONLY groups_permission
    ADD CONSTRAINT fk7f1951a43adb63d FOREIGN KEY (sec_id_permission) REFERENCES permission(id_permission);


--
-- TOC entry 2557 (class 2606 OID 16971)
-- Name: fk7f1951a45895aff; Type: FK CONSTRAINT; Schema: public; Owner: jpicado
--

ALTER TABLE ONLY groups_permission
    ADD CONSTRAINT fk7f1951a45895aff FOREIGN KEY (sec_id_group) REFERENCES groups(group_id);


--
-- TOC entry 2643 (class 2606 OID 17403)
-- Name: fk7f1957f8e53fbc6; Type: FK CONSTRAINT; Schema: public; Owner: jpicado
--

ALTER TABLE ONLY useraccount_followers
    ADD CONSTRAINT fk7f1957f8e53fbc6 FOREIGN KEY (uid_follower) REFERENCES useraccount(uid);


--
-- TOC entry 2642 (class 2606 OID 17398)
-- Name: fk7f1957f8f44558e9; Type: FK CONSTRAINT; Schema: public; Owner: jpicado
--

ALTER TABLE ONLY useraccount_followers
    ADD CONSTRAINT fk7f1957f8f44558e9 FOREIGN KEY (uid) REFERENCES useraccount(uid);


--
-- TOC entry 2546 (class 2606 OID 16916)
-- Name: fk866b670629091b05; Type: FK CONSTRAINT; Schema: public; Owner: jpicado
--

ALTER TABLE ONLY gadget_properties
    ADD CONSTRAINT fk866b670629091b05 FOREIGN KEY (gadget_gadgetid) REFERENCES gadget(gadgetid);


--
-- TOC entry 2545 (class 2606 OID 16911)
-- Name: fk866b6706369f8b2c; Type: FK CONSTRAINT; Schema: public; Owner: jpicado
--

ALTER TABLE ONLY gadget_properties
    ADD CONSTRAINT fk866b6706369f8b2c FOREIGN KEY (useraccount_uid) REFERENCES useraccount(uid);


--
-- TOC entry 2637 (class 2606 OID 17371)
-- Name: fk8749c18cb9d39f98; Type: FK CONSTRAINT; Schema: public; Owner: jpicado
--

ALTER TABLE ONLY tweetpoll_result
    ADD CONSTRAINT fk8749c18cb9d39f98 FOREIGN KEY (tweetpoll_switch_id) REFERENCES tweetpoll_switch(tweetpoll_switch_id);


--
-- TOC entry 2638 (class 2606 OID 17376)
-- Name: fk89f7b0a3550299a; Type: FK CONSTRAINT; Schema: public; Owner: jpicado
--

ALTER TABLE ONLY tweetpoll_switch
    ADD CONSTRAINT fk89f7b0a3550299a FOREIGN KEY (tweet_poll_id) REFERENCES tweetpoll(tweet_poll_id);


--
-- TOC entry 2639 (class 2606 OID 17381)
-- Name: fk89f7b0a3ddd118b5; Type: FK CONSTRAINT; Schema: public; Owner: jpicado
--

ALTER TABLE ONLY tweetpoll_switch
    ADD CONSTRAINT fk89f7b0a3ddd118b5 FOREIGN KEY (q_answer_id) REFERENCES questions_answers(q_answer_id);


--
-- TOC entry 2533 (class 2606 OID 16851)
-- Name: fk8af75923225a055; Type: FK CONSTRAINT; Schema: public; Owner: jpicado
--

ALTER TABLE ONLY attachment
    ADD CONSTRAINT fk8af75923225a055 FOREIGN KEY (project_id) REFERENCES project(project_id);


--
-- TOC entry 2620 (class 2606 OID 17286)
-- Name: fk9191445973ff13b; Type: FK CONSTRAINT; Schema: public; Owner: jpicado
--

ALTER TABLE ONLY surveys
    ADD CONSTRAINT fk9191445973ff13b FOREIGN KEY (project_project_id) REFERENCES project(project_id);


--
-- TOC entry 2622 (class 2606 OID 17296)
-- Name: fk919144598e4a448b; Type: FK CONSTRAINT; Schema: public; Owner: jpicado
--

ALTER TABLE ONLY surveys
    ADD CONSTRAINT fk919144598e4a448b FOREIGN KEY (owner_id) REFERENCES account(uid);


--
-- TOC entry 2619 (class 2606 OID 17281)
-- Name: fk91914459a3c7a06a; Type: FK CONSTRAINT; Schema: public; Owner: jpicado
--

ALTER TABLE ONLY surveys
    ADD CONSTRAINT fk91914459a3c7a06a FOREIGN KEY (survey_folder) REFERENCES survey_folder(survey_folderid);


--
-- TOC entry 2621 (class 2606 OID 17291)
-- Name: fk91914459a64fb606; Type: FK CONSTRAINT; Schema: public; Owner: jpicado
--

ALTER TABLE ONLY surveys
    ADD CONSTRAINT fk91914459a64fb606 FOREIGN KEY (editor) REFERENCES useraccount(uid);


--
-- TOC entry 2594 (class 2606 OID 17156)
-- Name: fk92e86adbddd118b5; Type: FK CONSTRAINT; Schema: public; Owner: jpicado
--

ALTER TABLE ONLY questions_dependencies
    ADD CONSTRAINT fk92e86adbddd118b5 FOREIGN KEY (q_answer_id) REFERENCES questions_answers(q_answer_id);


--
-- TOC entry 2612 (class 2606 OID 17246)
-- Name: fk92ea04a246bf7a1c; Type: FK CONSTRAINT; Schema: public; Owner: jpicado
--

ALTER TABLE ONLY survey_result
    ADD CONSTRAINT fk92ea04a246bf7a1c FOREIGN KEY (question_qid) REFERENCES questions(qid);


--
-- TOC entry 2613 (class 2606 OID 17251)
-- Name: fk92ea04a2496009b4; Type: FK CONSTRAINT; Schema: public; Owner: jpicado
--

ALTER TABLE ONLY survey_result
    ADD CONSTRAINT fk92ea04a2496009b4 FOREIGN KEY (answer_q_answer_id) REFERENCES questions_answers(q_answer_id);


--
-- TOC entry 2614 (class 2606 OID 17256)
-- Name: fk92ea04a251153812; Type: FK CONSTRAINT; Schema: public; Owner: jpicado
--

ALTER TABLE ONLY survey_result
    ADD CONSTRAINT fk92ea04a251153812 FOREIGN KEY (survey_sid) REFERENCES surveys(sid);


--
-- TOC entry 2591 (class 2606 OID 17141)
-- Name: fk95c5414d2b2a6ab4; Type: FK CONSTRAINT; Schema: public; Owner: jpicado
--

ALTER TABLE ONLY questions
    ADD CONSTRAINT fk95c5414d2b2a6ab4 FOREIGN KEY (uid) REFERENCES account(uid);


--
-- TOC entry 2592 (class 2606 OID 17146)
-- Name: fk95c5414d39e97991; Type: FK CONSTRAINT; Schema: public; Owner: jpicado
--

ALTER TABLE ONLY questions
    ADD CONSTRAINT fk95c5414d39e97991 FOREIGN KEY (section_ssid) REFERENCES survey_section(ssid);


--
-- TOC entry 2572 (class 2606 OID 17046)
-- Name: fk9d199ea7da98ffe1; Type: FK CONSTRAINT; Schema: public; Owner: jpicado
--

ALTER TABLE ONLY poll_hashtags
    ADD CONSTRAINT fk9d199ea7da98ffe1 FOREIGN KEY (hastag_id) REFERENCES hash_tags(hash_tag_id);


--
-- TOC entry 2573 (class 2606 OID 17051)
-- Name: fk9d199ea7f0ed6769; Type: FK CONSTRAINT; Schema: public; Owner: jpicado
--

ALTER TABLE ONLY poll_hashtags
    ADD CONSTRAINT fk9d199ea7f0ed6769 FOREIGN KEY (poll_id) REFERENCES poll(poll_id);


--
-- TOC entry 2608 (class 2606 OID 17226)
-- Name: fk9d62ed6c793d9e77; Type: FK CONSTRAINT; Schema: public; Owner: jpicado
--

ALTER TABLE ONLY survey_hashtags
    ADD CONSTRAINT fk9d62ed6c793d9e77 FOREIGN KEY (sid) REFERENCES surveys(sid);


--
-- TOC entry 2609 (class 2606 OID 17231)
-- Name: fk9d62ed6cda98ffe1; Type: FK CONSTRAINT; Schema: public; Owner: jpicado
--

ALTER TABLE ONLY survey_hashtags
    ADD CONSTRAINT fk9d62ed6cda98ffe1 FOREIGN KEY (hastag_id) REFERENCES hash_tags(hash_tag_id);


--
-- TOC entry 2627 (class 2606 OID 17321)
-- Name: fka027a9dd2b2a6ab4; Type: FK CONSTRAINT; Schema: public; Owner: jpicado
--

ALTER TABLE ONLY tweetpoll_folder
    ADD CONSTRAINT fka027a9dd2b2a6ab4 FOREIGN KEY (uid) REFERENCES account(uid);


--
-- TOC entry 2628 (class 2606 OID 17326)
-- Name: fka027a9dd6ef241e9; Type: FK CONSTRAINT; Schema: public; Owner: jpicado
--

ALTER TABLE ONLY tweetpoll_folder
    ADD CONSTRAINT fka027a9dd6ef241e9 FOREIGN KEY (createdby_uid) REFERENCES useraccount(uid);


--
-- TOC entry 2623 (class 2606 OID 17301)
-- Name: fka65b1d02b2a6ab4; Type: FK CONSTRAINT; Schema: public; Owner: jpicado
--

ALTER TABLE ONLY tweetpoll
    ADD CONSTRAINT fka65b1d02b2a6ab4 FOREIGN KEY (uid) REFERENCES account(uid);


--
-- TOC entry 2625 (class 2606 OID 17311)
-- Name: fka65b1d050fe71f5; Type: FK CONSTRAINT; Schema: public; Owner: jpicado
--

ALTER TABLE ONLY tweetpoll
    ADD CONSTRAINT fka65b1d050fe71f5 FOREIGN KEY (qid) REFERENCES questions(qid);


--
-- TOC entry 2626 (class 2606 OID 17316)
-- Name: fka65b1d0a64fb606; Type: FK CONSTRAINT; Schema: public; Owner: jpicado
--

ALTER TABLE ONLY tweetpoll
    ADD CONSTRAINT fka65b1d0a64fb606 FOREIGN KEY (editor) REFERENCES useraccount(uid);


--
-- TOC entry 2624 (class 2606 OID 17306)
-- Name: fka65b1d0d9ba7e54; Type: FK CONSTRAINT; Schema: public; Owner: jpicado
--

ALTER TABLE ONLY tweetpoll
    ADD CONSTRAINT fka65b1d0d9ba7e54 FOREIGN KEY (tweetpollfolderid) REFERENCES tweetpoll_folder(tweetpollfolderid);


--
-- TOC entry 2636 (class 2606 OID 17366)
-- Name: fka76ed60f953c854b; Type: FK CONSTRAINT; Schema: public; Owner: jpicado
--

ALTER TABLE ONLY tweetpoll_rate
    ADD CONSTRAINT fka76ed60f953c854b FOREIGN KEY (tweetpoll_tweet_poll_id) REFERENCES tweetpoll(tweet_poll_id);


--
-- TOC entry 2635 (class 2606 OID 17361)
-- Name: fka76ed60fe4669675; Type: FK CONSTRAINT; Schema: public; Owner: jpicado
--

ALTER TABLE ONLY tweetpoll_rate
    ADD CONSTRAINT fka76ed60fe4669675 FOREIGN KEY (user_uid) REFERENCES useraccount(uid);


--
-- TOC entry 2640 (class 2606 OID 17386)
-- Name: fka7d56be25ece45a2; Type: FK CONSTRAINT; Schema: public; Owner: jpicado
--

ALTER TABLE ONLY useraccount
    ADD CONSTRAINT fka7d56be25ece45a2 FOREIGN KEY (account_uid) REFERENCES account(uid);


--
-- TOC entry 2641 (class 2606 OID 17391)
-- Name: fka7d56be2b8eb1450; Type: FK CONSTRAINT; Schema: public; Owner: jpicado
--

ALTER TABLE ONLY useraccount
    ADD CONSTRAINT fka7d56be2b8eb1450 FOREIGN KEY (groupid) REFERENCES groups(group_id);


--
-- TOC entry 2534 (class 2606 OID 16856)
-- Name: fkaf12f3cb225a055; Type: FK CONSTRAINT; Schema: public; Owner: jpicado
--

ALTER TABLE ONLY client
    ADD CONSTRAINT fkaf12f3cb225a055 FOREIGN KEY (project_id) REFERENCES project(project_id);


--
-- TOC entry 2586 (class 2606 OID 17116)
-- Name: fkb4097c972b2a6ab4; Type: FK CONSTRAINT; Schema: public; Owner: jpicado
--

ALTER TABLE ONLY question_collection
    ADD CONSTRAINT fkb4097c972b2a6ab4 FOREIGN KEY (uid) REFERENCES account(uid);


--
-- TOC entry 2604 (class 2606 OID 17206)
-- Name: fkb4df867c310e993c; Type: FK CONSTRAINT; Schema: public; Owner: jpicado
--

ALTER TABLE ONLY survey_group_format
    ADD CONSTRAINT fkb4df867c310e993c FOREIGN KEY (sg_id) REFERENCES survey_group(sg_id);


--
-- TOC entry 2605 (class 2606 OID 17211)
-- Name: fkb4df867cb1a6912c; Type: FK CONSTRAINT; Schema: public; Owner: jpicado
--

ALTER TABLE ONLY survey_group_format
    ADD CONSTRAINT fkb4df867cb1a6912c FOREIGN KEY (id_sid_format) REFERENCES survey_format(id_sid_format);


--
-- TOC entry 2544 (class 2606 OID 16906)
-- Name: fkb549144cb975b5f9; Type: FK CONSTRAINT; Schema: public; Owner: jpicado
--

ALTER TABLE ONLY gadget
    ADD CONSTRAINT fkb549144cb975b5f9 FOREIGN KEY (dashboard_dashboardid) REFERENCES dashboard(dashboardid);


--
-- TOC entry 2556 (class 2606 OID 16966)
-- Name: fkb63dd9d45ece45a2; Type: FK CONSTRAINT; Schema: public; Owner: jpicado
--

ALTER TABLE ONLY groups
    ADD CONSTRAINT fkb63dd9d45ece45a2 FOREIGN KEY (account_uid) REFERENCES account(uid);


--
-- TOC entry 2587 (class 2606 OID 17121)
-- Name: fkbb424d49793d9e77; Type: FK CONSTRAINT; Schema: public; Owner: jpicado
--

ALTER TABLE ONLY question_dependence_survey
    ADD CONSTRAINT fkbb424d49793d9e77 FOREIGN KEY (sid) REFERENCES surveys(sid);


--
-- TOC entry 2644 (class 2606 OID 17408)
-- Name: fkbe01ce4c43adb63d; Type: FK CONSTRAINT; Schema: public; Owner: jpicado
--

ALTER TABLE ONLY useraccount_permission
    ADD CONSTRAINT fkbe01ce4c43adb63d FOREIGN KEY (sec_id_permission) REFERENCES permission(id_permission);


--
-- TOC entry 2645 (class 2606 OID 17413)
-- Name: fkbe01ce4c5f77a117; Type: FK CONSTRAINT; Schema: public; Owner: jpicado
--

ALTER TABLE ONLY useraccount_permission
    ADD CONSTRAINT fkbe01ce4c5f77a117 FOREIGN KEY (sec_id_secondary) REFERENCES useraccount(uid);


--
-- TOC entry 2611 (class 2606 OID 17241)
-- Name: fkbec9a99f1359b877; Type: FK CONSTRAINT; Schema: public; Owner: jpicado
--

ALTER TABLE ONLY survey_pagination
    ADD CONSTRAINT fkbec9a99f1359b877 FOREIGN KEY (ssid) REFERENCES survey_section(ssid);


--
-- TOC entry 2610 (class 2606 OID 17236)
-- Name: fkbec9a99f793d9e77; Type: FK CONSTRAINT; Schema: public; Owner: jpicado
--

ALTER TABLE ONLY survey_pagination
    ADD CONSTRAINT fkbec9a99f793d9e77 FOREIGN KEY (sid) REFERENCES surveys(sid);


--
-- TOC entry 2539 (class 2606 OID 16881)
-- Name: fkc18aea949229bca5; Type: FK CONSTRAINT; Schema: public; Owner: jpicado
--

ALTER TABLE ONLY dashboard
    ADD CONSTRAINT fkc18aea949229bca5 FOREIGN KEY (userboard_uid) REFERENCES useraccount(uid);


--
-- TOC entry 2528 (class 2606 OID 16826)
-- Name: fkc2760edb51153812; Type: FK CONSTRAINT; Schema: public; Owner: jpicado
--

ALTER TABLE ONLY access_rate
    ADD CONSTRAINT fkc2760edb51153812 FOREIGN KEY (survey_sid) REFERENCES surveys(sid);


--
-- TOC entry 2525 (class 2606 OID 16811)
-- Name: fkc2760edb546d76c9; Type: FK CONSTRAINT; Schema: public; Owner: jpicado
--

ALTER TABLE ONLY access_rate
    ADD CONSTRAINT fkc2760edb546d76c9 FOREIGN KEY (comments_commentid) REFERENCES comments(commentid);


--
-- TOC entry 2526 (class 2606 OID 16816)
-- Name: fkc2760edb63976e9; Type: FK CONSTRAINT; Schema: public; Owner: jpicado
--

ALTER TABLE ONLY access_rate
    ADD CONSTRAINT fkc2760edb63976e9 FOREIGN KEY (poll_poll_id) REFERENCES poll(poll_id);


--
-- TOC entry 2529 (class 2606 OID 16831)
-- Name: fkc2760edb953c854b; Type: FK CONSTRAINT; Schema: public; Owner: jpicado
--

ALTER TABLE ONLY access_rate
    ADD CONSTRAINT fkc2760edb953c854b FOREIGN KEY (tweetpoll_tweet_poll_id) REFERENCES tweetpoll(tweet_poll_id);


--
-- TOC entry 2527 (class 2606 OID 16821)
-- Name: fkc2760edbe4669675; Type: FK CONSTRAINT; Schema: public; Owner: jpicado
--

ALTER TABLE ONLY access_rate
    ADD CONSTRAINT fkc2760edbe4669675 FOREIGN KEY (user_uid) REFERENCES useraccount(uid);


--
-- TOC entry 2570 (class 2606 OID 17036)
-- Name: fkc5911cee2b2a6ab4; Type: FK CONSTRAINT; Schema: public; Owner: jpicado
--

ALTER TABLE ONLY poll_folder
    ADD CONSTRAINT fkc5911cee2b2a6ab4 FOREIGN KEY (uid) REFERENCES account(uid);


--
-- TOC entry 2571 (class 2606 OID 17041)
-- Name: fkc5911cee6ef241e9; Type: FK CONSTRAINT; Schema: public; Owner: jpicado
--

ALTER TABLE ONLY poll_folder
    ADD CONSTRAINT fkc5911cee6ef241e9 FOREIGN KEY (createdby_uid) REFERENCES useraccount(uid);


--
-- TOC entry 2580 (class 2606 OID 17086)
-- Name: fkc7652dd945895aff; Type: FK CONSTRAINT; Schema: public; Owner: jpicado
--

ALTER TABLE ONLY project_group
    ADD CONSTRAINT fkc7652dd945895aff FOREIGN KEY (sec_id_group) REFERENCES groups(group_id);


--
-- TOC entry 2581 (class 2606 OID 17091)
-- Name: fkc7652dd984536452; Type: FK CONSTRAINT; Schema: public; Owner: jpicado
--

ALTER TABLE ONLY project_group
    ADD CONSTRAINT fkc7652dd984536452 FOREIGN KEY (cat_id_project) REFERENCES project(project_id);


--
-- TOC entry 2631 (class 2606 OID 17341)
-- Name: fkd499a4b651153812; Type: FK CONSTRAINT; Schema: public; Owner: jpicado
--

ALTER TABLE ONLY tweetpoll_save_published_status
    ADD CONSTRAINT fkd499a4b651153812 FOREIGN KEY (survey_sid) REFERENCES surveys(sid);


--
-- TOC entry 2629 (class 2606 OID 17331)
-- Name: fkd499a4b65239d117; Type: FK CONSTRAINT; Schema: public; Owner: jpicado
--

ALTER TABLE ONLY tweetpoll_save_published_status
    ADD CONSTRAINT fkd499a4b65239d117 FOREIGN KEY (socialaccount_social_account_id) REFERENCES social_account(social_account_id);


--
-- TOC entry 2630 (class 2606 OID 17336)
-- Name: fkd499a4b663976e9; Type: FK CONSTRAINT; Schema: public; Owner: jpicado
--

ALTER TABLE ONLY tweetpoll_save_published_status
    ADD CONSTRAINT fkd499a4b663976e9 FOREIGN KEY (poll_poll_id) REFERENCES poll(poll_id);


--
-- TOC entry 2632 (class 2606 OID 17346)
-- Name: fkd499a4b6953c854b; Type: FK CONSTRAINT; Schema: public; Owner: jpicado
--

ALTER TABLE ONLY tweetpoll_save_published_status
    ADD CONSTRAINT fkd499a4b6953c854b FOREIGN KEY (tweetpoll_tweet_poll_id) REFERENCES tweetpoll(tweet_poll_id);


--
-- TOC entry 2588 (class 2606 OID 17126)
-- Name: fkd540d01f46bf7a1c; Type: FK CONSTRAINT; Schema: public; Owner: jpicado
--

ALTER TABLE ONLY question_preferences
    ADD CONSTRAINT fkd540d01f46bf7a1c FOREIGN KEY (question_qid) REFERENCES questions(qid);


--
-- TOC entry 2574 (class 2606 OID 17056)
-- Name: fkd981c89dddd118b5; Type: FK CONSTRAINT; Schema: public; Owner: jpicado
--

ALTER TABLE ONLY poll_result
    ADD CONSTRAINT fkd981c89dddd118b5 FOREIGN KEY (q_answer_id) REFERENCES questions_answers(q_answer_id);


--
-- TOC entry 2575 (class 2606 OID 17061)
-- Name: fkd981c89df0ed6769; Type: FK CONSTRAINT; Schema: public; Owner: jpicado
--

ALTER TABLE ONLY poll_result
    ADD CONSTRAINT fkd981c89df0ed6769 FOREIGN KEY (poll_id) REFERENCES poll(poll_id);


--
-- TOC entry 2536 (class 2606 OID 16866)
-- Name: fkdc17ddf4793d9e77; Type: FK CONSTRAINT; Schema: public; Owner: jpicado
--

ALTER TABLE ONLY comments
    ADD CONSTRAINT fkdc17ddf4793d9e77 FOREIGN KEY (sid) REFERENCES surveys(sid);


--
-- TOC entry 2537 (class 2606 OID 16871)
-- Name: fkdc17ddf4ce12cae8; Type: FK CONSTRAINT; Schema: public; Owner: jpicado
--

ALTER TABLE ONLY comments
    ADD CONSTRAINT fkdc17ddf4ce12cae8 FOREIGN KEY (pollid) REFERENCES poll(poll_id);


--
-- TOC entry 2538 (class 2606 OID 16876)
-- Name: fkdc17ddf4d9aa8e98; Type: FK CONSTRAINT; Schema: public; Owner: jpicado
--

ALTER TABLE ONLY comments
    ADD CONSTRAINT fkdc17ddf4d9aa8e98 FOREIGN KEY (tweetpollid) REFERENCES tweetpoll(tweet_poll_id);


--
-- TOC entry 2535 (class 2606 OID 16861)
-- Name: fkdc17ddf4f44558e9; Type: FK CONSTRAINT; Schema: public; Owner: jpicado
--

ALTER TABLE ONLY comments
    ADD CONSTRAINT fkdc17ddf4f44558e9 FOREIGN KEY (uid) REFERENCES useraccount(uid);


--
-- TOC entry 2577 (class 2606 OID 17071)
-- Name: fked904b194075e3fd; Type: FK CONSTRAINT; Schema: public; Owner: jpicado
--

ALTER TABLE ONLY project
    ADD CONSTRAINT fked904b194075e3fd FOREIGN KEY (users_uid) REFERENCES account(uid);


--
-- TOC entry 2576 (class 2606 OID 17066)
-- Name: fked904b19514c1986; Type: FK CONSTRAINT; Schema: public; Owner: jpicado
--

ALTER TABLE ONLY project
    ADD CONSTRAINT fked904b19514c1986 FOREIGN KEY (lead_uid) REFERENCES useraccount(uid);


--
-- TOC entry 2550 (class 2606 OID 16936)
-- Name: fkf4a1d3ee2b2a6ab4; Type: FK CONSTRAINT; Schema: public; Owner: jpicado
--

ALTER TABLE ONLY geopoint_folder
    ADD CONSTRAINT fkf4a1d3ee2b2a6ab4 FOREIGN KEY (uid) REFERENCES account(uid);


--
-- TOC entry 2552 (class 2606 OID 16946)
-- Name: fkf4a1d3ee6e4ed46d; Type: FK CONSTRAINT; Schema: public; Owner: jpicado
--

ALTER TABLE ONLY geopoint_folder
    ADD CONSTRAINT fkf4a1d3ee6e4ed46d FOREIGN KEY (sublocationfolder_locate_folder_id) REFERENCES geopoint_folder(locate_folder_id);


--
-- TOC entry 2551 (class 2606 OID 16941)
-- Name: fkf4a1d3ee6ef241e9; Type: FK CONSTRAINT; Schema: public; Owner: jpicado
--

ALTER TABLE ONLY geopoint_folder
    ADD CONSTRAINT fkf4a1d3ee6ef241e9 FOREIGN KEY (createdby_uid) REFERENCES useraccount(uid);


--
-- TOC entry 2597 (class 2606 OID 17171)
-- Name: fkf66bc0ad1366e48e; Type: FK CONSTRAINT; Schema: public; Owner: jpicado
--

ALTER TABLE ONLY scheduled
    ADD CONSTRAINT fkf66bc0ad1366e48e FOREIGN KEY (tpoll_tweet_poll_id) REFERENCES tweetpoll(tweet_poll_id);


--
-- TOC entry 2599 (class 2606 OID 17181)
-- Name: fkf66bc0ad51153812; Type: FK CONSTRAINT; Schema: public; Owner: jpicado
--

ALTER TABLE ONLY scheduled
    ADD CONSTRAINT fkf66bc0ad51153812 FOREIGN KEY (survey_sid) REFERENCES surveys(sid);


--
-- TOC entry 2596 (class 2606 OID 17166)
-- Name: fkf66bc0ad5239d117; Type: FK CONSTRAINT; Schema: public; Owner: jpicado
--

ALTER TABLE ONLY scheduled
    ADD CONSTRAINT fkf66bc0ad5239d117 FOREIGN KEY (socialaccount_social_account_id) REFERENCES social_account(social_account_id);


--
-- TOC entry 2598 (class 2606 OID 17176)
-- Name: fkf66bc0ad63976e9; Type: FK CONSTRAINT; Schema: public; Owner: jpicado
--

ALTER TABLE ONLY scheduled
    ADD CONSTRAINT fkf66bc0ad63976e9 FOREIGN KEY (poll_poll_id) REFERENCES poll(poll_id);


--
-- TOC entry 2595 (class 2606 OID 17161)
-- Name: fkf66bc0ad9c14a5e7; Type: FK CONSTRAINT; Schema: public; Owner: jpicado
--

ALTER TABLE ONLY scheduled
    ADD CONSTRAINT fkf66bc0ad9c14a5e7 FOREIGN KEY (tpollsavedpublished_status_save_poll_id) REFERENCES tweetpoll_save_published_status(status_save_poll_id);


--
-- TOC entry 2633 (class 2606 OID 17351)
-- Name: fkf8c717d6286705d7; Type: FK CONSTRAINT; Schema: public; Owner: jpicado
--

ALTER TABLE ONLY tweetpoll_hashtags
    ADD CONSTRAINT fkf8c717d6286705d7 FOREIGN KEY (tweetpoll_id) REFERENCES tweetpoll(tweet_poll_id);


--
-- TOC entry 2634 (class 2606 OID 17356)
-- Name: fkf8c717d6da98ffe1; Type: FK CONSTRAINT; Schema: public; Owner: jpicado
--

ALTER TABLE ONLY tweetpoll_hashtags
    ADD CONSTRAINT fkf8c717d6da98ffe1 FOREIGN KEY (hastag_id) REFERENCES hash_tags(hash_tag_id);


--
-- TOC entry 2647 (class 2606 OID 17423)
-- Name: fkfbc45bbc5f77a117; Type: FK CONSTRAINT; Schema: public; Owner: jpicado
--

ALTER TABLE ONLY useraccount_project
    ADD CONSTRAINT fkfbc45bbc5f77a117 FOREIGN KEY (sec_id_secondary) REFERENCES useraccount(uid);


--
-- TOC entry 2646 (class 2606 OID 17418)
-- Name: fkfbc45bbc84536452; Type: FK CONSTRAINT; Schema: public; Owner: jpicado
--

ALTER TABLE ONLY useraccount_project
    ADD CONSTRAINT fkfbc45bbc84536452 FOREIGN KEY (cat_id_project) REFERENCES project(project_id);


--
-- TOC entry 2606 (class 2606 OID 17216)
-- Name: fkfd028d3484536452; Type: FK CONSTRAINT; Schema: public; Owner: jpicado
--

ALTER TABLE ONLY survey_group_project
    ADD CONSTRAINT fkfd028d3484536452 FOREIGN KEY (cat_id_project) REFERENCES project(project_id);


--
-- TOC entry 2607 (class 2606 OID 17221)
-- Name: fkfd028d34b75f3482; Type: FK CONSTRAINT; Schema: public; Owner: jpicado
--

ALTER TABLE ONLY survey_group_project
    ADD CONSTRAINT fkfd028d34b75f3482 FOREIGN KEY (id_sid_format) REFERENCES survey_group(sg_id);


--
-- TOC entry 2615 (class 2606 OID 17261)
-- Name: fkfe5ad30051153812; Type: FK CONSTRAINT; Schema: public; Owner: jpicado
--

ALTER TABLE ONLY survey_section
    ADD CONSTRAINT fkfe5ad30051153812 FOREIGN KEY (survey_sid) REFERENCES surveys(sid);


