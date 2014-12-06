/*
 *
 *  * Copyright 2014 encuestame
 *  *
 *  *  Licensed under the Apache License, Version 2.0 (the "License");
 *  *  you may not use this file except in compliance with the License.
 *  *  You may obtain a copy of the License at
 *  *
 *  *       http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  *  Unless required by applicable law or agreed to in writing, software
 *  *  distributed under the License is distributed on an "AS IS" BASIS,
 *  *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  *  See the License for the specific language governing permissions and
 *  *  limitations under the License.
 *
 */

package org.encuestame.mvc.test.view;

import org.encuestame.mvc.test.config.AbstractFeedMvc;
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.utils.categories.test.DefaultTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

/**
 * Created by jpicado on 06/12/14.
 */

@Category(DefaultTest.class)
public class SyndicationControllerTestCase extends AbstractFeedMvc {

    UserAccount user;

    @org.junit.Before
    public void start(){
        this.user = this.quickLogin();
        createFakesTweetPoll(user);
    }

    @Test
    public void testHomeAtom() throws Exception {
        getRss("/feed/home.atom", "frontEndAtomFeedView");
    }

    @Test
    public void testHomeRss() throws Exception {
        getRss("/feed/home.rss", "frontEndRssFeedView");
    }

    @Test
    public void testtweetPollAtom() throws Exception {
        getRss("/feed/tweetpoll.atom", "tweetPollAtomFeedView");
    }

    @Test
    public void testtweetPollRss() throws Exception {
        getRss("/feed/tweetpoll.rss", "tweetPollRssFeedView");
    }

    @Test
    public void testSurveyRss() throws Exception {
        getRss("/feed/survey.rss", "surveyRssFeedView");
    }

    @Test
    public void testSurveyAtom() throws Exception {
        getRss("/feed/survey.atom", "surveyAtomFeedView");
    }

    @Test
    public void testPollRss() throws Exception {
        getRss("/feed/poll.rss", "pollRssFeedView");
    }

    @Test
    public void testPollAtom() throws Exception {
        getRss("/feed/poll.atom", "pollAtomFeedView");
    }

    @Test
    public void testUserPollAtom() throws Exception {
        getRss("/feed/{username}/poll.atom", user.getUsername(), null);
    }

    @Test
    public void testUserPollRss() throws Exception {
        getRss("/feed/{username}/poll.rss", user.getUsername(), null);
    }

    @Test
    public void testUserTPollRss() throws Exception {
        getRss("/feed/{username}/tweetpoll.rss", user.getUsername(), null);
    }

    @Test
    public void testUserTPollAtom() throws Exception {
        getRss("/feed/{username}/tweetpoll.atom", user.getUsername(), null);
    }

    @Test
    public void testUserProfilelRss() throws Exception {
        getRss("/feed/{username}/profile.rss", user.getUsername(), null);
    }

    @Test
    public void testUserProfileAtom() throws Exception {
        getRss("/feed/{username}/profile.atom", user.getUsername(), null);
    }

    @Test
    public void testUserSurveyRss() throws Exception {
        getRss("/feed/{username}/survey.rss", user.getUsername(), null);
    }

    @Test
    public void testUserSurveyAtom() throws Exception {
        getRss("/feed/{username}/survey.atom", user.getUsername(), null);
    }

    //wrong items

    @Test
    public void testUserPollAtomWrong() throws Exception {
        getRss("/feed/{username}/poll.atom", "Xxxxxx", null);
    }

    @Test
    public void testUserPollRssWrong() throws Exception {
        getRss("/feed/{username}/poll.rss", "Xxxxxx", null);
    }

    @Test
    public void testUserTPollRssWrong() throws Exception {
        getRss("/feed/{username}/tweetpoll.rss", "Xxxxxx", null);
    }

    @Test
    public void testUserTPollAtomWrong() throws Exception {
        getRss("/feed/{username}/tweetpoll.atom", "Xxxxxx", null);
    }

    @Test
    public void testUserProfilelRssWrong() throws Exception {
        getRss("/feed/{username}/profile.rss", "Xxxxxx", null);
    }

    @Test
    public void testUserProfileAtomWrong() throws Exception {
        getRss("/feed/{username}/profile.atom", "Xxxxxx", null);
    }

    @Test
    public void testUserSurveyRssWrong() throws Exception {
        getRss("/feed/{username}/survey.rss", "Xxxxxx", null);
    }

    @Test
    public void testUserSurveyAtomWrong() throws Exception {
        getRss("/feed/{username}/survey.atom", "Xxxxxx", null);
    }}
